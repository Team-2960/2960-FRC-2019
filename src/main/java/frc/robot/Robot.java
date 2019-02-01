
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.OI;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.vision.VisionThread;
import frc.robot.Camera.GripPipeline;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends IterativeRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private Joystick driver = new Joystick(0);
  private Joystick operator = new Joystick(1);
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private OI oi;
  private final int width = 320;
  private final int height = 240;
  private VisionThread webcam_thread;
  private final Object IMG_lcok = new Object();
  private double CenterX = 0.0;
  GripPipeline pipeline = new GripPipeline();
  UsbCamera camera;
  CvSink cam_sink;
  Mat output;
  
  CvSource hsv_threashold_source;
  CvSource erode_source;
  
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    oi = new OI();

    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    camera = CameraServer.getInstance().startAutomaticCapture(0);
    camera.setResolution(width, height);

    cam_sink = CameraServer.getInstance().getVideo();

    hsv_threashold_source = CameraServer.getInstance().putVideo("HSV Threshold", width, height);
    erode_source = CameraServer.getInstance().putVideo("Erode", width, height);

    output = new Mat();

/* 
    webcam_thread = new VisionThread(webcam, new GripPipeline(), pipeline-> {
        SmartDashboard.putNumber("webcam boxes", pipeline.filterContoursOutput().size());
      if(!pipeline.filterContoursOutput().isEmpty()){
        Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
        SmartDashboard.putNumber("webcan 2", r.x);
        synchronized (IMG_lcok){
          System.out.println("here");
          CenterX = r.x + (r.width/2);
        }
      }
    });
    webcam_thread.start();
*/
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // autoSelected = SmartDashboard.getString("Auto Selector",
    // defaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    
    double CenterX2 = 0.0;
   
   /*
    synchronized (IMG_lcok){
      CenterX2 = this.CenterX;
      System.out.println(CenterX2);
    }
    SmartDashboard.putNumber("webcam", CenterX2);
   */
    long result = cam_sink.grabFrameNoTimeout(output);
    //long result = cam_sink.grabFrame(output);
    System.out.println("Image Test Start");
    System.out.println(result);



    if(result == 0){
      System.out.println(cam_sink.getError());
    }
    else{
      System.out.println(output.size().width);
      System.out.println(output.size().height);
      System.out.println("Image Test End");
      pipeline.process(output);
      System.out.println("Start Filter");
      if (!pipeline.filterContoursOutput().isEmpty()){
        Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
        System.out.println(r.x);
        System.out.println(r.y);
        System.out.println("hello");
      }
      else{
        System.out.println("no Contours");
      }
      
      hsv_threashold_source.putFrame(pipeline.hsvThresholdOutput());
      erode_source.putFrame(pipeline.cvErodeOutput());
    }

    oi.driverControl(driver);
    oi.operatorControl(operator);
    
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
