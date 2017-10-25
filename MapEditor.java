import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class reads a map from a file and writes it on the map object to be edited. In addition, this class
 * also writes a map onto a file.
 * @author cyrusvillacampa
 */

public class MapEditor implements ActionListener {
    public static JFrame frame;
    private MapPanel mapPanel;
    private Map map;
    private MapReaderWriter mapReaderWriter;
    private JLabel totalTripDistanceLabel;

    /**
     * Constructor
     */
    public MapEditor() {
        map = new MapImpl();
        mapReaderWriter = new MapReaderWriter();
        mapPanel = new MapPanel(map);
        map.addListener(mapPanel);
        this.totalTripDistanceLabel = new JLabel();
    }

    /**
     * Create and show the GUI
     */
    public void show() {
        createAndShowGui();
    }

    /**
     * Invoke methods that create and initialize components(menu bar and display area) in the GUI and then shows
     * GUI.
     */
    private void createAndShowGui() {
        frame = new JFrame();
        frame.setTitle("Travel Maps");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar;
        menuBar = createMenuBar();
        frame.setPreferredSize(Constants.SCREEN_SIZE);   // Set the size of the frame to full screen
        mapPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.setJMenuBar(menuBar);
        frame.add(mapPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(true);
    }

    /**
     * Creates the menu bar
     * @return - The menu bar
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;

        // Create the menu bar
        menuBar = new JMenuBar();

        // Create and add the "File" menu
        menu = createFileMenu();
        menuBar.add(menu);

        // Create and add the "Edit" menu
        menu = createEditMenu();
        menuBar.add(menu);

        return menuBar;
    }

    /**
     * Creates and build the "File" menu
     * @return - The "File" menu
     */
    private JMenu createFileMenu() {
        JMenu menu;

        // Build "File" menu
        menu = new JMenu("[File]");
        menu.setMnemonic(KeyEvent.VK_F);                    // Set keyboard key 'F' to interact with this menu item
        menu.getAccessibleContext().setAccessibleDescription("File");
        createFileMenuItems(menu);
        return menu;
    }

    /**
     * Creates the menu items of the "File" menu
     * @param menu - The menu where the menu items will be added
     */
    private void createFileMenuItems(JMenu menu) {
        JMenuItem menuItem;

        // Create the menu item "Open"
        menuItem = new JMenuItem("[Open...]");
        menuItem.setSelected(true);
        menuItem.setMnemonic(KeyEvent.VK_O);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(     // Set keyboard keys 'CTRL + O' to select this menu item
                KeyEvent.VK_O,
                ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
        // Create the menu item "Save as"
        menuItem = new JMenuItem("[Save as...]");
        menuItem.setSelected(true);
        menuItem.setMnemonic(KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(     // Set keyboard keys 'CTRL + S' to select this menu item
                KeyEvent.VK_S,
                ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
        // Create the menu item "Append"
        menuItem = new JMenuItem("[Append...]");
        menuItem.setSelected(true);
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(     // Set keyboard keys 'CTRL + A' to select this menu item
                KeyEvent.VK_A,
                ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
        // Create the menu item "Quit"
        menuItem = new JMenuItem("[Quit...]");
        menuItem.setSelected(true);
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(     // Set keyboard keys 'CTRL + Q' to select this menu item
                KeyEvent.VK_Q,
                ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);

    }

    /**
     * Creates and build the "Edit" menu
     * @return - The "Edit" menu
     */
    private JMenu createEditMenu() {
        JMenu menu;
        // Build the "Edit" menu
        menu = new JMenu("[Edit]");
        menu.setMnemonic(KeyEvent.VK_E);    // Set keyboard key 'E' to interact with this menu item
        menu.getAccessibleContext().setAccessibleDescription("Edit");
        createEditMenuItems(menu);
        return menu;
    }

    private void createEditMenuItems(JMenu menu) {
        JMenuItem menuItem;

        // Create the menu item "New place"
        menuItem = new JMenuItem("[New place...]");
        menuItem.setSelected(true);
//        menuItem.setMnemonic(KeyEvent.VK_P);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(     // Set keyboard keys 'CTRL + P' to select this menu item
//                KeyEvent.VK_P,
//                ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
        // Create the menu item "Add place"
        menuItem = new JMenuItem("[Add place...]");
        menuItem.setSelected(true);
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(     // Set keyboard keys 'CTRL + P' to select this menu item
                KeyEvent.VK_P,
                ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
        // Create the menu item "Delete place"
        menuItem = new JMenuItem("[Delete place...]");
        menuItem.setSelected(true);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        // Create the menu item "New road"
        menuItem = new JMenuItem("[New road...]");
        menuItem.setSelected(true);
//        menuItem.setMnemonic(KeyEvent.VK_R);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(     // Set keyboard keys 'CTRL + R' to select this menu item
//                KeyEvent.VK_R,
//                ActionEvent.CTRL_MASK));
//        menuItem.addActionListener(this);
        menu.add(menuItem);
        // Create the menu item "Add road"
        menuItem = new JMenuItem("[Add road...]");
        menuItem.setSelected(true);
        menuItem.setMnemonic(KeyEvent.VK_R);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(     // Set keyboard keys 'CTRL + R' to select this menu item
                KeyEvent.VK_R,
                ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
        // Create the menu item "Set start"
        menuItem = new JMenuItem("[Set start...]");
        menuItem.setSelected(true);
        menuItem.setMnemonic(KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(     // Set keyboard keys 'CTRL + T' to select this menu item
                KeyEvent.VK_T,
                ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
        // Create the menu item "Unset start"
        menuItem = new JMenuItem("[Unset start...]");
        menuItem.setSelected(true);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        // Create the menu item "Set end"
        menuItem = new JMenuItem("[Set end...]");
        menuItem.setSelected(true);
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(     // Set keyboard keys 'CTRL + E' to select this menu item
                KeyEvent.VK_N,
                ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
        // Create the menu item "Unset end"
        menuItem = new JMenuItem("[Unset end...]");
        menuItem.setSelected(true);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        // Create the menu item "Delete"
        menuItem = new JMenuItem("[Delete...]");
        menuItem.setSelected(true);
        menuItem.setMnemonic(KeyEvent.VK_D);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(         // Set keyboard keys 'CTRL + E' to select this menu item
                KeyEvent.VK_D,
                ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String actionCommand = event.getActionCommand().toLowerCase();
        try {
            if (actionCommand.contains("open")) {
                openMapFileAction();
            } else if (actionCommand.contains("save as")) {
                saveMapAction();
            } else if (actionCommand.contains("append")) {
                appendMapFileAction();
            } else if (actionCommand.contains("quit")) {
                terminateProgramAction();
            } else if (actionCommand.contains("unset start")) {
                unsetStartPlaceAction();
            } else if (actionCommand.contains("unset end")) {
                unsetEndPlaceAction();
            }  else if (actionCommand.contains("set start")) {
                setStartPlaceAction();
            } else if (actionCommand.contains("set end")) {
                setEndPlaceAction();
            } else if (actionCommand.contains("add place")) {
                addPlaceAction();
            } else if (actionCommand.contains("add road")) {
                if (map.getPlaces().size() < 2) {
                    throw new IllegalArgumentException("Please add two or more places first before adding a road.");
                }
                addRoadAction();
            } else if (actionCommand.contains("delete place")) {
                System.err.printf("Delete place selected.%n");
                deletePlaceAction();
            }
        } catch (MapFormatException e) {
            System.err.println("MapFormatException: " + e.getMessage());
            new ErrorDialog(frame, "Error", e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            new ErrorDialog(frame, "Error", e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("IllegalArgumentException: " + e.getMessage());
            new ErrorDialog(frame, "Error", e.getMessage());
        }
    }

    /**
     * Sets a selected place to be the start place.
     */
    private void setStartPlaceAction() {
        System.out.println("Item clicked: Set start");
        MapImpl.PlaceImpl startPlace = (MapImpl.PlaceImpl)extractStartEndPlace(PlaceLabel.START);
        if (startPlace != null) {
            startPlace.setStartPlace(true);
            map.setStartPlace(startPlace);
        }
    }

    /**
     * Sets a selected place to be the end place. If there are more than one
     * place selected an error dialog box will pop up that informs the user
     * that only one place can be selected.
     */
    private void setEndPlaceAction() {
        System.out.println("Item clicked: Set end");
        MapImpl.PlaceImpl endPlace = (MapImpl.PlaceImpl)extractStartEndPlace(PlaceLabel.END);
        if (endPlace != null) {
            endPlace.setEndPlace(true);
            map.setEndPlace(endPlace);
        }
    }

    /**
     * Extracts the start or end place from the selected place icons. If there are more
     * than one place selected an error dialog box will pop up that informs the user
     * that only one place can be selected.
     * @param label - The label(i.e. start/end) of the place to be extracted
     * @return      - The extracted place
     */
    private Place extractStartEndPlace(PlaceLabel label) {
        Place startEndPlace = null;
        List<PlaceIcon> selectedPlaceIcons = mapPanel.getSelectedPlaceIcon();
        List<PlaceIcon> candidatePlaceIcons = new ArrayList<>();

        System.out.printf("Number of selected places: %d%n", selectedPlaceIcons.size());
        ///////////////////////////////////////////////////////////////////////
        //  Check if a place is already a start/end place if there is then   //
        //  return a null to prevent the map panel to be repainted.          //
        ///////////////////////////////////////////////////////////////////////
        switch (label) {
            case START:
                if (map.getStartPlace() != null) {
                    new InfoDialog(frame, "Warning", "A start place is already selected. Please unset it first then select a new start place.");
                    return null;
                }
                break;
            case END:
                if (map.getEndPlace() != null) {
                    new InfoDialog(frame, "Warning", "An end place is already selected. Please unset it first then select a new end place.");
                    return null;
                }
                break;
            default:
                break;
        }
        for (PlaceIcon placeIcon: selectedPlaceIcons) {
            if (!placeIcon.getPlace().isStartPlace() && !placeIcon.getPlace().isEndPlace()) {
                candidatePlaceIcons.add(placeIcon);
                continue;
            }
        }

        if (candidatePlaceIcons.size() > 1) {        // There are multiple places currently selected
            new InfoDialog(frame, "Warning", "Only one place can be selected");
        }
        if (candidatePlaceIcons.size() == 1) {
            startEndPlace = candidatePlaceIcons.get(0).getPlace();

        }
        return startEndPlace;
    }

    /**
     * Unsets the start place
     */
    private void unsetStartPlaceAction() {
        System.out.println("Item clicked: Unset start");
        MapImpl.PlaceImpl startPlace = (MapImpl.PlaceImpl)map.getStartPlace();
        if (startPlace != null) {
            startPlace.setStartPlace(false);
            map.setStartPlace(null);
        }
    }

    /**
     * Unsets the end place
     */
    private void unsetEndPlaceAction() {
        System.out.println("Item clicked: Unset end");
        MapImpl.PlaceImpl endPlace = (MapImpl.PlaceImpl)map.getEndPlace();
        if (endPlace != null) {
            endPlace.setEndPlace(false);
            map.setEndPlace(null);
        }
    }

    /**
     * Open then read a map file and discard the current map and store the contents
     * of the read file in a new map object. Throws MapFormatException if there
     * are any problems about the contents of the file.
     * @throws MapFormatException
     * @throws IOException
     */
    private void openMapFileAction() throws MapFormatException, IOException {
        System.out.println("Item clicked: Open");
        String filename = chooseFile(FileOption.OPEN);
        if (filename != null) {
            if (filename.contains("map")) {
                //////////////////////////////////////////////////////////////////////////////////
                //  IF THERE ARE NO ERRORS IN THE MAP FILE THEN READ THE MAP FILE AGAIN         //
                //  AND STORE IT IN THE ORIGINAL MAP. THIS IS TO PREVENT FROM ADDING PARTIAL    //
                //  PLACES AND/OR ROADS IN THE ORIGINAL MAP.                                    //
                //////////////////////////////////////////////////////////////////////////////////
                Map tempMap = new MapImpl();                  // Create a temporary map
                readMap(filename, tempMap);
                map = new MapImpl();                          // Create a new map to discard the places and/or roads of the previous map
                this.mapPanel.setMap(map);
                this.mapPanel.removeAllPlaceAndPlaceIcon();
                this.mapPanel.removeAllRoadAndRoadIcon();
                this.map.addListener(this.mapPanel);          // Add back the listeners to the map
                readMap(filename, this.map);
            } else {
                throw new MapFormatException(-1, "Invalid map file format. Valid map files should have a file extension of \".map\"");
            }
        }
        System.out.printf("Map:%n%s", map.toString());
    }

    /**
     * Save the current map in a file
     * @throws IOException
     */
    private void saveMapAction() throws IOException {
        System.out.println("Item clicked: Save as");
        String filename = chooseFile(FileOption.SAVE);
        if (filename != null) {
            writeMap(filename);
        }
    }

    /**
     * Open then read a map file and appends the contents of this map file to the this
     * map object.
     * @throws MapFormatException
     * @throws IOException
     */
    private void appendMapFileAction() throws MapFormatException, IOException {
        System.out.println("Item clicked: Append");
        String filename = chooseFile(FileOption.OPEN);
        if (filename != null) {
            if (filename.contains("map")) {
                //////////////////////////////////////////////////////////////////////////////////
                //  IF THERE ARE NO ERRORS IN THE MAP FILE THEN READ THE MAP FILE AGAIN         //
                //  AND STORE IT IN THE ORIGINAL MAP. THIS IS TO PREVENT FROM ADDING PARTIAL    //
                //  PLACES AND/OR ROADS IN THE ORIGINAL MAP.                                    //
                //////////////////////////////////////////////////////////////////////////////////
                Map tempMap = new MapImpl((MapImpl)this.map);    // Create a temporary map that has the same places and roads as the original map
                readMap(filename, tempMap);
                readMap(filename, this.map);
            } else {
                throw new MapFormatException(-1, "Invalid map file format. Valid map files should have a file extension of \".map\"");
            }
        }
        System.out.printf("Map:%n%s", map.toString());
    }

    /**
     * Reads a map file and stores it in the given map argument
     * @param filename  - The name of the file to read from
     * @param map       - The map on where the places and roads read from the map file will be stored
     * @throws MapFormatException
     * @throws IOException
     */
    private void readMap(String filename, Map map) throws MapFormatException, IOException {
        Reader reader = openFileForRead(filename);
        mapReaderWriter.read(reader, map);
    }

    /**
     * Terminates the program
     */
    private void terminateProgramAction() {
        System.out.println("Item clicked: Quit");
        System.exit(0);
    }

    /**
     * Writes the current map on a file
     * @param filename  - The name of the file to write the map to
     * @throws IOException
     */
    private void writeMap(String filename) throws IOException {
        Writer writer = openFileForWrite(filename);
        mapReaderWriter.write(writer, map);
    }

    /**
     * Opens a file and return a Reader object associated to that file
     * @param filename - The name of the file to open
     * @return         - The Reader object
     */
    private Reader openFileForRead(String filename) throws FileNotFoundException {
        Reader inFile;
        FileReader fReader = new FileReader(filename);
        inFile = new BufferedReader(fReader);
        return inFile;
    }

    /**
     * Opens and/or create a file and return a Writer object associated to that file
     * @param filename - The name of the file to write on
     * @return         - The Writer object
     * @throws IOException
     */
    private Writer openFileForWrite(String filename) throws IOException {
        Writer outFile;
        FileWriter fWriter = new FileWriter(filename);
        outFile = new BufferedWriter(fWriter);
        return outFile;
    }

    /**
     * Adds a place, provided by the user, into the map object and places
     * it at the center of the map
     */
    private void addPlaceAction() {
        PlaceInputDialog addPlace = new PlaceInputDialog(frame, "Add place", "Place name");
        String placeName = addPlace.getPlaceName();
        System.out.printf("[ addPlaceAction ] Place name is \"%s\"%n", placeName);
        if (addPlace.isOkPressed()) {
            map.newPlace(placeName, 0, 0);  // Add the new place in the map object and set it's location to be at the center of the map
        }
    }

    /**
     * Deletes the selected places on the map and the roads associated
     * to the deleted places.
     */
    private void deletePlaceAction() {
        List<PlaceIcon> selectedPlaces = mapPanel.getSelectedPlaceIcon();
        for (PlaceIcon placeIcon: selectedPlaces) {
            map.deletePlace(placeIcon.getPlace());
        }
    }

    /**
     * Adds a road, provided by the user, into the map object.
     */
    private void addRoadAction() {
        RoadInputDialog addRoad = new RoadInputDialog(frame, "Add road");
        String roadName = addRoad.getRoadName();
        if (addRoad.isOkPressed()) {
            try {
                int roadLength = Integer.parseInt(addRoad.getLength());
                System.out.printf("[ addRoadAction ] Road name is \"%s\"%n", roadName);
                System.out.printf("[ addRoadAction ] Road length is %d%n", roadLength);
                mapPanel.newRoad(roadName, roadLength);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid length: " + addRoad.getLength());
            }
        }
    }

    /**
     * An input dialog class that creates and shows a dialog box with a message
     * and two input boxes where the user can type in.
     */
    private class RoadInputDialog extends JDialog implements ActionListener {
        private boolean okPressed;
        private String roadName;
        private String length;
        private JTextField roadNameTextField;
        private JTextField roadLengthTextField;
        public RoadInputDialog (JFrame owner, String title) {
            super(owner, title, true);
            this.okPressed = false;
            setLayout(new GridLayout(3,2));
            roadName = "";
            length = "";
            this.createAndShow(owner);
        }

        private void createAndShow(JFrame owner) {
            setPreferredSize(new Dimension(350, 175));
            // Label and input for road name
            JPanel roadNamePanel = new JPanel();
            JLabel roadNameLabel = new JLabel();
            roadNameLabel.setText("Road name");
            roadNameLabel.setFont(new Font(roadNameLabel.getFont().getFontName(), Font.BOLD, 15));
            roadNamePanel.add(roadNameLabel);
            this.add(roadNamePanel);
            JPanel roadNameInputPanel = new JPanel();
            roadNameTextField = new JTextField();
            roadNameTextField.setPreferredSize(new Dimension(150, 22));
            roadNameInputPanel.add(roadNameTextField);
            this.add(roadNameInputPanel);
            // Label and input for road length
            JPanel roadLengthPanel = new JPanel();
            JLabel roadLengthLabel = new JLabel();
            roadLengthLabel.setText("Road length");
            roadLengthLabel.setFont(new Font(roadNameLabel.getFont().getFontName(), Font.BOLD, 15));
            roadLengthPanel.add(roadLengthLabel);
            this.add(roadLengthPanel);
            JPanel roadLengthInputPanel = new JPanel();
            roadLengthTextField = new JTextField();
            roadLengthTextField.setPreferredSize(new Dimension(150, 22));
            roadLengthInputPanel.add(roadLengthTextField);
            this.add(roadLengthInputPanel);
            // Buttons
            JButton okButton = new JButton("[OK]");
            okButton.addActionListener(this);
            JButton cancelButton = new JButton("[Cancel]");
            cancelButton.addActionListener(this);
            this.add(okButton);
            this.add(cancelButton);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            pack();
            if (owner != null) {
                setLocationRelativeTo(owner);
            }
            setVisible(true);
        }

        /**
         * Returns the name of the road that was typed in the
         * text box
         * @return  - The name of the road as a string
         */
        public String getRoadName() {
            return this.roadName;
        }

        /**
         * Returns the length of the road
         * @return  - The length of the road
         */
        public String getLength() {
            return this.length;
        }

        /**
         * Returns true if the ok button is pressed
         * @return  - True if ok button is pressed, false otherwise
         */
        public boolean isOkPressed() {
            return this.okPressed;
        }

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("[ok]")) {
                this.roadName = roadNameTextField.getText();
                this.length = roadLengthTextField.getText();
                this.okPressed = true;
            }
            setVisible(false);
            dispose();
        }
    }

    /**
     * An input dialog class that creates and shows a dialog box with a message
     * and one input boxe where the user can type in.
     */
    private class PlaceInputDialog extends JDialog implements ActionListener {
        private JTextField placeNameTextField;
        private String placeName;
        private boolean okPressed;
        public PlaceInputDialog (JFrame owner, String title, String message) {
            super(owner, title, true);
            this.placeName = "";
            this.okPressed = false;
            setLayout(new GridLayout(2,2));
            createAndShow(owner, message);
        }

        private void createAndShow(JFrame owner, String message) {
            JPanel messagePanel = new JPanel();
            setPreferredSize(new Dimension(350, 175));
            JLabel messageLabel = new JLabel();
            // Labels
            messageLabel.setText(message);
            messageLabel.setFont(new Font(messageLabel.getFont().getFontName(), Font.BOLD, 15));
            messagePanel.add(messageLabel);
            this.add(messagePanel);
            // Inputs
            JPanel inputPanel = new JPanel();
            placeNameTextField = new JTextField();
            placeNameTextField.setPreferredSize(new Dimension(150, 22));
            inputPanel.add(placeNameTextField);
            this.add(inputPanel);
            // Buttons
            JButton okButton = new JButton("[OK]");
            okButton.addActionListener(this);
            JButton cancelButton = new JButton("[Cancel]");
            cancelButton.addActionListener(this);
            this.add(okButton);
            this.add(cancelButton);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            pack();
            if (owner != null) {
                setLocationRelativeTo(owner);
            }
            setVisible(true);
        }

        /**
         * Returns the name of the place that was typed in the
         * text box
         * @return  - The name of the place as a string
         */
        public String getPlaceName() {
            return this.placeName;
        }

        /**
         * Returns true if the ok button is pressed
         * @return  - True if ok button is pressed, false otherwise
         */
        public boolean isOkPressed() {
            return this.okPressed;
        }

        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase("[ok]")) {
                this.placeName = placeNameTextField.getText();
                this.okPressed = true;
            }
            setVisible(false);
            dispose();
        }
    }

    /**
     * An information dialog class that creates and shows a dialog box with a message.
     * @author cyrusvillacampa
     */
    private class InfoDialog extends JDialog implements ActionListener {
        public InfoDialog (JFrame owner, String title, String message) {
            super(owner, title, true);
            JPanel messagePanel = new JPanel();
            setPreferredSize(new Dimension(350, 175));
            JLabel messageLabel = new JLabel();
            // To provide a wrap around effect of the label's text and also to center align the text.
            String labelText = String.format(String.format("<html><div style=\"width:%dpx;text-align:center\">%s</div></html>",
                    250,
                    message));
            messageLabel.setText(labelText);
            messageLabel.setFont(new Font(messageLabel.getFont().getFontName(), Font.BOLD, 20));
            messagePanel.add(messageLabel);
            getContentPane().add(messagePanel);
            JPanel buttonPanel = new JPanel();
            JButton okButton = new JButton("[OK]");
            okButton.setPreferredSize(new Dimension(150, 40));
            buttonPanel.add(okButton);
            okButton.addActionListener(this);
            getContentPane().add(buttonPanel, BorderLayout.SOUTH);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            pack();
            if (owner != null) {
                setLocationRelativeTo(owner);
            }
            setVisible(true);
        }

        public void actionPerformed(ActionEvent event) {
            setVisible(false);
            dispose();
        }
    }

    /**
     * An error dialog class that creates and shows a dialog box with the error message.
     * @author cyrusvillacampa
     */
    public static class ErrorDialog extends JDialog implements ActionListener {
        public ErrorDialog (JFrame owner, String title, String message) {
            super(owner, title, true);
            JPanel messagePanel = new JPanel();
            setPreferredSize(new Dimension(350, 175));
            JLabel messageLabel = new JLabel();
            // To provide a wrap around effect of the label's text and also to center align the text.
            String labelText = String.format(String.format("<html><div style=\"width:%dpx;text-align:center\">%s</div></html>",
                                                            250,
                                                            message));
            messageLabel.setText(labelText);
            messageLabel.setFont(new Font(messageLabel.getFont().getFontName(), Font.BOLD, 20));
            messagePanel.add(messageLabel);
            getContentPane().add(messagePanel);
            JPanel buttonPanel = new JPanel();
            JButton okButton = new JButton("[OK]");
            okButton.setPreferredSize(new Dimension(150, 40));
            buttonPanel.add(okButton);
            okButton.addActionListener(this);
            getContentPane().add(buttonPanel, BorderLayout.SOUTH);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            pack();
            if (owner != null) {
                setLocationRelativeTo(owner);
            }
            setVisible(true);
        }

        public void actionPerformed(ActionEvent event) {
            setVisible(false);
            dispose();
        }
    }

    /**
     * Open up an dialog box that lets the user select the file to be open/read or save the map in a file
     * @param option - The operation to be done on the file
     * @return       - The absolute path of the selected file
     * @throws IOException
     */
    private String chooseFile(FileOption option) {
        JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int retValue = -1;
        switch (option) {
            case OPEN:  retValue = jFileChooser.showOpenDialog(frame);
                        jFileChooser.setDialogTitle("Choose a map file to read.");
                        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        break;
            case SAVE:  retValue = jFileChooser.showSaveDialog(frame);
                        jFileChooser.setDialogTitle("Choose a directory to save the map file.");
                        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                        break;
            default:    break;
        }
        if (retValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

    /**
     * An enum type that stores the label of the place(i.e. start/end)
     */
    private enum PlaceLabel {
        START,  // The place is the start place
        END,    // The place is the end place
    }

    /**
     * An enum type that stores the different operation done to a file
     */
    private enum FileOption {
        OPEN,   // Open a file
        SAVE,   // Save a file
    }

    /////////////////////
    //  FOR DEBUGGING  //
    /////////////////////
    private void printChosenRoads() {
        for (Road r: map.getRoads()) {
            if (r.isChosen()) {
                System.out.println(r.toString());
            }
        }
    }

    /**
     * MAIN
     */
    public static void main(String args[]) {
        MapEditor travelMaps = new MapEditor();
        travelMaps.show();
    }
}
