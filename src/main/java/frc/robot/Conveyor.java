package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Conveyor
{
    public static final int CONVEYOR_CAN = 7;

    public static final double SPEED_IN = 0.6;
    public static final double SPEED_OUT = 0.6;

    private WPI_VictorSPX conveyor;

    public Conveyor()
    {
        conveyor = new WPI_VictorSPX( Conveyor.CONVEYOR_CAN );
    }

    public void intakeBalls()
    {
        conveyor.set( Intake.SPEED_IN );
    }

    public void outTakeBalls()
    {
        conveyor.set( -Intake.SPEED_OUT );
    }

    public void stop()
    {
        conveyor.set( 0.0 );
    }
}