package CS355.LWJGL.camera;

import CS355.LWJGL.Point3D;

/**
 * Created by BaronVonBaerenstein on 10/30/2015.
 */
public interface ICameraDefaults {

    public Point3D getStartLocation();

    public Point3D getRightAxis();

    public Point3D getUpAxis();

    public Point3D getForwardAxis();
}
