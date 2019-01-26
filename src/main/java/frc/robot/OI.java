package frc.robot;

import frc.robot.Motors.driveTrain;
import edu.wpi.first.wpilibj.Joystick;

public class OI {
    private Joystick moving = new Joystick(0);
    private driveTrain drive = driveTrain.getInstance();
   
    public OI(){

    }
    public void driveRobot(){
        drive.SetSpeed(moving.getRawAxis(5), moving.getRawAxis(1));
    }


}