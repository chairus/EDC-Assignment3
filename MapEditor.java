import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;

/**
 * This class reads a map from a file and writes it on the map object to be edited. In addition, this class
 * also writes a map onto a file.
 * @author cyrusvillacampa
 */

public class MapEditor implements ActionListener {
    private JFrame frame;
    private Map map;
    private MapReaderWriter mapReaderWriter;

    /**
     * Constructor
     */
    public MapEditor() {
        map = new MapImpl();
        mapReaderWriter = new MapReaderWriter();
    }

    /**
     * Create and show the GUI
     */
    public void show() {
        init();
    }

    /**
     * Initializes the Graphical User Interface
     */
    private void init() {
        frame = new JFrame();
        frame.setTitle("Travel Maps");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200,800);
        prepareAndShowGui();
    }

    /**
     * Invoke methods that create and initialize components(menu bar and display area) in the GUI and then shows
     * GUI.
     */
    private void prepareAndShowGui() {
        JMenuBar menuBar;
        menuBar = createMenuBar();
        frame.setJMenuBar(menuBar);
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

        /**
         * Create the menu item "Open"
         */
        menuItem = new JMenuItem("[Open...]");
        menuItem.setSelected(true);
        menuItem.setMnemonic(KeyEvent.VK_O);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(     // Set keyboard keys 'CTRL + O' to select this menu item
                KeyEvent.VK_O,
                ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
        /**
         * Create the menu item "Save as"
         */
        menuItem = new JMenuItem("[Save as...]");
        menuItem.setSelected(true);
        menuItem.setMnemonic(KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(     // Set keyboard keys 'CTRL + S' to select this menu item
                KeyEvent.VK_S,
                ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
        /**
         * Create the menu item "Append"
         */
        menuItem = new JMenuItem("[Append...]");
        menuItem.setSelected(true);
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(     // Set keyboard keys 'CTRL + A' to select this menu item
                KeyEvent.VK_A,
                ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
        /**
         * Create the menu item "Quit"
         */
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

        /**
         * Create the menu item "New place"
         */
        menuItem = new JMenuItem("[New place...]");
        menuItem.setSelected(true);
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(     // Set keyboard keys 'CTRL + P' to select this menu item
                KeyEvent.VK_P,
                ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
        /**
         * Create the menu item "New road"
         */
        menuItem = new JMenuItem("[New road...]");
        menuItem.setSelected(true);
        menuItem.setMnemonic(KeyEvent.VK_R);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(     // Set keyboard keys 'CTRL + R' to select this menu item
                KeyEvent.VK_R,
                ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
        /**
         * Create the menu item "Append"
         */
        menuItem = new JMenuItem("[Set start...]");
        menuItem.setSelected(true);
        menuItem.setMnemonic(KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(     // Set keyboard keys 'CTRL + T' to select this menu item
                KeyEvent.VK_T,
                ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
        /**
         * Create the menu item "Quit"
         */
        menuItem = new JMenuItem("[Unset start...]");
        menuItem.setSelected(true);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        /**
         * Create the menu item "Set end"
         */
        menuItem = new JMenuItem("[Set end...]");
        menuItem.setSelected(true);
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(     // Set keyboard keys 'CTRL + E' to select this menu item
                KeyEvent.VK_N,
                ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
        /**
         * Create the menu item "Unset end"
         */
        menuItem = new JMenuItem("[Unset end...]");
        menuItem.setSelected(true);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        /**
         * Create the menu item "Delete"
         */
        menuItem = new JMenuItem("[Delete...]");
        menuItem.setSelected(true);
        menuItem.setMnemonic(KeyEvent.VK_D);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(     // Set keyboard keys 'CTRL + E' to select this menu item
                KeyEvent.VK_D,
                ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
    }

    public void actionPerformed(ActionEvent event) {
        String actionCommand = event.getActionCommand().toLowerCase();
        try {
            if (actionCommand.contains("open")) {
                System.out.println("Item clicked: Open");
                String filename = chooseFile(FileOption.OPEN);
                if (filename != null) {
                    readMap(filename);
                }
                System.out.printf("Map:%n%s", map.toString());
            } else if (actionCommand.contains("save as")) {
                System.out.println("Item clicked: Save as");
                String filename = chooseFile(FileOption.SAVE);
                if (filename != null) {
                    writeMap(filename);
                }
            } else if (actionCommand.contains("append")) {
                System.out.println("Item clicked: Append");
            } else if (actionCommand.contains("quit")) {
                System.out.println("Item clicked: Quit");
                System.exit(0);
            }
        } catch (MapFormatException e) {
            new ErrorDialog(frame, "Error", e.getMessage());     // Show a dialog box with a message
            map = new MapImpl();
        } catch (IOException e) {
            new ErrorDialog(frame, "Error", e.getMessage());     // Show a dialog box with a message
            map = new MapImpl();
        }
    }

    /**
     * Reads a map and stores it in this map object
     * @param filename  - The name of the file
     * @throws MapFormatException
     * @throws IOException
     */
    private void readMap(String filename) throws MapFormatException, IOException {
        Reader reader = openFileForRead(filename);
        mapReaderWriter.read(reader, map);
    }

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

    private Writer openFileForWrite(String filename) throws IOException {
        Writer outFile;
        FileWriter fWriter = new FileWriter(filename);
        outFile = new BufferedWriter(fWriter);
        return outFile;
    }

    /**
     * An error dialog class that creates and shows a dialog box with the error message.
     * @author cyrusvillacampa
     */
    private class ErrorDialog extends JDialog implements ActionListener {
        public ErrorDialog (JFrame owner, String title, String message) {
            super(owner, title, true);
            if (owner != null) {
                setLocationRelativeTo(owner);
            }
            JPanel messagePanel = new JPanel();
            setPreferredSize(new Dimension(300, 150));
            JLabel messageLabel = new JLabel(message);
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
            setVisible(true);
        }

        public void actionPerformed(ActionEvent event) {
            setVisible(false);
            dispose();
        }
    }

    /**
     * Open up an dialog box that lets the user select the file to be open/read or save the map in a file
     * @param
     * @return - The absolute path of the selected file
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
     * An enum type that stores the different operation done to a file
     */
    private enum FileOption {
        OPEN,   // Open a file
        SAVE,   // Save a file
    }

    /**
     * MAIN
     */
    public static void main(String args[]) {
        MapEditor travelMaps = new MapEditor();
        travelMaps.show();
    }
}
