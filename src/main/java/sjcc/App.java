package sjcc;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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

        while (!quit) {
            System.out.println("Enter a command: ");
            String input = userInput.nextLine().trim();

            switch (input) {
                case "l":
                    //listing all the pharaohs
                    listPharaohs();
                    break;
                case "d":
                    //displaying information for a specific Egyptian pharoah
                    System.out.println("Enter the ID of the pharaoh: ");
                    String pharaohID = userInput.nextLine().trim();
                    if (!pharaohID.matches("\\d+")) {
                        System.out.println("Invalid. Please enter the correct ID");
                    } else {
                        displayPharaohInfo(pharaohID);
                    }
                    break;
                case "p":
                    //listing all the pyramids
                    listPyramids();
                    break;
                case "s":
                    //display a specific pyramid
                    System.out.println("Enter the pyramid ID: ");
                    String pyramidID = userInput.nextLine().trim();
                    if (!pyramidID.matches("\\d+")) {
                        System.out.println("Invalid ID. Please enter the correct ID");
                    } else {
                        displayPyramidInfo(pyramidID);
                    }
                    break;
                case "r":
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

    
    private static void listPharaohs() {
        JSONArray pharaohs = JSONFile.readArray(PHARAOHS_JSON_PATH);
        if (pharaohs == null || pharaohs.length() == 0) {
            System.out.println("No pharaohs were found!");
            return;
        }
        for (Object o : pharaohs) {
            if (o instanceof JSONObject) {
                JSONObject pharaoh = (JSONObject) o;
                System.out.println(pharaoh.optString("name"));
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
                JSONObject pyramid = (JSONObject) o;
                System.out.println(pyramid.optString("name"));
            }
        }
    }

    private static void displayPyramidInfo(String pyramidID) {
        JSONArray pyramids = JSONFile.readArray(PYRAMIDS_JSON_PATH);
        boolean found = false;
        for (Object o : pyramids) {
            if (o instanceof JSONObject) {
                JSONObject pyramid = (JSONObject) o;
                if (String.valueOf(pyramid.optInt("id")).equals(pyramidID)) {
                    System.out.println(pyramid.toString(4));
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
        for (Object o : pyramids) {
            JSONObject pyramid = (JSONObject) o;
            if (requestedPyramids.contains(String.valueOf(pyramid.optInt("id")))) {
                System.out.println(pyramid.optString("name"));
            }
        }
    }
    
}
