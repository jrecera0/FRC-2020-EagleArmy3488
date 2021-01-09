package frc.robot;

public class Counter
{
    // Shooter Warmup Delay. Based on default timed 20ms polling rate
    public static final int warmupTime = 75; // +1 for every 20ms (.02s); +5 per .1s; +25 per .5s; +50 per 1s
    public static final int indexerTime = 110; // Add this to warmupTime

    private int counter;

    public Counter()
    {
        counter = 0;
    }

    public void reset()
    {
        counter = 0;
    }

    public void increment()
    {
        counter += 1;
    }

    public void decrement()
    {
        counter -= 1;
    }

    public boolean isShooterReady()
    {
        if(counter >= Counter.warmupTime)
            return true;
        else    
            return false;
    }

    public boolean isIndexerFinished()
    {
        if(counter >= Counter.indexerTime)
            return true;
        else
            return false;
    }
}