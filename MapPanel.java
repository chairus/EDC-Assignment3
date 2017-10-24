import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * This class displays the current map on the GUI. It also contains listeners to the map.
 * @author cyrusvillacampa
 */

public class MapPanel extends JPanel implements MapListener {
    private List<Place> places;             // The list of places currently being displayed by this MapPanel
    private List<PlaceIcon> placeIcons;     // The list of listeners to each place in the list of places
    private List<Road> roads;               // The list of roads currently being displayed by this MapPanel
    private List<RoadIcon> roadIcons;       // The list of listeners to each road in the list of roads
    private Map map;                        // A map object that stores the current map
    private Point startPoint, endPoint;     // Start and end point of the selection box
    private RectangleStroke rectangleStroke;      // Represents how the rectangle was drawn
    private Point mouseStartPosition, mouseEndPosition;    // The start and end point of the mouse when drawing the selection box
    private NewRoadState newRoadState;      // Holds the state of each stage in adding a new road into the map
    private String newRoadName;                   // Contains the name field of the road to be added
    private int newRoadLength;                    // Contains the length field of the road to be added
    private Place startPlace;               // The start place of the new road to be added
    private PlaceIcon startPlaceIcon;       // The place icon that corresponds to the selected start place
    private Place endPlace;                 // The end place of the new road to be added
    private PlaceIcon endPlaceIcon;         // The place icon that corresponds to the selected end place
    private boolean hasAddedRoad;           // A flag that tells if the new road has been successfully added


    /**
     * Constructor
     */
    public MapPanel(Map map) {
        init(map);
    }

    /**
     * Initializes the properties of this object and adds listeners.
     * @param map   - A map object that may contain places and roads
     */
    private void init(Map map) {
        this.map = map;
        this.places = new ArrayList<>(map.getPlaces());
        this.placeIcons = new ArrayList<>();
        this.roads = new ArrayList<>(map.getRoads());
        this.roadIcons = new ArrayList<>();
        this.startPoint = new Point();
        this.endPoint = new Point();
        this.rectangleStroke = RectangleStroke.DOWN_RIGHT;
        this.newRoadState = NewRoadState.DONE;
        this.startPlace = null;
        this.endPlace = null;
        this.startPlaceIcon = null;
        this.endPlaceIcon = null;
        this.hasAddedRoad = false;
        setLayout(null);
        addMouseListeners();
        addMouseMotionListeners();
        addResizeListener();
    }

    /**
     * Adds a component listener that listens if the size of the window has changed.
     */
    private void addResizeListener() {
        this.addComponentListener(new ComponentAdapter() {
            /**
             * Invoked when the component's size changes. It invokes the placeChanged
             * method of each place icon to force them to adjust their coordinates
             * according to the new display size.
             *
             * @param e
             */
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                super.componentResized(e);
                for (PlaceIcon placeIcon: placeIcons) {
                    placeIcon.placeChanged();
                }
            }
        });
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
     * @param place - The place that was removed
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
     * Updates the list of places maintained by this MapPanel object by comparing the
     * map's places and this MapPanel's object places. If a place is in the map then
     * it would be removed from a copy of the list of places in this object's map and the
     * remaining places after will be added on the map panel.
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
     * Adds a new road into the map by first asking the user to select
     * the first place, then the second place using the mouse.
     * @param roadName  - The name of the road
     * @param length    - The length of the road
     */
    public void newRoad(String roadName, int length) {
        newRoadName = roadName;
        newRoadLength = length;
        newRoadState = NewRoadState.FIRST_PLACE;
        // Deselect all selected place icons so as not to cause
        // any problem in choosing the first and second place of the road
        for (PlaceIcon placeIcon: getSelectedPlaceIcon()) {
            placeIcon.setIsSelected(false);
        }
        updatePlaceIconsNewRoadState(newRoadState);
        performAction();
    }

    /**
     * Perform some action according to the current state it is in
     */
    private void performAction() {
        switch (newRoadState) {
            case FIRST_PLACE:
                if (getCursor().getType() != Cursor.CROSSHAIR_CURSOR) {
                    setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    System.err.println("CURSOR HAS BEEN CHANGED");
                }
                break;
            case SECOND_PLACE:
                repaint();
                break;
            case ADD_ROAD:
                try {
                    map.newRoad(startPlace, endPlace, newRoadName, newRoadLength);
                } catch (Exception e) {
                    new MapEditor.ErrorDialog(MapEditor.frame, "Warning", e.getMessage());
                } finally {
                    startPlace = null;
                    endPlace = null;
                    startPlaceIcon.setIsSelected(false);
                    endPlaceIcon.setIsSelected(false);
                    startPlaceIcon = null;
                    endPlaceIcon = null;
                    System.err.println("[ MapPanel ] ROAD ADDED");
                    this.hasAddedRoad = true;
                    repaint();
                    updateState(NewRoadState.ADD_ROAD);
                    break;
                }
            case DONE:
                if (getCursor().getType() != Cursor.DEFAULT_CURSOR) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    System.err.println("CURSOR HAS BEEN CHANGED");
                }
                break;
            default:
                break;
        }
    }

    /**
     * Updates the "newRoadState" field of all place icon
     * @param nextState
     */
    private void updatePlaceIconsNewRoadState(NewRoadState nextState) {
        for (PlaceIcon placeIcon: placeIcons) {
            placeIcon.setNewRoadState(nextState);
        }
    }

    /**
     * Update the state according to it's previous state
     */
    private void updateState(NewRoadState currentState) {
        switch (currentState) {
            case FIRST_PLACE:
                if (startPlace != null) {
                    System.err.println("[ MapPanel ] updateState: USER HAS CLICKED A PLACE");
                    newRoadState = NewRoadState.SECOND_PLACE;
                    updatePlaceIconsNewRoadState(newRoadState);
                    performAction();
                }
                break;
            case SECOND_PLACE:
                if (endPlace != null) {
                    newRoadState = NewRoadState.ADD_ROAD;
                    updatePlaceIconsNewRoadState(newRoadState);
                    performAction();
                }
                break;
            case ADD_ROAD:
                if (hasAddedRoad) {
                    newRoadState = NewRoadState.DONE;
                    updatePlaceIconsNewRoadState(newRoadState);
                }
                performAction();
            default:
                break;
        }
    }

    /**
     * Create and add a PlaceIcon object associated to the given place
     * @param road  - The road that was added into the map
     */
    private void addRoadIcon(Road road) {
        RoadIcon roadIcon = new RoadIcon(road);
        road.addListener(roadIcon);
        this.roadIcons.add(roadIcon);
        this.add(roadIcon);                                // Add a place icon associated to the added place into the JPanel
        roadIcon.paintComponent(this.getGraphics());
    }

    /**
     * Removes the place icon associated to the removed place
     * @param road  - The road icon associated to road that was removed
     */
    private void removeRoadIcon(Road road) {
        int roadIconsIndex = 0;
        while (roadIconsIndex < this.roadIcons.size()) {
            RoadIcon roadIcon = this.roadIcons.get(roadIconsIndex);
            if (areEqual(roadIcon.getRoad(), road)) {
                this.roadIcons.remove(roadIcon);
                this.remove(roadIcon);                     // Remove from the JPanel to remove it from the GUI
                roadIconsIndex -= 1;
            }
            roadIconsIndex += 1;
        }
    }

    /**
     * Updates the list of roads maintained by this map panel
     */
    private void updateRoads() {
        List<Road> mapRoads = new ArrayList<>(map.getRoads());
        ///////////////////////////////////////////////////////////////
        //  REMOVE ROADS in this.roads THAT ARE NOT IN THE mapRoads  //
        ///////////////////////////////////////////////////////////////
        int roadsIndex = 0;
        while (roadsIndex < this.roads.size()) {
            if (mapRoads.size() == 0) {                        // This means that the remaining roads in the roads list are not in the map's roads
                removePlaceIcon(this.places.get(roadsIndex));  // Remove the roadIcon associated to this road
                this.roads.remove(roadsIndex);
                continue;
            }
            int mapRoadsIndex = 0;
            boolean removeRoadFlag = true;                     // This stays true if a road in this.roads is not in mapRoads list
            while (mapRoadsIndex < mapRoads.size()) {
                if (areEqual(this.roads.get(roadsIndex), mapRoads.get(mapRoadsIndex))) {
                    mapRoads.remove(mapRoadsIndex);
                    removeRoadFlag = false;
                    break;
                }
                mapRoadsIndex += 1;
            }
            if (removeRoadFlag) {
                removeRoadIcon(this.roads.get(roadsIndex));  // Remove the roadsIcon associated to this road
                this.roads.remove(roadsIndex);
                continue;
            }
            roadsIndex += 1;
        }
        //////////////////////////////////////////////////////////////////////////////
        //  ADD ROADS into this.roads that are in mapRoads but not in this.roads    //
        //////////////////////////////////////////////////////////////////////////////
        if (mapRoads.size() != 0) {                            // If there are new roads that has been added
            for (Road road: mapRoads) {
                this.roads.add(road);
                addRoadIcon(road);
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
                List<PlaceIcon> selectedPlaceIcon;
                switch (newRoadState) {
                    case DONE:
                        boolean hasDeselected = clearSelectedPlaceIcons();
                        if (hasDeselected) repaint();
                        break;
                    case FIRST_PLACE:
                        System.err.println("[ MapPanel ] mouseClicked: FIRST_PLACE");
                        selectedPlaceIcon = getSelectedPlaceIcon();
                        System.err.println("[ MapPanel ] mouseClicked: Number of selected place icon: " + selectedPlaceIcon.size());
                        for (PlaceIcon placeIcon: selectedPlaceIcon) {
                            startPlaceIcon = placeIcon;
                            startPlace = placeIcon.getPlace();
                            break;
                        }
                        updateState(NewRoadState.FIRST_PLACE);
                        break;
                    case SECOND_PLACE:
                        System.err.println("[ MapPanel ] mouseClicked: SECOND_PLACE");
                        selectedPlaceIcon = getSelectedPlaceIcon();
                        System.err.println("[ MapPanel ] mouseClicked: Number of selected place icon: " + selectedPlaceIcon.size());
                        for (PlaceIcon placeIcon: selectedPlaceIcon) {  // Look for the selected end place
                            if (placeIcon.getPlace() != startPlace) {   // Found the selected end place of the road
                                endPlaceIcon = placeIcon;
                                endPlace = placeIcon.getPlace();
                                break;
                            }
                        }
                        updateState(NewRoadState.SECOND_PLACE);
                        break;
                    default:
                        System.err.println("[ MapPanel ] mouseClicked: DEFAULT");
                        break;
                }

            }

            /**
             * Invoked when a mouse button has been pressed on a component.
             *
             * @param e
             */
            @Override
            public void mousePressed(MouseEvent e) {
                switch (newRoadState) {
                    case DONE:
                        System.out.printf("[ MapPanel ] Mouse Pressed%n");
                        startPoint = e.getPoint();
                        mouseStartPosition = e.getPoint();
                        System.out.printf("[ MapPanel ] Mouse position at(x,y): (%f,%f)%n", startPoint.getX(), startPoint.getY());
                        break;
                    default:
                        break;
                }
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
                switch (newRoadState) {
                    case DONE:
                        int width = endPoint.x - startPoint.x;
                        int height = endPoint.y - startPoint.y;
                        List<Component> selectedPlaceIcons = getIntersectedComponents(new Rectangle(startPoint.x, startPoint.y, width, height));
                        setIsSelected(selectedPlaceIcons);
                        endPoint = new Point(-1,-1);                // To remove the drawn rectangle
                        rectangleStroke = RectangleStroke.DOWN_RIGHT;     // Reset the stroke
                        repaint();
                        break;
                    default:
                        break;
                }
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
                switch (newRoadState) {
                    case DONE:
                        mouseEndPosition = e.getPoint();
                        updateRectangleStartEndPoint(e);
                        setStartEndPoint();
                        repaint();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                switch (newRoadState) {
                    case SECOND_PLACE:
                        System.err.println("[ MapPanel ] mouseMoved: SECOND PLACE");
                        repaint();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * Updates one of the vertex of the drawn rectangle
     * @param e     - The mouse event
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
     * Checks if two roads are equal or not
     * @param r1    - The first road
     * @param r2    - The second road
     * @return      - True if they are equal, false otherwise
     */
    private static boolean areEqual(Road r1, Road r2) {
        if (areEqual(r1.firstPlace(), r2.firstPlace()) &&
                areEqual(r1.secondPlace(), r2.secondPlace()) &&
                r1.roadName().equalsIgnoreCase(r2.roadName()) &&
                r1.length() == r2.length()) {
            return true;
        }
        return false;
    }

    /**
     * Sets correctly the start and end point of the rectangle depending on how it was
     * drawn and also update the state on how the rectangle is drawn
     */
    private void setStartEndPoint() {
        if (mouseStartPosition.x > mouseEndPosition.x && mouseStartPosition.y > mouseEndPosition.y) {       // When mouse is dragged up-and-left/left-and-up
            if (rectangleStroke != RectangleStroke.UP_LEFT) {
                startPoint = new Point(mouseEndPosition);
                endPoint = new Point(mouseStartPosition);
                rectangleStroke = RectangleStroke.UP_LEFT;
            }
        } else if (mouseStartPosition.x > mouseEndPosition.x && mouseStartPosition.y < mouseEndPosition.y) {    // When mouse is dragged down-and-left/left-and-down
            if (rectangleStroke != RectangleStroke.DOWN_LEFT) {
                // Switch their x-coordinate
                Point tempMouseEndPosition = new Point(mouseEndPosition);
                Point tempMouseStartPosition = new Point(mouseStartPosition);
                startPoint.x = tempMouseEndPosition.x;
                endPoint.x = tempMouseStartPosition.x;
                rectangleStroke = RectangleStroke.DOWN_LEFT;
            }
        } else if (mouseStartPosition.x < mouseEndPosition.x && mouseStartPosition.y > mouseEndPosition.y) {    // When mouse is dragged up-and-right/right-and-up
            if (rectangleStroke != RectangleStroke.UP_RIGHT) {
                // Switch their y-coordinate
                Point tempMouseEndPosition = new Point(mouseEndPosition);
                Point tempMouseStartPosition = new Point(mouseStartPosition);
                startPoint.y = tempMouseEndPosition.y;
                endPoint.y = tempMouseStartPosition.y;
                rectangleStroke = RectangleStroke.UP_RIGHT;
            }
        } else if (mouseStartPosition.x < mouseEndPosition.x && mouseStartPosition.y < mouseEndPosition.y) {
            if (rectangleStroke != RectangleStroke.DOWN_RIGHT) {
                startPoint = new Point(mouseStartPosition);
                endPoint = new Point(mouseEndPosition);
                rectangleStroke = RectangleStroke.DOWN_RIGHT;
            }
        }
    }

    /**
     * Returns the place icon components inside a rectangle
     * @param selectionBox  - A rectangle/square on where to check if there
     *                        are any place icon inside it
     * @return              - A list of place icon inside the dawn rectangle
     */
    private List<Component> getIntersectedComponents(Rectangle selectionBox) {
        List<Component> selectedComponents = new ArrayList<>();
        Component[] placeIconComponents = this.getComponents();
        for (Component comp: placeIconComponents) {
            if (selectionBox.intersects(comp.getBounds())) {
                selectedComponents.add(comp);
            }
        }
        return selectedComponents;
    }

    /**
     * Sets the isSelected property of each of the components in the list
     * @param components    - A list of components that has an isSelected boolean property
     */
    private void setIsSelected(List<Component> components) {
        for (Component comp: components) {
            if (comp instanceof PlaceIcon) {
                PlaceIcon placeIcon = (PlaceIcon)comp;
                placeIcon.setIsSelected(true);
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
//        System.out.printf("[ MapPanel ] paintComponent called%n");
        super.paintComponent(g);        // Customize what to paint after calling this
        Graphics2D g2 = (Graphics2D)g;
        switch (newRoadState) {
            case DONE:
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(Constants.SELECTION_BOX_THICKNESS));
                int width = this.endPoint.x - this.startPoint.x;
                int height = this.endPoint.y - this.startPoint.y;
                g2.drawRect(this.startPoint.x, this.startPoint.y, width, height);
                break;
            case SECOND_PLACE:
                g.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(Constants.ROAD_LINE_THICKNESS));
//                Point mousePosition = getMousePosition();
//                while (mousePosition == null) { mousePosition = getMousePosition(); }
                Point mousePosition = MouseInfo.getPointerInfo().getLocation();
                mousePosition.x -= getLocationOnScreen().x;
                mousePosition.y -= getLocationOnScreen().y;
                g2.drawLine(startPlaceIcon.x, startPlaceIcon.y, mousePosition.x, mousePosition.y);
                break;
            default:
                break;
        }

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
        updateRoads();
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
    private enum RectangleStroke {
        UP_LEFT,
        UP_RIGHT,
        DOWN_LEFT,
        DOWN_RIGHT,
    }

    /**
     * State that describes the behaviour of each stage when
     * adding a new road to the map.
     */
    public enum NewRoadState {
        FIRST_PLACE,
        SECOND_PLACE,
        ADD_ROAD,
        DONE,
    }
}
