package frc.robot.Motors;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Intake{
    private TalonSRX Ball ;
    private TalonSRX Wrist;
    private Encoder eWrist;
    private static Intake m_Instance;
    private DoubleSolenoid hatch;

  //  private Encoder encoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);

    public void setupTalon(){
        Ball = new TalonSRX(Constants.ballIntakeID);
        Wrist = new TalonSRX(Constants.wristIntakeID);
        hatch = new DoubleSolenoid(Constants.hatch1, Constants.hatch2);
        eWrist = new Encoder(2, 3);
        eWrist.setMaxPeriod(.1);
        eWrist.setMinRate(10);
        eWrist.setReverseDirection(true);
        eWrist.setSamplesToAverage(7);

    }
    private Intake(){
        setupTalon();
    }
    public void SetSpeedBall(double ball){
        Ball.set(ControlMode.PercentOutput, ball);
    }
    public void SetSpeedWrist(double wrist){
        Wrist.set(ControlMode.PercentOutput, wrist);

    }
    public static Intake getInstance(){
        if (m_Instance == null) {
            m_Instance = new Intake();
        }
        return m_Instance;
    }

    public void print(){
        double distance =  eWrist.getRaw();
        double distance2 = eWrist.getDistance();
        double distance3 = eWrist.getRate();
        SmartDashboard.putNumber("1", distance);
        SmartDashboard.putNumber("2", distance2);
        SmartDashboard.putNumber("3", distance3);
    }

    public void setHatch(boolean hDirection ){
        if(hDirection){
            hatch.set(DoubleSolenoid.Value.kForward);
        }
        else if(hDirection){
            hatch.set(DoubleSolenoid.Value.kReverse);
        }
    }

    



} 