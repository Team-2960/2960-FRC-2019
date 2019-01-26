package frc.robot.Motors;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;


public class Arm{

    //hello
    private CANSparkMax Arm1 = new CANSparkMax(Constants.ArmID1, MotorType.kBrushed);
    private CANSparkMax Arm2 = new CANSparkMax(Constants.ArmID2, MotorType.kBrushed);

    private static Arm m_Instance;

    public static Arm getInstance(){
        if (m_Instance == null){
            m_Instance = new Arm();
        }
        return m_Instance;
    }

    public void SetSpeed(double arm){
        Arm1.set(arm);
        Arm2.set(arm);
    }


}