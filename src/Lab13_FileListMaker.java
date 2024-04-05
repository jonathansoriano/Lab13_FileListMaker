import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class Lab13_FileListMaker {
    private static ArrayList<String> randomList = new ArrayList<>(); // Global ArrayList so I can insert method in private static methods outside the main method.

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean saved = true;
        boolean doneResponse = false;
        boolean saveYN = false;
        String menuPrompt = "Select from Menu [A - Add  D - Delete  V - View  Q - Quit  O - Open  S - Save  C - Clear]";
        String userChoice = "";
        String fileName = "";
        int deleteInput = 0;

        try {
            while (!doneResponse){ // doneResponse is set to "false". If doneResponse changes to "true", loop ends.
                userChoice = SafeInput.getRegExString(in, menuPrompt, "[AaDdVvQqOoSsCc]");

                if (userChoice.equalsIgnoreCase("a")) { // ADD OPTION
                    addItem(in, "What item would you like to add to the list?");
                    saved = false;
                }
                if (userChoice.equalsIgnoreCase("d")) { // DELETE OPTION
                    if (randomList.isEmpty()){
                        System.out.println("You must have items to be able to delete them!");
                    }else {
                        numItemList();
                        deleteInput = SafeInput.getRangedInt(in, "What item from the list would you like to delete", 1, randomList.size()); // "high" is Arraylist.size since we don't know how many items will be in list.
                        deleteItem(deleteInput);
                        // Figure out how to overwrite already existing txt file for ArrayList
                        saved = false;
                    }
                }
                if (userChoice.equalsIgnoreCase("v")){ // VIEW OPTION
                    displayArrayList();
                }
                if (userChoice.equalsIgnoreCase("q")){ // QUIT OPTION
                    if (!saved){ // If saved is NOT equal to true then...
                        System.out.println("If you Quit, you will lose your current data.");
                        saveYN = SafeInput.getYNConfirm(in, "Do you want want to save before Quiting?");
                        if (saveYN){ // If saveYN is TRUE, then...
                            fileName = SafeInput.getNonZeroLenString(in, "Please provide a name for your file");
                            fileMaker(fileName);
                            System.out.println("Your file has been saved!");
                        } // if saveYN is False, then... doneResponse = true and program ends.
                        doneResponse = true;

                    }else { // if user didn't add or delete items in array list, then when Quit, prompt them if they want to quit.
                        doneResponse= SafeInput.getYNConfirm(in, "Are you sure you want to Quit?");
                    }

                }
                if (userChoice.equalsIgnoreCase("o")){ //OPEN OPTION
                    if (!saved){ // If saved is NOT equal to true then...
                        System.out.println("You're about to lose your current data.");
                        saveYN = SafeInput.getYNConfirm(in, "Do you want want to save your current data?");
                        if (saveYN){
                            fileName = SafeInput.getNonZeroLenString(in, "Please provide a name for your file");
                            fileMaker(fileName);
                            openTextFile();
                        }
                        else {
                            openTextFile();
                        }
                    }
                    saved = true; // Resets saved variable value to "true".
                    openTextFile();
                }
                if (userChoice.equalsIgnoreCase("s")){ // SAVE OPTION
                    if (randomList.isEmpty()){ // Prompts user to add items since Array List doesn't have items.
                        System.out.println("Please add items to be able to save.");

                    }else { // if Array has Items, then it will let you name your file.
                        fileName = SafeInput.getNonZeroLenString(in, "Please provide a name for your file");
                        fileMaker(fileName);
                        saved = true; // Resets saved variable value to "true" so if user wanted to just view or exit, it would tell you "You will lose current data".
                    }

                }
                if (userChoice.equalsIgnoreCase("c")){
                    randomList.clear(); // clear() is more efficient than removeAll(). Empties all items in array list.
                    System.out.println("You have cleared the list.");
                }



            }
        }
        catch (FileNotFoundException e) // Error if file is not found. Specific IOException.
        {
            System.out.println("File not found!");
            e.printStackTrace();
        }
        catch (IOException e) // General Error. Includes the FileNotFoundException
        {
            e.printStackTrace();
        }






    } // <------ end of Main Method

    // Private methods since these are one time use methods.
    private static void displayArrayList (){ //View Method
        if (randomList.isEmpty()){ // isEmpty method checks if there are any elements or items in randomList.
            System.out.println("You haven't added to the list! Please add items to the list.");
        }else {
            for (String x : randomList) // enhanced for-loop that goes through all of arraylist
                System.out.println(x); // prints all the items in the arraylist
        }

    }
    private static void addItem (Scanner pipe, String prompt){ // Add Item Method. Needs prompt and scanner in parameters.
        String itemToBeAdded = "";
        do {
            System.out.print("\n" + prompt + ": "); // Prints out prompt
            itemToBeAdded = pipe.nextLine(); // String becomes valid input to use bc of nextLine();
        }while (itemToBeAdded.isEmpty()); //  same as (itemToBeAdded.length()==0);
        randomList.add(itemToBeAdded);

    }
    private static void numItemList(){ // Numbered ArrayList method
        for (int x = 0; x < randomList.size(); x++) // regular for-loop since I understand this better than enhanced for loop
            System.out.println(((x+1) + ". " + randomList.get(x))); // x by itself is the location(index) in array list.
                                                                    // Added plus 1 so the Numbered list looks "normal"
                                                                    // Can't show list as computer scientists sees it.
    }
    private static void deleteItem (int item){ // Delete Item Method. You'll put input from GetRangedInt value for "item" in this method.
        int adjValue = 0; // it's the adjusted number that goes inside .remove("Here")

        adjValue = item -1; // (input from GetRangedInt value - 1) so we can get the real index in arraylist.
        randomList.remove(adjValue); // adjValue is actual number used to remove from arraylist.
    }

    private static void openTextFile() throws IOException // Open Method
    {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;

        File workDirectory = new File(System.getProperty("user.dir"));

        chooser.setCurrentDirectory(workDirectory);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) // if User chooses a file in user.dir then...
        {
            selectedFile = chooser.getSelectedFile(); // File becomes what was picked from user.dir
            Path file = selectedFile.toPath();

            InputStream in = new BufferedInputStream(Files.newInputStream(file,CREATE));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));


            String currentLine;
            while ((currentLine = reader.readLine()) != null) // reads all the lines in file/array list until there isn't a line to read.
            {
                randomList.add(currentLine);

                System.out.println(currentLine);
            }
            reader.close(); // Stops reader
            System.out.println("\nYou have selected file: " + selectedFile.getName()); // gives you the name of the file you picked from user.dir
            System.out.println("Data file read!");
        }
        else // if user exits user.dir then...
        {
            System.out.println("No file selected! Run program again and select a file.");
        }
    }

    private static void fileMaker(String filename) throws IOException { // Save Method. Parameter is needed to put inside
                                                                        // Of newFileWrite(String) to name file and add ".txt"

        FileWriter myWriter = new FileWriter(filename + ".txt"); // adding ".txt" at end of filename makes correct text file format.

        for (String line : randomList)
        {
            myWriter.write(line + "\n");

        }
        myWriter.close();
        System.out.println("Successfully created and wrote to the file!");
    }

} // <----- end of Class