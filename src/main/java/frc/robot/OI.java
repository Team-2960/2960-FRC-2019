package frc.robot;

import frc.robot.Motors.Drive;
import edu.wpi.first.wpilibj.Joystick;
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


    public void driverControl(Joystick driver_control){
       switch_angle = drive.switch_GotoAngle;
        if(driver_control.getRawButton(1)){
          //  drive.Gyro1.reset();
         //   drive.setAngle(90);
            switch_angle = true;
            drive.switchTarget();
        }
        if(!switch_angle){
            drive.setSpeed(driver_control.getRawAxis(1), driver_control.getRawAxis(5));
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
        if(operator_control.getRawAxis(2) > 0.1 && operator_control.getRawAxis(3) > 0.1) { //up
            climb.SetSpeed(1);
        }
        else if(operator_control.getRawButton(5) && operator_control.getRawButton(6)){ //down 
            climb.SetSpeed(-1);
        }   
        else{
            climb.SetSpeed(0);
        }

        //clamper control
        if(operator_control.getRawButton(2)){
            climb.setClamp(true);
        }
        else if(operator_control.getRawButton(3)){
            climb.setClamp(false);
        }

        //sPucher
        if(operator_control.getRawButton(1)){
            climb.setPusher(true);
        }
        else{
            climb.setPusher(false);
        }

        //arm control
            arm.SetSpeed(operator_control.getRawAxis(1)); 
    }
}