package frc.robot.Motors;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Ultrasonic;
import java.lang.Math;

public class Drive {
    
    //initialize ALL motors
    private TalonSRX mRightMaster;
    private TalonSRX mRightFollower1;
    private TalonSRX mRightFollower2;
    private TalonSRX mLeftMaster;
    private TalonSRX mLeftFollower1;
    private TalonSRX mLeftFollower2;
    public AnalogGyro Gyro1 = new AnalogGyro(0);
    private int tolerance = 2;
    private static Drive m_Instance;
    public boolean switch_GotoAngle = false;
    private double gyroAngle;
    private Timer tGyro = new Timer();
    private boolean sTimer = false;
   

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

    public boolean gotoAngle(double angle){
        double error = Math.abs(angle - Gyro1.getAngle());
        boolean atAngle = false;
        int dirc = 1;
        if(angle - Gyro1.getAngle() < 0) dirc = -1;

        
        if(error > 90){
            setSpeed(1 * dirc, 1 * dirc);
        }
        else if(error > 70){
            setSpeed(0.7 * dirc, 0.7 * dirc);
        }
        else if(error > 50){
            setSpeed(0.5 * dirc, 0.5 * dirc);
        }
        else if(error > 15){
            setSpeed(0.3 * dirc, 0.3 * dirc);
        }
        else if(error > tolerance){
            setSpeed(0.2 * dirc, 0.2 * dirc);
            
        }
        else{
            
            setSpeed(0, 0);
            if(sTimer){
                tGyro.reset();
                tGyro.start();
                sTimer = false;
            }
            
        }
        
        if(Gyro1.getAngle() < tolerance + angle && Gyro1.getAngle() > angle - tolerance && tGyro.get() > 0.1){
            atAngle = true;
            tGyro.stop();
        }
    
        return atAngle;
}
    public double returnAngle(){
      return Gyro1.getAngle();
    }

    

    public void resetGyro(){
        Gyro1.reset();
    }

    public void ultrasonic(){
        

    }

    public void setAngle(double angle){
        Gyro1.reset();
        switch_GotoAngle = true;
        gyroAngle = angle;
        sTimer = true;
    }

    public void update(){
        if(switch_GotoAngle){
            boolean Done = false; 
        
            Done = gotoAngle(gyroAngle);
        
            if(Done){   
                switch_GotoAngle = false;
            }
        }
    }
}

