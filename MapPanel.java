import javax.swing.*;
import java.awt.*;
import java.util.Set;

/**
 * This class displays the current map on the GUI. It also contains listeners to the map.
 * @author cyrusvillacampa
 */

public class MapPanel extends JPanel implements MapListener {
    Set<Place> places;     // The list of places currently being displayed by this MapPanel
    Map map;               // A map object that stores the current map

    /**
     * Constructor
     */
    public MapPanel(Map map) {
        this.map = map;
        this.places = map.getPlaces();
        setLayout(null);
    }

    /**
     * Creates a PlaceIcon object that would be displayed on the MapPanel
     * @param x - X coordinate
     * @param y - Y coordinate
     * @return  - The PlaceIcon object
     */
    private PlaceIcon createPlaceIcon(int x, int y) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        PlaceIcon place = new PlaceIcon(x, y);
        place.setBounds(0, 0, screenSize.width, screenSize.height);
        return place;
    }

    @Override
    public void placesChanged() {
        System.out.println("placesChanged");
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
}
