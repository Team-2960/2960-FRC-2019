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
    private DoubleSolenoid hatch_pusher;

    public void setup(){
        Ball = new TalonSRX(Constants.ballIntakeID);
        hatch = new DoubleSolenoid(Constants.hatch1, Constants.hatch2);
        hatch_pusher = new DoubleSolenoid(Constants.hatch_pusher1, Constants.hatch_pusher2);
        setHatch(true);   //default the hatch
        setHatchPusher(false);  //deafault the hatch pusher potision

    }
    private Intake(){
        setup();
    } 

    public void SetSpeedBall(double ball){
        Ball.set(ControlMode.PercentOutput, ball * 0.75);
    }

    //hatch Intake
    public void setHatch(boolean hDirection ){
        if(hDirection){
            hatch.set(DoubleSolenoid.Value.kForward);
        }
        else if(!hDirection){
            hatch.set(DoubleSolenoid.Value.kReverse);
        }
    }

    public void setHatchPusher(boolean hDirection ){
        if(hDirection){
            hatch_pusher.set(DoubleSolenoid.Value.kForward);
        }
        else if(!hDirection){
            hatch_pusher.set(DoubleSolenoid.Value.kReverse);
        }
    }
    
    public static Intake getInstance(){
        if (m_Instance == null) {
            m_Instance = new Intake();
        }
        return m_Instance;
    }
} 
