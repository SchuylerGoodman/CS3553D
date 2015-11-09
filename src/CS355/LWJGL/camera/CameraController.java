package CS355.LWJGL.camera;

import CS355.LWJGL.Point3D;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

/**
 * Created by BaronVonBaerenstein on 10/30/2015.
 */
public class CameraController implements ICameraController {

    private ICamera camera;

    public CameraController(ICamera camera) {
        this.camera = camera;
    }

    @Override
    public void moveLeft(int amount) {

        // Get the right axis vector (assumed to be normalized)
        Point3D rightAxis = this.camera.getRightAxis();

        // Reverse the right axis
        Point3D leftAxis = new Point3D(
                rightAxis.x * -1,
                rightAxis.y * -1,
                rightAxis.z * -1
        );

        // Move "amount" in the direction of the left axis
        this.moveInDirection(leftAxis, amount);
    }

    @Override
    public void moveRight(int amount) {

        // Get the right axis vector (assumed to be normalized)
        Point3D rightAxis = this.camera.getRightAxis();

        // Move "amount" in the direction of the right axis
        this.moveInDirection(rightAxis, amount);
    }

    @Override
    public void moveForward(int amount) {

        // Get the forward axis vector (assumed to be normalized)
        Point3D forwardAxis = this.camera.getForwardAxis();

        // Move "amount" in the direction of the forward axis
        this.moveInDirection(forwardAxis, amount);
    }

    @Override
    public void moveBackward(int amount) {

        // Get the forward axis vector (assumed to be normalized)
        Point3D forwardAxis = this.camera.getForwardAxis();

        // Reverse the forward axis
        Point3D backwardAxis = new Point3D(
                forwardAxis.x * -1,
                forwardAxis.y * -1,
                forwardAxis.z * -1
        );

        // Move "amount" in the direction of the backward axis
        this.moveInDirection(backwardAxis, amount);
    }

    @Override
    public void moveUp(int amount) {

        // Get the up axis vector (assumed to be normalized)
        Point3D upAxis = this.camera.getUpAxis();

        // Move "amount" in the direction of the up axis
        this.moveInDirection(upAxis, amount);
    }

    @Override
    public void moveDown(int amount) {

        // Get the up axis vector (assumed to be normalized)
        Point3D upAxis = this.camera.getUpAxis();

        // Reverse the up axis
        Point3D downAxis = new Point3D(
                upAxis.x * -1,
                upAxis.y * -1,
                upAxis.z * -1
        );

        // Move "amount" in the direction of the down axis
        this.moveInDirection(downAxis, amount);
    }

    private void moveInDirection(Point3D direction, int amount) {

        // Get the current camera location
        Point3D location = this.camera.getLocation();

        // Move in the direction of the right axis
        Point3D newLocation = new Point3D(
                location.x + direction.x * amount,
                location.y + direction.y * amount,
                location.z + direction.z * amount
        );

        // Update camera location
        this.camera.setLocation(newLocation);
    }

    @Override
    public void turnLeft(int degrees) {

        // Get the up axis
        Point3D upAxis = this.camera.getUpAxis();

        // Get the rotation angle in radians
        float angle = (float) Math.toRadians(degrees);

        // Initialize the rotation
        Matrix4f rotationMatrix = new Matrix4f();
        Vector3f upVector = new Vector3f();
        upVector.set(
                (float) upAxis.x,
                (float) upAxis.y,
                (float) upAxis.z
        );
        rotationMatrix.rotate(angle, upVector);

        // Get the forward axis
        Point3D forwardAxis = this.camera.getForwardAxis();
        Vector4f oldForwardVector = new Vector4f(
                (float) forwardAxis.x,
                (float) forwardAxis.y,
                (float) forwardAxis.z,
                0
        );

        // Rotate forward axis by rotation matrix
        Vector4f newForwardVector = new Vector4f();
        Matrix4f.transform(rotationMatrix, oldForwardVector, newForwardVector);

        // Initialize the new forward axis from rotated vector
        Point3D newForwardAxis = new Point3D(
                newForwardVector.x,
                newForwardVector.y,
                newForwardVector.z
        );

        // Set the forward axis
        this.camera.setForwardAxis(newForwardAxis);
    }

    @Override
    public void turnRight(int degrees) {

        // Negate degrees and then turn left
        int negDegrees = -1 * degrees;
        this.turnLeft(negDegrees);
    }

    @Override
    public void resetDefaults() {
        this.camera.reset();
    }

    private Point3D normalize(Point3D vector) {
        double x = vector.x;
        double y = vector.y;
        double z = vector.z;

        Double denominator = Math.sqrt(x*x+y*y+z*z);
        x /= denominator;
        y /= denominator;
        z /= denominator;

        return new Point3D(x, y, z);
    }
}
