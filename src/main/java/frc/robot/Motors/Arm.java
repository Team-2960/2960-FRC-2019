package frc.robot.Motors;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;


public class Arm{

    //hello
    private CANSparkMax RTArm;
    private CANSparkMax LTArm;

    private static Arm m_Instance;

    public void setupTalon(){
        RTArm = new CANSparkMax(Constants.ArmID1, MotorType.kBrushless);
        LTArm = new CANSparkMax(Constants.ArmID2, MotorType.kBrushless);
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

    public void SetSpeed(double right, double left){
        RTArm.set(right);
        LTArm.set(left);
    }


}