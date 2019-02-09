package frc.robot.Motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Climb{
    private TalonSRX Climb1;
    private TalonSRX Climb2;
    private DoubleSolenoid sclamp;
    private DoubleSolenoid kicker;
    private static Climb m_Instance;

    public void setupTalon(){
        Climb1 = new TalonSRX(Constants.ClimbID1);
        Climb2 = new TalonSRX(Constants.ClimbID2);

        sclamp = new DoubleSolenoid(Constants.clamp1, Constants.clamp2);
        kicker = new DoubleSolenoid(Constants.kicker1, Constants.kicker2);
    }

    private Climb(){
        setupTalon();
    }


    public static Climb getInstance(){
        if (m_Instance == null){
            m_Instance = new Climb();
        }
        return m_Instance;
    }

    public void SetSpeed(double climb1, double climb2){
        Climb1.set(ControlMode.PercentOutput, climb1);
        Climb2.set(ControlMode.PercentOutput, climb2);
    }
    
    public void setKicker(boolean kDirection){
        if(kDirection){
            kicker.set(DoubleSolenoid.Value.kForward);
        }
        else{
            kicker.set(DoubleSolenoid.Value.kReverse);
        }
    }

    public void setClamp(boolean cDirection){
        if(cDirection){
            sclamp.set(DoubleSolenoid.Value.kForward);
        }
        else{
            sclamp.set(DoubleSolenoid.Value.kReverse);
        }
    }

}