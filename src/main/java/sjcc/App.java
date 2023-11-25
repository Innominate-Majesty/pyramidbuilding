package sjcc;

import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class App 
{

    private static final String PHARAOHS_JSON_PATH = "pharaoh.json";
    private static final String PYRAMIDS_JSON_PATH = "pyramid.json";
    private static Set<String> requestedPyramids = new HashSet<>();
    public static void main( String[] args )
    {
        Scanner userInput = new Scanner(System.in);
        boolean quit = false;

        while (!quit) {
            System.out.println("Enter a command: ");
            String input = userInput.nextLine();

            switch (input) {
                case "l":
                    //listing all the pharaohs
                    listPharaohs();
                    break;
                case "d":
                    //displaying information for a specific Egyptian pharoah
                    System.out.println("Etner the ID of the pharaoh: ");
                    String pharaohID = userInput.nextLine();
                    displayPharaohInfo(pharaohID);
                    break;
                case "p":
                    //listing all the pyramids
                    listPyramids();
                    break;
                case "s":
                    //display a specific pyramid
                    System.out.println("Etner the pyramid ID: ");
                    String pyramidID = userInput.nextLine();
                    displayPyramidInfo(pyramidID);
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
        try {
            String data = new String(Files.readAllBytes(Paths.get(PHARAOHS_JSON_PATH)));
            JSONArray pharaohs = new JSONArray(data);

            for (int i = 0; i < pharaohs.length(); i++) {
                JSONObject pharaoh = pharaohs.getJSONObject(i);
                System.out.println(pharaoh.getString("name"));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayPharaohInfo(String pharaohID) {
        try {
            String data = new String(Files.readAllBytes(Paths.get(PHARAOHS_JSON_PATH)));
            JSONArray pharaohs = new JSONArray(data);

            for (int i = 0; i < pharaohs.length(); i++) {
                JSONObject pharaoh = pharaohs.getJSONObject(i);
                if (pharaoh.getString("id").equals(pharaohID)) {
                    System.out.println(pharaoh.toString());
                    break;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void listPyramids() {
        try {
            String data = new String(Files.readAllBytes(Paths.get(PYRAMIDS_JSON_PATH)));
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
            String data = new String(Files.readAllBytes(Paths.get(PYRAMIDS_JSON_PATH)));
            JSONArray pyramids = new JSONArray();

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

    }
    
}
