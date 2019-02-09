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
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;

public class Intake{
    private TalonSRX Ball ;
    private TalonSRX Wrist;
    private static Intake m_Instance;
    private Compressor cHatch = new Compressor(0);
  //  private Encoder encoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);

    public void setupTalon(){
        Ball = new TalonSRX(Constants.ballIntakeID);
        Wrist = new TalonSRX(Constants.wristIntakeID);
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
    public void SetHatch(boolean cSwitch){
        cHatch.setClosedLoopControl(cSwitch);
    }

    


    



} 