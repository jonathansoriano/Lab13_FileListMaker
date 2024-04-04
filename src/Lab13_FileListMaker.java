import java.util.ArrayList;
import java.util.Scanner;

public class Lab13_FileListMaker {
    private static ArrayList<String> randomList = new ArrayList<>(); // Global ArrayList so I can insert method in private static methods outside the main method.

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean doneResponse = false;
        String userChoice = "";
        int deleteInput = 0;

            while (!doneResponse){
                userChoice = SafeInput.getRegExString(in, "Select from Menu [A, D, P, Q]", "[AaDdPpQq]");

                    if (userChoice.equalsIgnoreCase("a")) {
                        addItem(in, "What item would you like to add to the list?");
                        displayArrayList();
                    }
                    if (userChoice.equalsIgnoreCase("d")) {
                        numItemList();
                        deleteInput = SafeInput.getRangedInt(in, "What item from the list would you like to delete", 1, randomList.size()); // "high" is Arraylist.size since we don't know how many items will be in list.
                        deleteItem(deleteInput);
                    }
                    if (userChoice.equalsIgnoreCase("p")){
                        displayArrayList();
                    }
                    if (userChoice.equalsIgnoreCase("q")){
                        doneResponse= SafeInput.getYNConfirm(in, "Are you sure you want to Quit?");
                    }


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


} // <----- end of Class