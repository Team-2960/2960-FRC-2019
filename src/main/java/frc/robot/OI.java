package frc.robot;
import java.lang.Math;

import frc.robot.Motors.Drive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Motors.Intake;
import frc.robot.Motors.Arm;
import frc.robot.Motors.Climb;

public class OI {
    private Drive drive = Drive.getInstance();
    private Intake intake = Intake.getInstance();
    private Climb climb = Climb.getInstance();
    private Arm arm = Arm.getInstance();
    private boolean switch_angle = false; 
    public OI(){}
    private boolean DvSwitch = false;
   


    public void driverControl(Joystick driver_control){
        double rRumble = java.lang.Math.abs(driver_control.getRawAxis(5));
        double lRumble = java.lang.Math.abs(driver_control.getRawAxis(1));

       switch_angle = drive.switch_GotoTarget;
       if(driver_control.getPOV(0) == 0){
            drive.switch_GotoTarget = false;
        }
        if(driver_control.getRawButton(1)){
          //  drive.Gyro1.reset();
         //   drive.setAngle(90);
            switch_angle = true;
            drive.switchTarget();  
        }
        if(driver_control.getRawButton(8)){DvSwitch = true;
        } 
        else if(driver_control.getRawButton(7)){
            DvSwitch = false;
            driver_control.setRumble(RumbleType.kLeftRumble, 0);
            driver_control.setRumble(RumbleType.kRightRumble, 0);
        }
        if(!switch_angle){
            drive.setSpeed(driver_control.getRawAxis(5), driver_control.getRawAxis(1));
           
           
           if(DvSwitch){
                if(driver_control.getRawAxis(5) > 0.11){
                    driver_control.setRumble(RumbleType.kRightRumble, rRumble);
                }
                else if(driver_control.getRawAxis(5) < -0.11){
                    driver_control.setRumble(RumbleType.kRightRumble, rRumble);
                }
                else{
                    driver_control.setRumble(RumbleType.kRightRumble, 0);
                }
                if(driver_control.getRawAxis(1) > 0.11){
                    driver_control.setRumble(RumbleType.kRightRumble, lRumble);
                }
            else if(driver_control.getRawAxis(1) < -0.11){
                driver_control.setRumble(RumbleType.kRightRumble, lRumble);
                }
                else{
                    driver_control.setRumble(RumbleType.kRightRumble, 0);
                }
            }

        }
       
        //Ball intake contol 
        if(driver_control.getRawAxis(2) > 0.1){
           intake.SetSpeedBall(1);
        }
        else if(driver_control.getRawAxis(3) > 0.1){
           intake.SetSpeedBall(-1);
        }
        else{
            intake.SetSpeedBall(0);  
        }

        //hatch control
        if(driver_control.getRawButton(5)){
            intake.setHatch(false);
        }
        else if(driver_control.getRawButton(6)){
            intake.setHatch(true);
        }
    }

    public void operatorControl(Joystick operator_control){

        //wrist control
       // intake.startWristPID(2500 *-1);
            intake.SetSpeedWrist(operator_control.getRawAxis(5));
        
        //Climber control
        if(operator_control.getRawButton(1)) { //up
            climb.SetSpeed(1);
        }
        else if(operator_control.getRawButton(4)) { //down 
            climb.SetSpeed(-1);
        }   
        else{
            climb.SetSpeed(0);
        }

        //clamper control
        if(operator_control.getRawButton(5)){
            climb.setClamp(true);
        }
        else if(operator_control.getRawButton(6)){
            climb.setClamp(false);
        }
        

        //sPucher
        if(operator_control.getRawAxis(2) > 0.1){
            arm.setPusher(true);
        }
        else if(operator_control.getRawAxis(3) > 0.1){
            arm.setPusher(false);
        }

        //arm control
            //arm.SetSpeed(operator_control.getRawAxis(1));
            SmartDashboard.putNumber("joystick arm", operator_control.getRawAxis(1));
            if(operator_control.getRawButton(2)){
                arm.startArmPID(-40);
            } 
            else if(operator_control.getPOV(0) == 90){
                arm.startArmPID(-90);
            }
            else if(operator_control.getPOV(0) == 180){
                arm.startArmPID(-60);
            }
            else if(operator_control.getPOV(0) == 270){
                arm.startArmPID(-20);
            }
            else if(operator_control.getRawButton(3)){
                arm.disableArmPID();
            }
           
            if(operator_control.getPOV(0) == 0){
                arm.ArmEncoderReset();
            } 
        
        if(operator_control.getRawButton(2)) climb.Start_autoDepoly();
    }
}