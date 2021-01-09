package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Controller
{
    public static final int CONTROLLER_PORT = 0;
    public static final double DEAD_ZONE_VALUE = 0.3;

    private XboxController xBoxPad;

    public Controller()
    {
        xBoxPad = new XboxController( CONTROLLER_PORT );
    }

    public double getLeftStickX() {  return xBoxPad.getRawAxis( 0 );  }
    public double getLeftStickY() {  return xBoxPad.getRawAxis( 1 );  }
    public double getRightStickX() {  return xBoxPad.getRawAxis( 4 );  }
    public double getRightStickY() {  return xBoxPad.getRawAxis( 5 );  }

    public double getLeftStickX( boolean applyDeadZone ) {  double val = xBoxPad.getRawAxis( 0 );  return applyDeadZone ? applyDeadZone(val) : val;  }
    public double getLeftStickY( boolean applyDeadZone ) {  double val = xBoxPad.getRawAxis( 1 );  return applyDeadZone ? applyDeadZone(val) : val;  }
    public double getRightStickX( boolean applyDeadZone ) {  double val = xBoxPad.getRawAxis( 4 );  return applyDeadZone ? applyDeadZone(val) : val;  }
    public double getRightStickY( boolean applyDeadZone ) {  double val = xBoxPad.getRawAxis( 5 );  return applyDeadZone ? applyDeadZone(val) : val;  }

    public boolean getAButton() {  return xBoxPad.getAButton();  }
    public boolean getBButton() {  return xBoxPad.getBButton();  }
    public boolean getXButton() {  return xBoxPad.getXButton();  }
    public boolean getYButton() {  return xBoxPad.getYButton();  }

    public boolean getDPadUp() {  return xBoxPad.getPOV() == 0;  }
    public boolean getDPadRight() {  return xBoxPad.getPOV() == 90;  }
    public boolean getDPadDown() {  return xBoxPad.getPOV() == 180;  }
    public boolean getDPadLeft() {  return xBoxPad.getPOV() == 270;  }

    public boolean getLeftBumper() {  return xBoxPad.getBumper( Hand.kLeft );  }
    public boolean getRightBumper() {  return xBoxPad.getBumper( Hand.kRight );  }

    public double getLeftTrigger() {  return xBoxPad.getTriggerAxis( Hand.kLeft ); }
    public double getRightTrigger() {  return xBoxPad.getTriggerAxis( Hand.kRight ); }

    public double getLeftTrigger( boolean applyDeadZone ) {  double val = xBoxPad.getTriggerAxis( Hand.kLeft );  return applyDeadZone ? applyDeadZone(val) : val;  }
    public double getRightTrigger( boolean applyDeadZone ) {  double val = xBoxPad.getTriggerAxis( Hand.kRight );  return applyDeadZone ? applyDeadZone(val) : val;  }


    private static double applyDeadZone( double in )
    {
        if ( Math.abs( in ) < DEAD_ZONE_VALUE )
            return 0.0;
        else
            return in;
    }
}