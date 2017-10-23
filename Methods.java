import java.awt.*;

/**
 * This class holds all methods that are used across all defined classes
 * @author cyrusvillacampa
 */
public class Methods {
    /**
     * Converts the position of the place on the map to a position
     * on the GUI
     * @param pos   - The position to be converted
     * @return      - The converted position
     */
    public static Point convertMapPositionToGridPosition(Point pos) {
        Point gridPosition = new Point();
        gridPosition.x = pos.x + MapEditor.frame.getWidth()/2;
        if (gridPosition.x > MapEditor.frame.getWidth()) {
            gridPosition.x = MapEditor.frame.getWidth();
        } else if (gridPosition.x < 0) {
            gridPosition.x = 0;
        }

        gridPosition.y = MapEditor.frame.getHeight()/2 - pos.y;
        if (gridPosition.y > MapEditor.frame.getHeight()) {
            gridPosition.y = MapEditor.frame.getHeight();
        } else if (gridPosition.y < 0) {
            gridPosition.y = 0;
        }
        return gridPosition;
    }
}
