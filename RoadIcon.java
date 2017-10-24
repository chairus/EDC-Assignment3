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
        System.err.printf("First place: %s%n", this.road.firstPlace().toString());
        System.err.printf("Second place: %s%n", this.road.secondPlace().toString());
        System.out.println("Start and end point of the drawn line:");
        System.err.printf("Start(x,y): (%d,%d)%n", start.x, start.y);
        System.err.printf("End(x,y): (%d,%d)%n", end.x, end.y);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
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
        System.out.println("[ RoadIcon ] paintComponent called");
        super.paintComponent(g);    // Customize what to paint after calling this
        drawRoad(g);
    }

    @Override
    public void roadChanged() {
        System.out.println("[ RoadIcon ] roadChanged called");
//        this.setBounds(0,0, MapEditor.frame.getWidth(), MapEditor.frame.getHeight());
        repaint();
    }
}
