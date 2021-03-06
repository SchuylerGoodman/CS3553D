package CS355.LWJGL.camera;

import CS355.LWJGL.Point3D;

/**
 * Created by BaronVonBaerenstein on 10/30/2015.
 */
public interface ICamera {

    /**
     * Get the location of the camera in world coordinates.
     *
     * @return the location of the camera in world coordinates as a Point3D.
     */
    public Point3D getLocation();

    /**
     * Get the angle to rotate from the camera start position about the axis of rotation.
     *
     * @return the angle to rotate as a double in degrees.
     */
    public double getRotationAngle();

    /**
     * Get the axis of rotation, using the right-hand rule.
     *
     * @return the right-hand rule axis of rotation as a Point3D.
     */
    public Point3D getRotationAxis();

    /**
     * Get the camera axis that points to the right in world coordinates.
     *
     * @return the right-facing camera axis as a Point3D.
     */
    public Point3D getRightAxis();

    /**
     * Get the camera axis that points up in world coordinates.
     *
     * @return the up-facing camera axis as a Point3D.
     */
    public Point3D getUpAxis();

    /**
     * Get the camera axis that points forward in world coordinates.
     *
     * @return the forward-facing camera axis as a Point3D.
     */
    public Point3D getForwardAxis();

    /**
     * Sets the location of the camera in world coordinates.
     *
     * @param newLocation = the new location of the camera.
     */
    public void setLocation(Point3D newLocation);

    /**
     * Sets the direction of the new forward axis for the camera.
     * (i.e. rotates the camera to face this direction)
     *
     * @param newForwardAxis = the new axis for the camera to face.
     */
    public void setForwardAxis(Point3D newForwardAxis);

    /**
     * Reset the camera to its defaults.
     */
    public void reset();
}
