import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This class
 * @author cyrusvillacampa
 */

public class PlaceIcon extends JComponent implements PlaceListener, MouseListener {
    private int x, y;       // X and Y coordinates of the place
    private Place place;    // The place that this place listener is listening to

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

    @Override
    public void placeChanged() {
        System.out.printf("[PlaceIcon] placeChanged called%n");
        this.updatePlaceIconCoordinate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("[PlaceIcon] paintComponent called");
        super.paintComponent(g);    // Customize what to paint after calling this
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, Constants.placeWidth, Constants.placeHeight);      // Draw the rectangle relative to the upper left corner of the rectangular bound set in the method setBounds()
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, Constants.placeWidth, Constants.placeHeight);      // Draw the rectangle relative to the upper left corner of the rectangular bound set in the method setBounds()
    }

    //////////////////////
    //  MOUSE LISTENERS //
    //////////////////////
    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.printf("Mouse clicked at(x,y): (%d,%d)%n", e.getX(), e.getY());
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
