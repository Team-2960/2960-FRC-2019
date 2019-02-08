package frc.robot;

import frc.robot.Motors.Drive;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Motors.Intake;
import frc.robot.Motors.Arm;
import frc.robot.Motors.Climb;
import edu.wpi.first.wpilibj.AnalogGyro;



public class OI {
    private Drive drive = Drive.getInstance();
    private Intake intake = Intake.getInstance();
    private Climb climb = Climb.getInstance();
    private Arm arm = Arm.getInstance();

    //gyro
    private AnalogGyro gyro1 = new AnalogGyro(1);
    private double Angle = gyro1.getAngle();
    private int error = 5;
    public OI(){
    }
    //gryo goto angle
    public void Gyro(){
        if(Angle < 180 + error){
            drive.Gyro(0.5, 0.5);
        }
        else if(Angle > 180 + error){
            drive.Gyro(0.5, -0.5);
        }
        else{
            drive.Gyro(0, 0);
            gyro1.reset();
        }

    }


    public void driverControl(Joystick driver_control){
        drive.setSpeed(-driver_control.getRawAxis(1), driver_control.getRawAxis(5));
       //gyro control
        if (driver_control.getRawButton(5)){
            Gyro();
        }
        else{}
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
        if(operator_control.getRawButton(4)){ //up
            climb.SetSpeed(1, 1);
        }
        else if(operator_control.getRawButton(5)){ //down 
            climb.SetSpeed(-1, -1);
        }   
        else{
            climb.SetSpeed(0, 0);
        }
        //arm control
        if(operator_control.getRawButton(5)){  //up
            arm.SetSpeed(1, 1); 
        }
        else if(operator_control.getRawButton(6)){  //down
            arm.SetSpeed(-1, -1);
        }
        else{
            arm.SetSpeed(0, 0);
        }
    }
}