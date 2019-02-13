package frc.robot.Motors;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.PID.aPIDoutput;



public class Arm{

    private CANSparkMax RTArm;
    private CANSparkMax LTArm;

    private static Arm m_Instance;

    public void setupTalon(){
        RTArm = new CANSparkMax(Constants.ArmID1, MotorType.kBrushless);
        LTArm = new CANSparkMax(Constants.ArmID2, MotorType.kBrushless);
   
        LTArm.follow(RTArm);
        LTArm.setInverted(true);
    }
    private Arm(){
        setupTalon();
    }
    public static Arm getInstance(){
        if (m_Instance == null){
            m_Instance = new Arm();
        }
        return m_Instance;
    }

    public void SetSpeed(double speed){
        RTArm.set(speed);
    }

    /* public void startArmPID(double Rate){
        aPidController.enable();
        aPidController.setSetpoint(Rate);
    }
    
    public void disableWristPID(){
        aPidController.disable();
    } */


}