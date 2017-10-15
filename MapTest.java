import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Set;

public class MapTest {


    public static void main (String []args)
    {

        // Test a initialise map
        System.out.println("== Test a =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        MapImpl currentMap = new MapImpl();
        System.out.println((currentMap.toString().equals("")));

        // Test b initialise place and toString for map
        System.out.println("== Test b =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        Place place1 = currentMap.newPlace("Sone", 10, 11);
        System.out.println(currentMap.toString().equals("PLACE Sone(10,11)\n"));

        // Test c test getter methods for place
        System.out.println("== Test c =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        System.out.println(place1.getName().equals("Sone"));
        System.out.println(place1.getX() == 10);
        System.out.println(place1.getY() == 11);
        System.out.println(place1.toString().equals("Sone(10,11)"));

        // Test d for moveby method
        System.out.println("== Test d =================");
        System.out.println("#Re-Paints Place/Road ==  1/0");
        place1.moveBy(100, 11);
        System.out.println(place1.toString().equals("Sone(110,22)"));
        System.out.println(place1.getX() == 110);
        System.out.println(place1.getY() == 22);

        // Test e add another place
        System.out.println("== Test e =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        Place place2 = currentMap.newPlace("Stwo", 20, 22);
        System.out.println(currentMap.toString().equals("PLACE Sone(110,22)\nPLACE Stwo(20,22)\n"));

        // Test f test getter methods for place
        System.out.println("== Test f =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        System.out.println(place2.getName().equals("Stwo"));
        System.out.println(place2.getX() == 20);
        System.out.println(place2.getY() == 22);
        System.out.println(place2.toString().equals("Stwo(20,22)"));

        // Test g adding a road and getting the two roads
        System.out.println("== Test g =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        Road road1 = currentMap.newRoad(place1, place2, "road12", 111);
        System.out.println("Want : [Sone(road12:111)Stwo]");
        Set<Road> roadSet1 = place1.toRoads();
        System.out.println("Get  : " + roadSet1);
        System.out.println(currentMap.toString().equals("PLACE Sone(110,22)\nPLACE Stwo(20,22)\nROAD Sone(road12:111)Stwo\n"));
        System.out.println("Want : [Sone(road12:111)Stwo]");
        Set<Road> roadSet2 = place2.toRoads();
        System.out.println("Get  : " + roadSet2);

        // Test f checking road getters, first/second place, to string
        System.out.println("== Test f =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        System.out.println(roadSet1.contains(road1) && roadSet1.size() == 1);
        System.out.println(roadSet2.contains(road1) && roadSet2.size() == 1);
        System.out.println(road1.firstPlace() == place1);
        System.out.println(road1.secondPlace() == place2);
        System.out.println(road1.toString().equals("Sone(road12:111)Stwo"));

        // Test i checking toString Method
        System.out.println("== Test i =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        System.out.println(currentMap.toString().equals("PLACE Sone(110,22)\nPLACE Stwo(20,22)\nROAD Sone(road12:111)Stwo\n"));

        // Test j add new place
        System.out.println("== Test j =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        Place place3 = currentMap.newPlace("saaa", -22, 109);
        System.out.println("Want --- :\n" + "PLACE saaa(-22,109)\nPLACE Sone(110,22)\nPLACE Stwo(20,22)\nROAD Sone(road12:111)Stwo\n");
        System.out.println("Get  --- : (order doesn't matter)\n" +  currentMap.toString());

        // Test k delete place
        System.out.println("== Test k =================");
        System.out.println("#Re-Paints Place/Road ==  1/0");
        currentMap.deletePlace(place3);
        System.out.println(currentMap.toString().equals("PLACE Sone(110,22)\nPLACE Stwo(20,22)\nROAD Sone(road12:111)Stwo\n"));

        // Test l add new place and new road, and to roads methods
        System.out.println("== Test l =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        Place place4 = currentMap.newPlace("s4", -22, 109);
        Road road2 = currentMap.newRoad(place4, place1, "road14", 222);

        System.out.println("Want : [Sone(road12:111)Stwo, Sone(road14:222)s4]");
        roadSet1 = place1.toRoads();
        System.out.println("Get  : " + roadSet1);

        System.out.println("Want --- :\n" + "PLACE s4(-22,109)\nPLACE Sone(110,22)\nPLACE Stwo(20,22)\nROAD Sone(road12:111)Stwo\nROAD Sone(road14:222)s4\n");
        System.out.println("Get  --- : (order doesn't matter)\n" +  currentMap.toString());

        System.out.println("Want : [Sone(road14:222)s4]");
        Set<Road> roadSet3 = place4.toRoads();
        System.out.println("Get  : " + roadSet3);

        // Test m road deletion
        System.out.println("== Test m =================");
        System.out.println("#Re-Paints Place/Road ==  0/1");
        currentMap.deleteRoad(road2);

        System.out.println("Want : [Sone(road12:111)Stwo]");
        roadSet1 = place1.toRoads();
        System.out.println("Get  : " + roadSet1);

        System.out.println("Want --- :\n" + "PLACE s4(-22,109)\nPLACE Sone(110,22)\nPLACE Stwo(20,22)\nROAD Sone(road12:111)Stwo\n");
        System.out.println("Get  --- : (order doesn't matter)\n" +  currentMap.toString());

        System.out.println("Want : []");
        roadSet3 = place4.toRoads();
        System.out.println("Get  : " + roadSet3);

        // Test n add new place
        System.out.println("== Test n =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        Place place5 = currentMap.newPlace("s5", 100, 200);

        System.out.println("Want --- :\n" + "PLACE s4(-22,109)\nPLACE s5(100,200)\nPLACE Sone(110,22)\nPLACE Stwo(20,22)\nROAD Sone(road12:111)Stwo\n");
        System.out.println("Get  --- : (order doesn't matter)\n" +  currentMap.toString());

        // Test o add new road
        System.out.println("== Test o =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        Road road3 = currentMap.newRoad(place1, place5, "road15", 333);

        System.out.println("Want : [Sone(road12:111)Stwo, Sone(road15:333)s5]");
        roadSet1 = place1.toRoads();
        System.out.println("Get  : " + roadSet1);

        System.out.println("Want --- :\n" + "PLACE s4(-22,109)\nPLACE s5(100,200)\nPLACE Sone(110,22)\nPLACE Stwo(20,22)\nROAD Sone(road12:111)Stwo\nROAD Sone(road15:333)s5\n");
        System.out.println("Get  --- : (order doesn't matter)\n" +  currentMap.toString());

        System.out.println("Want : [Sone(road15:333)s5]");
        Set<Road> roadSet4 = place5.toRoads();
        System.out.println("Get  : " + roadSet4);

        // Test p add new road
        System.out.println("== Test p =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        Road road4 = currentMap.newRoad(place2, place5, "road52", 444);

        System.out.println("Want : [Sone(road15:333)s5, Stwo(road52:444)s5]");
        roadSet4 = place5.toRoads();
        System.out.println("Get  : " + roadSet4);

        System.out.println("Want --- :\n" + "PLACE s4(-22,109)\nPLACE s5(100,200)\nPLACE Sone(110,22)\nPLACE Stwo(20,22)\nROAD Sone(road12:111)Stwo\nROAD Sone(road15:333)s5\nROAD Stwo(road52:444)s5\n");
        System.out.println("Get  --- : (order doesn't matter)\n" +  currentMap.toString());

        System.out.println("Want : [Sone(road12:111)Stwo, Stwo(road52:444)s5]");
        roadSet2 = place2.toRoads();
        System.out.println("Get  : " + roadSet2);

        // Test q find place
        System.out.println("== Test q =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        System.out.println(currentMap.findPlace("s5").toString().equals(place5.toString()));

        // Test r find place with get places
        System.out.println("== Test r =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        Set<Place> placeSet2 = currentMap.getPlaces();
        Iterator<Place> placeItr = placeSet2.iterator();
        System.out.println("Want --\ns4(-22,109)\ns5(100,200)\nSone(110,22)\nStwo(20,22)");
        System.out.println("Get  -- (order doesn't matter)");
        while(placeItr.hasNext())
        {
            System.out.println(placeItr.next().toString());
        }

        // Test s delete roads and places
        System.out.println("== Test s =================");
        System.out.println("#Re-Paints Place/Road ==  1/2");
        currentMap.deletePlace(place5);
        currentMap.deleteRoad(road4);
        currentMap.deleteRoad(road3);

        System.out.println("Want --- :\n" + "PLACE s4(-22,109)\nPLACE Sone(110,22)\nPLACE Stwo(20,22)\nROAD Sone(road12:111)Stwo\n");
        System.out.println("Get  --- : (order doesn't matter)\n" +  currentMap.toString());

        // Test t test place getters
        System.out.println("== Test t =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        System.out.println(place1.getName().equals("Sone"));
        System.out.println(place1.getX() == 110);
        System.out.println(place1.getY() == 22);
        System.out.println(place1.toString().equals("Sone(110,22)"));

        System.out.println("Want : [Sone(road12:111)Stwo]");
        roadSet1 = place1.toRoads();
        System.out.println("Get  : " + roadSet1);

        // Test u test place getters
        System.out.println("== Test u =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        System.out.println(place2.getName().equals("Stwo"));
        System.out.println(place2.getX() == 20);
        System.out.println(place2.getY() == 22);
        System.out.println(place2.toString().equals("Stwo(20,22)"));

        System.out.println("Want : [Sone(road12:111)Stwo]");
        roadSet2 = place2.toRoads();
        System.out.println("Get  : " + roadSet2);

        // Test v test move by
        System.out.println("== Test v =================");
        System.out.println("#Re-Paints Place/Road ==  1/0");
        place1.moveBy(29, -91);
        System.out.println(place1.getX() == 139);
        System.out.println(place1.getY() == -69);
        System.out.println(place1.toString().equals("Sone(139,-69)"));

        // Test w illegal argument exception duplicate
        System.out.println("== Test w =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        try
        {
            Place place6 = currentMap.newPlace("Sone", 40, 44);
            System.out.println(false + " Duplicate Place Input: Sone(40,44)");
            return;
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(true + " " + e);
            System.out.println("Exception Successfully Caught");
            System.out.println("Want --- :\n" + "PLACE s4(-22,109)\nPLACE Sone(139,-69)\nPLACE Stwo(20,22)\nROAD Sone(road12:111)Stwo\n");
            System.out.println("Get  --- : (order doesn't matter)\n" +  currentMap.toString());
        }

        // Test x illegal argument exception illegal start character
        System.out.println("== Test x =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        try
        {
            Place place6 = currentMap.newPlace("2S", 40, 44);
            System.out.println(false + " Illegal Place Input: 2S(40,44)");
            return;
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(true + " " + e);
            System.out.println("Exception Successfully Caught");
            System.out.println("Want --- :\n" + "PLACE s4(-22,109)\nPLACE Sone(139,-69)\nPLACE Stwo(20,22)\nROAD Sone(road12:111)Stwo\n");
            System.out.println("Get  --- : (order doesn't matter)\n" +  currentMap.toString());
        }

        // Test y illegal argument illegal characters
        System.out.println("== Test y =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        try
        {
            Place place6 = currentMap.newPlace("$#", 40, 44);
            System.out.println(false + " Illegal Place Input: $#(40,44)");
            return;
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(true + " " + e);
            System.out.println("Exception Successfully Caught");
            System.out.println("Want --- :\n" + "PLACE s4(-22,109)\nPLACE Sone(139,-69)\nPLACE Stwo(20,22)\nROAD Sone(road12:111)Stwo\n");
            System.out.println("Get  --- : (order doesn't matter)\n" +  currentMap.toString());
        }

        // Test z illegal argument illegal characters
        System.out.println("== Test z =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        try
        {
            Place place6 = currentMap.newPlace("_A", 40, 44);
            System.out.println(false + " Illegal Place Input: _A(40,44)");
            return;
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(true + " " + e);
            System.out.println("Exception Successfully Caught");
            System.out.println("Want --- :\n" + "PLACE s4(-22,109)\nPLACE Sone(139,-69)\nPLACE Stwo(20,22)\nROAD Sone(road12:111)Stwo\n");
            System.out.println("Get  --- : (order doesn't matter)\n" +  currentMap.toString());
        }

        // Test A illegal argument illegal characters
        System.out.println("== Test A =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        try
        {
            Place place6 = currentMap.newPlace("", 40, 44);
            System.out.println(false + " Illegal Place Input: (40,44)");
            return;
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(true + " " + e);
            System.out.println("Exception Successfully Caught");
            System.out.println("Want --- :\n" + "PLACE s4(-22,109)\nPLACE Sone(139,-69)\nPLACE Stwo(20,22)\nROAD Sone(road12:111)Stwo\n");
            System.out.println("Get  --- : (order doesn't matter)\n" +  currentMap.toString());
        }

        // Test B illegal argument illegal characters
        System.out.println("== Test B =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        try
        {
            Place place6 = currentMap.newPlace(null, 40, 44);
            System.out.println(false + " Illegal Place Input: null(40,44)");
            return;
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(true + " " + e);
            System.out.println("Exception Successfully Caught");
            System.out.println("Want --- :\n" + "PLACE s4(-22,109)\nPLACE Sone(139,-69)\nPLACE Stwo(20,22)\nROAD Sone(road12:111)Stwo\n");
            System.out.println("Get  --- : (order doesn't matter)\n" +  currentMap.toString());
        }

        // Test C add duplicate road, delete road, add places with negative length
        System.out.println("== Test C =================");
        System.out.println("#Re-Paints Place/Road ==  0/1");
        Place place6 = currentMap.newPlace("s6", 50, 55);
        currentMap.deleteRoad(road1);
        System.out.println("Want --- :\n" + "PLACE s4(-22,109)\nPLACE s6(50,55)\nPLACE Sone(139,-69)\nPLACE Stwo(20,22)\n");
        System.out.println("Get  --- : (order doesn't matter)\n" +  currentMap.toString());

        try
        {
            Road road6 = currentMap.newRoad(place1, place2, "fakeRoad", -1);
            System.out.println(false + " Illegal Road Input: Sone(fakeRoad:-1)two");
            return;
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(true + " " + e);
            System.out.println("Exception Successfully Caught");
        }

        // Test D illegal first character
        System.out.println("== Test D =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        try
        {
            Road road6 = currentMap.newRoad(place1, place2, "1fakeRoad", 1);
            System.out.println(false + " Illegal Road Input: Sone(1fakeRoad:1)two");
            return;
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(true + " " + e);
            System.out.println("Exception Successfully Caught");
        }

        // Test E illegal character
        System.out.println("== Test E =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        try
        {
            Road road6 = currentMap.newRoad(place1, place2, "fakeRoad#$", 1);
            System.out.println(false + " Illegal Road Input: Sone(fakeRoad#$:1)two");
            return;
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(true + " " + e);
            System.out.println("Exception Successfully Caught");
        }

        // Test F add road from s6 to s1, illegal road
        System.out.println("== Test F =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        Road road5 = currentMap.newRoad(place6, place1, "", 94);
        road1 = currentMap.newRoad(place1, place2, "road12", 111);

        try
        {
            Road road6 = currentMap.newRoad(place1, place5, "road15", 333);
            System.out.println(false + " Illegal Road Input: Sone(road15:333)s5");
            return;
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(true + " " + e);
            System.out.println("Exception Successfully Caught");
        }

        System.out.println("Want : [Sone(:94)s6, Sone(road12:111)Stwo]");
        roadSet1 = place1.toRoads();
        System.out.println("Get  : " + roadSet1);

        System.out.println("Want --- :\n" + "PLACE s4(-22,109)\nPLACE s6(50,55)\nPLACE Sone(139,-69)\nPLACE Stwo(20,22)\nROAD Sone(road12:111)Stwo\nROAD Sone(:94)s6\n");
        System.out.println("Get  --- : (order doesn't matter)\n" +  currentMap.toString());

        // Test G deletion, adding non-existant place when adding road
        System.out.println("== Test G =================");
        System.out.println("#Re-Paints Place/Road ==  1/1");

        try
        {
            Road road6 = currentMap.newRoad(place2, place5, "road52", 444);
            System.out.println(false + " Illegal Road Input: Stwo(road52:44)s5");
            return;
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(true + " " + e);
            System.out.println("Exception Successfully Caught");
        }
        currentMap.deletePlace(place6);
        currentMap.deleteRoad(road5);

        System.out.println("Want --- :\n" + "PLACE s4(-22,109)\nPLACE Sone(139,-69)\nPLACE Stwo(20,22)\nROAD Sone(road12:111)Stwo\n");
        System.out.println("Get  --- : (order doesn't matter)\n" +  currentMap.toString());

        // Test H null start/end places, illegal setting
        System.out.println("== Test H =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");

        try
        {
            currentMap.setEndPlace(place5);
            currentMap.setStartPlace(place5);

            System.out.println(false + " Start/End Not on Map");
            return;

        }
        catch (IllegalArgumentException e)
        {
            System.out.println(true + " " + e);
            System.out.println("Exception Successfully Caught");
            System.out.println(currentMap.getStartPlace() == null);
            System.out.println(currentMap.getEndPlace() == null);
        }


        // Test I Set start place
        System.out.println("== Test I =================");
        System.out.println("#Re-Paints Place/Road ==  1/0");
        currentMap.setStartPlace(place1);
        System.out.println(currentMap.getStartPlace().toString().equals("Sone(139,-69)"));
        System.out.println(currentMap.getEndPlace() == null);

        // Test J Set end place
        System.out.println("== Test J =================");
        System.out.println("#Re-Paints Place/Road ==  1/0");
        currentMap.setEndPlace(place2);
        System.out.println(currentMap.getEndPlace().toString().equals("Stwo(20,22)"));
        System.out.println(currentMap.getStartPlace().toString().equals("Sone(139,-69)"));

        // Test K Set start place null
        System.out.println("== Test K =================");
        System.out.println("#Re-Paints Place/Road ==  1/0");
        currentMap.setStartPlace(null);
        System.out.println(currentMap.getEndPlace().toString().equals("Stwo(20,22)"));
        System.out.println(currentMap.getStartPlace() == null);

        // Test L Set end place null
        System.out.println("== Test L =================");
        System.out.println("#Re-Paints Place/Road ==  1/0");
        currentMap.setEndPlace(null);
        System.out.println(currentMap.getEndPlace() == null);
        System.out.println(currentMap.getStartPlace() == null);

        // Test M set end place
        System.out.println("== Test M =================");
        System.out.println("#Re-Paints Place/Road ==  1/0");
        currentMap.setEndPlace(place1);
        System.out.println(currentMap.getEndPlace().toString().equals("Sone(139,-69)"));
        System.out.println(currentMap.getStartPlace() == null);

        // Test N set start place to same as end place
        System.out.println("== Test N =================");
        System.out.println("#Re-Paints Place/Road ==  1/0");
        currentMap.setStartPlace(place1);
        System.out.println(currentMap.getEndPlace().toString().equals("Sone(139,-69)"));
        System.out.println(currentMap.getStartPlace().toString().equals("Sone(139,-69)"));

        // Test O map toString
        System.out.println("== Test O =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        System.out.println("Want --- :\n" + "PLACE s4(-22,109)\nPLACE Sone(139,-69)\nPLACE Stwo(20,22)\nROAD Sone(road12:111)Stwo\nSTART Sone\nEND Sone\n");
        System.out.println("Get  --- : (order doesn't matter)\n" +  currentMap.toString());

        // Test P delete place1
        System.out.println("== Test P =================");
        System.out.println("#Re-Paints Place/Road ==  1/1");
        currentMap.deletePlace(place1);
        System.out.println("Want --- :\n" + "PLACE s4(-22,109)\nPLACE Stwo(20,22)\n");
        System.out.println("Get  --- : (order doesn't matter)\n" +  currentMap.toString());

        // Test Q try adding illegal roads
        System.out.println("== Test Q =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");

        try
        {
            Road road6 = currentMap.newRoad(place1, place5, "road15", 333);
            Road road7 = currentMap.newRoad(place2, place5, "road52", 444);
            System.out.println("Illegal Road: Sone(road15:333)s5 OR Stwo(road52:444)s5");
            return;

        }
        catch (IllegalArgumentException e)
        {
            System.out.println(true + " " + e);
            System.out.println("Exception Successfully Caught");
            System.out.println("Want --- :\n" + "PLACE s4(-22,109)\nPLACE Stwo(20,22)\n");
            System.out.println("Get  --- : (order doesn't matter)\n" +  currentMap.toString());
        }

        // Test R clear the map
        System.out.println("== Test R =================");
        System.out.println("#Re-Paints Place/Road ==  2/0");
        currentMap.deletePlace(place2);
        currentMap.deletePlace(place4);
        System.out.println(currentMap.toString().equals(""));

        // Initialise parser
        MapReaderWriter parser = new MapReaderWriter();

        // Test a test writer add two places
        System.out.println("== Test a =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        Place place7 = currentMap.newPlace("sOne", 1, 1);
        Place place8 = currentMap.newPlace("sTwo", 2, 2);
        System.out.println("Want --- :\n" + "place sOne 1 1\nplace sTwo 2 2\n");
        try
        {
            System.out.println("Get  --- : (order doesn't matter)");
            parser.write(new PrintWriter(System.out), currentMap);
        }
        catch (IOException e)
        {
            System.out.println("Writer Failed " + e);
            return;
        }

        // Test b test writer add place and two roads
        System.out.println("== Test b =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        Place place9 = currentMap.newPlace("sThree", 3, 3);
        Road road6 = currentMap.newRoad(place7, place8, "road1", 12);
        Road road7 = currentMap.newRoad(place9, place8, "road2", 32);
        System.out.println("Want --- :\n" + "place sOne 1 1\nplace sThree 3 3\nplace sTwo 2 2\nroad sOne road1 12 sTwo\nroad sThree road2 32 sTwo\n");
        try
        {
            System.out.println("Get  --- : (order doesn't matter)");
            parser.write(new PrintWriter(System.out), currentMap);
        }
        catch (IOException e)
        {
            System.out.println("Writer Failed " + e);
            return;
        }

        // Test c set start place
        System.out.println("== Test c =================");
        System.out.println("#Re-Paints Place/Road ==  1/0");
        currentMap.setStartPlace(place7);
        System.out.println("Want --- :\n" + "place sOne 1 1\nplace sThree 3 3\nplace sTwo 2 2\nroad sOne road1 12 sTwo\nroad sThree road2 32 sTwo\nstart sOne\n");
        try
        {
            System.out.println("Get  --- : (order doesn't matter)");
            parser.write(new PrintWriter(System.out), currentMap);
        }
        catch (IOException e)
        {
            System.out.println("Writer Failed " + e);
            return;
        }

        // Test d set start place
        System.out.println("== Test d =================");
        System.out.println("#Re-Paints Place/Road ==  1/0");
        currentMap.setEndPlace(place7);
        System.out.println("Want --- :\n" + "end sOne\nplace sOne 1 1\nplace sThree 3 3\nplace sTwo 2 2\nroad sOne road1 12 sTwo\nroad sThree road2 32 sTwo\nstart sOne\n");
        try
        {
            System.out.println("Get  --- : (order doesn't matter)");
            parser.write(new PrintWriter(System.out), currentMap);
        }
        catch (IOException e)
        {
            System.out.println("Writer Failed " + e);
            return;
        }

        // Test A file with one place
        System.out.println("== Test A =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");

        MapImpl mapA = new MapImpl();
        try
        {
            parser.read(new StringReader("place sOne 10 20"), mapA);

            System.out.println("Want --- :\n" + "PLACE sOne(10,20)\n");
            System.out.println("Get  --- : (order doesn't matter)\n" + mapA.toString());
        }
        catch (Exception e)
        {
            System.out.println("Unexpected Exception " + e);
            return;
        }

        // Test B file with two places 1 road legal name
        System.out.println("== Test B =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        mapA = new MapImpl();
        try
        {
            parser.read(new StringReader("place sOne 10 20\nplace sTwo 21 22\nroad sOne roadOne 111 sTwo"), mapA);

            System.out.println("Want --- :\n" + "PLACE sOne(10,20)\nPLACE sTwo(21,22)\nROAD sOne(roadOne:111)sTwo\n");
            System.out.println("Get  --- : (order doesn't matter)\n" + mapA.toString());
        }
        catch (Exception e)
        {
            System.out.println("Unexpected Exception " + e);
            return;
        }

        // Test C file with two places 1 road empty name
        System.out.println("== Test C =================");
        System.out.println("#Re-Paints Place/Road ==  0/0");
        mapA = new MapImpl();
        try
        {
            parser.read(new StringReader("place sOne 10 20\nplace sTwo 21 22\nroad sOne - 111 sTwo"), mapA);

            System.out.println("Want --- :\n" + "PLACE sOne(10,20)\nPLACE sTwo(21,22)\nROAD sOne(:111)sTwo\n");
            System.out.println("Get  --- : (order doesn't matter)\n" + mapA.toString());
        }
        catch (Exception e)
        {
            System.out.println("Unexpected Exception " + e);
            return;
        }

        // Test D file with two places 1 road, 1 start
        System.out.println("== Test D =================");
        System.out.println("#Re-Paints Place/Road ==  1/0");
        mapA = new MapImpl();
        try
        {
            parser.read(new StringReader("place sOne 10 20\nplace sTwo 21 22\nroad sOne - 111 sTwo\nstart sOne"), mapA);

            System.out.println("Want --- :\n" + "PLACE sOne(10,20)\nPLACE sTwo(21,22)\nROAD sOne(:111)sTwo\nSTART sOne\n");
            System.out.println("Get  --- : (order doesn't matter)\n" + mapA.toString());
        }
        catch (Exception e)
        {
            System.out.println("Unexpected Exception " + e);
            return;
        }

        // Test E file with two places 1 road, 1 end
        System.out.println("== Test E =================");
        System.out.println("#Re-Paints Place/Road ==  1/0");
        mapA = new MapImpl();
        try
        {
            parser.read(new StringReader("place sOne 10 20\nplace sTwo 21 22\nroad sOne - 111 sTwo\nend sTwo\n"), mapA);

            System.out.println("Want --- :\n" + "PLACE sOne(10,20)\nPLACE sTwo(21,22)\nROAD sOne(:111)sTwo\nEND sTwo\n");
            System.out.println("Get  --- : (order doesn't matter)\n" + mapA.toString());
        }
        catch (Exception e)
        {
            System.out.println("Unexpected Exception " + e);
            return;
        }

        // Test F file with bad place name
        System.out.println("== Test F =================");
        System.out.println("#Re-Paints Place/Road == 0/0");
        mapA = new MapImpl();
        try
        {
            parser.read(new StringReader("place b$d_place 10 20\n"), mapA);
            System.out.println("Failed to Find Exception: Bad Place Name : b$d_place(10,20)");
            return;
        }
        catch (MapFormatException e)
        {
            System.out.println("Succesful Exception " + e);
            System.out.println(mapA.toString().equals(""));

        }
        catch (IOException e)
        {
            System.out.println("Unexpected Exception " + e);
            return;
        }

        // Test G file with bad place name
        System.out.println("== Test G =================");
        System.out.println("#Re-Paints Place/Road == 0/0");
        mapA = new MapImpl();
        try
        {
            parser.read(new StringReader("place 4addplace 10 20\n"), mapA);
            System.out.println("Failed to Find Exception: Bad Place Name : 4addplace(10,20)");
            return;
        }
        catch (MapFormatException e)
        {
            System.out.println("Succesful Exception " + e);
            System.out.println(mapA.toString().equals(""));

        }
        catch (IOException e)
        {
            System.out.println("Unexpected Exception " + e);
            return;
        }

        // Test H file with bad place name
        System.out.println("== Test H =================");
        System.out.println("#Re-Paints Place/Road == 0/0");
        mapA = new MapImpl();
        try
        {
            parser.read(new StringReader("place $a123456%^ 10 20\n"), mapA);
            System.out.println("Failed to Find Exception: Bad Place Name : addplac23e%^(10,20)");
            return;
        }
        catch (MapFormatException e)
        {
            System.out.println("Succesful Exception " + e);
            System.out.println(mapA.toString().equals(""));

        }
        catch (IOException e)
        {
            System.out.println("Unexpected Exception " + e);
            return;
        }

        // Test I missing fields
        System.out.println("== Test I =================");
        System.out.println("#Re-Paints Place/Road == 0/0");
        mapA = new MapImpl();
        try
        {
            parser.read(new StringReader("plac\n"), mapA);
            System.out.println("Failed to Find Exception: Missing First Field - Desired plac");
            return;
        }
        catch (MapFormatException e)
        {
            System.out.println("Succesful Exception " + e);
            System.out.println(mapA.toString().equals(""));

        }
        catch (IOException e)
        {
            System.out.println("Unexpected Exception " + e);
            return;
        }

        // Test J missing fields, ypos
        System.out.println("== Test J =================");
        System.out.println("#Re-Paints Place/Road == 0/0");
        mapA = new MapImpl();
        try
        {
            parser.read(new StringReader("place goodName 40\n"), mapA);
            System.out.println("Failed to Find Exception: Place Missing Y Field");
            return;
        }
        catch (MapFormatException e)
        {
            System.out.println("Succesful Exception " + e);
            System.out.println(mapA.toString().equals(""));

        }
        catch (IOException e)
        {
            System.out.println("Unexpected Exception " + e);
            return;
        }

        // Test K improper xpos field
        System.out.println("== Test J =================");
        System.out.println("#Re-Paints Place/Road == 0/0");
        mapA = new MapImpl();
        try
        {
            parser.read(new StringReader("place goodName sd 40\n"), mapA);
            System.out.println("Failed to Find Exception: Place Invalid X Field");
            return;
        }
        catch (MapFormatException e)
        {
            System.out.println("Succesful Exception " + e);
            System.out.println(mapA.toString().equals(""));

        }
        catch (IOException e)
        {
            System.out.println("Unexpected Exception " + e);
            return;
        }

        // Test L two places defined, bad road name starting character
        System.out.println("== Test L =================");
        System.out.println("#Re-Paints Place/Road == 0/0");
        mapA = new MapImpl();
        try
        {
            parser.read(new StringReader("place sOne 11 12\nplace sTwo 21 22\nroad sOne 4badName 44 sTwo"), mapA);
            System.out.println("Failed to Find Exception: Road Invalid Name Field");
            return;
        }
        catch (MapFormatException e)
        {
            System.out.println("Succesful Exception " + e);
            System.out.println("Want --- :\nPlace sOne(11,12)\nPlace sTwo(21,22)\n");
            System.out.println("Get  --- : (order doesn't matter)\n" + mapA.toString());

        }
        catch (IOException e)
        {
            System.out.println("Unexpected Exception " + e);
            return;
        }

        // Test M one place defined, bad road name starting character
        System.out.println("== Test M =================");
        System.out.println("#Re-Paints Place/Road == 0/0");
        mapA = new MapImpl();
        try
        {
            parser.read(new StringReader("place sTwo 21 22\nroad sTwo #$(#* 44 sTwo"), mapA);
            System.out.println("Failed to Find Exception: Road Invalid Name Field");
            return;
        }
        catch (MapFormatException e)
        {
            System.out.println("Succesful Exception " + e);
            System.out.println("Want --- :\nPlace sTwo(21,22)\n");
            System.out.println("Get  --- : (order doesn't matter)\n" + mapA.toString());

        }
        catch (IOException e)
        {
            System.out.println("Unexpected Exception " + e);
            return;
        }

        // Test N two places defined , non existing place specified
        System.out.println("== Test N =================");
        System.out.println("#Re-Paints Place/Road == 0/0");
        mapA = new MapImpl();
        try
        {
            parser.read(new StringReader("place sOne 11 12\nplace sTwo 21 22\nroad sThree goodName 44 sTwo"), mapA);
            System.out.println("Failed to Find Exception: Non Existing Place Specified");
            return;
        }
        catch (MapFormatException e)
        {
            System.out.println("Succesful Exception " + e);
            System.out.println("Want --- :\nPlace sOne(11,12)\nPlace sTwo(21,22)\n");
            System.out.println("Get  --- : (order doesn't matter)\n" + mapA.toString());

        }
        catch (IOException e)
        {
            System.out.println("Unexpected Exception " + e);
            return;
        }


        // Test O one places defined, non existing number field
        System.out.println("== Test O =================");
        System.out.println("#Re-Paints Place/Road == 0/0");
        mapA = new MapImpl();
        try
        {
            parser.read(new StringReader("place sOne 11 12\nplace sTwo 21 22\nroad sOne goodName"), mapA);
            System.out.println("Failed to Find Exception: Unspecified Road Length");
            return;
        }
        catch (MapFormatException e)
        {
            System.out.println("Succesful Exception " + e);
            System.out.println("Want --- :\nPlace sOne(11,12)\nPlace sTwo(21,22)\n");
            System.out.println("Get  --- : (order doesn't matter)\n" + mapA.toString());

        }
        catch (IOException e)
        {
            System.out.println("Unexpected Exception " + e);
            return;
        }

        // Test P one places defined, non existing place field
        System.out.println("== Test P =================");
        System.out.println("#Re-Paints Place/Road == 0/0");
        mapA = new MapImpl();
        try
        {
            parser.read(new StringReader("place sOne 11 12\nroad sOne goodName 44 sTwo\n"), mapA);
            System.out.println("Failed to Find Exception: Non Existing Place Specified");
            return;
        }
        catch (MapFormatException e)
        {
            System.out.println("Succesful Exception " + e);
            System.out.println("Want --- :\nPlace sOne(11,12)\n");
            System.out.println("Get  --- : (order doesn't matter)\n" + mapA.toString());

        }
        catch (IOException e)
        {
            System.out.println("Unexpected Exception " + e);
            return;
        }

        // Test Q one places defined, non name field
        System.out.println("== Test Q =================");
        System.out.println("#Re-Paints Place/Road == 0/0");
        mapA = new MapImpl();
        try
        {
            parser.read(new StringReader("place sOne 11 12\nroad sOne"), mapA);
            System.out.println("Failed to Find Exception: Invalid Road Name");
            return;
        }
        catch (MapFormatException e)
        {
            System.out.println("Succesful Exception " + e);
            System.out.println("Want --- :\nPlace sOne(11,12)\n");
            System.out.println("Get  --- : (order doesn't matter)\n" + mapA.toString());

        }
        catch (IOException e)
        {
            System.out.println("Unexpected Exception " + e);
            return;
        }

        // Test R one places defined, no place1 field
        System.out.println("== Test R =================");
        System.out.println("#Re-Paints Place/Road == 0/0");
        mapA = new MapImpl();
        try
        {
            parser.read(new StringReader("place sOne 11 12\nroad"), mapA);
            System.out.println("Failed to Find Exception: Place not Specified");
            return;
        }
        catch (MapFormatException e)
        {
            System.out.println("Succesful Exception " + e);
            System.out.println("Want --- :\nPlace sOne(11,12)\n");
            System.out.println("Get  --- : (order doesn't matter)\n" + mapA.toString());

        }
        catch (IOException e)
        {
            System.out.println("Unexpected Exception " + e);
            return;
        }


        // Test S one places defined, negative length
        System.out.println("== Test S =================");
        System.out.println("#Re-Paints Place/Road == 0/0");
        mapA = new MapImpl();
        try
        {
            parser.read(new StringReader("place sOne 11 12\nroad sOne goodName -11 sOne"), mapA);
            System.out.println("Failed to Find Exception: Negative Road Length Specified");
            return;
        }
        catch (MapFormatException e)
        {
            System.out.println("Succesful Exception " + e);
            System.out.println("Want --- :\nPlace sOne(11,12)\n");
            System.out.println("Get  --- : (order doesn't matter)\n" + mapA.toString());

        }
        catch (IOException e)
        {
            System.out.println("Unexpected Exception " + e);
            return;
        }

        // Test T no defined, bad road
        System.out.println("== Test S =================");
        System.out.println("#Re-Paints Place/Road == 0/0");
        mapA = new MapImpl();
        try
        {
            parser.read(new StringReader("road sOne goodName 11 sOne"), mapA);
            System.out.println("Failed to Find Exception: Negative Road Length Specified");
            return;
        }
        catch (MapFormatException e)
        {
            System.out.println("Succesful Exception " + e);
            System.out.println(mapA.toString().equals(""));

        }
        catch (IOException e)
        {
            System.out.println("Unexpected Exception " + e);
            return;
        }


    }


}
