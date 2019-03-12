package frc.robot.Motors;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogGyro;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants;
import java.lang.Math;
import frc.robot.Motors.Drive;
import frc.robot.Camera.Camera;

public class Drive {
    
    //initialize ALL motors
    private TalonSRX mRightMaster;
    private TalonSRX mRightFollower1;
    private TalonSRX mRightFollower2;
    private TalonSRX mLeftMaster;
    private TalonSRX mLeftFollower1;
    private TalonSRX mLeftFollower2;
    
    private int tolerance = 20;
    private static Drive m_Instance;
    public boolean switch_GotoAngle = false;
    private double gyroAngle;
    private Timer tGyro = new Timer();
    private boolean sTimer = false; // switch timer
    private Timer tTarget = new Timer();
    private boolean sTarget = false;// switch target
    private Camera hatchCamera;
    public boolean switch_GotoTarget = false;

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
        
        //set motor invert
        mLeftMaster.setInverted(true);
        mLeftFollower2.setInverted(true);
        mLeftFollower1.setInverted(true);
        }
      
    public static Drive getInstance(){
        if (m_Instance == null) {
            m_Instance = new Drive();
        }
        return m_Instance;
    }

    private Drive(){
        hatchCamera = new Camera(0);
        setupTalon();
    }

    public void setSpeed(double right, double left){
        mLeftMaster.set(ControlMode.PercentOutput, left);
        mRightMaster.set(ControlMode.PercentOutput, right);
    }

    public void setAngle(double angle){
      //  Gyro1.reset();
        switch_GotoAngle = true;
        gyroAngle = angle;
        sTimer = true;
    }
    public void switchTarget() {
        switch_GotoTarget = true;
    }

    public boolean gotoTarget(){
        double error = Math.abs(hatchCamera.getImageResults());
        boolean atTarget = false;
        int dirc = 1;
        if(hatchCamera.getImageResults() > 0) dirc = -1;

        if(error > tolerance){
            setSpeed(0.15 * dirc, -0.15 * dirc);
        }
        else{
            
            setSpeed(0, 0);
             if(sTarget){
                tTarget.reset();
                tTarget.start();
                sTarget = false; 
           }
           atTarget = true;
        }
        
        if(hatchCamera.getImageResults() < tolerance && hatchCamera.getImageResults() > tolerance && tTarget.get() > 0.1){
            atTarget = true;
            tTarget.stop();
        } 
    
        return atTarget;
    }

    public void isTargetCentered(){
        boolean targetAquired = false;
        if(hatchCamera.isImageFound()){
            if(hatchCamera.getImageResults() < tolerance && hatchCamera.getImageResults() > tolerance){
               targetAquired = true; 
            } 
        }
        SmartDashboard.putBoolean("Target Aquired!", targetAquired);
        
    }
    public void update(){
       /* //Go To Angle - Not currently used
        if(switch_GotoAngle){
            boolean aDone = false; 
        
          //  aDone = gotoAngle(gyroAngle);
        
            if(aDone){   
                switch_GotoAngle = false;
            }    
        }*/
        //Auto aquire target - Not currently in use
        /*if(switch_GotoTarget) {
            boolean tDone = false; 
        
            tDone = gotoTarget();
        
            if(tDone){   
                switch_GotoTarget = false;
            }
        }*/
        isTargetCentered();
    }
}

