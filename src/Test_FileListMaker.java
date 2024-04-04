import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class Test_FileListMaker {
    private static ArrayList<String> randomList = new ArrayList<>(); // Global ArrayList so I can insert method in private static methods outside the main method.

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean needsToBeSaved = false;
        boolean doneResponse = false;
        String menuPrompt = "Select from Menu [A - Add  D - Delete  V - View  Q - Quit  O - Open  S - Save  C - Clear]";
        String userChoice = "";
        int deleteInput = 0;

        try {
            while (!doneResponse){
                userChoice = SafeInput.getRegExString(in, menuPrompt, "[AaDdVvQqOoSsCc]");

                if (userChoice.equalsIgnoreCase("a")) {
                    addItem(in, "What item would you like to add to the list?");
                    needsToBeSaved = true;
                }
                if (userChoice.equalsIgnoreCase("d")) {
                    numItemList();
                    deleteInput = SafeInput.getRangedInt(in, "What item from the list would you like to delete", 1, randomList.size()); // "high" is Arraylist.size since we don't know how many items will be in list.
                    deleteItem(deleteInput);
                    // Figure out how to overwrite already existing txt file for ArrayList
                    needsToBeSaved = true;
                }
                if (userChoice.equalsIgnoreCase("v")){
                    displayArrayList();
                }
                if (userChoice.equalsIgnoreCase("q")){
                    doneResponse= SafeInput.getYNConfirm(in, "Are you sure you want to Quit?");
                }
                if (userChoice.equalsIgnoreCase("o")){
                    // Double check and make sure this code works
                    openTextFile();
                }
                if (userChoice.equalsIgnoreCase("s")){
                    makeTextFile(randomList);
                }
                if (userChoice.equalsIgnoreCase("c")){
                    System.out.println("STILL NEED TO CODE");
                }



            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found!");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }






    } // <------ end of Main Method

    // Private methods since these are one time use methods.
    private static void displayArrayList (){ //Print Method
        if (randomList.isEmpty()){ // isEmpty method checks if there are any elements or items in randomList.
            System.out.println("You haven't added to the list! Please add items to the list.");
        }else {
            for (String x : randomList) // enhanced for-loop that goes through all of arraylist
                System.out.println(x); // prints all the items in the arraylist
        }

    }
    private static void addItem (Scanner pipe, String prompt){ // Add Item Method
        String itemToBeAdded = "";
        do {
            System.out.print("\n" + prompt + ": ");
            itemToBeAdded = pipe.nextLine();
        }while (itemToBeAdded.isEmpty()); //  same as (itemToBeAdded.length()==0);
        randomList.add(itemToBeAdded);

    }
    private static void numItemList(){ // Numbered ArrayList method
        for (int x = 0; x < randomList.size(); x++) // regular for-loop since I understand this better than enhanced for loop
            System.out.println(((x+1) + ". " + randomList.get(x))); // x by itself is the location(index) in array list.
    }
    private static void deleteItem (int item){ // Delete Item Method. You'll but input from GetRangedInt value for "item" in this method.
        int adjValue = 0; // it's the adjusted number that goes inside .remove("Here")

        adjValue = item -1; // (input from GetRangedInt value - 1) so we can get the real index in arraylist.
        randomList.remove(adjValue); // adjValue is actual number used to remove from arraylist.
    }

    private static void makeTextFile(ArrayList<String> myArrayList) throws IOException {
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "/src/list.txt");

        OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

        for (String item : myArrayList)
        {
            writer.write(item, 0, item.length());
            writer.newLine();
        }
        writer.close();
        System.out.println("File has been written.");

    }

    private static void openTextFile() throws IOException {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String items = ""; // items in ArrayList

        File workDirectory = new File(System.getProperty("user.dir"));

        chooser.setCurrentDirectory(workDirectory);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            selectedFile = chooser.getSelectedFile();
            Path file = selectedFile.toPath();

            InputStream in = new BufferedInputStream(Files.newInputStream(file,CREATE));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while (reader.ready()){
                items = reader.readLine(); // This reads every line

                System.out.println(items); // Format on how the lines will be printed out in terminal
            }
            reader.close(); // Stops reader
            System.out.println("\n\nData file read!");
        }
        else
        {
            System.out.println("No file selected! Run program again and select a file.");
        }
    }


} // <----- end of Class