package frc.robot;

import frc.robot.Motors.Drive;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Motors.Intake;
import frc.robot.Motors.Arm;
import frc.robot.Motors.Climb;
import frc.robot.Sensors.Gyro;

public class OI {
    private Drive drive = Drive.getInstance();
    private Intake intake = Intake.getInstance();
    private Climb climb = Climb.getInstance();
    public OI(){

    }

    public void driverControl(Joystick driver_control){
        drive.setSpeed(-driver_control.getRawAxis(1), driver_control.getRawAxis(5));
        Gyro.AngleTurn(driver_control.getRawButton(12));
    }
    
    public void operatorControl(Joystick operator_control){
        //Ball intake contol 
        if(operator_control.getRawButton(1)){       //Ball Intake
            intake.SetSpeedBall(1);
        }else if(operator_control.getRawButton(0)){ //Ball Output
            intake.SetSpeedBall(-1);
        }else{
            intake.SetSpeedBall(0);
        }

        //Wrist control
        if(operator_control.getRawButton(2)){ //Up
            intake.SetSpeedWrist(1);
        }
        else if(operator_control.getRawButton(3)){ //Down
            intake.SetSpeedWrist(-1);
        }   
        else{
            intake.SetSpeedWrist(0);
        }  

        //Climber control
        if(operator_control.getRawButton(2)){
            climb.SetSpeed(1, 1);
        }
        else if(operator_control.getRawButton(3)){
            climb.SetSpeed(-1, -1);
        }   
        else{
            climb.SetSpeed(0, 0);
        }
    }
}