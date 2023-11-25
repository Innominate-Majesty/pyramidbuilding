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
    
    private static String readFileFromClasspath(String path) throws IOException {
        InputStream inputStream = App.class.getResourceAsStream(path);
        if (inputStream == null) {
            throw new IOException("Resource not found! " + path);
        }
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

    }
    
    private static void listPharaohs() {
        try {
            String data = readFileFromClasspath(PHARAOHS_JSON_PATH);
            JSONArray pharaohs = new JSONArray(data);

            if (pharaohs.isEmpty()) {
                System.out.println("no pharaohs were found!");
                return;
            }

            for (int i = 0; i < pharaohs.length(); i++) {
                JSONObject pharaoh = pharaohs.getJSONObject(i);
                System.out.println(pharaoh.getString("name"));
            }
        }
        catch (IOException e) {
            System.out.println("Error reading pharaohs file! " + e.getMessage());
        }
    }

    private static void displayPharaohInfo(String pharaohID) {
        try {
            String data = readFileFromClasspath(PHARAOHS_JSON_PATH);
            JSONArray pharaohs = new JSONArray(data);

            boolean found = false;

            for (int i = 0; i < pharaohs.length(); i++) {
                JSONObject pharaoh = pharaohs.getJSONObject(i);
                if (pharaoh.getInt("id") == Integer.parseInt(pharaohID)) {
                    System.out.println(pharaoh.toString(4));
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                System.out.println("Pharaoh with ID " + pharaohID + " not found.");
            }
        }
        catch (IOException e) {
            System.out.println("Error reading pharaohs file: " + e.getMessage());
        }
    }
    
    private static void listPyramids() {
        try {
            String data = readFileFromClasspath(PYRAMIDS_JSON_PATH);
            JSONArray pyramids = new JSONArray(data);

            for (int i = 0; i < pyramids.length(); i++) {
                JSONObject pyramid = pyramids.getJSONObject(i);
                System.out.println(pyramid.getString("name"));
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayPyramidInfo(String pyramidID) {
        try {
            String data = readFileFromClasspath(PYRAMIDS_JSON_PATH);
            JSONArray pyramids = new JSONArray(data);

            for (int i = 0; i < pyramids.length(); i++) {
                JSONObject pyramid = pyramids.getJSONObject(i);
                if (pyramid.getString("id").equals(pyramidID)) {
                    System.out.println(pyramid.toString());
                    requestedPyramids.add(pyramidID);
                    break;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayRequestedPyramids() {
        try {
            String data = readFileFromClasspath(PYRAMIDS_JSON_PATH);
            JSONArray pyramids = new JSONArray(data);

            for (int i = 0; i < pyramids.length(); i++) {
                JSONObject pyramid = pyramids.getJSONObject(i);
                if (requestedPyramids.contains(pyramid.getString("id"))) {
                    System.out.println(pyramid.getString("name"));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
