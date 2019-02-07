package frc.robot.Sensors;



//todo import the analog gyro


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.AnalogGyro;
import frc.robot.Motors.Drive;



public class Gyro{
    double error = 5;
    private AnalogGyro gyros = new AnalogGyro(1);
    private Drive drive = Drive.getInstance();
    
    public void Turn(){
        double Angle = gyros.getAngle();
        if(Angle < 180 + error){
            drive.setSpeed(-0.5, 0.5);
        }
        else if(Angle > 180 + error){
            drive.setSpeed(0.5, -0.5);
        }
        else{
            drive.setSpeed(0.5, -0.5); 
        }
        gyros.reset();
    }
}





