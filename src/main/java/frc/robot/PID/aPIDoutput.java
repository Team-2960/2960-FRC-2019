package frc.robot.PID;

import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.Motors.Arm;

public class aPIDoutput implements PIDOutput{
    Arm arm;
    public aPIDoutput(Arm arm){
        this.arm = arm;
    }

    public void pidWrite(double speed){
        arm.SetSpeed(speed);
        System.out.println("arm speed: " + speed);
    }
}


