package frc.robot.Motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Climb{
    private TalonSRX rClimb;
    private TalonSRX lClimb;
    private DoubleSolenoid sClamp;
    private static Climb m_Instance;

    public void setupTalon(){
        rClimb = new TalonSRX(Constants.ClimbID1);
        lClimb = new TalonSRX(Constants.ClimbID2);
        
        rClimb.follow(lClimb);
       // rClimb.setInverted(true);

        sClamp = new DoubleSolenoid(Constants.clamp1, Constants.clamp2);
   
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
        lClimb.set(ControlMode.PercentOutput, speed);
    }

    public void setClamp(boolean cDirection){
        if(cDirection){
            sClamp.set(DoubleSolenoid.Value.kForward);
        }
        else if(!cDirection){
            sClamp.set(DoubleSolenoid.Value.kReverse);
        }
    }

}