package CS355.LWJGL.camera;

/**
 * Created by BaronVonBaerenstein on 10/30/2015.
 */
public interface ICameraController {

    /**
     * Moves the camera to the left by amount.
     *
     * @param amount = the amount to move the camera.
     */
    public void moveLeft(int amount);

    /**
     * Moves the camera to the right by amount.
     *
     * @param amount = the amount to move the camera.
     */
    public void moveRight(int amount);

    /**
     * Moves the camera forward by amount.
     *
     * @param amount = the amount to move the camera.
     */
    public void moveForward(int amount);

    /**
     * Moves the camera backward by amount.
     *
     * @param amount = the amount to move the camera.
     */
    public void moveBackward(int amount);

    /**
     * Moves the camera up by amount.
     *
     * @param amount = the amount to move the camera.
     */
    public void moveUp(int amount);

    /**
     * Moves the camera down by amount.
     *
     * @param amount = the amount to move the camera.
     */
    public void moveDown(int amount);

    /**
     * Turns the camera to the left by amount.
     *
     * @param degrees = the number of degrees to turn the camera.
     */
    public void turnLeft(int degrees);

    /**
     * Turns the camera to the right by amount.
     *
     * @param degrees = the number of degrees to turn the camera.
     */
    public void turnRight(int degrees);

    /**
     * Reset the camera defaults.
     */
    public void resetDefaults();
}
