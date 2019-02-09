package frc.robot;

import frc.robot.Motors.Drive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Motors.Intake;
import frc.robot.Motors.Arm;
import frc.robot.Motors.Climb;

import java.sql.Time;

import edu.wpi.first.wpilibj.AnalogGyro;



public class OI {
    private Drive drive = Drive.getInstance();
    private Intake intake = Intake.getInstance();
    private Climb climb = Climb.getInstance();
    private Arm arm = Arm.getInstance();
    private boolean hSwitch;

    //gyro
    public AnalogGyro gyro1 = new AnalogGyro(10);
    public double Angle = gyro1.getAngle();
    private int error = 3;
    private Boolean switch1 = false;
    public AnalogGyro Gyro1 = new AnalogGyro(0);;
    public OI(){
    }
    //gryo goto angle fontion
    public void Gyro(Joystick driver_control){
               //gyro control
               if(driver_control.getRawButtonPressed(5)){
                   switch1 = true;
                   Gyro1.reset();
               }
               else{
               }
            while(switch1){
                if(Gyro1.getAngle() < 180 - error){
                    drive.Gyro(0.5, 0.5);
                }
                else if(Gyro1.getAngle() > 180 + error){
                    drive.Gyro(-0.5, -0.5);
                }
                else{
                    Gyro1.reset();
                    switch1 = false;       
                }
            }
        }
   


    

    
    public void driverControl(Joystick driver_control){
        drive.setSpeed(-driver_control.getRawAxis(1), driver_control.getRawAxis(5));
        //Ball intake contol 
       if(driver_control.getRawAxis(2) > 0.1){
           intake.SetSpeedBall(1);
       }
       if(driver_control.getRawAxis(3) > 0.1){
           intake.SetSpeedBall(-1);
        }

        if(driver_control.getRawButton(5)){
            intake.SetHatch(false);
        }
        else if(driver_control.getRawButton(6)){
            intake.SetHatch(true);
        }
    }

    public void operatorControl(Joystick operator_control){


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