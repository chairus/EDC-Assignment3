import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

/**
 * This class displays the current map on the GUI. It also contains listeners to the map.
 * @author cyrusvillacampa
 */

public class MapPanel extends JPanel implements MapListener {
    List<Place> places;          // The list of places currently being displayed by this MapPanel
    List<PlaceIcon> placeIcons; // The list of listeners to each place in the list of places
    Map map;                    // A map object that stores the current map

    /**
     * Constructor
     */
    public MapPanel(Map map) {
        this.map = map;
        this.places = new ArrayList<>(map.getPlaces());
        this.placeIcons = new ArrayList<>();
        setLayout(null);
        setBounds(0, 0, Constants.screenSize.width, Constants.screenSize.height);
        addMouseListeners();
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
                System.out.printf("Mouse clicked at(x,y): (%d,%d)%n", e.getX(), e.getY());
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
                // DOESN'T DO ANYTHING FOR NOW
            }

            /**
             * Invoked when a mouse button has been released on a component.
             *
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                // DOESN'T DO ANYTHING FOR NOW
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
     * Set this map object with the given argument
     * @param map - The map object to set to
     */
    public void setMap(Map map) {
        this.map = map;
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
}
