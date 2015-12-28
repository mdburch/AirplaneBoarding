import greenfoot.*; 
import java.lang.*;
/**
 * This PersonAdder class is responsible for adding new passengers onto the plane.  This class is 
 * also responsible for causing the simulation to stop once all the passengers are on board.
 * 
 * Matt D Burch
 * 4/3/2011
 */
public class PersonAdder  extends Actor
{
    public int passengerCount = 1;/** # of passengers*/
    public boolean[][] seatsAvail;/**Seating chart*/
   
    /**These three variables keep track of how many people are carrying 1,2, or 3 bags
     * This will be used to make sure we are following the baggage policy correctly */
    public int bag1Count;
    public int bag2Count;
    public int bag3Count;
    
    /**This adds one extra step at the end to make sure the last passenger's picture gets updated without
     * an arrow that would be symbolizing that he is moving*/
    public boolean extraStep;
   
    /**CONSRTUCTOR*/
    public PersonAdder()
    /**Pre: None
     * Post: A seating chart is created setting all of the seats open for passengers to sit at.
     *       The number of passengers with each number of bags is set to 0
     */    
    {
        seatsAvail = new boolean[8][Global.COLS];
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < Global.COLS; j++)
            {
                if(i == 0 || i == 4)/**This is where the overhead bin or an aisle is located*/
                    seatsAvail[i][j] = false;
                else
                    seatsAvail[i][j] = true;
            }
        }
        
        bag1Count = 0;
        bag2Count = 0;
        bag3Count = 0;
        extraStep = false;
    }
    public void act()
    {
          if(Global.started)
            newPerson();
    }
    
    public void newPerson()
    /**Pre: The seating chart has been created
     * Post: If the passenger count is under the maxPassengers and the first spot in the aisle is open, 
     *       then a new passenger is created including their spot, number of bags and bags sizes.
     *       The passenger is then added to the world.  Otherwise, a new passenger is not created. When the
     *       passenger is created, their seat on the plane is set to false in the seatsAvail array.
     */
    {       
       if(passengerCount <=  Global.maxPassengers)/**If there is enough space on the plane to add a new passenger.*/
       {
         if(getOneObjectAtOffset(0,0,Person.class) == null)/**If the first spot in the aisle is open for a new passenger.*/
         {
                int y = 0, x = 0;
                /**Find a random spot on the plane*/
                y = findY();
                x = findX();
                
                while(seatsAvail[y][x] != true)/**WHILE the random seat has already been taken. Find a new seat.*/
                {
                    y = findY();
                    x = findX();
                }
                seatsAvail[y][x] = false;  /**Make the seat availability false*/
                
                /**Finding the passenger's number of bags*/
                int tempBag = 0, tempBag2 = 0, tempBag3 = 0;
                int passengerBags = numBags();/**Randomly assign the passenger a number of bags*/
               if(passengerBags >= 1)
                    tempBag = bagSize(5,8);/**Carry-on bag*/
               if(passengerBags >= 2)
                    tempBag2 = bagSize(2,4);/**Personal item bag*/
               if(passengerBags == 3)
                    tempBag3 = bagSize(1,3);/**Smaller third bag since they are not really allowed*/

                /**Add new Person into the world*/    
                getWorld().addObject(new Person(x, y,passengerBags, tempBag, tempBag2, tempBag3), 0, 4);
                passengerCount++;
        }
      }
      else /**Check if everyone is in their seat*/
        checkFull();
    }
    
    private void checkFull()
    /**Pre: All of the passengers are on the plane
     * Post: The simulation will stop if all passengers are in their seats. If they are not
     *       nothing will happen and execution will continue.
     */
    {
        int personCount = 0; /**Count of people in their seat*/
        Global.extraBags = 0;/**Count how many bags were not stored*/
        
        for(int y = 0; y < Global.COLS; y++)
        {
            for(int x = -3; x < 4; x++)
            {
                if ( x != 0) /**We do not want to check if passengers are in the aisle*/
                {
                    Person person;
                    person = (Person)getOneObjectAtOffset(y, x, Person.class);
                    if( person != null)/**If a person is in a seat, increment the counter*/
                    {
                        personCount++; 
                        Global.extraBags += person.tempBags;/**Add the bags they could not store*/
                    }
                }
            }
        }
        
        if (personCount == Global.maxPassengers)/**If all the passengers are in their seat*/
        {
            if(extraStep == false)/**We need an extra step in the simulation to make sure the last actor only has his number of bags*/
                extraStep = true;
            else
            {
                Greenfoot.stop(); /**Stop execution*/
                System.out.println("Policy: " + Global.policy);
                System.out.println("Occupancy Rate: " + Global.occupancy);
                Counter.print();/**Print out the total time it took for the execution to run*/
                System.out.println("Bags left unpacked: " + Global.extraBags);
                
                Global.started = false; /**Used because it remains true if we reset the scnerario*/
                extraStep = false;/**Used because it remains true if we reset the scnerario*/
            }
        }
    }
    
    private int findY ()
    /**Pre: None
     * Post: The Y value (row) of the seat for the new person is returned.
     */
    {
        int y = Greenfoot.getRandomNumber(7);/**row*/
        if( y >= 4)
            y++; /**Skips the aisle*/
        return y;
    }
    
    private int findX()
    /**Pre: None
     * Post: The X value (column) of the seat for the new person is returned.
     */
    {
        return Greenfoot.getRandomNumber(Global.COLS);/**col*/
    }
    
    private int numBags()
    /**Pre:  The passenger has already been assigned a seat
     * Post: The passenger is assigned a random number of bags to carry. For this simulation, the 
     *       distribution of passengers who have 1, 2 and 3 bags will be set by global variables. If 
     *       a passenger's number of bags exceeds the alloted proportion, they will be assigned a new number.
     *       example: 60% of the passengers have 1 bag, 30% have 2 and 10% have 3 bags. The number is returned.
     */
    {
        boolean found = false;
        int num = 1;
        while(!found)/**while a valid amount of luggage for a passenger is not found*/
        {
            num = Greenfoot.getRandomNumber(Global.MAXBAGS)+1;
            if(num == 1 && bag1Count <= Global.max1Bags && Global.max1Bags != 0)/**If there is still room for the passenger to have 1 bag*/
            {
                bag1Count++;
                found = true;
            }
            else if (num == 2 && bag2Count <= Global.max2Bags && Global.max2Bags != 0)/**If there is still room for the passenger to have 2 bags*/
            {
                bag2Count++;
                found = true;
            }
            else if (num == 3 && bag3Count <= Global.max3Bags && Global.max3Bags != 0)/**If there is still room for the passenger to have 3 bags*/
            {
                bag3Count++;
                found = true;
            }
        }
        return num;
    }
    
    private int bagSize(int small, int large)
    /**Pre: The number of bags has already been decided for the passenger.
     * Post: The size of one bag is returned depending on a range of values from the smallest (small) bag
     *       and the largest (large) bag. This value is returned.
     */
    {
        return Greenfoot.getRandomNumber(large-small) + small;
    }
}

