package frc.robot.Motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Climb{
    private TalonSRX Climb1;
    private TalonSRX Climb2;
    private DoubleSolenoid sClamp;
    private DoubleSolenoid sPusher;
    private static Climb m_Instance;

    public void setupTalon(){
        Climb1 = new TalonSRX(Constants.ClimbID1);
        Climb2 = new TalonSRX(Constants.ClimbID2);
        Climb2.follow(Climb1);
        Climb2.setInverted(true);

        sClamp = new DoubleSolenoid(Constants.clamp1, Constants.clamp2);
        sPusher = new DoubleSolenoid(Constants.pusher1, Constants.pusher2);
   
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

    public void SetSpeed(double speed){
        Climb1.set(ControlMode.PercentOutput, speed);
    }
    
    public void setPusher(boolean kDirection){
        if(kDirection){
            sPusher.set(DoubleSolenoid.Value.kForward);
        }
        else{
            sPusher.set(DoubleSolenoid.Value.kReverse);
        }
    }

    public void setClamp(boolean cDirection){
        if(cDirection){
            sClamp.set(DoubleSolenoid.Value.kForward);
        }
        else{
            sClamp.set(DoubleSolenoid.Value.kReverse);
        }
    }

}