import javax.swing.*;

/**
 * This class displays the current map on the GUI. It also contains listeners to the map.
 * @author cyrusvillacampa
 */

public class MapPanel extends JPanel implements MapListener {

    public MapPanel() {
        setLayout(null);    // Set to null to avoid strange behaviour
    }

    @Override
    public void placesChanged() {
        System.out.println("placesChanged");
    }

    @Override
    public void roadsChanged() {
        System.out.println("roadsChanged");
    }

    @Override
    public void otherChanged() {
        System.out.println("otherChanged");
    }
}
