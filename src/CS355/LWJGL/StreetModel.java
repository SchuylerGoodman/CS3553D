package CS355.LWJGL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BaronVonBaerenstein on 11/9/2015.
 */
public class StreetModel {

    private static final int SPACE_BETWEEN_HOUSES = 20;

    private List<StudentWireFrame> streetObjects;

    public StreetModel() {

        this.streetObjects = new ArrayList<>();

        this.addHouseModel(
                new Point3D(0.0, 0.0, -15.0),
                0,
                new Point3D(0.0, 1.0, 0.0)
        );

        // Add 4 houses on the street going to the right
        /**int numForwardStreet = 4;
        for (int i = 0; i < numForwardStreet; ++i) {
            Point3D location = new Point3D()
            this.addHouseModel();
        }**/

        // Add 2 houses perpendicular facing in

        // Add 4 more houses on the opposite side of the street
    }

    private void addHouseModel(Point3D location, float rotationAngle, Point3D rotationAxis) {

        // Initialize a new StudentWireFrame with a HouseModel
        HouseModel houseModel = new HouseModel();
        StudentWireFrame studentHouseModel = new StudentWireFrame(
                houseModel,
                location,
                rotationAngle,
                rotationAxis
        );

        // Add the model to the objects list
        this.streetObjects.add(studentHouseModel);
    }

}
