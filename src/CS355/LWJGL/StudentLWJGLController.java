package CS355.LWJGL;


//You might notice a lot of imports here.
//You are probably wondering why I didn't just import org.lwjgl.opengl.GL11.*
//Well, I did it as a hint to you.
//OpenGL has a lot of commands, and it can be kind of intimidating.
//This is a list of all the commands I used when I implemented my project.
//Therefore, if a command appears in this list, you probably need it.
//If it doesn't appear in this list, you probably don't.
//Of course, your milage may vary. Don't feel restricted by this list of imports.
import CS355.LWJGL.camera.*;
import org.lwjgl.input.Keyboard;

import java.security.Key;
import java.util.Iterator;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

/**
 *
 * @author Brennan Smith
 */
public class StudentLWJGLController implements CS355LWJGLController 
{

    // For camera manipulation
    private static final int MOVE_AMOUNT = 1;
    private static final int ROTATE_AMOUNT = 1;

    // For perspective projection
    private static final int FIELD_OF_VIEW_Y = 75;
    private static final float ASPECT_RATIO = ((float) LWJGLSandbox.DISPLAY_WIDTH) / LWJGLSandbox.DISPLAY_HEIGHT;

    // For orthographic projection
    private static final float BOUND_RIGHT = (float) 10.2;
    private static final float BOUND_LEFT = -1 * BOUND_RIGHT;
    private static final float BOUND_UP = BOUND_RIGHT / ASPECT_RATIO;
    private static final float BOUND_DOWN = -1 * BOUND_UP;

    // For clipping
    private static final int CLIP_NEAR = 1;
    private static final int CLIP_FAR = 200;

    //This is a model of a house.
    //It has a single method that returns an iterator full of Line3Ds.
    //A "Line3D" is a wrapper class around two Point2Ds.
    //It should all be fairly intuitive if you look at those classes.
    //If not, I apologize.
    private WireFrame model = new HouseModel();

    private ICamera camera;

    private ICameraController cameraController;

    //This method is called to "resize" the viewport to match the screen.
    //When you first start, have it be in perspective mode.
    @Override
    public void resizeGL()
    {
        this.camera = new Camera(new CameraDefaults());
        this.cameraController = new CameraController(this.camera);

        // Set the camera projection to perspective
        this.glSetPerspective();

    }

    @Override
    public void update()
    {

    }

    //This is called every frame, and should be responsible for keyboard updates.
    //An example keyboard event is captured below.
    //The "Keyboard" static class should contain everything you need to finish
    // this up.
    @Override
    public void updateKeyboard()
    {
        if (Keyboard.isKeyDown(Keyboard.KEY_A))
        {
            this.cameraController.moveLeft(MOVE_AMOUNT);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D))
        {
            this.cameraController.moveRight(MOVE_AMOUNT);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_W))
        {
            this.cameraController.moveForward(MOVE_AMOUNT);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_S))
        {
            this.cameraController.moveBackward(MOVE_AMOUNT);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_R))
        {
            this.cameraController.moveUp(MOVE_AMOUNT);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_F))
        {
            this.cameraController.moveDown(MOVE_AMOUNT);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_Q))
        {
            this.cameraController.turnLeft(ROTATE_AMOUNT);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_E))
        {
            this.cameraController.turnRight(ROTATE_AMOUNT);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_H))
        {
            this.cameraController.resetDefaults();
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_P))
        {
            this.glSetPerspective();
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_O))
        {
            this.glSetOrthographic();
        }
    }

    //This method is the one that actually draws to the screen.
    @Override
    public void render()
    {
        //This clears the screen.
        glClear(GL_COLOR_BUFFER_BIT);

        //Do your drawing here.
        glColor3f(0.5f, 0.5f, 1.0f);



        glPushMatrix();

            this.glWorldToCamera(this.camera);

            int numHousesPerSide = 50;
            this.drawStreet(numHousesPerSide);

        glPopMatrix();
    }

    private void drawStreet(int numHousesPerSide) {

        int halfNum = numHousesPerSide / 2;
        for (int i = -1 * halfNum; i < halfNum; ++i) {

            glPushMatrix();

                    // Translate next house
                    glTranslatef(20 * i, 0, -15);

                    this.drawModel(this.model);

            glPopMatrix();
        }

        for (int i = -1 * halfNum; i < halfNum; ++i) {

            glPushMatrix();

                // Translate next house
                glTranslatef(20 * i, 0, 15);

                this.drawModel(this.model);

            glPopMatrix();
        }
    }

    private void drawModel(WireFrame model) {

        Iterator<Line3D> modelIterator = model.getLines();

        // Begin drawing lines
        glBegin(GL_LINES);

        while (modelIterator.hasNext()) {
            Line3D nextLine = modelIterator.next();
            glVertex3d(nextLine.start.x, nextLine.start.y, nextLine.start.z);
            glVertex3d(nextLine.end.x, nextLine.end.y, nextLine.end.z);
        }
        glEnd();

    }

    /**
     * Tell LWJGL to move all coordinates from world to camera.
     *
     * @param camera = the camera to transform the coordinates to.
     */
    private void glWorldToCamera(ICamera camera) {

        // Get amount to rotate and axis of rotation
        // Rotate in direction opposite camera rotation
        float rotationAngle = (float) camera.getRotationAngle() * -1;
        Point3D rotationAxis = camera.getRotationAxis();
        float xRotation = (float) rotationAxis.x;
        float yRotation = (float) rotationAxis.y;
        float zRotation = (float) rotationAxis.z;

        // Get amount to translate
        // Translate in direction opposite camera translation
        Point3D cameraLocation = camera.getLocation();
        float xTranslate = (float) cameraLocation.x * -1;
        float yTranslate = (float) cameraLocation.y * -1;
        float zTranslate = (float) cameraLocation.z * -1;

        // Do the transformation
        glRotatef(rotationAngle, xRotation, yRotation, zRotation);
        glTranslatef(xTranslate, yTranslate, zTranslate);
    }

    /**
     * Tell LWJGL to move all coordinates from camera to world.
     *
     * @param camera = the camera to transform the coordinates from.
     */
    private void glCameraToWorld(ICamera camera) {

        // Get amount to translate
        Point3D cameraLocation = camera.getLocation();
        float xTranslate = (float) cameraLocation.x;
        float yTranslate = (float) cameraLocation.y;
        float zTranslate = (float) cameraLocation.z;

        // Get amount to rotate and axis of rotation
        float rotationAngle = (float) camera.getRotationAngle();
        Point3D rotationAxis = camera.getRotationAxis();
        float xRotation = (float) rotationAxis.x;
        float yRotation = (float) rotationAxis.y;
        float zRotation = (float) rotationAxis.z;

        // Do the transformation
        glTranslatef(xTranslate, yTranslate, zTranslate);
        glRotatef(rotationAngle, xRotation, yRotation, zRotation);
    }

    /**
     * Tell LWJGL to use a perspective projection for rendering.
     */
    private void glSetPerspective() {

        // Change matrix mode to alter projection matrix
        glMatrixMode(GL_PROJECTION);

        // Reset projection matrix
        glLoadIdentity();

        // Set perspective projection settings
        gluPerspective(
                FIELD_OF_VIEW_Y,
                ASPECT_RATIO,
                CLIP_NEAR,
                CLIP_FAR
        );

        // Go back to modelview matrix mode
        glMatrixMode(GL_MODELVIEW);
    }

    private void glSetOrthographic() {

        // Change matrix mode to alter projection matrix
        glMatrixMode(GL_PROJECTION);

        // Reset projection matrix
        glLoadIdentity();

        // Set orthographic projection settings
        glOrtho(
                BOUND_LEFT,
                BOUND_RIGHT,
                BOUND_DOWN,
                BOUND_UP,
                CLIP_NEAR,
                CLIP_FAR
        );

        // Go back to modelview matrix mode
        glMatrixMode(GL_MODELVIEW);
    }

}
