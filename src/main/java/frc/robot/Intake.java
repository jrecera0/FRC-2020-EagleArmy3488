package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Intake
{
    public static final int INTAKE_CAN = 12;

    public static final double SPEED_IN = 0.6;
    public static final double SPEED_OUT = 0.6;

    private WPI_VictorSPX intake;

    public Intake()
    {
        intake = new WPI_VictorSPX( Intake.INTAKE_CAN );
    }

    public void intakeBalls()
    {
        intake.set( Intake.SPEED_IN );
    }

    public void outTakeBalls()
    {
        intake.set( -Intake.SPEED_OUT );
    }

    public void stop()
    {
        intake.set( 0.0 );
    }
}