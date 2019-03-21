package frc.robot.PID;

import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.Motors.Arm;

public class wPIDoutput implements PIDOutput{
    Arm arm;
    public wPIDoutput(Arm arm){
        this.arm = arm;
    }

    public void pidWrite(double output){
        arm.SetSpeedWrist(output);
        //System.out.println(output);
    }
    
}