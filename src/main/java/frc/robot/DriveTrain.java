package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain
{
    public static final int FRONT_LEFT_CAN = 2;
    public static final int BACK_LEFT_CAN = 1;
    public static final int FRONT_RIGHT_CAN = 3;
    public static final int BACK_RIGHT_CAN = 4;

    public static final boolean LEFT_MOTOR_INVERTED = true;
    public static final boolean RIGHT_MOTOR_INVERTED = true;

    public static final boolean LEFT_PHASE = false;
    public static final boolean RIGHT_PHASE = false;

    public static final NeutralMode DRIVE_MODE = NeutralMode.Brake;

    private WPI_TalonFX leftMaster;
    private WPI_TalonFX leftSlave;
    private WPI_TalonFX rightMaster;
    private WPI_TalonFX rightSlave;
    
    private SpeedControllerGroup leftMotors;
    private SpeedControllerGroup rightMotors;

    private DifferentialDrive driveTrain;

    public DriveTrain()
    {
        leftMaster = new WPI_TalonFX( DriveTrain.FRONT_LEFT_CAN );
        leftSlave = new WPI_TalonFX( DriveTrain.BACK_LEFT_CAN );
        rightMaster = new WPI_TalonFX( DriveTrain.BACK_RIGHT_CAN );
        rightSlave = new WPI_TalonFX( DriveTrain.FRONT_RIGHT_CAN );

        leftMotors = new SpeedControllerGroup( leftMaster, leftSlave );
        leftMotors.setInverted( DriveTrain.LEFT_MOTOR_INVERTED );

        rightMotors = new SpeedControllerGroup( rightMaster, rightSlave );
        rightMotors.setInverted( DriveTrain.RIGHT_MOTOR_INVERTED );

        driveTrain = new DifferentialDrive( leftMotors, rightMotors );

        setDriveSensorPhase();
        setDriveMode();
    }

    public void arcadeDrive( double x, double y )
    {
        driveTrain.arcadeDrive( y, -x );
    }

    private void setDriveSensorPhase()
    {
        leftMaster.setSensorPhase( DriveTrain.LEFT_PHASE );
        leftSlave.setSensorPhase( DriveTrain.LEFT_PHASE );
        rightMaster.setSensorPhase( DriveTrain.RIGHT_PHASE );
        rightSlave.setSensorPhase( DriveTrain.RIGHT_PHASE );
    }

    private void setDriveMode()
    {
        leftMaster.setNeutralMode( DriveTrain.DRIVE_MODE );
        leftSlave.setNeutralMode( DriveTrain.DRIVE_MODE );
        rightMaster.setNeutralMode( DriveTrain.DRIVE_MODE );
        rightSlave.setNeutralMode( DriveTrain.DRIVE_MODE );
    }
}