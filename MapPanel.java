import javax.swing.*;
import java.awt.*;
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
     * Checks if two places are equal or not
     * @param p1 - The first place
     * @param p2 - The second place
     * @return   - True if they are equal, false otherwise
     */
    private boolean areEqual(Place p1, Place p2) {
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

    /**
     * Returns the placeIcons in this MapPanel object
     * @return - The placeIcons of this MapPanel object
     */
    public List<PlaceIcon> getPlaceIcons() {
        return this.placeIcons;
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
