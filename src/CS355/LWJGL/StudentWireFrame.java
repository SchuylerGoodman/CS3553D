package CS355.LWJGL;

import java.util.Iterator;

/**
 * Created by BaronVonBaerenstein on 11/9/2015.
 */
public class StudentWireFrame {

    private WireFrame model;

    private Point3D location;

    private float rotationAngle;

    private Point3D rotationAxis;

    public StudentWireFrame(WireFrame model, Point3D location, float rotationAngle, Point3D rotationAxis) {
        this.model = model;
        this.location = location;
        this.rotationAngle = rotationAngle;
        this.rotationAxis = rotationAxis;
    }

    /**
     * Gets the lines for the model.
     *
     * @return an iterator for the lines in the model.
     */
    public Iterator<Line3D> getLines() { return this.model.getLines(); }

    /**
     * Gets the location of the model in world coordinates.
     *
     * @return the location as a Point3D.
     */
    public Point3D getLocation() {
        return this.location;
    }

    /**
     * Gets the angle of rotation about the model's rotation axis.
     *
     * @return the angle as a float in degrees.
     */
    public float getRotationAngle() {
        return this.rotationAngle;
    }

    /**
     * Gets the axis of rotation for the object.
     *
     * @return the axis of rotation as a Point3D.
     */
    public Point3D getRotationAxis() {
        return this.rotationAxis;
    }
}
