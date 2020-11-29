import java.util.ArrayList;
import java.util.concurrent.*;

public class shop
{
    final private Semaphore seats = new Semaphore(5, true); // for the fairness

    private static int shoptime = 0; //do not change static as sharing the time each thread
    private ArrayList<customer> myCustomer = new ArrayList<>();
    private static boolean servedAll = false; //until all customers are served, false
    private boolean seated;
    private static int lastCust = 0; //sharing the time who leaves the last

    public shop()
    {
        this.seated = false;
    }

    //getter
    public int getTime() { return shoptime; }

    public Semaphore getSeats() { return seats; }

    public ArrayList<customer> getMyCustomer() { return myCustomer; }

    public static boolean isServedAll() { return servedAll; }

    public boolean isSeated() { return seated; }


    //setter

    public static void setShoptime(int new_shoptime) { shoptime = new_shoptime; }

    public void setMyCustomer(ArrayList<customer> myCustomer) { this.myCustomer = myCustomer; }

    public static void setServedAll(boolean servedAll) { shop.servedAll = servedAll; }


    public void serve(customer myCustomer) throws InterruptedException
    {
        while(!myCustomer.isFinished()) //while this customer is not finished
        {
            if(!myCustomer.isSeated()) //first serve is here
            {
                try
                {
                    if(lastCust < getTime() + myCustomer.getEat())
                    {
                        lastCust = getTime()  + myCustomer.getEat(); //updating the last time here
                    }

                    if(seats.availablePermits() == 0) //if 5 seats are occupied update the time
                    {
                        setShoptime(getTime() + lastCust);
                    }
                    seats.acquire(); //critical section and mim actions need during the time
                    myCustomer.setSeat(getTime());
                    myCustomer.setSeated(true); //set to true
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            if(getTime() >= myCustomer.getSeat() + myCustomer.getEat()) //if the customer not finished, the time will be increased by the main interrupting
            {
                seats.release();
                int leaves = myCustomer.getSeat() + myCustomer.getEat(); //time to leave
                myCustomer.setLeave(leaves);
                myCustomer.setFinished(true); //set as finished this customer
            }
        }
    }

    public boolean servedAll() //calling this function from p2
    {
        for(customer cust: myCustomer) //loop for my customer
        {
            if(!cust.isFinished()) //check each customer is finished when time time is increased
                return false;
        }
        return true;
    }

    public void incTime()
    {
        shoptime++;
    }  //increase the time by the main as it is process and controls threads

}
