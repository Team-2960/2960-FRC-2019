package frc.robot.PID;

import edu.wpi.first.wpilibj.PIDOutput;
import frc.robot.Motors.Intake;

public class wPIDoutput implements PIDOutput{
    Intake intake;
    public wPIDoutput(Intake intake){
        this.intake = intake;
    }

    public void pidWrite(double output){
        intake.SetSpeedWrist(output);
    }
}