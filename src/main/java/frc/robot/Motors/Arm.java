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
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.PID.wPIDoutput;


public class Arm{

    private Encoder eArm;
    private wPIDoutput WPIDoutput;
    private TalonSRX Wrist;
    private DoubleSolenoid sPusher;
    private CANSparkMax RTArm;
    private CANSparkMax LTArm;
    private PIDController aPidController;
    private  aPIDoutput  APIDoutput;
    private static Arm m_Instance;
    private PIDController wPidController;
    public Encoder eWrist;
    
    public void setup(){
        RTArm = new CANSparkMax(Constants.rArmID1, MotorType.kBrushless);
        LTArm = new CANSparkMax(Constants.lArmID2, MotorType.kBrushless);
        Wrist = new TalonSRX(Constants.wristIntakeID);
        eArm = new Encoder(Constants.eArm1, Constants.eArm2, true, Encoder.EncodingType.k4X);
        sPusher = new DoubleSolenoid(Constants.pusher1, Constants.pusher2);
        setPusher(false);

        eWrist = new Encoder(Constants.eWrist1, Constants.eWrist2);
        Wrist.setInverted(false);
        eWrist.reset();
        eWrist.setMaxPeriod(.1);
        eWrist.setMinRate(10);
        eWrist.setReverseDirection(true);
        eWrist.setSamplesToAverage(7);
        eWrist.setPIDSourceType(PIDSourceType.kDisplacement);
        eWrist.setDistancePerPulse(360.0/1024.0);
        WPIDoutput = new wPIDoutput(this);
        wPidController = new PIDController(Constants.wP, Constants.wI, Constants.wD, eWrist, WPIDoutput);
        wPidController.setOutputRange(-1, 0.25);
        
        eArm.reset();
        eArm.setMaxPeriod(.1);
        eArm.setMinRate(10);
        eArm.setSamplesToAverage(7);
        eArm.setPIDSourceType(PIDSourceType.kDisplacement);
        eArm.setDistancePerPulse(360.0/1024.0);
        APIDoutput = new aPIDoutput(this);
        aPidController = new PIDController(Constants.aP, Constants.aI, Constants.aD, eArm, APIDoutput);
        aPidController.setOutputRange(-1, 0.25);
        aPidController.setInputRange(-90, 0);



        aPidController.disable();
        wPidController.disable();
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
        RTArm.set(speed); //may have to change back for the comptation robot
        LTArm.set(-speed);
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
    public boolean isArmPIDEnabld(){
        return aPidController.isEnabled();
    }
    public void disableArmPID(){
        aPidController.disable();
    }

    public void SetSpeedWrist(double wrist){
        Wrist.set(ControlMode.PercentOutput, -wrist);
    }
    public void startWristPID(double Rate){
        wPidController.enable();
        wPidController.setSetpoint(Rate);
    }
    
    public void disableWristPID(){
        wPidController.disable();
    }



    public void ArmEncoderReset(){
            eArm.reset();
    }

    public void WristEncoderReset(){
        eWrist.reset();
    }




    public void print(){
        SmartDashboard.putNumber("ArmEncoder Distance", eArm.getDistance());
        SmartDashboard.putNumber("ArmEncoder Rate", eArm.getRate());
        
            double distance2 = eWrist.getDistance();
            double distance3 = eWrist.getRate();
    
            SmartDashboard.putNumber("wrist distance", distance2);
            SmartDashboard.putNumber("wrist rate", distance3);
        
    }

}