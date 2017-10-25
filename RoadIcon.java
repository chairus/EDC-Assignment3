import javax.swing.*;
import java.awt.*;

/**
 * This class is responsible in creating and displaying a road on the GUI
 * @author cyrusvillacampa
 */

public class RoadIcon extends JComponent implements RoadListener{
    private Road road;  // The road associated to this road icon/listener

    @SuppressWarnings("Default constructor not available")
    private RoadIcon() {}

    /**
     * Constructor
     * @param road  - The road associated to this listener
     */
    public RoadIcon(Road road) {
        super();
        this.road = road;
    }

    /**
     * Converts a map coordinates to grid coordinates
     * @param place - The place where it contains the map coordinates to be converted
     * @return      - The grid coordinates in the GUI of a place on the map
     */
    private Point getGridCoordinates(Place place) {
        return Methods.convertMapPositionToGridPosition(new Point(place.getX(), place.getY()));
    }

    /**
     * Draw a road on the GUI display
     * @param g
     */
    private void drawRoad(Graphics g) {
        Point start = getGridCoordinates(this.road.firstPlace());
        Point end = getGridCoordinates(this.road.secondPlace());
        updateBounds();
        Graphics2D g2 = (Graphics2D)g;
        Color roadColor = Color.BLACK;
        if (road.isChosen()) {
            roadColor = Color.ORANGE;
        }
        g2.setColor(roadColor);
        g2.setStroke(new BasicStroke(Constants.ROAD_LINE_THICKNESS));
        g2.drawLine(start.x, start.y, end.x, end.y);
        String roadNameAndLength = this.road.roadName() + "(" + this.road.length() + ")";
        int lineMidXCoordinate = start.x + (end.x - start.x)/2;
        int lineMidYCoordinate = start.y + (end.y - start.y)/2;
        if (start.x > end.x) {
            lineMidXCoordinate = end.x + (start.x - end.x)/2;
        }
        if (lineMidXCoordinate >= MapEditor.frame.getWidth() - 75) {    // If the x-coordinate is close to the edge of the frame
            lineMidXCoordinate -= (13*roadNameAndLength.length()/2);            // Shift the name of the road and its length to the left
        }
        if (start.y > end.y) {
            lineMidYCoordinate = end.y + (start.y - end.y)/2;
        }
        g2.setColor(Color.BLUE);
        g2.drawString(roadNameAndLength, lineMidXCoordinate, lineMidYCoordinate);
    }

    /**
     * Update the bounds of the drawn line
     */
    private void updateBounds() {
        this.setBounds(0, 0, MapEditor.frame.getWidth(), MapEditor.frame.getHeight());
    }

    /**
     * Return the road associated to this road icon
     * @return  - The road
     */
    public Road getRoad() {
        return this.road;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);    // Customize what to paint after calling this
        drawRoad(g);
    }

    @Override
    public void roadChanged() {
        repaint();
    }
}
