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
import edu.wpi.first.wpilibj.Ultrasonic;

public class Drive {
    
    //initialize ALL motors
    private TalonSRX mRightMaster;
    private TalonSRX mRightFollower1;
    private TalonSRX mRightFollower2;
    private TalonSRX mLeftMaster;
    private TalonSRX mLeftFollower1;
    private TalonSRX mLeftFollower2;

    private static Drive m_Instance;
    
   

     private void setupTalon(){
        //Initialize Talons
        mRightMaster = new TalonSRX(Constants.mRightMasterId);
        mRightFollower1 = new TalonSRX(Constants.mRightFollowerId1);
        mRightFollower2 = new TalonSRX(Constants.mRightFollowerId2);
        mLeftMaster = new TalonSRX(Constants.mLeftMasterId);
        mLeftFollower1 = new TalonSRX(Constants.mLeftFollowerId1);
        mLeftFollower2 = new TalonSRX(Constants.mLeftFollowerId2);

        //Set follower motors
        mRightFollower1.follow(mRightMaster);
        mRightFollower2.follow(mRightMaster);
        mLeftFollower1.follow(mLeftMaster);
        mLeftFollower2.follow(mLeftMaster);
      }
      
    public static Drive getInstance(){
        if (m_Instance == null) {
            m_Instance = new Drive();
        }
        return m_Instance;
    }

    private Drive() {
        setupTalon();
    }

    public void setSpeed(double right, double left){
        mLeftMaster.set(ControlMode.PercentOutput, left);
        mRightMaster.set(ControlMode.PercentOutput, right);
    }
    public void Gyro(double right, double left){
        mLeftMaster.set(ControlMode.PercentOutput, left);
        mRightMaster.set(ControlMode.PercentOutput, right);      
    }

    public void ultrasonic(){
        

    }

    public void update(){

    }
}

