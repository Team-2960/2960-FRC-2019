package frc.robot.PID;

import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.Motors.Arm;

public class wPIDoutput implements PIDOutput{
    Arm arm;
    public wPIDoutput(Arm arm){
        this.arm = arm;
    }

    public void pidWrite(double output){
        /*if (arm.isWristLimit() && output < 0.0)
        {
            output = 0;
        }
        */
        arm.SetSpeedWrist(output);
        //System.out.println(output);
    }
    
}