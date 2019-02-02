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
import java.util.concurrent.locks.Lock;

public class Camera{
	
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	
	private Rect visionTargets[] = new Rect[2]; 

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

		//Camera output to smartdash board
		hsv_threashold_source = CameraServer.getInstance().putVideo("HSV Threshold", IMG_WIDTH, IMG_HEIGHT);
		erode_source = CameraServer.getInstance().putVideo("Erode", IMG_WIDTH, IMG_HEIGHT);

		startThread();
	}
	
	private void startThread(){
	
		visionThread = new Thread(() -> {
			GripPipeline pipeline = new GripPipeline();
			Mat cam_frame = new Mat();

			while(!Thread.interrupted()){
				System.out.println("Getting image");
				long result = cam_sink.grabFrameNoTimeout(cam_frame);
				
				//Check whether we received an image
				if(result == 0){
					System.out.println(cam_sink.getError());
				}else{
					//Use grip code to process image
					pipeline.process(cam_frame);
					
					//Find countors in image
					if (!pipeline.filterContoursOutput().isEmpty()){
						synchronized(visionTargets){
							visionTargets[0] = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
							//Is there a second contour?
							if (pipeline.filterContoursOutput().size() > 1)
								visionTargets[1] = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));

						}
						double center = ((visionTargets[0].x + visionTargets[0].width/2) + (visionTargets[1].x + visionTargets[1].width/2)) / 2;
						System.out.println("Center: " + center);
						
						
						MatOfPoint2f testMat = new MatOfPoint2f(pipeline.filterContoursOutput().get(1).toArray());
						RotatedRect rectest = Imgproc.minAreaRect(testMat);
						System.out.println("Target Angle" + rectest.angle);

						MatOfPoint2f testMat2 = new MatOfPoint2f(pipeline.filterContoursOutput().get(0).toArray());
						RotatedRect rectest2 = Imgproc.minAreaRect(testMat2);
						System.out.println("Target Angle 2" + rectest2.angle);

						MatOfPoint2f testMat3 = new MatOfPoint2f(pipeline.filterContoursOutput().get(2).toArray());
						RotatedRect rectest3 = Imgproc.minAreaRect(testMat3);
						System.out.println("Target Angle 3" + rectest3.angle);

					}else{
						System.out.println("No Contours");
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
	
	private Rect[] getResult() {
		//Get results from vision thread -- This will change. 
		synchronized(visionTargets){
			return visionTargets;
		}
	}
	



}