import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.Math;

/**
 * The Global class keeps track of all of the variables that can be used globally including
 * all of the names constants.
 * 
 * Matt D Burch
 * 12/29/2010
 */
public class Global
{
    public static final int ROWS = 11;/** Number of rows*/
    public static final int COLS = 20;/** Number of columns*/
    public static final int MAXBAGS = 3;/**Max number of bags a passenger can bring on the plane*/
    public static final int BIN_SIZE = 40;
    
    public static int extraBags = 0;
    
    /**Proportion of the airplane population that can have each size bag*/
    public static int max1Bags = 0;
    public static int max2Bags = 0;
    public static int max3Bags = 0;
    public static int maxPassengers = (ROWS-5) * COLS;/**Max number of passengers*/
    public static int occupancy = 100;
    public static String policy = "";
    public static boolean started = false;

    public Global()
    /**Pre: None
     * Post: None
     */
    {    
        /** Do not need a constructor for this class*/
    }
    
     public static void setPolicy(int bag1, int bag2, int bag3)
    /**Pre: The occupancy method must be called before this method can be called.
     * Post: The policy will be implemented by keeping track of the max amount of passengers that are
     *       allowed to have 1,2, or 3 bags.
     */
    {
        max1Bags = Math.round(maxPassengers * (bag1/100.0f));
        max2Bags = Math.round(maxPassengers * (bag2/100.0f));
        max3Bags = Math.round(maxPassengers * (bag3/100.0f));

        policy = bag1 + "/" + bag2 + "/" + bag3;
    }
    
    public static void occupancy(int percent)
    /**Pre: This will be called before the policy method is called.  maxPassengers has previously been
     *      set to how many passengers are needed for a full occupancy level.
     * Post: The max number of passengers is set to maxPassengers based on the occupancy level.
     */
    {
        maxPassengers = Math.round(((ROWS-5) * COLS)*(percent/100.0f));/**Max number of passengers*/
        occupancy = percent;
    }
}
