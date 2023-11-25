package sjcc;

import java.util.Scanner;

public class App 
{

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
    
    
}
