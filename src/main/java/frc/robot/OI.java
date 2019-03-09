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

    //switch on or off
    private boolean switch_angle = false; 
    private boolean DvSwitch = false;
   

    public OI(){

    }

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
        //start fun codd
        if(driver_control.getRawButton(8)) DvSwitch = true; 
        //stop fun code
        else if(driver_control.getRawButton(7)){
            DvSwitch = false;
            driver_control.setRumble(RumbleType.kLeftRumble, 0);
            driver_control.setRumble(RumbleType.kRightRumble, 0);
        }

        if(!switch_angle){
            drive.setSpeed(driver_control.getRawAxis(5), driver_control.getRawAxis(1));
           
           //fun code
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
        //Climber control
        if(operator_control.getPOV(0) == 0) { //up
            climb.SetSpeed(1);
        }
        else if(operator_control.getPOV(0) == 180) { //down 
            climb.SetSpeed(-1);
        }   
        else{
            if(!climb.AutoDepoly_Switch){
            climb.SetSpeed(0);
            }
        }

        //clamper control
        if(operator_control.getRawButton(5)){
            climb.setClamp(true);
        }
        else if(operator_control.getRawButton(6)){
            climb.setClamp(false);
        }
        

        //sPusher
        if(operator_control.getRawButton(7)){
            arm.setPusher(true);
        }
        else if(operator_control.getRawButton(8)){
            arm.setPusher(false);
        }

        //arm control
        if(!arm.isArmPIDEnabld()) 
            arm.SetSpeed(operator_control.getRawAxis(1));
        
        if(operator_control.getRawButton(3)){
            arm.disableArmPID();
            arm.disableWristPID();
        }
        if(operator_control.getPOV(0) == 90){
            arm.startArmPID(-75);
            arm.startWristPID(20);
        } 
        else if(operator_control.getRawButton(2)){
            arm.startArmPID(0);
            arm.startWristPID(70);
        }
        else if(operator_control.getRawButton(4)){
            arm.startArmPID(-32);
            arm.startWristPID(-20);
        }

        if(operator_control.getRawAxis(1) > 0.2 || operator_control.getRawAxis(1) < -0.2) 
            arm.disableArmPID();
    
        //wrist
        if(operator_control.getRawAxis(3) > 0.2){
            arm.WristPIDPosition(2);
        }
        else if(operator_control.getRawAxis(2) > 0.2){
            arm.WristPIDPosition(-2);
        }




        /* if(!arm.isWristPIDEnabld()){
            arm.SetSpeedWrist(operator_control.getRawAxis(5));
        } */
        //if(operator_control.getRawAxis(5) > 0.2 || operator_control.getRawAxis(5) < -0.2) arm.disableWristPID();
        
        if(operator_control.getRawButton(7)) climb.Start_autoDepoly();
    }
}