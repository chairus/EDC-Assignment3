import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;

public class PlaceIcon extends JComponent implements PlaceListener {
    private int x, y;       // X and Y coordinates of the place
    private Place place;    // The place that this place listener is listening to

    @SuppressWarnings("Default constructor not available")
    private PlaceIcon() {}

    /**
     * Constructor
     * @param x - X coordinate
     * @param y - Y coordinate
     */
    public PlaceIcon(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void placeChanged() {
        System.out.printf("placeChanged() called%n");

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);    // Customize what to paint after calling this

        g.setColor(Color.GREEN);
        g.fillRect(x, y, Constants.placeWidth, Constants.placeHeight);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, Constants.placeWidth, Constants.placeHeight);
    }
}
