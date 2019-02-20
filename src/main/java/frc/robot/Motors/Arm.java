package frc.robot.Motors;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.PID.aPIDoutput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PIDSourceType;


public class Arm{

    private Encoder eArm;
    private DoubleSolenoid sPusher;
    private CANSparkMax RTArm;
    private CANSparkMax LTArm;
    private PIDController aPidController;
    private  aPIDoutput  APIDoutput;
    private static Arm m_Instance;

    public void setup(){
        RTArm = new CANSparkMax(Constants.rArmID1, MotorType.kBrushless);
        LTArm = new CANSparkMax(Constants.lArmID2, MotorType.kBrushless);
        eArm = new Encoder(Constants.eArm1, Constants.eArm2, true, Encoder.EncodingType.k4X);
        sPusher = new DoubleSolenoid(Constants.pusher1, Constants.pusher2);
        setPusher(false);


        eArm.reset();
        eArm.setMaxPeriod(.1);
        eArm.setMinRate(10);
        eArm.setSamplesToAverage(7);
        eArm.setPIDSourceType(PIDSourceType.kDisplacement);
        eArm.setDistancePerPulse(1024.0/360.0);
        APIDoutput = new aPIDoutput(this);
        aPidController = new PIDController(Constants.aP, Constants.aI, Constants.aD, eArm, APIDoutput);
    }
    private Arm(){
        setup();
    }
    public static Arm getInstance(){
        if (m_Instance == null){
            m_Instance = new Arm();
        }
        return m_Instance;
    }

    public void SetSpeed(double speed){
        RTArm.set(-speed * 0.75);
        LTArm.set(speed * 0.75);
    }
    
    public void setPusher(boolean kDirection){
        if(kDirection){
            sPusher.set(DoubleSolenoid.Value.kForward);
        }
        else if(!kDirection){
            sPusher.set(DoubleSolenoid.Value.kReverse);
        }
    }

    public void startArmPID(double Distance){
        aPidController.enable();
        aPidController.setSetpoint(Distance);
    }
    public void disableArmPID(){
        aPidController.disable();
        
    }



    public void ArmEncoderReset(){
            eArm.reset();
    }




    public void print(){
        SmartDashboard.putNumber("ArmEncoder Distance", eArm.getDistance());
        SmartDashboard.putNumber("ArmEncoder Rate", eArm.getRate());
        
    }

}