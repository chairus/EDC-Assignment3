import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;
import java.util.List;

/**
 * This class displays the current map on the GUI. It also contains listeners to the map.
 * @author cyrusvillacampa
 */

public class MapPanel extends JPanel implements MapListener {
    private List<Place> places;             // The list of places currently being displayed by this MapPanel
    private List<PlaceIcon> placeIcons;     // The list of listeners to each place in the list of places
    private Map map;                        // A map object that stores the current map
    private Point startPoint, endPoint;     // Start and end point of the selection box
    private Rectangle rectangleStroke;      // Represents how the rectangle was drawn


    /**
     * Constructor
     */
    public MapPanel(Map map) {
        this.map = map;
        this.places = new ArrayList<>(map.getPlaces());
        this.placeIcons = new ArrayList<>();
        this.startPoint = new Point();
        this.endPoint = new Point();
        this.rectangleStroke = Rectangle.DOWN_RIGHT;
        setLayout(null);
        setBounds(0, 0, Constants.screenSize.width, Constants.screenSize.height);
        addMouseListeners();
        addMouseMotionListeners();
    }

    /**
     * Create and add a PlaceIcon object associated to the given place
     * @param place - The place that was added
     */
    private void addPlaceIcon(Place place) {
        PlaceIcon placeIcon = new PlaceIcon(place);
        place.addListener(placeIcon);
        this.placeIcons.add(placeIcon);
        this.add(placeIcon);                                // Add a place icon associated to the added place into the JPanel
        placeIcon.paintComponent(this.getGraphics());
    }

    /**
     * Removes the place icon associated to the removed place
     * @param place - The places that were removed
     */
    private void removePlaceIcon(Place place) {
        int placeIconsIndex = 0;
        while (placeIconsIndex < placeIcons.size()) {
            PlaceIcon placeIcon = this.placeIcons.get(placeIconsIndex);
            if (areEqual(placeIcon.getPlace(), place)) {
                this.placeIcons.remove(placeIcon);
                this.remove(placeIcon);                     // Remove from the JPanel to remove it from the GUI
                placeIconsIndex -= 1;
            }
            placeIconsIndex += 1;
        }
    }

    /**
     * Removes all the places and placeIcons in this map panel.
     */
    public void removeAllPlaceAndPlaceIcon() {
        // Remove all place icons and mouse listeners
        while (!this.placeIcons.isEmpty()) {
            this.remove(this.placeIcons.get(0));
            this.placeIcons.remove(0);
        }
        // Remove all places
        while (!this.places.isEmpty()) {
            this.places.remove(0);
        }
    }

    /**
     * Updates the list of places maintained by this MapPanel object by comparing the map's places and this
     * MapPanel's object places.
     */
    private void updatePlaces() {
        List<Place> mapPlaces = new ArrayList<>(map.getPlaces());
        //////////////////////////////////////////////////////////////////
        //  REMOVE PLACES in this.places THAT ARE NOT IN THE mapPlaces  //
        //////////////////////////////////////////////////////////////////
        int placesIndex = 0;
        while (placesIndex < this.places.size()) {
            if (mapPlaces.size() == 0) {                        // This means that the remaining places in the places list are not in the map's places
                removePlaceIcon(this.places.get(placesIndex));  // Remove the placeIcon associated to this place
                this.places.remove(placesIndex);
                continue;
            }
            int mapPlacesIndex = 0;
            boolean removePlaceFlag = true;                     // This stays true if a place in this.places is not in mapPlaces list
            while (mapPlacesIndex < mapPlaces.size()) {
                if (areEqual(this.places.get(placesIndex), mapPlaces.get(mapPlacesIndex))) {
                    mapPlaces.remove(mapPlacesIndex);
                    removePlaceFlag = false;
                    break;
                }
                mapPlacesIndex += 1;
            }
            if (removePlaceFlag) {
                removePlaceIcon(this.places.get(placesIndex));  // Remove the placeIcon associated to this place
                this.places.remove(placesIndex);
                continue;
            }
            placesIndex += 1;
        }
        //////////////////////////////////////////////////////////////////////////////////
        //  ADD PLACES into this.places that are in mapPlaces but not in this.places    //
        //////////////////////////////////////////////////////////////////////////////////
        if (mapPlaces.size() != 0) {                            // If there are new places that has been added
            for (Place place: mapPlaces) {
                this.places.add(place);
                addPlaceIcon(place);
            }
        }
    }

    /**
     * Returns the place icons that have been selected by the user.
     * @return  - A list of selected place icons
     */
    public List<PlaceIcon> getSelectedPlaceIcon() {
        List<PlaceIcon> selectedPlaceIcons = new ArrayList<>();
        for (PlaceIcon placeIcon: this.placeIcons) {
            if (placeIcon.isSelected()) {
                selectedPlaceIcons.add(placeIcon);
            }
        }
        return selectedPlaceIcons;
    }

    /**
     * This method adds mouse listeners to this panel
     */
    private void addMouseListeners() {
        this.addMouseListener(new MouseListener() {
            /**
             * Invoked when the mouse button has been clicked (pressed
             * and released) on a component. It deselects all selected
             * place icon and calls repaint() if there were icons that
             * have been deselected
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.printf("[ MapPanel ] Mouse clicked at(x,y): (%d,%d)%n", e.getX(), e.getY());
                boolean hasDeselected = clearSelectedPlaceIcons();
                if (hasDeselected) repaint();
            }

            /**
             * Invoked when a mouse button has been pressed on a component.
             *
             * @param e
             */
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.printf("[ MapPanel ] Mouse Pressed%n");
                startPoint = e.getPoint();
                System.out.printf("[ MapPanel ] Mouse position at(x,y): (%f,%f)%n", startPoint.getX(), startPoint.getY());
                Point screenLocation = e.getLocationOnScreen();
                System.out.printf("[ MapPanel ] Mouse location on screen(x,y): (%d,%d)%n", screenLocation.x, screenLocation.y);
            }

            /**
             * Invoked when a mouse button has been released on a component.
             *
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.printf("[ MapPanel ] Mouse Released%n");
                System.out.printf("[ MapPanel ] Mouse position at(x,y): (%d,%d)%n", e.getX(), e.getY());
                Point screenLocation = e.getLocationOnScreen();
                System.out.printf("[ MapPanel ] Mouse location on screen(x,y): (%d,%d)%n", screenLocation.x, screenLocation.y);
                endPoint = new Point(-1,-1);      // To remove the drawn rectangle
                rectangleStroke = Rectangle.DOWN_RIGHT; //  Reset the stroke so that a new rectangle can be drawn
                repaint();
            }

            /**
             * Invoked when the mouse enters a component.
             *
             * @param e
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                // DOESN'T DO ANYTHING FOR NOW
            }

            /**
             * Invoked when the mouse exits a component.
             *
             * @param e
             */
            @Override
            public void mouseExited(MouseEvent e) {
                // DOESN'T DO ANYTHING FOR NOW
            }
        });
    }

    /**
     * This method adds mouse motion listeners
     */
    private void addMouseMotionListeners() {
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                updateRectangleStartEndPoint(e);
                System.out.printf("[ MapPanel ] Mouse Dragged%n");
                System.out.printf("[ MapPanel ] Mouse position at(x,y): (%d,%d)%n", e.getX(), e.getY());
                Point screenLocation = e.getLocationOnScreen();
                System.out.printf("[ MapPanel ] Mouse location on screen(x,y): (%d,%d)%n", screenLocation.x, screenLocation.y);
                setStartEndPoint();
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
//                System.out.printf("Mouse Moved%n");
//                System.out.printf("Mouse position at(x,y): (%d,%d)%n", e.getX(), e.getY());
//                Point screenLocation = e.getLocationOnScreen();
//                System.out.printf("Mouse location on screen(x,y): (%d,%d)%n", screenLocation.x, screenLocation.y);
            }
        });
    }

    /**
     * Updates one of the end point of the drawn rectangle
     */
    private void updateRectangleStartEndPoint(MouseEvent e) {
        switch (rectangleStroke) {
            case DOWN_RIGHT:
                endPoint = e.getPoint();
                break;
            case UP_LEFT:
                startPoint = e.getPoint();
                break;
            case UP_RIGHT:
                startPoint.y = e.getPoint().y;
                endPoint.x = e.getPoint().x;
                break;
            case DOWN_LEFT:
                startPoint.x = e.getPoint().x;
                endPoint.y = e.getPoint().y;
                break;
            default:
                break;
        }
    }

    /**
     * Deselects all selected place icons. Returns true if there are place icons
     * that has been deselected.
     * @return  - True if there were place icons that were deselected, false otherwise
     */
    private boolean clearSelectedPlaceIcons() {
        boolean hasDeselected = false;
        for (PlaceIcon placeIcon: this.placeIcons) {
            if (placeIcon.isSelected()) {
                placeIcon.setIsSelected(false);
                hasDeselected = true;
            }
        }
        return hasDeselected;
    }

    /**
     * Checks if two places are equal or not
     * @param p1 - The first place
     * @param p2 - The second place
     * @return   - True if they are equal, false otherwise
     */
    private static boolean areEqual(Place p1, Place p2) {
        if (p1.getName().equalsIgnoreCase(p2.getName()) &&
                p1.getX() == p2.getX() &&
                p1.getY() == p2.getY()) {
            return true;
        }
        return false;
    }

    /**
     * Sets the start and end point of the rectangle
     */
    private void setStartEndPoint() {
        if (endPoint.x >= 0 && endPoint.y >= 0) {
            if (startPoint.x > endPoint.x && startPoint.y > endPoint.y) {       // When mouse is dragged up-and-left/left-and-up
                if (rectangleStroke != Rectangle.UP_LEFT) {
                    Point tempPoint = startPoint;
                    startPoint = endPoint;
                    endPoint = tempPoint;
                    rectangleStroke = Rectangle.UP_LEFT;
                }
            } else if (startPoint.x > endPoint.x && startPoint.y < endPoint.y) {    // When mouse is dragged down-and-left/left-and-down
                if (rectangleStroke != Rectangle.DOWN_LEFT) {
                    // Switch their x-coordinate
                    Point tempPoint = new Point(startPoint);
                    endPoint.x = tempPoint.x;
                    rectangleStroke = Rectangle.DOWN_LEFT;
                }
            } else if (startPoint.x < endPoint.x && startPoint.y > endPoint.y) {    // When mouse is dragged up-and-right/right-and-up
                if (rectangleStroke != Rectangle.UP_RIGHT) {
                    // Switch their y-coordinate
                    Point tempPoint = new Point(startPoint);
                    endPoint.y = tempPoint.y;
                    rectangleStroke = Rectangle.UP_RIGHT;
                }
            }
        }
    }

    /**
     * Set this map object with the given argument
     * @param map - The map object to set to
     */
    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    protected void paintComponent(Graphics g) {
        System.out.printf("[ MapPanel ] paintComponent called%n");
        System.out.printf("[ MapPanel ] startPoint(x,y): (%d,%d)%n", startPoint.x, startPoint.y);
        System.out.printf("[ MapPanel ] endPoint(x,y): (%d,%d)%n", endPoint.x, endPoint.y);
        super.paintComponent(g);        // Customize what to paint after calling this
        g.setColor(Color.BLACK);
        float thickness = 2.0f;
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(thickness));
        int width = this.endPoint.x - this.startPoint.x;
        int height = this.endPoint.y - this.startPoint.y;
        g2.drawRect(this.startPoint.x, this.startPoint.y, width, height);
    }

    @Override
    public void placesChanged() {
        System.out.println("placesChanged");
        updatePlaces();
        repaint();
    }

    @Override
    public void roadsChanged() {
        System.out.println("roadsChanged");
        repaint();
    }

    @Override
    public void otherChanged() {
        System.out.println("otherChanged");
        repaint();
    }

    /**
     * State that represents if the rectangle currently being
     * drawn started from up-left/left-up, down-left/left-down,
     * up-right/right-up, down-right/right-down.
     */
    private enum Rectangle {
        UP_LEFT,
        UP_RIGHT,
        DOWN_LEFT,
        DOWN_RIGHT,
    }
}
