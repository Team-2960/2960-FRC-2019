
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

import javax.management.loading.PrivateMLet;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.Motors.Drive;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.vision.VisionThread;
import frc.robot.Camera.Camera;
import frc.robot.Camera.GripPipeline;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.AnalogGyro;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import edu.wpi.first.wpilibj.Ultrasonic;


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
  private Camera hatchCamera;
  public Ultrasonic Ultrasonic1 = new Ultrasonic(0, 1);
  public Ultrasonic Ultrasonic2 = new Ultrasonic(8, 9);
  private Drive drive = Drive.getInstance();
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    oi = new OI();
    hatchCamera = new Camera(0);
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    
    SmartDashboard.putNumber("HUE min", Constants.HUEmin);
    SmartDashboard.putNumber("HUE max", Constants.HUEmax);
    SmartDashboard.putNumber("Saturation min", Constants.Saturationmin);
    SmartDashboard.putNumber("Saturation max", Constants.Saturationmax);
    SmartDashboard.putNumber("HSValue min", Constants.HSValuemin);
    SmartDashboard.putNumber("HSValue max", Constants.HSValuemax);
    
    SmartDashboard.putNumber("ultrasonic1", Ultrasonic1.getRangeInches());
    SmartDashboard.putNumber("ultrasonic2", Ultrasonic2.getRangeInches());

    SmartDashboard.putNumber("gyro4", drive.returnAngle());






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
    cameraConfig();
    drive.update();
    oi.driverControl(driver); // driver control 
    oi.operatorControl(operator); // operator control
   // oi.Gyro(driver);
   SmartDashboard.putNumber("gyro4", drive.returnAngle());
  
   SmartDashboard.putNumber("ultrasonic1", Ultrasonic1.getRangeInches());
   SmartDashboard.putNumber("ultrasonic2", Ultrasonic2.getRangeInches());
  }

  public void cameraConfig(){  //config camear value in smardashboard
    Constants.HUEmin = NetworkTable.getTable("SmartDashboard").getDouble("HUE min", 0);
    Constants.HUEmax= NetworkTable.getTable("SmartDashboard").getDouble("HUE max", 0);
    Constants.Saturationmin = NetworkTable.getTable("SmartDashboard").getDouble("Saturation min", 0);
    Constants.Saturationmax = NetworkTable.getTable("SmartDashboard").getDouble("Saturation max", 0);
    Constants.HSValuemin = NetworkTable.getTable("SmartDashboard").getDouble("HSValue min", 0);
    Constants.HSValuemax = NetworkTable.getTable("SmartDashboard").getDouble("HSValue max", 0);
    SmartDashboard.putNumber("HUE min", Constants.HUEmin);
    SmartDashboard.putNumber("HUE max", Constants.HUEmax);
    SmartDashboard.putNumber("HSValue min", Constants.HSValuemin);
    SmartDashboard.putNumber("HSValue max", Constants.HSValuemax);
    SmartDashboard.putNumber("Saturationmax", Constants.Saturationmax);
    SmartDashboard.putNumber("Saturation min", Constants.Saturationmin);
 }
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
