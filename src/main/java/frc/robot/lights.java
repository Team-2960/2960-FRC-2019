package frc.robot;

import java.sql.Driver;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class lights{
    private DigitalOutput dig1;
    private DigitalOutput dig2;

    public lights(){
        dig1 = new DigitalOutput(1);
        dig2 = new DigitalOutput(2);
    
    }
    public void sLights(DriverStation.Alliance Color){
       
        

        if(Color == DriverStation.Alliance.Blue ){
            dig1.set(true);
            dig2.set(false);
        }else if(Color == DriverStation.Alliance.Red ){
            dig1.set(false);
            dig2.set(true);
        }
        else{
            dig1.set(false);
            dig2.set(false);
        }

    }











}