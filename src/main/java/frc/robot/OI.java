package frc.robot;

import frc.robot.Motors.drive;


import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Motors.Intake;
import frc.robot.Motors.Arm;
import frc.robot.Motors.Climb;

public class OI {
    private Joystick moving = new Joystick(0);
    private drive Left_And_Right = drive.getInstance();
    private Intake Ballintake = Intake.getInstance();
    private Intake Wristintake = Intake.getInstance();
    private Climb mClimb = Climb.getInstance();
    public OI(){

    }

    public void driveRobot(){
        Left_And_Right.SetSpeed(moving.getRawAxis(5), moving.getRawAxis(1));
    }
    
    public void inTake(){
       //todo set a botton for ball intake and outtake and else
        if(){
            Ballintake.SetSpeedBall(1);
        }
        else if(){
            Ballintake.SetSpeedBall(-1);
        }
        else{
            Ballintake.SetSpeedBall(0);
        }
        //todo set a botton for wrist intake and outtake and else
        if(){
            Wristintake.SetSpeedWrist(1);
        }
        else if(){
            Wristintake.SetSpeedWrist(-1);
        }   
        else{
            Wristintake.SetSpeedWrist(0);
        }
    }
    public void climbRobot(){
        if(){
            mClimb.SetSpeed(1, 1);
        }
        else if(){
            mClimb.SetSpeed(-1, -1);
        }   
        else{
            mClimb.SetSpeed(0, 0);
        }
    }
}