import java.io.*;
import java.util.*;

public class P2
{
    private shop myshop = new shop(); // manage customers
    private ArrayList<customer> myCustomer = new ArrayList<>();

    private void importFile(String fileName) //given file name comes here
    {
        String importName = null;
        Scanner importStream = null;
        String new_iD = "unknown"; //give default values just in case
        int new_arrival = -999, new_eat = -999; //ready to store the value into the array

        try //this try is try to access the file
        {
            System.out.println("Looks Like You Gave Me a File Name.");
            System.out.println("...Importing...");
            importName = fileName;

            //importName = "/Users/Yugi/IdeaProjects/P2/src/P2-1in.txt";
            importStream = new Scanner(new File(importName));

            while(importStream.hasNextLine())
            {
                try //this try is try to scan the contexts
                {
                    String line = importStream.nextLine(); //reading lines
                    if(line.equals("")) //to preventing to stop run when the begining of the line is nothing
                    {
                        continue;
                    }

                    //importing data look like
                    // "arrive ID eating "  <- this info does not include in the txt file
                    //      "0 C1 5"

                    String[] parts = line.split(" "); //sprit the lines by space

                    if(!parts[0].equalsIgnoreCase("END")) //this end of file has END
                    {
                        new_arrival = Integer.parseInt(parts[0]); //first part is some numerical values
                        new_iD = parts[1]; //keep the value as string
                        new_eat = Integer.parseInt(parts[2]);
                        myCustomer.add( new customer (new_arrival, new_iD, new_eat));
                    }

                } //end of try to read

                catch(ArrayIndexOutOfBoundsException a)
                {
                    System.out.println("Invalid Line Format: Not Enough Information");
                }
                catch(NoSuchElementException | NullPointerException n)
                {
                    System.out.println(n.getMessage());
                }
            } // end of while loop

            myshop.setMyCustomer(myCustomer); //set the myCustomer at the shop class
        } // end of try to access the files

        catch(FileNotFoundException e) //catch for access files' errors
        {
            System.out.println("!!! Rage Mode !!! ");
            System.out.println("!!! Why you gave me wrong file name !!! ");
            System.out.println("After I count to 10, I'll be a nice girl");
            System.out.println("10, 9, 8, ... , 2, 1 ...");
            System.out.println("Error Opening The File " + importName);
        }
        finally //finally done to store the values from the text file
        {
            if(importStream !=null)
            {
                System.out.println("Importing is done. Let simulate the results =) "); //not necessary to show the nessage
                importStream.close(); //closing import stream for the next
            }
        }
    }

    private void run(String fileName) throws InterruptedException  //here is actual main runs for security reasons
    {
        System.out.println("G'day, I'm AggRetsuko, How are you?");
        System.out.println("Now, waiter is my job...as I lost my job because of Corona...");
        importFile(fileName); //given name from Java cmd goes to importing function

        customer.setCorona(myshop); //very bad idea!!

        FIFO();

        System.out.println(customer.new_case(myCustomer)); //display the result
    }


    private void FIFO() //first in first out
    {
        int head = 0; //iteration for the arraylist
        int size = myCustomer.size();
        while(!myshop.servedAll()) //while all customers are served: updating at shop class
        {
            while (head < size && myCustomer.get(head).getArrive() <= myshop.getTime()) //
            {
                myCustomer.get(head).start(); //calling customer run() with thread
                head++;
            }

            try
            {
                Thread.sleep(1); // try to interrupt every 1 m/s for increase the time
                myshop.incTime();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        String fileName = args[0]; //this is for java cmd and any file name can take and user can gives any file from the cmd
        P2 sim = new P2();
        //sim.run();
        sim.run(fileName); //passing the file name to the run
    }
}
