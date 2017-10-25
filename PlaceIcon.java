import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * This class is responsible in creating and displaying a place on the GUI
 * @author cyrusvillacampa
 */

public class PlaceIcon extends JComponent implements PlaceListener, MouseListener, MouseMotionListener {
    private MapPanel.NewRoadState newRoadState; // Holds the state of each stage in adding a new road into the map
    public int x, y;                            // X and Y coordinates of the place
    private Place place;                        // The place that this place listener is listening to
    private boolean isSelected;                 // A value that determines if this place icon is selected
    private int xDiff, yDiff;                   // Difference between the x and y coordinate of the exact position of the place icon and its location on the screen
    private PlaceIconState placeIconState;      // Represents the state of the place icon
                                                // { DESELECT, SELECT, START, END }

    @SuppressWarnings("Default constructor not available")
    private PlaceIcon() {}

    /**
     * Constructor
     * @param place - The place associated to this listener
     */
    public PlaceIcon(Place place) {
        super();
        this.place = place;
        this.updatePlaceIconCoordinate();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.isSelected = false;
        this.placeIconState = PlaceIconState.DESELECTED;
        this.newRoadState = MapPanel.NewRoadState.DONE;
    }

    /**
     * Updates this place icon's coordinate to the coordinates of the place it is listening to
     */
    private void updatePlaceIconCoordinate() {
        Point gridPosition = Methods.convertMapPositionToGridPosition(new Point(place.getX(), place.getY()));
        this.x = gridPosition.x;
        this.y = gridPosition.y;
        // To set the location of the place on the center of the bounds
        this.setBounds(this.x - (Constants.PLACE_WIDTH/2), this.y - (Constants.PLACE_HEIGHT/2), Constants.PLACE_WIDTH, Constants.PLACE_HEIGHT);
        // Uncomment to set the location of the place on the top left of the bounds
        // this.setBounds(this.x, this.y, Constants.placeWidth, Constants.placeHeight);
    }

    /**
     * Returns the place associated to this placeIcon
     * @return - The place associated to this placeIcon
     */
    public Place getPlace() {
        return this.place;
    }

    /**
     * Set the value of the isSelected field of this place icon
     * @param val   - True/False
     */
    public void setIsSelected(boolean val) {
        this.isSelected = val;
        if (val) {
            placeIconState = PlaceIconState.SELECTED;
        } else {
            placeIconState = PlaceIconState.DESELECTED;
            if (this.place.isStartPlace()) placeIconState = PlaceIconState.START;
            if (this.place.isEndPlace()) placeIconState = PlaceIconState.END;
        }
        repaint();
    }

    /**
     * Returns true if this place icon is selected
     * @return  - True if selected, false otherwise
     */
    public boolean isSelected() {
        return this.isSelected;
    }

    @Override
    public void placeChanged() {
        if (this.place.isStartPlace()) {
            placeIconState = PlaceIconState.START;
            if (this.isSelected) this.isSelected = false;
        } else if (this.place.isEndPlace()) {
            placeIconState = PlaceIconState.END;
            Point newCoordinates = Methods.convertMapPositionToGridPosition(new Point(place.getX(), place.getY()));
            if (this.isSelected && (this.x != newCoordinates.x || this.y != newCoordinates.y)) {
                placeIconState = PlaceIconState.SELECTED;
            }
            if (this.isSelected) this.isSelected = false;
        } else {
            if (this.isSelected) placeIconState = PlaceIconState.SELECTED;
            else placeIconState = PlaceIconState.DESELECTED;
        }
        this.updatePlaceIconCoordinate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);             // Customize what to paint after calling this
        Color fillColor = selectColor();
        g.setColor(fillColor);
        g.fillRect(0, 0, Constants.PLACE_WIDTH, Constants.PLACE_HEIGHT);      // Draw the rectangle relative to the upper left corner of the rectangular bound set in the method setBounds()
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(Constants.PLACE_LINE_THICKNESS));
        g2.drawRect(0, 0, Constants.PLACE_WIDTH, Constants.PLACE_HEIGHT);      // Draw the rectangle relative to the upper left corner of the rectangular bound set in the method setBounds()
    }

    /**
     * Selects the color depending if a place is selected or is the start
     * place or is the end place
     * @return  - The selected color(BLUE for selected place, RED for start, GREEN for end)
     */
    private Color selectColor() {
        Color selectedColor;
        switch (placeIconState) {
            case START:
                selectedColor = Color.RED;
                break;
            case END:
                selectedColor = Color.GREEN;
                break;
            case SELECTED:
                selectedColor = Color.BLUE;
                break;
            default:
                selectedColor = this.getBackground();
                break;
        }

        return selectedColor;
    }

    /**
     * Sets this object's "newRoadState" field
     * @param nextState - The next state
     */
    public void setNewRoadState(MapPanel.NewRoadState nextState) {
        this.newRoadState = nextState;
    }

    //////////////////////
    //  MOUSE LISTENERS //
    //////////////////////
    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component. It switches the value from
     * true to false or false to true of the "isSelected" field.
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (newRoadState == MapPanel.NewRoadState.DONE) {
            isSelected = !isSelected;
        } else {
            if (!isSelected) isSelected = !isSelected;
        }
        if (this.isSelected) {
            placeIconState = PlaceIconState.SELECTED;
        } else {
            placeIconState = PlaceIconState.DESELECTED;
            if (this.place.isStartPlace()) placeIconState = PlaceIconState.START;
            if (this.place.isEndPlace()) placeIconState = PlaceIconState.END;
        }
        repaint();
        switch (newRoadState) {
            case FIRST_PLACE:
                this.getParent().dispatchEvent(e);
                break;
            case SECOND_PLACE:
                this.getParent().dispatchEvent(e);
                break;
            default:
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
        Point screenLocation = e.getLocationOnScreen();
        Point gridPosition = Methods.convertMapPositionToGridPosition(new Point(this.place.getX(), this.place.getY()));
        xDiff = screenLocation.x - gridPosition.x;
        yDiff = screenLocation.y - gridPosition.y;
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // IGNORE
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // IGNORE
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // IGNORE
    }

    //////////////////////////////
    //  MOUSE MOTION LISTENERS  //
    //////////////////////////////

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  <code>MOUSE_DRAGGED</code> events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * <code>MOUSE_DRAGGED</code> events may not be delivered during a native
     * Drag&amp;Drop operation.
     *
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        Point screenLocation = e.getLocationOnScreen();
        Point originLocation = Methods.convertMapPositionToGridPosition(new Point(this.place.getX(), this.place.getY()));
        int dx = screenLocation.x - originLocation.x - this.xDiff;       // Change in x direction
        int dy = originLocation.y + this.yDiff - screenLocation.y;       // Change in y direction
        this.place.moveBy(dx, dy);
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        // IGNORE
    }

    /**
     * Determines which color the place icon should take place
     */
    private enum PlaceIconState {
        SELECTED,
        DESELECTED,
        START,
        END
    }
}
