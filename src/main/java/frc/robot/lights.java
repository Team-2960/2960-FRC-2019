package frc.robot;

import java.sql.Driver;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.Constants;

public class lights{
    private double DriverAlert = 40;
    private DigitalOutput light1;
    private DigitalOutput light2;
    private static lights m_Instance;

    private lights(){
        light1 = new DigitalOutput(Constants.light1);
        light2 = new DigitalOutput(Constants.light2);
    }

    public static lights getInstance(){
        if (m_Instance == null) {
            m_Instance = new lights();
        }
        return m_Instance;
    }

    public void sLights(DriverStation.Alliance Color){
       
        

        if(Color == DriverStation.Alliance.Blue ){
            light1.set(true);
            light2.set(false);
        }else if(Color == DriverStation.Alliance.Red ){
            light1.set(false);
            light2.set(true);
        }
    
    double time = DriverStation.getInstance().getMatchTime();
    if(time <= DriverAlert){
        light1.set(true);
        light2.set(true);
    
        

    }
}
    public void rainbow(){
        light1.set(false);
        light2.set(false);
    }
    








}