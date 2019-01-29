package frc.robot;

import frc.robot.Motors.drive;


import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Motors.Intake;
import frc.robot.Motors.Arm;
import frc.robot.Motors.Climb;

public class OI {
    private Joystick control1 = new Joystick(0);
    private drive Left_And_Right = drive.getInstance();
    private Intake Ballintake = Intake.getInstance();
    private Intake Wristintake = Intake.getInstance();
    private Climb RobotClimb = Climb.getInstance();
    public OI(){

    }

    public void mdrive(){
        Left_And_Right.SetSpeed(control1.getRawAxis(5), control1.getRawAxis(1));
    }
    
    public void mBall(){
       //todo set a botton for ball intake and outtake 
        if(control1.getRawButton(1)){
            Ballintake.SetSpeedBall(1);
        }
        else if(control1.getRawButton(0)){
            Ballintake.SetSpeedBall(-1);
        }
        else{
            Ballintake.SetSpeedBall(0);
        }
    }
    public void mWrist(){
        if(control1.getRawButton(0)){
            Wristintake.SetSpeedWrist(1);
        }
        else if(control1.getRawButton(0)){
            Wristintake.SetSpeedWrist(-1);
        }   
        else{
            Wristintake.SetSpeedWrist(0);
        }  
    }
        //todo set a botton for wrist intake and outtake 
    public void mClimb(){
        if(control1.getRawButton(0)){
            RobotClimb.SetSpeed(1, 1);
        }
        else if(control1.getRawButton(0)){
            RobotClimb.SetSpeed(-1, -1);
        }   
        else{
            RobotClimb.SetSpeed(0, 0);
        }
    }
}