package frc.robot;

public class Constants {

    //talons
    public static int mRightMasterId = 1;
    public static int mRightFollowerId1 = 2;
    public static int mRightFollowerId2 = 3;
    public static int mLeftMasterId = 4;
    public static int mLeftFollowerId1 = 5;
    public static int mLeftFollowerId2 = 6;
    public static int ClimbID1 = 7;
    public static int ClimbID2 = 8;
    public static int ballIntakeID = 9;
    public static int wristIntakeID = 10;
    public static int ArmID1 = 11;
    public static int ArmID2 = 12;

    //camera values
    public static double HUEmin = 32.560428437342786;
    public static double HUEmax = 110.96897432790306;
    public static double Saturationmin = 22.93165467625899;
    public static double Saturationmax = 255;
    public static double HSValuemin = 220;
    public static double HSValuemax = 254.54;
  
    //pid values
    public static double wP = .9;
    public static double wI = -0.02;
    public static double wD = .6;
    public static double aP = .9;
    public static double aI = -0.02;
    public static double aD = .6;  
    
    //Solenoids
    public static int clamp1 = 0;
    public static int clamp2 = 1;
    public static int kicker1 = 2;
    public static int kicker2 = 3;
    public static int hatch1 = 4;
    public static int hatch2 = 5;

    //Digit Input Output
    public static int eWrist1 = 2;
    public static int eWrist2 = 3;
}
