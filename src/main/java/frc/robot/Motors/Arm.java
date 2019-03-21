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
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.PID.wPIDoutput;


public class Arm{
    private DoubleSolenoid sPusher;
    
    private TalonSRX Wrist;
    private CANSparkMax RTArm;
    private CANSparkMax LTArm;
    private Encoder eArm;
    public Encoder eWrist;

    private static Arm m_Instance;

    private PIDController aPidController;
    private PIDController wPidController;
    private aPIDoutput APIDoutput;
    private wPIDoutput WPIDoutput;
    
    public void setup(){
        //initialize the motor
        RTArm = new CANSparkMax(Constants.rArmID1, MotorType.kBrushless);
        LTArm = new CANSparkMax(Constants.lArmID2, MotorType.kBrushless);
        Wrist = new TalonSRX(Constants.wristIntakeID);
        Wrist.setNeutralMode(NeutralMode.Brake);
        //initialize the Encorder
        eArm = new Encoder(Constants.eArm1, Constants.eArm2, true, Encoder.EncodingType.k4X);
        eWrist = new Encoder(Constants.eWrist1, Constants.eWrist2, false, Encoder.EncodingType.k4X);

        //initialize the solenoid
        sPusher = new DoubleSolenoid(Constants.pusher1, Constants.pusher2);
        setPusher(false);


        //Wrist PID
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
        wPidController.setOutputRange(-1, 0.5);
        
        //Arm PID
        eArm.reset();
        eArm.setMaxPeriod(.1);
        eArm.setMinRate(10);
        eArm.setSamplesToAverage(7);
        eArm.setPIDSourceType(PIDSourceType.kDisplacement);
        eArm.setDistancePerPulse(360.0/1024.0);
        APIDoutput = new aPIDoutput(this);
        aPidController = new PIDController(Constants.aP, Constants.aI, Constants.aD, eArm, APIDoutput);
        aPidController.setOutputRange(-0.5, 0.25);
        aPidController.setInputRange(-150, 0);


        //PID disable
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

    //set up speed
    public void SetSpeed(double speed){ 
        RTArm.set(-speed); //may have to change back for the comptation robot
        LTArm.set(speed);
    }
    
    //setup Pusher
    public void setPusher(boolean kDirection){
        if(kDirection){
            sPusher.set(DoubleSolenoid.Value.kForward);
        }
        else if(!kDirection){
            sPusher.set(DoubleSolenoid.Value.kReverse);
        }
    }

    //Start Arm PID
    public void startArmPID(double Distance){
        aPidController.enable();
        aPidController.setSetpoint(Distance);
    }

    //Check Arm PID is Enable
    public boolean isArmPIDEnabld(){
        return aPidController.isEnabled();
    }

    public boolean atPosition(){
       boolean atPosition = false;
       if (Math.abs(aPidController.getError()) < 5)
            atPosition = true;
       return atPosition;
    }
    //disable Arm PID
    public void disableArmPID(){
        if(aPidController.isEnabled()) aPidController.disable();
    }

    public void ArmPIDPosition(double position){
        double rate = aPidController.getSetpoint() + position;
        aPidController.setSetpoint(rate);
    }


    //set speed for wrist motor
    public void SetSpeedWrist(double wrist){
        Wrist.set(ControlMode.PercentOutput, -wrist);
    }

    //Start wrist PID
    public void startWristPID(double Rate){
        wPidController.enable();
        wPidController.setSetpoint(Rate);
    }
    //check Wrist PID
    public boolean isWristPIDEnabld(){
        return wPidController.isEnabled();
    }

    //disable wrist PID
    public void disableWristPID(){
        if(wPidController.isEnabled()) wPidController.disable();
    }


    //reset the encoder
    public void ArmEncoderReset(){
        eArm.reset();
    }

    public void WristEncoderReset(){
        eWrist.reset();
    }

    public void WristPIDPosition(double position){
        double rate = wPidController.getSetpoint() + position;
        wPidController.setSetpoint(rate);
    }



    public void print(){
        SmartDashboard.putNumber("ArmEncoder Distance", eArm.getDistance());
        SmartDashboard.putNumber("ArmEncoder Rate", eArm.getRate());
        SmartDashboard.putBoolean("Arm PID Enable", isArmPIDEnabld());
        SmartDashboard.putBoolean("Wrist PID Enable", isWristPIDEnabld());
            double distance2 = eWrist.getDistance();
            double distance3 = eWrist.getRate();
    
            SmartDashboard.putNumber("wrist distance", distance2);
            SmartDashboard.putNumber("wrist rate", distance3);
        
    }

}