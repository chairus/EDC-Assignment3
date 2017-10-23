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

    private void drawRoad(Graphics g) {
        Point start = getGridCoordinates(this.road.firstPlace());
        Point end = getGridCoordinates(this.road.secondPlace());
        g.drawLine(start.x, start.y, end.x, end.y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("[ RoadIcon ] paintComponent called");
        super.paintComponent(g);    // Customize what to paint after calling this
        drawRoad(g);
    }

    @Override
    public void roadChanged() {
        System.out.println("[ RoadIcon ] roadChanged called");
        repaint();
    }
}
