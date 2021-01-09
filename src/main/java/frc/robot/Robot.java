package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot 
{
  private Controller xBoxPad;
  private DriveTrain driveTrain;
  private Intake intake;
  private Conveyor conveyor;
  private Indexer indexer;
  private Shooter shooter;
  private Climber climber;
  private Blinkin blinkin;
  private Camera camera;
  private Counter counter;
  private Limelight limelight;
  private Command autonomousCommand;
  private RobotContainer robotContainer;

  @Override
  public void robotInit() 
  {
    if ( xBoxPad == null )  xBoxPad = new Controller();
    if ( driveTrain == null )  driveTrain = new DriveTrain();
    if ( intake == null )  intake = new Intake();
    if ( conveyor == null )  conveyor = new Conveyor();
    if ( indexer == null )  indexer = new Indexer();
    if ( shooter == null )  shooter = new Shooter();
    if ( climber == null ) climber = new Climber();
    if ( blinkin == null ) blinkin = new Blinkin();
    if ( camera == null ) camera = new Camera();
    if ( counter == null ) counter = new Counter();
    if ( limelight == null ) limelight = new Limelight();
    if ( robotContainer == null ) robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic()
  {
    CommandScheduler.getInstance().run();
  }
 
  @Override
  public void teleopInit() 
  {
    if (autonomousCommand != null)
    {
      autonomousCommand.cancel();
    }
    indexer.makeEmpty();
    counter.reset();
  }

  
  @Override
  public void teleopPeriodic() 
  {
    double leftStickX = xBoxPad.getLeftStickX( true );
    double leftStickY = xBoxPad.getLeftStickY( true );

    driveTrain.arcadeDrive( leftStickX, leftStickY );

    if (!xBoxPad.getLeftBumper())
    {
      // ROBOT COLOR
      if (indexer.isBallAtOut())
        blinkin.setWarningColor();
      else
        blinkin.setNormalColor();
    
      // TAKING IN BALLS
      if ( xBoxPad.getRightTrigger( true ) > 0.0 )
      {
        intake.intakeBalls();
        conveyor.intakeBalls();
      }
      else
      {
        intake.stop();
        conveyor.stop();
      }
    
      // STOP THE CONVEYOR IF THE BALL IS AT THE INDEXER SO IT DOESNT SUCK
      if ( indexer.isBallAtIn() ) {}
        //conveyor.stop();

      // INDEX THE BALLS
      indexer.indexBalls();

      // SHOOTING
      /*shooter.shoot();
        counter.increment();
        if(counter.isShooterReady())
        {
          indexer.advanceBalls();
          // NEED TO COPY BACK LOGIC
          if(counter.isIndexerFinished())
            counter.reset();
        }*/
      if ( xBoxPad.getXButton() )
      {
        shooter.shoot75();
        counter.increment();
        if(counter.isShooterReady()) // Shooter warmup is over
        {
          indexer.advanceBalls();
          if(!indexer.isBallAtOut()) // "Neutralize" counter if ball isn't over sensor
            counter.decrement();
          else
            counter.increment();
        
          if(counter.isIndexerFinished())
            counter.reset();
        }
      }
      else if (xBoxPad.getYButton())
      {
        shooter.shoot65();
        counter.increment();
        if(counter.isShooterReady()) // Shooter warmup is over
        {
          indexer.advanceBalls();
          if(!indexer.isBallAtOut()) // "Neutralize" counter if ball isn't over sensor
            counter.decrement();
          else
            counter.increment();
        
          if(counter.isIndexerFinished())
            counter.reset();
        } 
      }
      else if (xBoxPad.getAButton())
      {
        shooter.shoot85();
        counter.increment();
        if(counter.isShooterReady()) // Shooter warmup is over
        {
          indexer.advanceBalls();
          if(!indexer.isBallAtOut()) // "Neutralize" counter if ball isn't over sensor
            counter.decrement();
          else
            counter.increment();
        
          if(counter.isIndexerFinished())
            counter.reset();
        } 
      }
      else
      {
        shooter.stop();
        counter.reset();
        if (!indexer.isBallAtIn())
          indexer.stop();
      }

      // CLIMBER
      if (xBoxPad.getRightBumper())
        climber.climbRobot();
      else
        climber.stopClimbing();

      // LIMELIGHT
      /*if(xBoxPad.getYButton())
      {
        double tx = limelight.targetXOffsetEntry.getDouble(0.0);
        double ta = limelight.targetAreaEntry.getDouble(0.0);
        limelight.setPipeline(Limelight.shooterPipeline);
        limelight.positionRobot(tx, ta, Limelight.xThresh, Limelight.areaThresh, Limelight.areaAddition, Limelight.centeringSpeed, Limelight.movementSpeed);
      }
      else
      {
        limelight.setLedState(1);
        limelight.setPipeline(Limelight.driverPipeline);
      }*/
    }

    // DEBUG CONTROLS
    if ( xBoxPad.getLeftBumper() )
    {
      blinkin.setDebugColor(); // SET DEBUG COLOR

      // FULL POWER SHOOTER
      if(xBoxPad.getXButton())
        shooter.fullPowerShoot();
      else
        shooter.stop();

      // MANUAL INDEXER
      if (xBoxPad.getDPadRight())
        indexer.advanceBalls();
      else if (xBoxPad.getDPadLeft())
        indexer.reverseBalls();
      else
        indexer.stop();
      
      // INVERTED INTAKE
      if (xBoxPad.getRightTrigger(true) > 0.0)
      {
        intake.outTakeBalls();
        conveyor.outTakeBalls();
      }
      else
      {
        intake.stop();
        conveyor.stop();
      }

      // REVERSED CLIMB
      if (xBoxPad.getRightBumper())
      {
        climber.reverseClimb();
      }
      else
      {
        climber.stopClimbing();
      }
    }
  }
  @Override
  public void autonomousInit()
  {
    autonomousCommand = robotContainer.getAutonomousCommand();

    if(autonomousCommand != null)
    {
      autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic()
  {
    if(robotContainer != null && robotContainer.rCommand.isFinished() )
    {
      driveTrain.arcadeDrive(0, 0);;
      robotContainer.shootBall();
      System.out.println("WARNING! Running!");
    }
  }
}
