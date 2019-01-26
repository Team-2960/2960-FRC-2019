package frc.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants;;

public class driveTrain {
    
    //initialize ALL motors
    private TalonSRX mRightMaster = new TalonSRX(Constants.mRightMasterId);
    private TalonSRX mRightFollower1 = new TalonSRX(Constants.mRightFollowerId1);
    private TalonSRX mRightFollower2 = new TalonSRX(Constants.mRightFollowerId2);
    private TalonSRX mLeftMaster = new TalonSRX(Constants.mLeftMasterId);
    private TalonSRX mLeftFollower1 = new TalonSRX(Constants.mLeftFollowerId1);
    private TalonSRX mLeftFollower2 = new TalonSRX(Constants.mLeftFollowerId2);

    private static driveTrain m_Instance;
    



    
        
    
     private void SetupTalon(){
            //tells follower motors to follow master motors
        mRightFollower1.follow(mRightMaster);
        mRightFollower2.follow(mRightMaster);
        mLeftFollower1.follow(mLeftMaster);
        mLeftFollower2.follow(mLeftMaster);
      }
      
    public static driveTrain getInstance(){
        if (m_Instance == null) {
            m_Instance = new driveTrain();
        }
        return m_Instance;
    }

    private driveTrain() {
        SetupTalon();
    }
    public void SetSpeed(double right, double left){
        mLeftMaster.set(ControlMode.PercentOutput, left);
        mRightMaster.set(ControlMode.PercentOutput, right);
    }

    public void update(){

    }
}

