package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

public class Camera {
    public static final int CAMERA_USB = 0;

    private UsbCamera camera;

    public Camera()
    {
        camera = CameraServer.getInstance().startAutomaticCapture(Camera.CAMERA_USB);
    }
}