package CS355.LWJGL.camera;

import CS355.LWJGL.Point3D;

/**
 * Created by BaronVonBaerenstein on 10/30/2015.
 */
public class CameraDefaults implements ICameraDefaults {

    @Override
    public Point3D getStartLocation() {
        return new Point3D(0, 2.5, 0);
    }

    @Override
    public Point3D getRightAxis() {
        return new Point3D(1.0, 0.0, 0.0);
    }

    @Override
    public Point3D getUpAxis() {
        return new Point3D(0.0, 1.0, 0.0);
    }

    @Override
    public Point3D getForwardAxis() {
        return new Point3D(0.0, 0.0, -1.0);
    }
}
