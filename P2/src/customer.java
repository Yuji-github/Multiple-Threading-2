import java.util.concurrent.Semaphore;
import java.util.*;

public class customer implements Runnable
{
    private int arrive, eat, seat, leave;
    private String ID = "Unknown";
    private Semaphore sem;
    private Thread t;
    private static shop corona; //sharing this object among all threads
    private boolean finished, seated; //check they are finished or not

    public customer (int new_arrival, String ID, int new_eat)
    {
        this.arrive = new_arrival;
        this.ID = ID;
        this.eat = new_eat;
        this.finished = false;
        this.seated = false;
        corona = new shop();
        this.seat = 0;
        this.leave = 0;
    }

    //setter

    public void setSem(Semaphore sem) { this.sem = sem; }

    public void setFinished(boolean finished) { this.finished = finished; }

    public void setArrive(int arrive) { this.arrive = arrive; }

    public void setEat(int eat) { this.eat = eat; }

    public void setSeat(int seat) { this.seat = seat; }

    public void setLeave(int leave) { this.leave = leave; }

    public void setID(String ID) { this.ID = ID; }

    public static void setCorona(shop corona) { customer.corona = corona; } //sounds really bad


    public void setSeated(boolean seated) { this.seated = seated; }

    //getters
    public String getID()
    {
        return this.ID;
    }

    public int getArrive() { return this.arrive; }

    public int getEat() { return this.eat; }

    public int getSeat() { return this.seat; }

    public int getLeave()
    {
        return this.leave;
    }

    public static shop getCorona() { return corona; } //if I get Corona, I get Corona. At the end of the day, I'm not going to let it stop me

    public boolean isFinished() { return finished; }

    public Semaphore getSem() { return sem; }

    public boolean isSeated() { return seated; }


    //functions

    public void start()
    {
        if(t == null)
        {
            t = new Thread(this, ID);
            t.start();
        }
    }

    @Override
    public void run()
    {
        try
        {
            this.corona.serve(this);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public static String new_case(ArrayList<customer> myCustomer) //display the result
    {
        String new_case = "Customer   arrives     Seats     Leaves\n";
        for(int i = 0; i < myCustomer.size(); i++)
        {
                new_case += myCustomer.get(i).toString() +" \n"; //calling toString function here
        }
        return new_case;
    }

    @Override
    public String toString() //here is actual print contents and changing the space might affect the result time somewhat
    {
        return this.ID +"\t        " + this.arrive +"\t       " + this.seat +"\t      " + this.leave;
    }
}
