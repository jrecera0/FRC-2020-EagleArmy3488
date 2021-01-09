package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;

public class Indexer
{
    public static final int INDEXER_CAN = 9;
    public static final int INDEXER_SENSOR_IN_CAN = 13;
    public static final int INDEXER_SENSOR_OUT_CAN = 14;

    public static final double SPEED_FORWARD = 0.9;
    public static final double SPEED_REVERSE = 0.3;

    public static final int BALL_CAPACITY = 5;

    public static final RangingMode SENSOR_MODE = RangingMode.Long;
    public static final int SENSOR_RATE = 24;
    public static final double MM_PER_INCH = 25.4;

    public static final double SENSOR_IN_THRESHOLD = 9.0;
    public static final double SENSOR_OUT_THRESHOLD = 2.5;
    
    private WPI_TalonSRX indexer;

    private TimeOfFlight indexSensorIn;
    private TimeOfFlight indexSensorOut;

    private int ballCount;
    private boolean prevSensorInState;

    public Indexer()
    {
        indexer = new WPI_TalonSRX( Indexer.INDEXER_CAN );
        indexer.setNeutralMode( NeutralMode.Brake );

        indexSensorIn = new TimeOfFlight( Indexer.INDEXER_SENSOR_IN_CAN );
        indexSensorIn.setRangingMode( Indexer.SENSOR_MODE, Indexer.SENSOR_RATE );

        indexSensorOut = new TimeOfFlight( Indexer.INDEXER_SENSOR_OUT_CAN );
        indexSensorOut.setRangingMode( Indexer.SENSOR_MODE, Indexer.SENSOR_RATE );

        ballCount = 0;
        prevSensorInState = false;
    }

    public void updateBallCount()
    {
        boolean hasBallAtIn = isBallAtIn();

        if ( !prevSensorInState && hasBallAtIn )
        {
            ballCount++;
            System.out.println( "WARNING! Ball Count: " + ballCount );
            System.out.println( "WARNING! getIndexSensorIn: " + getSensorInValue());
            System.out.println( "WARNING! getIndexSensorOut: " + getSensorOutValue());
        }

        prevSensorInState = hasBallAtIn;
    }

    public void indexBalls()
    {   
        updateBallCount();

        if ( isBallAtOut() )
        {  
            makeFull();
            stop();
        }
        else if ( isBallAtIn() )
        {
            if ( !fullIndexer() )
                advanceBalls();
        }
        else
        {
            stop();
        }
    }

    public boolean isBallAtIn()  { return getSensorInValue() <= SENSOR_IN_THRESHOLD; }
    public boolean isBallAtOut()  { return getSensorOutValue() > 0.0 && getSensorOutValue() <= SENSOR_OUT_THRESHOLD; }

    public boolean fullIndexer()  { return ballCount >= Indexer.BALL_CAPACITY && isBallAtOut(); }
    public void makeFull()  { ballCount = Indexer.BALL_CAPACITY; }
    public void makeEmpty()  { ballCount = 0; }

    public double getSensorInValue()  { return indexSensorIn.getRange() / Indexer.MM_PER_INCH; }
    public double getSensorOutValue()  { return indexSensorOut.getRange() / Indexer.MM_PER_INCH; }

    public void advanceBalls()
    {
        indexer.set( Indexer.SPEED_FORWARD );
    }

    public void reverseBalls()
    {
        indexer.set( -Indexer.SPEED_REVERSE );
    }

    public void stop()
    {
        indexer.set( 0.0 );
    }

    public void printBallCount()  { System.out.println( "Ball Count:" + ballCount ); }
}