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
    private AnalogGyro gyros = new AnalogGyro(2);
    /*
    public void AngleTurn(){
        if(gyros.getAngle() < 90 + error){
            Drive.setSpeed(1, -1);
        }
        else if(gyros.getAngle() > 90 + error){
            Drive.setSpeed(-1, 1);
        }
        else{
            Drive.setSpeed(0, 0);
        }
   
    }
    */
}





