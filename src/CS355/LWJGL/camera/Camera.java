package CS355.LWJGL.camera;

import CS355.LWJGL.Point3D;

/**
 * Created by BaronVonBaerenstein on 10/30/2015.
 */
public class Camera implements ICamera {

    private ICameraDefaults cameraDefaults;

    private Point3D lookFrom;

    private Point3D lookAt;

    private Point3D upVector;

    public Camera(ICameraDefaults cameraDefaults) {
        this.initFromDefaults(cameraDefaults);
    }

    @Override
    public Point3D getLocation() {
        return this.lookFrom;
    }

    @Override
    public double getRotationAngle() {

        // Get the original forward axis
        Point3D startForwardAxis = this.cameraDefaults.getForwardAxis();

        // Get the current forward axis
        Point3D forwardAxis = this.getForwardAxis();

        // Create a right triangle where the adjacent side is the dot product of the vectors,
        // and the hypotenuse is the start vector
        double dot = this.dot(startForwardAxis, forwardAxis);
        double startMagnitude = this.magnitude(startForwardAxis);

        // Take the acos of those sides to get the angle
        // Note that the axis of rotation uses the right-hand rule,
        // so the smallest angle is always the counter-clockwise one.
        double angleRadians = Math.acos(dot / startMagnitude);

        // Convert radians to degrees
        return Math.toDegrees(angleRadians);
    }

    @Override
    public Point3D getRotationAxis() {

        // Get the original forward axis
        Point3D startForwardAxis = this.cameraDefaults.getForwardAxis();

        // Cross the original forward axis and the current forward axis to get the axis of rotation
        Point3D rotationAxis = this.cross(startForwardAxis, this.getForwardAxis());

        return rotationAxis;
    }

    @Override
    public Point3D getRightAxis() {

        // Get the forward axis
        Point3D forwardAxis = this.getForwardAxis();

        // Cross the forward axis and up vector to get the right axis
        Point3D rightAxis = this.cross(forwardAxis, this.upVector);

        // Normalize the right axis
        return this.normalize(rightAxis);
    }

    @Override
    public Point3D getUpAxis() {

        // Get the right axis
        Point3D rightAxis = this.getRightAxis();

        // Get the forward axis
        Point3D forwardAxis = this.getForwardAxis();

        // Cross the right and forward axes to get the up axis
        Point3D upAxis = this.cross(rightAxis, forwardAxis);

        // Normalize the up axis
        return this.normalize(upAxis);
    }

    @Override
    public Point3D getForwardAxis() {

        Point3D forwardAxis = this.difference(this.lookAt, this.lookFrom);

        // Normalize the forward axis
        return this.normalize(forwardAxis);
    }

    @Override
    public void setLocation(Point3D newLocation) {

        // Get the old forward axis
        Point3D forwardAxis = this.getForwardAxis();

        // Set the new location as lookFrom
        this.lookFrom = new Point3D(
                newLocation.x,
                newLocation.y,
                newLocation.z
        );

        // Reset forward axis, because it sets the lookAt value, and we want that to move with lookFrom
        this.setForwardAxis(forwardAxis);
    }

    @Override
    public void setForwardAxis(Point3D newForwardAxis) {

        this.lookAt = new Point3D(
                this.lookFrom.x + newForwardAxis.x,
                this.lookFrom.y + newForwardAxis.y,
                this.lookFrom.z + newForwardAxis.z
        );
    }

    @Override
    public void reset() {
        // If this didn't get set somehow, then set it to this because it's the best guess.
        if (this.cameraDefaults == null) {
            this.cameraDefaults = new CameraDefaults();
        }
        this.initFromDefaults(this.cameraDefaults);
    }

    /**
     * Sets the camera properties to the given defaults.
     *
     * @param cameraDefaults = the defaults to set in the camera.
     */
    private void initFromDefaults(ICameraDefaults cameraDefaults) {
        this.cameraDefaults = cameraDefaults;
        this.lookFrom = cameraDefaults.getStartLocation();

        Point3D forwardAxis = cameraDefaults.getForwardAxis();
        this.setForwardAxis(forwardAxis);

        this.upVector = cameraDefaults.getUpAxis();
    }

    /**
     * Calculates the projection of a given vector onto a plane.
     *
     * @param vector = the vector to project.
     * @param normal = the normal of the plane the vector is projected onto.
     * @return = the projection of the given vector as a Point3D.
     */
    private Point3D projectVectorToPlane(Point3D vector, Point3D normal) {

        double dot = this.dot(vector, normal);
        Point3D projectedVector = new Point3D(vector.x, vector.y, vector.z);

        projectedVector.x -= dot * normal.x;
        projectedVector.y -= dot * normal.y;
        projectedVector.z -= dot * normal.z;

        return projectedVector;
    }

    /**
     * Take the dot product of two vectors.
     *
     * @param first = the first vector in the dot product.
     * @param second = the second vector in the dot product.
     * @return a double representing the magnitude of the projection of the given vectors.
     */
    private double dot(Point3D first, Point3D second) {

        double dot = first.x * second.x;
        dot += first.y * second.y;
        dot += first.z * second.z;

        return dot;
    }

    /**
     * Take the cross product of two 3D vectors.
     *
     * @param first = the first vector in the cross product.
     * @param second = the second vector in the cross product.
     * @return a Point3D perpendicular to both given vectors with magnitude ||a|| ||b|| sin(Theta).
     */
    private Point3D cross(Point3D first, Point3D second) {

        double xComp = ( first.y * second.z ) - ( first.z * second.y );
        double yComp = -( ( first.x * second.z ) - ( first.z * second.x ) );
        double zComp = ( first.x * second.y ) - ( first.y * second.x );

        return new Point3D(xComp, yComp, zComp);
    }

    /**
     * Take the difference of two vectors.
     *
     * @param first = the vector to subtract from.
     * @param second = the vector to subtract.
     * @return a vector pointing from second to first.
     */
    private Point3D difference(Point3D first, Point3D second) {

        double xNew = first.x - second.x;
        double yNew = first.y - second.y;
        double zNew = first.z - second.z;

        return new Point3D(xNew, yNew, zNew);
    }

    /**
     * Normalize the given vector.
     *
     * @param vector = the vector to normalize.
     * @return a normalized version of the vector as a Point3D.
     */
    private Point3D normalize(Point3D vector) {

        double magnitude = this.magnitude(vector);

        return new Point3D(
                vector.x / magnitude,
                vector.y / magnitude,
                vector.z / magnitude
        );
    }

    /**
     * Get the magnitude of the given vector.
     *
     * @param vector = the vector whose magnitude is desired.
     * @return the magnitude of the given vector as a double.
     */
    private double magnitude(Point3D vector) {

        return Math.sqrt(
                vector.x * vector.x
                        + vector.y * vector.y
                        + vector.z * vector.z
        );
    }
}
