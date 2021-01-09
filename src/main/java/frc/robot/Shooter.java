package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Shooter
{
    public static final int SHOOTER_CAN = 8;
    public static final double SPEED_OUT = 0.75; //0.65
    public static final double SPEED_IN = 1.0;

    private WPI_TalonSRX shooter;

    public Shooter()
    {
        shooter = new WPI_TalonSRX( Shooter.SHOOTER_CAN );
    }

    public void shoot65()
    {
        shooter.set( -0.65);
    }

    public void shoot75()
    {
        shooter.set( -Shooter.SPEED_OUT );
    }

    public void shoot85()
    {
        shooter.set( -0.85);
    }

    public void stop()
    {
        shooter.set( 0.0 );
    }

    public void reverse()
    {
        shooter.set(Shooter.SPEED_IN);
    }

    public void fullPowerShoot()
    {
        shooter.set(-Shooter.SPEED_IN);
    }
}