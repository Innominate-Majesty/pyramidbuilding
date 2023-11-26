package sjcc;


import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;



public class App 
{

    private static final String PHARAOHS_JSON_PATH = "/pharaoh.json";
    private static final String PYRAMIDS_JSON_PATH = "/pyramid.json";
    
    
    private static Set<String> requestedPyramids = new HashSet<>();

    

    public static void main( String[] args )
    {
        Scanner userInput = new Scanner(System.in);
        boolean quit = false;

        printMenu();

        while (!quit) {
            System.out.println("Enter a command: ");
            String input = userInput.nextLine().trim();

            switch (input) {
                case "1":
                    //listing all the pharaohs
                    listPharaohs();
                    break;
                case "2":
                    //displaying information for a specific Egyptian pharoah
                    System.out.println("Enter the ID of the pharaoh: ");
                    String pharaohID = userInput.nextLine().trim();
                    if (!pharaohID.matches("\\d+")) {
                        System.out.println("Invalid. Please enter the correct ID");
                    } else {
                        displayPharaohInfo(pharaohID);
                    }
                    break;
                case "3":
                    //listing all the pyramids
                    listPyramids();
                    break;
                case "4":
                    //display a specific pyramid
                    System.out.println("Enter the pyramid ID: ");
                    String pyramidID = userInput.nextLine().trim();
                    if (!pyramidID.matches("\\d+")) {
                        System.out.println("Invalid ID. Please enter the correct ID");
                    } else {
                        displayPyramidInfo(pyramidID);
                    }
                    break;
                case "5":
                    //dispaying a list of requested pyramids (without duplicates)
                    displayRequestedPyramids();
                    break;
                case "q":
                    //quitting
                    quit = true;
                    break;
                default:
                    //Handling invalid commands
                    System.out.println("Please enter a valid command!");
                    break;
            }
        }

        userInput.close();
        System.out.println("Goodbye!");
    }

    
    public static void printMenu() {
        System.out.println("  ");
        System.out.println("****************************************\n");
        System.out.println("     Building a Pyramids with Venus   \n");
        System.out.println("****************************************\n");
        System.out.println(" ");
        System.out.println("****************************************\n");
        System.out.println("1    :    display a list of all the pharaohs");
        System.out.println("2    :    display information about a pharaoh");
        System.out.println("3    :    display all the pyramids");
        System.out.println("4    :    display a specific pyramid");
        System.out.println("5    :    display all requested pyramids without duplicates");;
        System.out.println("q    :    quit\n");
        System.out.println("****************************************\n");

    }
    

    private static void listPharaohs() {
        JSONArray pharaohs = JSONFile.readArray(PHARAOHS_JSON_PATH);
        if (pharaohs == null || pharaohs.length() == 0) {
            System.out.println("No pharaohs were found!");
            return;
        }
        for (Object o : pharaohs) {
            if (o instanceof JSONObject) {
                JSONObject pharaohJSON = (JSONObject) o;
                Pharaoh pharaoh = new Pharaoh (
                    pharaohJSON.getInt("id"), 
                    pharaohJSON.getString("name"),
                    pharaohJSON.getInt("begin"),
                    pharaohJSON.getInt("end"),
                    pharaohJSON.getInt("contribution"),
                    pharaohJSON.getString("hieroglyphic")
                );
                pharaoh.print();
            }
        }
    }

    private static void displayPharaohInfo(String pharaohID) {
        JSONArray pharaohs = JSONFile.readArray(PHARAOHS_JSON_PATH);
        boolean found = false;
        for (Object o : pharaohs) {
            if (o instanceof JSONObject) {
                JSONObject pharaoh = (JSONObject) o;
                if (pharaoh.optInt("id") == Integer.parseInt(pharaohID)) {
                    System.out.println(pharaoh.toString(4));
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            System.out.println("Pharaoh with ID " + pharaohID + " not found!");
        }
    }
    
    private static void listPyramids() {
        JSONArray pyramids = JSONFile.readArray(PYRAMIDS_JSON_PATH);
        for (Object o : pyramids) {
            if (o instanceof JSONObject) {
                JSONObject pyramidJSON = (JSONObject) o;
                Pyramid pyramid = new Pyramid (
                    pyramidJSON.optInt("id"),
                    pyramidJSON.optString("name"),
                        toStringArray(pyramidJSON.optJSONArray("contributors"))
                );
                pyramid.printDetailsWithContributors();
            }
        }
    }

    private static void displayPyramidInfo(String pyramidID) {
        JSONArray pyramids = JSONFile.readArray(PYRAMIDS_JSON_PATH);
        boolean found = false;
        for (Object o : pyramids) {
            if (o instanceof JSONObject) {
                JSONObject pyramidJSON = (JSONObject) o;
                if (String.valueOf(pyramidJSON.optInt("id")).equals(pyramidID)) {
                    Pyramid pyramid = new Pyramid(
                        pyramidJSON.optInt("id"),
                        pyramidJSON.optString("name"),
                        toStringArray(pyramidJSON.optJSONArray("contributors"))
                    );
                    pyramid.printDetailsWithContributors();
                    requestedPyramids.add(pyramidID);
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            System.out.println("Pyramid with ID " + pyramidID + " not found!");
        }
    }

    private static void displayRequestedPyramids() {
        JSONArray pyramids = JSONFile.readArray(PYRAMIDS_JSON_PATH);
        System.out.println("********************************************************\n");
        System.out.println("          List of Requested Pyramids         \n");
        System.out.printf("%-25s %s\n", "ID", "Name\n");

        System.out.println("********************************************************\n");

        for (String id : requestedPyramids) {
            for (Object o : pyramids) {
                if (o instanceof JSONObject) {
                    JSONObject pyramid = (JSONObject) o;
                    if (String.valueOf(pyramid.optInt("id")).equals(id)) {
                        System.out.printf("%-25s %s\n", id, pyramid.optString("name"));
                    }
                }
            }
        }
    }

    //method that converts JSONArray to String Array
    public static String[] toStringArray(JSONArray array) {
        if (array == null) {
            return new String[0];
        }
        String[] arr = new String[array.length()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = array.optString(i);
        }
        return arr;
    }

    //method to find the pharaohs by their hieroglyphic
    public static Pharaoh findPharaohHieroglyphic(String hieroglyphic) {
        JSONArray pharaohs = JSONFile.readArray(PHARAOHS_JSON_PATH);
        for (Object o : pharaohs) {
            if (o instanceof JSONObject) {
                JSONObject pharaohJSON = (JSONObject) o;
                if (hieroglyphic.equals(pharaohJSON.optString("hieroglyphic"))) {
                    return new Pharaoh(
                        pharaohJSON.optInt("id"),
                        pharaohJSON.optString("name"),
                        pharaohJSON.optInt("begin"),
                        pharaohJSON.optInt("end"),
                        pharaohJSON.optInt("contribution"),
                        pharaohJSON.optString("hieroglyphic")
                    );
                }
            }
        }

        return null;
    }
    
}
