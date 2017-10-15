public class MapListenerImpl implements MapListener {
    //Called whenever the number of places in the map has changed
    public void placesChanged() {
        // System.out.println("The number of places in the map has changed.");
    }

    //Called whenever the number of roads in the map has changed
    public void roadsChanged() {
        // System.out.println("The number of roads in the map has changed.");
    }

    //Called whenever something about the map has changed
    //(other than places and roads)
    public void otherChanged() {
        // System.out.println("Something in the map has changed.");
    }
}
