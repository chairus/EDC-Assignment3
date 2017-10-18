import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;

/**
 * This class
 * @author cyrusvillacampa
 */

public class PlaceIcon extends JComponent implements PlaceListener {
    private int x, y;       // X and Y coordinates of the place
    private Place place;    // The place that this place listener is listening to

    @SuppressWarnings("Default constructor not available")
    private PlaceIcon() {}

    /**
     * Constructor
     * @param place - The place associated to this listener
     */
    public PlaceIcon(Place place) {
        this.place = place;
        this.updatePlaceIconCoordinate();
    }

    /**
     * Updates this place icon's coordinate to the coordinates of the place it is listening to
     */
    private void updatePlaceIconCoordinate() {
        this.x = place.getX();
        this.y = place.getY();
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
        System.out.printf("placeChanged() called%n");
        this.updatePlaceIconCoordinate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);    // Customize what to paint after calling this

        g.setColor(Color.GREEN);
        g.fillRect(x, y, Constants.placeWidth, Constants.placeHeight);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, Constants.placeWidth, Constants.placeHeight);
        System.out.println("paintComponent called(PlaceIcon)");
    }
}
