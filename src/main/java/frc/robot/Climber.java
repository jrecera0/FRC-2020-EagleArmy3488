package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Climber
{
    public static final int CLIMBER_MASTER_CAN = 5;
    public static final int CLIMBER_SLAVE_CAN = 6;

    public static final double SPEED = 0.7;

    private WPI_VictorSPX climberMaster;
    private WPI_VictorSPX climberSlave;

    public Climber()
    {
        climberMaster = new WPI_VictorSPX(Climber.CLIMBER_MASTER_CAN);
        climberSlave = new WPI_VictorSPX(Climber.CLIMBER_SLAVE_CAN);
    }

    public void climbRobot()
    {
        climberMaster.set(Climber.SPEED);
        climberSlave.set(-Climber.SPEED);
    }

    public void stopClimbing()
    {
        climberMaster.set(0);
        climberSlave.set(0);
    }

    public void reverseClimb()
    {
        climberMaster.set(-Climber.SPEED);
        climberSlave.set(Climber.SPEED);
    }
}