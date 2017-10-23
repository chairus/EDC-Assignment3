import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * This class
 * @author cyrusvillacampa
 */

public class PlaceIcon extends JComponent implements PlaceListener, MouseListener, MouseMotionListener {
    private int x, y;                           // X and Y coordinates of the place
    private Place place;                        // The place that this place listener is listening to
    private boolean isSelected;                 // A value that determines if this place icon is selected
    private int xDiff, yDiff;                   // Difference between the x and y coordinate of the exact position of the place icon and its location on the screen

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
    }

    /**
     * Updates this place icon's coordinate to the coordinates of the place it is listening to
     */
    private void updatePlaceIconCoordinate() {
//        this.x = place.getX();
//        this.y = place.getY();
        Point gridPosition = convertMapPositionToGridPosition(new Point(place.getX(), place.getY()));
        this.x = gridPosition.x;
        this.y = gridPosition.y;
        // To set the location of the place on the center of the bounds
        this.setBounds(this.x - (Constants.PLACE_WIDTH/2), this.y - (Constants.PLACE_HEIGHT/2), Constants.PLACE_WIDTH, Constants.PLACE_HEIGHT);
//        this.setBounds(this.x, this.y, Constants.placeWidth, Constants.placeHeight);
    }

    /**
     * Converts the position of the place on the map to a position
     * on the GUI
     * @param pos   - The position to be converted
     * @return      - The converted position
     */
    private Point convertMapPositionToGridPosition(Point pos) {
        Point gridPosition = new Point();
        gridPosition.x = pos.x + Constants.ORIGIN.x;
        if (gridPosition.x > Constants.SCREEN_SIZE.width) {
            gridPosition.x = Constants.SCREEN_SIZE.width;
        } else if (gridPosition.x < 0) {
            gridPosition.x = 0;
        }
        gridPosition.y = Constants.ORIGIN.y - pos.y;
        if (gridPosition.y > Constants.SCREEN_SIZE.height) {
            gridPosition.y = Constants.SCREEN_SIZE.height;
        } else if (gridPosition.y < 0) {
            gridPosition.y = 0;
        }
        return gridPosition;
    }

    /**
     * Converts the position of the place on the GUI to its
     * position on the map
     * @param pos   - The position to be converted
     * @return      - The converted position
     */
    private Point convertGridPositionToMapPosition(Point pos) {
        Point mapPosition = new Point();
        mapPosition.x = pos.x - Constants.ORIGIN.x;
        mapPosition.y = Constants.ORIGIN.y - pos.y;
        return mapPosition;
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
        System.out.printf("[PlaceIcon] placeChanged called%n");
        this.updatePlaceIconCoordinate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
//        System.out.println("[PlaceIcon] paintComponent called");
        super.paintComponent(g);            // Customize what to paint after calling this
        Color fillColor = selectColor();
        if (fillColor == null) {            // The place is not in one of the state {SELECTED,START,END}
            fillColor = this.getBackground();
        }
        g.setColor(fillColor);
        g.fillRect(0, 0, Constants.PLACE_WIDTH, Constants.PLACE_HEIGHT);      // Draw the rectangle relative to the upper left corner of the rectangular bound set in the method setBounds()
        Graphics2D g2 = (Graphics2D)g;
        float thickness = 3.0f;
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(thickness));
        g2.drawRect(0, 0, Constants.PLACE_WIDTH, Constants.PLACE_HEIGHT);      // Draw the rectangle relative to the upper left corner of the rectangular bound set in the method setBounds()
    }

    /**
     * Selects the color depending if a place is selected or is the start
     * place or is the end place
     * @return  - The selected color(BLUE for selected place, RED for start, GREEN for end)
     */
    private Color selectColor() {
        Color selectedColor = null;
        if (this.isSelected && !this.place.isStartPlace() && !this.place.isEndPlace()) {
            selectedColor = Color.BLUE;
        }
        if (this.place.isStartPlace()) {
            selectedColor = Color.RED;
            if (this.isSelected) {
                this.isSelected = false;
            }
        }
        if (this.place.isEndPlace()) {
            selectedColor = Color.GREEN;
            if (this.isSelected) {
                this.isSelected = false;
            }
        }
        return selectedColor;
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
        System.out.printf("Mouse clicked at(x,y): (%d,%d)%n", e.getX(), e.getY());
//        System.out.printf("isSelected before: %s%n", isSelected);
//        System.out.printf("isStartPlace: %s%n", this.place.isStartPlace());
//        System.out.printf("isEndPlace: %s%n", this.place.isEndPlace());
        if (!this.place.isStartPlace() && !this.place.isEndPlace()) {
            isSelected = !isSelected;
            repaint();
        }
//        System.out.printf("isSelected after: %s%n", isSelected);
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        System.out.printf("[ PlaceIcon ] Mouse pressed%n");
        Point screenLocation = e.getLocationOnScreen();
        Point gridPosition = convertMapPositionToGridPosition(new Point(this.place.getX(), this.place.getY()));
//        xDiff = screenLocation.x - this.place.getX();
//        yDiff = screenLocation.y - this.place.getY();
        xDiff = screenLocation.x - gridPosition.x;
        yDiff = screenLocation.y - gridPosition.y;
        System.err.printf("(xDiff, yDiff): (%d, %d)%n", xDiff, yDiff);
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.printf("[ PlaceIcon ] Mouse released%n");
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.printf("[ PlaceIcon ] Mouse entered%n");
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {
        System.out.printf("[ PlaceIcon ] Mouse exited%n");
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
        System.out.printf("[ PlaceIcon ] Mouse dragged%n");
        Point screenLocation = e.getLocationOnScreen();
//        Point originLocation = new Point(this.place.getX(), this.place.getY());
        Point originLocation = convertMapPositionToGridPosition(new Point(this.place.getX(), this.place.getY()));
//        System.out.printf("Mouse location on screen(x,y): (%d,%d)%n", screenLocation.x, screenLocation.y);
//        int dx = screenLocation.x - originLocation.x - this.xDiff - (this.mousePressedBoundsLocation.x - Constants.placeWidth/2);       // Change in x direction
//        int dy = screenLocation.y - originLocation.y - this.yDiff - (this.mousePressedBoundsLocation.y - Constants.placeHeight/2);  // Change in y direction
        int dx = screenLocation.x - originLocation.x - this.xDiff;       // Change in x direction
        int dy = screenLocation.y - originLocation.y - this.yDiff;       // Change in y direction
        System.err.printf("(dx, dy): (%d, %d)%n", dx, dy);
        System.err.printf("Old place location(x,y): (%d,%d)%n", this.place.getX(), this.place.getY());
        System.err.printf("New place location(x,y) with plus dy: (%d,%d)%n", this.place.getX() + dx, this.place.getY() + dy);
        this.place.moveBy(dx, -dy);
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
//        System.out.printf("[ PlaceIcon ] Mouse Moved%n");
//        System.out.printf("[ PlaceIcon ] Mouse position at(x,y): (%d,%d)%n", e.getX(), e.getY());
//        Point screenLocation = e.getLocationOnScreen();
//        System.out.printf("[ PlaceIcon ] Mouse location on screen(x,y): (%d,%d)%n", screenLocation.x, screenLocation.y);
    }
}
