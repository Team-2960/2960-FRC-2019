package frc.robot;

import frc.robot.Motors.driveTrain;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Motors.Intake;

public class OI {
    private Joystick moving = new Joystick(0);
    private driveTrain drive = driveTrain.getInstance();
    private Intake ball_and_wrist = Intake.getInstance();

    public OI(){

    }
    public void driveRobot(){
        drive.SetSpeed(moving.getRawAxis(5), moving.getRawAxis(1));
    }
    public void inTake(){

    }

}