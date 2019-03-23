
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
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Motors.Arm;
import frc.robot.Motors.Climb;
import frc.robot.Motors.Drive;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import frc.robot.Motors.Intake;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.lights;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends IterativeRobot{
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  //set joysticks
  private Joystick driver = new Joystick(0);
  private Joystick operator = new Joystick(1);

  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  
  private OI oi;

  private Compressor compressor = new Compressor(0);
 
  private Drive drive = Drive.getInstance();
  private Intake intake = Intake.getInstance();
  private Arm arm = Arm.getInstance();
  private Climb climb = Climb.getInstance();
  private lights Lights;
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    Lights = new lights();
    oi = new OI();

    compressor.setClosedLoopControl(true);

    //camera config
    SmartDashboard.putNumber("HUE min", Constants.HUEmin);
    SmartDashboard.putNumber("HUE max", Constants.HUEmax);
    SmartDashboard.putNumber("Saturation min", Constants.Saturationmin);
    SmartDashboard.putNumber("Saturation max", Constants.Saturationmax);
    SmartDashboard.putNumber("HSValue min", Constants.HSValuemin);
    SmartDashboard.putNumber("HSValue max", Constants.HSValuemax);
    
    //encoders
    SmartDashboard.putNumber("wrist distance", 0);
    SmartDashboard.putNumber("wrist rate", 0);
    SmartDashboard.putNumber("ArmEncoder Distance", 0);
    SmartDashboard.putNumber("ArmEncoder Rate", 0);
    SmartDashboard.putBoolean("Arm PID Enable", false);
    SmartDashboard.putBoolean("Wrist PID Enable", false);

    //Camera Target
    SmartDashboard.putBoolean("Target Aquired!", false);

    arm.eWrist.reset();
    intake.setHatch(true);
    arm.startArmPID(0);
    arm.startWristPID(0);
  }

  @Override
  public void robotPeriodic() {

    if(DriverStation.getInstance().isDisabled()){
      arm.disableArmPID();
      arm.disableWristPID();
    }
  }
  

  @Override
  public void autonomousInit() {
    arm.WristEncoderReset();
    arm.ArmEncoderReset();
    arm.startArmPID(0);
    arm.startWristPID(0);
  }
  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    drive.update();
    climb.update();
    oi.driverControl(driver); // driver control 
    oi.operatorControl(operator); // operator control
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
   // cameraConfig();
    //drive.update();
    //climb.update();
    //oi.driverControl(driver); // driver control 
    //oi.operatorControl(operator); // operator control
    Lights.sLights(DriverStation.getInstance().getAlliance());
    //arm.print();
  }

  public void cameraConfig(){  //config camera value in smardashboard
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
