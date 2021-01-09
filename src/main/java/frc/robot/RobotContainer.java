package frc.robot;

import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import java.util.Arrays;
import java.util.List;

import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;

public class RobotContainer
{
    private AutoTrain autoTrain;
    private Shooter shooter;
    private Indexer indexer;
    private Counter counter;
    public RamseteCommand rCommand;

    public RobotContainer()
    {
        if(autoTrain == null) autoTrain = new AutoTrain();
        if(shooter == null) shooter = new Shooter();
        if(indexer == null) indexer = new Indexer();
        if(counter == null) counter = new Counter();
    }

    public void resetGyro()
    {
        autoTrain.resetGyro();
    }

    
    private Trajectory createTrajectory()
    {
        TrajectoryConfig config = new TrajectoryConfig( Units.feetToMeters( 6 ), Units.feetToMeters( 4 ) );
        config.setKinematics( autoTrain.getKinematics() );

        Trajectory tempTrajectory = TrajectoryGenerator.generateTrajectory(
            Arrays.asList( new Pose2d(), new Pose2d( 5, 0, new Rotation2d() ) ),

            config
        ); // WRONG TRAJECTORY; CURRENTLY USED
        
        Trajectory forwardTrajectory = TrajectoryGenerator.generateTrajectory(new Pose2d(0, 0, new Rotation2d(0)),
            List.of(), new Pose2d(Units.feetToMeters(10), 0, new Rotation2d()),
            config
        );

        Trajectory alternateTrajectory = TrajectoryGenerator.generateTrajectory(
            new Pose2d(0, 0, new Rotation2d(0)),
            List.of(
                new Translation2d(Units.feetToMeters(5), 0), // 5 Feet forward
                new Translation2d(Units.feetToMeters(5), Units.feetToMeters(5)), // Turn left and move 5 feet
                new Translation2d(Units.feetToMeters(10), Units.feetToMeters(5))
            ),
            new Pose2d(0, 0, new Rotation2d()), config);
        
        return tempTrajectory;

        /*Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
        new Pose2d(0, 0, new Rotation2d(0)),
        // Pass through these two interior waypoints, making an 's' curve path
        List.of(
            new Translation2d(1, 1),
            new Translation2d(2, -1)
        ),
        // End 3 meters straight ahead of where we started, facing forward
        new Pose2d(3, 0, new Rotation2d(0)),
        // Pass config
        config
        );*/
    }

    public Command getAutonomousCommand()
    {
        autoTrain.resetOdometry( new Pose2d() );

        Trajectory trajectory = createTrajectory();

        RamseteCommand command = new RamseteCommand(
            trajectory,
            autoTrain::getPose,
            new RamseteController( AutoTrain.kRamseteB, AutoTrain.kRamseteZeta ),
            autoTrain.getSimpleMotorFeedForward(),
            autoTrain.getKinematics(),
            autoTrain::getSpeeds,
            autoTrain.getLeftPIDCOntroller(),
            autoTrain.getRightPIDCOntroller(),
            autoTrain::tankDriveVolts,
            autoTrain
        );

        if ( rCommand == null ) rCommand = command;
        return command;
    }

    public void shootBall()
    {
        shooter.shoot75();
        if(indexer.isBallAtOut())
        {
            indexer.stop();
            counter.increment();
        }
        else
            indexer.advanceBalls();
        if(counter.isShooterReady()) // Shooter warmup is over
        {
          indexer.advanceBalls();
          if(counter.isIndexerFinished())
            counter.reset();
        }
    }
}