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

public class Climb{
    private TalonSRX Climb1;
    private TalonSRX Climb2;

    private static Climb m_Instance;

    public void setupTalon(){
        Climb1 = new TalonSRX(Constants.ClimbID1);
        Climb2 = new TalonSRX(Constants.ClimbID2);
    }

    private Climb(){
        setupTalon();
    }


    public static Climb getInstance(){
        if (m_Instance == null){
            m_Instance = new Climb();
        }
        return m_Instance;
    }

    public void SetSpeed(double climb1, double climb2){
        Climb1.set(ControlMode.PercentOutput, climb1);
        Climb2.set(ControlMode.PercentOutput, climb2);
    }
}