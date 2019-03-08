package frc.robot.Motors;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Intake{
    private TalonSRX Ball ;
    private static Intake m_Instance;
    private DoubleSolenoid hatch;

  //  private Encoder encoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);

    public void setup(){
        Ball = new TalonSRX(Constants.ballIntakeID);
        hatch = new DoubleSolenoid(Constants.hatch1, Constants.hatch2);
       
        setHatch(false);
    }
    private Intake(){
        setup();
    }

    public void SetSpeedBall(double ball){
        Ball.set(ControlMode.PercentOutput, ball * 0.75);
    }

    

    public static Intake getInstance(){
        if (m_Instance == null) {
            m_Instance = new Intake();
        }
        return m_Instance;
    }


    public void setHatch(boolean hDirection ){
        if(hDirection){
            hatch.set(DoubleSolenoid.Value.kForward);
        }
        else if(!hDirection){
            hatch.set(DoubleSolenoid.Value.kReverse);
        }
    }


} 
