package frc.robot;
import java.lang.Math;

import frc.robot.Motors.Drive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Motors.Intake;
import frc.robot.Motors.Arm;
import frc.robot.Motors.Climb;

public class OI {
    private Drive drive = Drive.getInstance();
    private Intake intake = Intake.getInstance();
    private Climb climb = Climb.getInstance();
    private Arm arm = Arm.getInstance();
    private Lights lights = Lights.getInstance();
    //switch on or off
    private boolean switch_angle = false; 
    private boolean DvSwitch = false;
    private double firstDriverAlert = 30;
    private double secondDriverAlert = 20;
    private double duration = 1; 
    private boolean light_switch = true;

    public OI(){
//        Lights = lights.getInstance();
    }

    public void driverControl(Joystick driver_control){

       switch_angle = false; //drive.switch_GotoTarget;
       //This code is for auto target tracking
       /*if(driver_control.getPOV(0) == 0){
            drive.switch_GotoTarget = false;
        }
        if(driver_control.getRawButton(1)){
          //  drive.Gyro1.reset();
         //   drive.setAngle(90);
            switch_angle = true;
            drive.switchTarget();  
        }
        //start fun codd
        if(driver_control.getRawButton(8)) DvSwitch = true; 
        //stop fun code
        else if(driver_control.getRawButton(7)){
            DvSwitch = false;
            driver_control.setRumble(RumbleType.kLeftRumble, 0);
            driver_control.setRumble(RumbleType.kRightRumble, 0);
        }*/

        if(!switch_angle){
            drive.setSpeed(driver_control.getRawAxis(5), driver_control.getRawAxis(1));
           
        }
       
        //Ball intake contol 
        if(driver_control.getRawAxis(2) > 0.1){
           intake.SetSpeedBall(1);
        }
        else if(driver_control.getRawAxis(3) > 0.1){
           intake.SetSpeedBall(-1);
        }
        else{
            intake.SetSpeedBall(0);  
        }

        if(driver_control.getRawButton(4)){
            arm.disableArmPID();
            arm.disableWristPID();
            intake.setHatch(true);
            intake.setHatchPusher(0);
            arm.SetSpeed(-0.3);
            arm.SetSpeedWrist(-0.2);
       }

        //hatch control
        if(driver_control.getRawButton(5)){
            intake.setHatch(false);
        }
        else if(driver_control.getRawButton(6)){
            intake.setHatch(true);
        }

        //hatch pusher
        if(driver_control.getRawButton(10)){
            intake.setHatchPusher(0);
        }
        else if(driver_control.getRawButton(9)){
            intake.setHatchPusher(1);
        }
        else{
            intake.setHatchPusher(2);
        }

        //emergency disable PID
        if(driver_control.getRawButton(3)){
            arm.disableArmPID();
            arm.disableWristPID();
        }

        if(driver_control.getRawButton(2)){
            climb.setClamp(true);
        }
        else if(driver_control.getRawButton(1)){
            climb.setClamp(false);
        }
        

        //Match Timer - Alert Driver at 40 seconds
        driverMatchAlert(driver_control);
    }

    public void operatorControl(Joystick operator_control){   
        if(operator_control.getRawButton(1)){
            arm.startArmPID(-15);
            arm.startWristPID(13);
        }




        //Climber control
        if(operator_control.getPOV(0) == 0) { //up
            climb.SetSpeed(1);
           //light_switch = false;
        }
        else if(operator_control.getPOV(0) == 180) { //down 
            climb.SetSpeed(-1);
                //light_switch = false;
        }   
        else{
            if(!climb.AutoDepoly_Switch){
            climb.SetSpeed(0);
            }
        }

        //clamper control
        if(operator_control.getRawButton(5)){
            climb.setClamp(true);
        }
        else if(operator_control.getRawButton(6)){
            climb.setClamp(false);
        }
        

        //sPusher
        if(operator_control.getRawButton(7)){
            arm.setPusher(1);
        }
        else if(operator_control.getRawButton(8)){
            arm.setPusher(0);
        }
        else{
            arm.setPusher(2);
        }

        //arm control
        if(!arm.isArmPIDEnabld()) {
            if(operator_control.getRawAxis(1) > 0.1 || operator_control.getRawAxis(1) < -0.1){
                arm.SetSpeed(operator_control.getRawAxis(1)*.5);
            }
            else{
                arm.SetSpeed(operator_control.getRawAxis(1)*0);  
            }
        }
        //cargo cargoship
        if(operator_control.getPOV(0) == 90){
            arm.startArmPID(-85);
            arm.startWristPID(30); // 20
        } 
        //ball pickup
        else if(operator_control.getRawButton(2)){
            arm.startArmPID(0);
            arm.startWristPID(87);
        }
        //hatch pickup
        else if(operator_control.getRawButton(4)){
            arm.startArmPID(-25);
            arm.startWristPID(13);
        }
        //level two hatch
        else if(operator_control.getRawButton(3)) {
            arm.startArmPID(-90);
            arm.startWristPID(4);
        }
          
        if(operator_control.getRawAxis(1) > 0.2 || operator_control.getRawAxis(1) < -0.2) 
            arm.disableArmPID();
    
        //wrist
        if(operator_control.getRawAxis(3) > 0.2){
            arm.WristPIDPosition(2);
        }
        else if(operator_control.getRawAxis(2) > 0.2){
            arm.WristPIDPosition(-2);
        }
        
        //Arm PID adjust
        /*if(operator_control.getRawAxis(5) > 0.2){
            arm.ArmPIDPosition(3);
        }
        else if(operator_control.getRawAxis(5) < -0.2){
            arm.ArmPIDPosition(-3);
        }*/ 

        //Match Timer - Alert Operator at 40 seconds
        driverMatchAlert(operator_control);

        //Manual Wrist
        /*if(!arm.isWristPIDEnabld()){
            arm.SetSpeedWrist(operator_control.getRawAxis(5));
        }
        if(operator_control.getRawAxis(5) > 0.2 || operator_control.getRawAxis(5) < -0.2) arm.disableWristPID();
        */
        if(operator_control.getPOV(0) == 270) climb.Start_autoDepoly();
    }
    public void lights(){
        if(light_switch){
            //Lights.sLights(DriverStation.getInstance().getAlliance());
        }
        else if(!light_switch){
            //Lights.rainbow();
        }
        }
    private void driverMatchAlert(Joystick joy){
        double time = DriverStation.getInstance().getMatchTime();
        if((time >= firstDriverAlert && time <= firstDriverAlert+duration) || 
           (time >= secondDriverAlert && time <= secondDriverAlert+duration) ||
           (time >= secondDriverAlert+(duration*3) && time <= secondDriverAlert+(duration*4)))
        {
            joy.setRumble(RumbleType.kLeftRumble, 1);
            joy.setRumble(RumbleType.kRightRumble, 1);
        }
        else{
            joy.setRumble(RumbleType.kLeftRumble, 0);
            joy.setRumble(RumbleType.kRightRumble, 0);
        }
    }
}