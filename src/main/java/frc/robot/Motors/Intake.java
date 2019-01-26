package frc.robot.Motors;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants;

public class Intake{
    private TalonSRX Ball = new TalonSRX(Constants.ballIntakeID);
    private TalonSRX Wrist = new TalonSRX(Constants.wristIntakeID);
    private static Intake m_Instance;

    private Intake(){

    }
    public void SetSpeed(double ball, double wrist){
        Ball.set(ControlMode.PercentOutput, ball);
        Wrist.set(ControlMode.PercentOutput, wrist);
    }
    public static Intake getInstance(){
        if (m_Instance == null) {
            m_Instance = new Intake();
        }
        return m_Instance;
    }





} 