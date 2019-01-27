package frc.robot;

import frc.robot.Motors.driveTrain;


import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Motors.Intake;
import frc.robot.Motors.driveTrain;
import frc.robot.Motors.Arm;
import frc.robot.Motors.Climb;

public class OI {
    private Joystick moving = new Joystick(0);
    private driveTrain drive = driveTrain.getInstance();
    private Intake balls = Intake.getInstance();
    private Intake Wrists = Intake.getInstance();
    private Climb Climbs = Climb.getInstance();
    public OI(){

    }

    public void driveRobot(){
        drive.SetSpeed(moving.getRawAxis(5), moving.getRawAxis(1));
    }
    
    public void inTake(){
       //todo set a botton for ball intake and outtake and else
        if(){
            ball = 1;
            Balls.SetSpeed(ball);
        }
        else if(){
            ball = -1;
            Balls.SetSpeed(ball);
        }
        //todo set a botton for wrist intake and outtake and else
        if(){
            wrist = 1;
            Wrists.SetSpeed(ball);
        }
        else if(){
            wrist = -1;
            Wrists.SetSpeed(ball);
        }   
    }
    public void climbRobot(){
        if(){
            climb1 = 1;
            climb2 = 1;
            Climbs.SetSpeed(climb1, climb2);
        }
        else if(){
            climb1 = 1;
            climb2 = 1;
            Climbs.SetSpeed(climb1, climb2);
        }   
        else{
            Climbs.SetSpeed(0, 0);
        }
    }
}