package frc.robot;

import edu.wpi.first.wpilibj.Spark;

public class Blinkin
{
    public static final int BLINKIN_PWM = 0;

    public static final double BLUE = 0.79;
    public static final double GOLD = 0.67;
    public static final double RED = 0.61;

    private Spark blinkin;

    public Blinkin()
    {
        blinkin = new Spark(Blinkin.BLINKIN_PWM);
    }

    public void setNormalColor()
    {
        blinkin.set(Blinkin.BLUE);
    }

    public void setWarningColor()
    {
        blinkin.set(Blinkin.GOLD);
    }

    public void setDebugColor()
    {
        blinkin.set(Blinkin.RED);
    }
}