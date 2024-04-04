import java.util.Scanner;

public class SafeInput
{
    /**
     * Get a String which contains at least one character
     * @param pipe - a Scanner open to read from System.in
     * @param prompt - msg to tell the user what to input
     * @return - a String response that is not zero length
     */

    public static String getNonZeroLenString(Scanner pipe, String prompt)
    {
        String retString = "";
        do
        {
            System.out.print("\n" + prompt + ": ");
            retString = pipe.nextLine();
        } while (retString.length()==0);
        return retString;
    }

    /**
     * Gets any Int value
     * @param pipe - Scanner to read from the data from System.in
     * @param prompt - msg to tell the user what to input
     * @return - a valid Int value
     */

    public static int getInt(Scanner pipe, String prompt)
    {
        int retVal = 0;
        boolean done = false;
        String trash = "";
        do {
            System.out.print("\n" + prompt + ": ");
            if (pipe.hasNextInt())
            {
                retVal = pipe.nextInt();
                pipe.nextLine();
                done = true;
            }
            else
            {
                trash = pipe.nextLine();
                System.out.println("Enter a valid int, not: " + trash);
            }
        } while(!done);

        return retVal;
    }

    /**
     * Gets any double value
     * @param pipe - Scanner to read from the data from System.in
     * @param prompt - msg to tell the user what to input
     * @return - a valid double value
     */
    public static double getDouble(Scanner pipe, String prompt)
    {
        double retVal = 0.0;
        boolean done = false;
        String trash = "";
        do
        {
            System.out.print("\n" + prompt + ": ");
            if (pipe.hasNextDouble())
            {
                retVal = pipe.nextDouble();
                pipe.nextLine();
                done = true;
            }
            else
            {
                trash = pipe.nextLine();
                System.out.println("Enter a valid double, not: " + trash);
            }
        } while(!done);

        return retVal;
    }

    /**
     * Get an int value within specified numeric range
     *
     * @param pipe - Scanner to read from the data from System.in
     * @param prompt - msg to tell the user what to input
     * @param low - low end of range
     * @param high - high end of range
     * @return - int value within the range
     */

    public static int getRangedInt(Scanner pipe, String prompt, int low, int high)
    {
        int retVal = 0;
        boolean done = false;
        String trash = "";

       do
       {
           System.out.print("\n" + prompt + "[" + low + "-" + high + "]: ");
           if (pipe.hasNextInt())
           {
               retVal = pipe.nextInt();
               pipe.nextLine();
               if (retVal >= low && retVal <= high)
               {
                   done = true;
               }
               else
               {
                   System.out.println("\nNumber is out of range, please enter an Int [" + low + "-" + high + "] not: " + retVal);
               }
           }
           else
           {
               trash = pipe.nextLine();
               System.out.println("You must enter a valid int not: " + trash);
           }

       }while(!done);
       return retVal;
    }

    /**
     * Get a double value within a specific numeric range
     * @param pipe - Scanner to read data from System.in
     * @param prompt - msg to tell the user what to input
     * @param low - low end of range
     * @param high - high end of range
     * @return - double value that is without range
     */
    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high)
    {
        double retVal = 0;
        boolean done = false;
        String trash = "";

        do
        {
            System.out.print("\n" + prompt + "[" + low + "-" + high + "]: ");
            if (pipe.hasNextDouble())
            {
                retVal = pipe.nextDouble();
                pipe.nextLine();
                if (retVal >= low && retVal <= high)
                {
                    done = true;
                }
                else
                {
                    System.out.println("\nNumber is out of range [" + low + "-" + high + "]: " + retVal);
                }
            }
            else
            {
                trash = pipe.nextLine();
                System.out.println("You must enter a valid double not: " + trash);
            }

        }while(!done);

        return retVal;
    }

    /**
     * Get a Yes or No reply
     * @param pipe - Scanner to read from the data from System.in
     * @param prompt - msg to tell user what to input
     * @return - a  boolean (True or False) value based on Yes or No reply
     */
    public static boolean getYNConfirm(Scanner pipe, String prompt)
    {
        boolean retVal = true;
        String response = "";
        boolean gotAVal = false;

        do
        {
            System.out.print("\n" + prompt + " [Y/N] ");
            response = pipe.nextLine();
            if (response.equalsIgnoreCase("Y"))
            {
                gotAVal = true; // this will get out of do while loop in method and also get retValue out of loop
                retVal = true; // this will make doneResponse true
            }
            else if (response.equalsIgnoreCase("N"))
            {
               gotAVal = true; // this will get out of do while loop in method and also get retValue out of loop
               retVal = false; // this will make doneResponse false
            }
            else
            {
                System.out.println("You must enter [Y/N], not: " + response);
            }
        }while(!gotAVal);

        return retVal; // this will change doneResponse boolean value depending on Y/N reply in method
    }

    /**
     * Get a string that matches a RegEx pattern
     * @param pipe - Scanner to read from the data from System.in
     * @param prompt - msg to tell user what to input
     * @param regExPattern - java style RegEx pattern to constrain the input
     * @return - a string that follows the RegEx Patter provided
     */

    public static String getRegExString(Scanner pipe, String prompt, String regExPattern)
    {
       String response = "";
       boolean gotAVal = false;

       do
       {
           System.out.print("\n" + prompt + ": ");
           response = pipe.nextLine();
           if (response.matches(regExPattern))
           {
               gotAVal = true;
           }
           else
           {
               System.out.println("\nInvalid input: " + response);
               System.out.println("Try again!");
           }
       }while (!gotAVal);

       return response;
    }

    /**
     * make a unique header with a msg from user in the center of it
     * @param msg - random message from user that will be centered.
     */
    public static void prettyHeader(String msg) {
        int totalLength = 60; // Total length of the header
        int msgLength = msg.length();
        int sideLength = (totalLength - msgLength - 6) / 2;
        // -6 accounts for the three asterisks on each side. Dividing the remaining space by 2 ensures equal amount of padding on both sides of msg
        // ^This centers the message within the total length of the header

        //top row of asterisks using a loop
        for (int i = 0; i < totalLength; i++) {
            System.out.print("*");
        }
        System.out.println();

        //middle row with the message centered using a loop
        System.out.print("***");
        for (int i = 0; i < sideLength; i++) {
            System.out.print(" ");
        }
        System.out.print(msg);
        for (int i = 0; i < sideLength; i++) {
            System.out.print(" ");
        }
        System.out.println("***");

        //bottom row of asterisks using a loop
        for (int i = 0; i < totalLength; i++) {
            System.out.print("*");
        }
    }
}