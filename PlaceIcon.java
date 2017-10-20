import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This class
 * @author cyrusvillacampa
 */

public class PlaceIcon extends JComponent implements PlaceListener, MouseListener {
    private int x, y;           // X and Y coordinates of the place
    private Place place;        // The place that this place listener is listening to
    private boolean isSelected; // A value that determines if this place icon is selected

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
        this.isSelected = false;
    }

    /**
     * Updates this place icon's coordinate to the coordinates of the place it is listening to
     */
    private void updatePlaceIconCoordinate() {
        this.x = place.getX();
        this.y = place.getY();
        this.setBounds(this.x, this.y, Constants.placeWidth, Constants.placeHeight);
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
        System.out.println("[PlaceIcon] paintComponent called");
        super.paintComponent(g);            // Customize what to paint after calling this
        Color fillColor = selectColor();
        if (fillColor == null) {            // The place is not in one of the state {SELECTED,START,END}
            fillColor = this.getBackground();
        }
        g.setColor(fillColor);
        g.fillRect(0, 0, Constants.placeWidth, Constants.placeHeight);      // Draw the rectangle relative to the upper left corner of the rectangular bound set in the method setBounds()
        Graphics2D g2 = (Graphics2D)g;
        float thickness = 3.0f;
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(thickness));
        g2.drawRect(0, 0, Constants.placeWidth, Constants.placeHeight);      // Draw the rectangle relative to the upper left corner of the rectangular bound set in the method setBounds()
    }

    /**
     * Selects the color depending if a place is selected or is the start
     * place or is the end place
     * @return  - The selected color(CYAN for selected place, BLUE for start, GREEN for end)
     */
    private Color selectColor() {
        Color selectedColor = null;
        if (this.isSelected && !this.place.isStartPlace() && !this.place.isEndPlace()) {
            selectedColor = Color.CYAN;
        }
        if (this.place.isStartPlace()) {
            selectedColor = Color.BLUE;
//            if (!this.isSelected) {
//                this.isSelected = true;
//            }
            if (this.isSelected) {
                this.isSelected = false;
            }
        }
        if (this.place.isEndPlace()) {
            selectedColor = Color.GREEN;
//            if (!this.isSelected) {
//                this.isSelected = true;
//            }
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
        System.out.printf("isSelected before: %s%n", isSelected);
        System.out.printf("isStartPlace: %s%n", this.place.isStartPlace());
        System.out.printf("isEndPlace: %s%n", this.place.isEndPlace());
        if (!this.place.isStartPlace() && !this.place.isEndPlace()) {
            isSelected = !isSelected;
            repaint();
        }

        System.out.printf("isSelected after: %s%n", isSelected);
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        System.out.printf("Mouse pressed%n");
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.printf("Mouse released%n");
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.printf("Mouse entered%n");
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {
        System.out.printf("Mouse exited%n");
    }
}
