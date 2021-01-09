package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight
{
    NetworkTable netTable;
    NetworkTableInstance netInst;

    NetworkTableEntry targetStateEntry;
    NetworkTableEntry targetXOffsetEntry;
    NetworkTableEntry targetYOffsetEntry;
    NetworkTableEntry targetAreaEntry;
    NetworkTableEntry targetHorizontalEntry;
    NetworkTableEntry pipelineEntry; 
    NetworkTableEntry ledModeEntry;

    DriveTrain driveTrain;

    public static final double driverPipeline = 3;
    public static final double shooterPipeline = 1;

    public static final double xThresh = 2;
    public static final double areaThresh = .2;
    public static final double areaAddition = .8;
    public static final double movementSpeed = .5;
    public static final double centeringSpeed = .5;
    
    public Limelight()
    {
        driveTrain = new DriveTrain();
        netTable = NetworkTableInstance.getDefault().getTable("limelight");
        targetStateEntry = netTable.getEntry("tv");
        targetXOffsetEntry = netTable.getEntry("tx");
        targetYOffsetEntry = netTable.getEntry("ty");
        targetAreaEntry = netTable.getEntry("ta");
        targetHorizontalEntry = netTable.getEntry("thor");
        pipelineEntry = netTable.getEntry("pipeline");
        ledModeEntry = netTable.getEntry("ledMode");
        setPipeline(Limelight.driverPipeline);
        setLedState(1); // Keep the limelight leds off during initialization
    }

    public void positionRobot(double xPos, double area,
                            double xThresh, double areaThresh, double areaAddition,
                            double centeringSpeed, double movementSpeed)
    {
        double xSpeed = 0.0;
        double ySpeed = 0.0;

        setLedState(0);

        // Set rotation speed
        if(xPos >= -xThresh && xPos <= xThresh){}
        else if(xPos <= -xThresh) {xSpeed = -centeringSpeed;}
        else if(xPos >= xThresh)  {xSpeed = centeringSpeed;}

        // Set movement speed
        if(area >= areaThresh && area <= areaThresh + areaAddition){}
        else if(area <= areaThresh)                 {ySpeed = movementSpeed;}
        else if(area >= areaThresh + areaAddition)  {ySpeed = -movementSpeed;}

        driveTrain.arcadeDrive(xSpeed, ySpeed);
    }

    public void setLedState(double state)
    {
        ledModeEntry.setNumber(state);
    }

    public void setPipeline(double pipeline)
    {
        pipelineEntry.setNumber(pipeline);
    }

    public void showValues()
    {
        System.out.println("WARNING! targetState: " + targetStateEntry.getDouble(0));
        System.out.println("WARNING! targetXOffset: " + targetXOffsetEntry.getDouble(0));
        System.out.println("WARNING! targetYOffset: " + targetYOffsetEntry.getDouble(0));
        System.out.println("WARNING! tArea: " + targetAreaEntry.getDouble(0));
        System.out.println("WARNING! tHorizontal: " + targetHorizontalEntry.getDouble(0));
    }
}