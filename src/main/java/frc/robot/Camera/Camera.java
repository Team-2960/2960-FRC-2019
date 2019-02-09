package frc.robot.Camera;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.imgproc.Imgproc;

import java.lang.Math;

public class Camera{
	
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	
	private double visionTarget; 
	private Boolean targetFound;
	private Object IMG_LOCK;

	private UsbCamera camera;
	private CvSink cam_sink;
	private Thread visionThread;

	private CvSource hsv_threashold_source;
	private CvSource erode_source;
	

	public Camera(int cameraPort){  
		//Setup camera
		camera = CameraServer.getInstance().startAutomaticCapture(cameraPort);
		camera.setResolution(IMG_WIDTH, IMG_HEIGHT);

		//Get video from camera 
		cam_sink = CameraServer.getInstance().getVideo();

		IMG_LOCK = new Object();

		//Camera output to smartdash board
		hsv_threashold_source = CameraServer.getInstance().putVideo("HSV Threshold", IMG_WIDTH, IMG_HEIGHT);
		erode_source = CameraServer.getInstance().putVideo("Erode", IMG_WIDTH, IMG_HEIGHT);

		startThread();
	}
	
	private void startThread(){
	
		visionThread = new Thread(() -> {
			GripPipeline pipeline = new GripPipeline();
			Mat cam_frame = new Mat();
			Boolean lTargetFound;
			Double lVisionTarget;
			
			//Run this code as long as the thread is not interrupted
			while(!Thread.interrupted()){
				//reset target varibles
				lTargetFound = false;
				lVisionTarget = 0.0;

				//Grab image from camera
				long result = cam_sink.grabFrameNoTimeout(cam_frame);
				
				//Check whether we received an image
				if(result == 0){
					//System.out.println(cam_sink.getError());
					lTargetFound = false;
				}else{
					//Use grip code to process image
					pipeline.process(cam_frame);
					
					//Find countors in image
					if (!pipeline.filterContoursOutput().isEmpty()){
						int numBoxes = pipeline.filterContoursOutput().size();
						double [] RightTarget = new double[numBoxes];
						double [] LeftTarget = new double[numBoxes];
						double [][] matchTargets = new double[3][numBoxes];
						
						int rightCount = 0;
						int leftCount = 0;
						int matchCount = 0;
						
						//Find target angle and center point for each contour
						for(int count = 0; count < numBoxes; count++) {

							MatOfPoint2f tempMat = new MatOfPoint2f(pipeline.filterContoursOutput().get(count).toArray());
							RotatedRect tempAngle = Imgproc.minAreaRect(tempMat);
							Rect tempRec = Imgproc.boundingRect(pipeline.filterContoursOutput().get(count));
							//System.out.println("Target: " + count + " x: " + tempRec.x + " Angle: " + tempAngle.angle);
							
							//Is this a right target? Angle is about -14.
							//Or is this a left target? Angle is about -75.
							if(tempAngle.angle < 0 && tempAngle.angle > -45) {
								RightTarget[rightCount] = tempRec.x + tempRec.width/2;
								rightCount++;
							}else if(tempAngle.angle < -45 && tempAngle.angle > -100) {
								LeftTarget[leftCount] = tempRec.x + tempRec.width/2;
								leftCount++;
							}
						}
						
						//Match Left and Right Targets
						for(int iRight = 0; iRight < rightCount; iRight++){ //Loop thru right
							int match = -1;
							double lastDiffTarget = IMG_WIDTH;
							
							for(int iLeft = 0; iLeft < leftCount; iLeft++){ //Loop thru left
								//Difference between left and right target
								double diffTarget = RightTarget[iRight]-LeftTarget[iLeft];
								//Is the target of the left (positive value)?
								// Is it a closer than the last matching target?
								if(diffTarget > 0 && diffTarget < lastDiffTarget){
									lastDiffTarget = diffTarget;
									match = iLeft;
									//record pair of targets: right, left, center
									matchTargets[0][matchCount] = RightTarget[iRight];
									matchTargets[1][matchCount] = LeftTarget[iLeft];
									matchTargets[2][matchCount] = (RightTarget[iRight] + LeftTarget[iLeft])/2;
								}
							}
							if (match >= 0) matchCount++; 
						}
						
						double lastCenterDist = IMG_WIDTH;
						int centerTargets = -1;
						//Find the closest pair to the center of the target
						for(int i = 0; i <= matchCount; i++){
							//Find the absolute distance (allows postive) betwen target and center
							double tempCentDist = java.lang.Math.abs(matchTargets[2][i] - IMG_WIDTH/2);
							//Is this closer than the last pair?
							if (tempCentDist < lastCenterDist){
								lastCenterDist = tempCentDist;
								centerTargets = i;
							}
						}
				
						//Did we find a target? Record center value. 
						if (centerTargets >= 0){
							lVisionTarget = IMG_WIDTH/2 - matchTargets[2][centerTargets];
							lTargetFound = true;
						}

						System.out.println("Center Target Right: " + matchTargets[0][centerTargets]);
						System.out.println("Center Target Left: " + matchTargets[1][centerTargets]);
						System.out.println("Center Target Middle: " + matchTargets[2][centerTargets]); 
						

					}else{
						lTargetFound = false;
						//System.out.println("No Contours");
					}
					
					//System.out.println("Is target found:" + lTargetFound);
					//Allow main thread to access center
					synchronized(IMG_LOCK){
						targetFound = lTargetFound;
						visionTarget = lVisionTarget;
					}
					//Output to smartdash board - It may not like having this inside the thread
					hsv_threashold_source.putFrame(pipeline.hsvThresholdOutput());
					erode_source.putFrame(pipeline.cvErodeOutput());
				}
			}
        }
        );
		visionThread.start();
	}
	
	public double getImageResults() {
		//Get results from vision thread -- This will change. 
		synchronized(IMG_LOCK){
			return visionTarget;
		}
	}
	
	public Boolean isImageFound() {
		//Get results from vision thread -- This will change. 
		synchronized(IMG_LOCK){
			return targetFound;
		}
	}
	


}