package frc.robot.Motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Climb{
    private TalonSRX rClimb;
    private TalonSRX lClimb;
    private DoubleSolenoid sClamp;
    private static Climb m_Instance;
    private Arm arm;
    private Timer timer = new Timer();
    public Boolean AutoDepoly_Switch = false; 
    private Intake intake = Intake.getInstance();

    public void setupTalon(){
        //initialize all motors

        rClimb = new TalonSRX(Constants.ClimbID1);
        lClimb = new TalonSRX(Constants.ClimbID2);
        
    

        rClimb.follow(lClimb);
       // rClimb.setInverted(true);

       arm = Arm.getInstance();

        sClamp = new DoubleSolenoid(Constants.clamp1, Constants.clamp2);
   
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

    public void SetSpeed(double speed){
        lClimb.set(ControlMode.PercentOutput, speed);
    }

    public void setClamp(boolean cDirection){
        if(cDirection){
            sClamp.set(DoubleSolenoid.Value.kForward);
        }
        else if(!cDirection){
            sClamp.set(DoubleSolenoid.Value.kReverse);
        }
    }

    public void Start_autoDepoly(){
        timer.reset();
        timer.start();
        AutoDepoly_Switch = true;
        
    }
    public Boolean autoDepoly(){
        Boolean AutoDepoly_Done = false;

        
        if(timer.get() > 0 && timer.get() < 0.2){
            setClamp(false);
            intake.setHatchPusher(0);
        }else if(timer.get() > 0.2 && timer.get() < 0.5){
            SetSpeed(-0.4);
        }else if(timer.get() > 2 && timer.get() < 2.5){
            arm.startArmPID(-150);
            arm.startWristPID(68);
            setClamp(true);
        }else if(timer.get() > 2.75 && timer.get() < 3.25){
            SetSpeed(0);
        }else if(timer.get() > 4){
            AutoDepoly_Done = true;
            timer.stop();
        }
        if(timer.get() > 2.5 && arm.atPosition()){
            arm.startArmPID(-25);
            arm.startWristPID(-15);
        }
        
        /* if(timer.get() > 0 && timer.get() < 0.2){
            setClamp(false);
            intake.setHatchPusher(0);
        }else if(timer.get() > 0.2 && timer.get() < 0.5){
            SetSpeed(-1);
            arm.startArmPID(-150);
            arm.startWristPID(68);
            setClamp(true);
        }else if(timer.get() > 1 && timer.get() < 1.5){
            SetSpeed(0);
        }else if(timer.get() > 2){
            AutoDepoly_Done = true;
            timer.stop();
        }
        if(timer.get() > 1 && arm.atPosition()){
            arm.startArmPID(-25);
            arm.startWristPID(-15);    
        } */

        return AutoDepoly_Done;

    }

     public void update(){
        if(AutoDepoly_Switch){
            Boolean aDone;
            aDone = autoDepoly();
            if(aDone){
                AutoDepoly_Switch = false;
            }
        }
    }
}