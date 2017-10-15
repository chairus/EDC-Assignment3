import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.Writer;

public class MapReaderWriterTest {
    public static void main(String[] args) {
        MapReaderWriter mprw = new MapReaderWriter();
        Map map = new MapImpl();
        try {
            System.out.println("Reading file content to map...");
//            FileReader fReader = new FileReader("exampleMapWithComments.map");
             FileReader fReader = new FileReader("example.map");
            Reader in = new BufferedReader(fReader);
            mprw.read(in, map);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (MapFormatException e) {
            System.err.println(e);
            System.exit(1);
        }

        System.out.println(map);

        int totalDistance = map.getTripDistance();
        System.out.print("Total distance from " + map.getStartPlace() + " to " + map.getEndPlace());
        System.out.println(": " + totalDistance);
        for (Road r: map.getRoads()) {
            System.out.println(r + " isChosen: " + r.isChosen());
        }

        try {
            System.out.println("Writing map content to file...");
            FileWriter fWriter = new FileWriter("example.map");
            Writer out = new BufferedWriter(fWriter);
            mprw.write(out, map);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // System.out.println(map);
    }
}