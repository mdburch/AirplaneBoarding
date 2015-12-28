import greenfoot.*; 
/**
 * This person class is responsible for making sure each passenger makes it to
 * to their seats.  They avoid other passsengers they may encounter along the way and also packs their bags.
 * 
 * Matt D Burch
 * 4/5/2011
 */
public class Person  extends Actor
{
    int y;/**desired row*/
    int x;/**desired col*/
    int numBags;/**Number of bags the passenger has*/
    int tempBags; /**The number of bags of the passenger that can't be stored*/
    
    /**Sizes for the bags that the passenger brings on board*/
    int bag1Size;
    int bag2Size;
    int bag3Size;
    
    static boolean firstBin = true;/**If the passenger can store their luggage on their side of the row*/
    boolean checked;
    boolean inSeat;
    int waitTime;
    String color;
    String direction;
    Bin bin;
    Bin bin2;
    
    /**CONSTRUCTOR*/
    public Person(int x, int y, int numBags, int bag1Size, int bag2Size, int bag3Size)
    /**Pre: The seat the new person is going to sit at is passed in as well as bag sizes. 
     *      The seat passed in is not already taken.
     * Post: The passenger's seat location and information is stored.
     */
    {
        this.y = y;
        this.x = x;
        this.numBags = numBags;
        this.tempBags = 0;
        this.bag1Size = bag1Size;
        this.bag2Size = bag2Size;
        this.bag3Size = bag3Size;
        direction = "right";
        checked = false;
        inSeat = false;
        waitTime = 0;
        setPicture();
    }

    public void act() 
    /**Pre: The passenger is in the world.
     * Post: If the passenger is not in his seat, a call to move will be made
     */
    {
        if (!inSeat)
            Move();
    }
    
    public void Move()
    /**Pre: The passenger is in the world.  The passenger is not in his seat.
     * Post: If the passenger is not at the right seat row (column)
     *       Then the passenger will move one step closer to the desired seat row.
     *       Then the passenger will store their luggage. After that, the passenger will move
     *       one step closer to his seat number (row) until he is in his seat.
     *       Then inSeat will be set to true and this method will not be called anymore.
     */
    {
        if(getX() < x )/**Find the right column(seat row)*/
            findColumn();            
        else if (numBags > 0) /**If the passenger has bags to pack*/
        {
            if(direction != "")/**The actor has reached the correct row and now is stationary so we will change his picture*/
            {
                direction = "";
                updatePicture();
                waitTime = 0;/**Resets waiting time*/
            }

            /**Checking if the passneger has loaded a bag or not*/
            if(waitTime <= 0)
            {
                findBins();/**Find possibly bins for the passenger to use*/
                
                /**How full each of the bins from findBins are*/
                int num = bin.getImageNum();
                int num2 = bin2.getImageNum();
               
                if (!checkBin(bin, num))/**Checks the main bin for space, if there is space checkBin will place the bag there*/ 
                {
                    if(checkBin(bin2, num2))/**Checking the bin on the other side for space if there is space checkBin will place the bag there*/
                        firstBin = false;
                    else
                        findNewSpot(); /**No space in that row, needs to find a new spot to place the bag*/
                }
            }
            else
                waitTime--;
              
           /**Loads the bag*/
           if(waitTime == 0)/**Now if the time is 0 then you can load the bag*/
               loadBag();
        }
        else /**Bags have been packed and the passenger needs to sit down*/
            moveToSeat();
    }
    
    public void updatePicture()
    /**Pre: The passenger's direction or number of bags has changed.
     * Post: If the passenger wasn't able to store all of his bags then the number in tempBags will be stored
     *       Otherwise the actual number of bags will be used to update the picture.
     */
    {
        if(tempBags > 0)
            setImage("button-" + color + tempBags + direction + ".png");
        else
            setImage("button-" + color + numBags + direction + ".png");
    }
    
    public void setPicture()
    /**Pre: This is the last step of the instantiation of the person.  This is only called from the constructor.
     * Post: The person will be given their own color and their picture will be set.
     */
    {
        int y = Greenfoot.getRandomNumber(7);
        
        switch(y){
            case 0: 
                color = "blue";
                setImage("button-blue" + numBags + direction + ".png");
                break;
            case 1:
                color = "red";
                setImage("button-red" + numBags + direction + ".png");
                break;
            case 2:
                color = "cyan";
                setImage("button-cyan" + numBags + direction + ".png");
                break;
            case 3:
                color = "orange";
                setImage("button-orange" + numBags + direction + ".png");
                break;
            case 4:
                color = "yellow";
                setImage("button-yellow" + numBags + direction + ".png");
                break;
            case 5:
                color = "pink";
                setImage("button-pink" + numBags + direction + ".png");
                break;
            case 6:
                color = "green";
                setImage("button-green" + numBags + direction + ".png");
                break;
            default:
                color = "blue";
                setImage("button-blue" + numBags + direction + ".png");
                break;
            }
    }
    
    public void findColumn()
    /**Pre: The person has been added to the world. The person is not at their row yet.
     * Post: If there is space, the person will move to the right one spot. 
     */
    {
         if( direction != "right")
         {
            direction = "right";
            updatePicture();
         }
           waitTime++;/**Used because it takes each passenger 2 seconds to pass a row*/
           if(getOneObjectAtOffset(1,0,Person.class) == null && waitTime % 2 == 0)
               moveRight();      
    }
            
    public void findBins()
    /**Pre: The passenger has made it to hs row. Or is past his row looking for spots to palce luggage.
     * Post: bin and bin2 will be set to the bins on either side of the aisle of the current row the passenger is on.
     */
    {
        /**bin = the bin on the the passenger's seat side
         * bin2 = the bin on the opposite side of the passenger's seat side
         */
        if(y < 4) /**If the passenger is going up*/
        {
           if (getX() % 2 == 0)/**Making sure to get access to the left bin.  This is helpful when updating the bin [see bin class]*/
           {
                bin = (Bin)this.getOneObjectAtOffset(0,-4, Bin.class);
                bin2 = (Bin)this.getOneObjectAtOffset(0,4, Bin.class);
           }
           else 
           {
                bin = (Bin)this.getOneObjectAtOffset(-1,-4, Bin.class);
                bin2 = (Bin)this.getOneObjectAtOffset(-1,4, Bin.class);
           }
        }
        else /**else the passenger is going down*/
        {
           if (getX() % 2 == 0)/**Making sure to get access to the left bin.  This is helpful when updating the bin [see bin class]*/
           {
                bin = (Bin)this.getOneObjectAtOffset(0,4, Bin.class);
                bin2 = (Bin)this.getOneObjectAtOffset(0,-4, Bin.class);
           }
           else 
           {
                bin = (Bin)this.getOneObjectAtOffset(-1,4, Bin.class);
                bin2 = (Bin)this.getOneObjectAtOffset(-1,-4, Bin.class);
           }
        }
    }
    
    public void loadBag()
    /**Pre: The passenger has already waited the time delay for how many bags he has and how full the bin is.  
     *      There is space in the bin. The image of the bin shows that the luggage is already in the bin.
     *Post: The passenger is shown to have one less bag then before and the passenger's internal information will show that
     *      he has one less bag.
     */
    {
            if(numBags == 3)
                bag3Size = 0;
            else if (numBags == 2)
                bag2Size = 0;
            else if (numBags == 1)
                bag1Size = 0;
            
            numBags--;
            firstBin = true;
            
        updatePicture();/**Change the passengers picture*/
    }
    
    public boolean checkBin(Bin b, int num)
    /**Pre: The bin has been found that correponds ot the row the passenger is located.  The num is the corresponding 
     *      fullness of the bin.  The passenger still has bags to pack.
     * Post: A boolean is returned, whether or not the passenger was successful in storing his luggage in that bin.  
     *       The image of the bin has been updated to show that this space is now taken.      
     */
    {
        boolean space = false;
        
        /**Deciphering which bag the passenger is storing*/
        if (numBags == 3 && (num + bag3Size <= Global.BIN_SIZE))
        {
            waitTime = storeLuggage(1, num);/**Finding how long it will take the passenger to store that bag*/
            b.setImageNum(bag3Size);/**set IMAGE so that someone in the the row next to them in the same bin cannot add to the bin when there is no space*/
            b.updateImage(); /**Updates the bin now to show that the space is now taken*/
            space = true;
        }
        else if (numBags == 2 && (num + bag2Size <= Global.BIN_SIZE))
        {
            waitTime = storeLuggage(2, num);
            b.setImageNum(bag2Size);
            b.updateImage();
            space = true;
        }
        else if (numBags == 1 && (num + bag1Size <= Global.BIN_SIZE))
        {
            waitTime = storeLuggage(1, num);
            b.setImageNum(bag1Size);
            b.updateImage();
            space = true;
        }
        
        return space;
    }
    
    public void findNewSpot()
    /**Pre: There was no space in the previous row to place the passenger's luggage.
     * Post: The passenger moves to the next row if there is space.  If the passenger is at the end of plane, then 
     *       he will be prepared to jump back to his seat.
     */
    {
        if (getX() == Global.COLS - 1)/**The passenger has run out of rows to try and find a spot to place his luggage*/
        {
            tempBags = numBags;/** Used so that when the updatePicutre function is run, it does not error out*/
            numBags = 0;/**So the next iteration will execute moveToSeat();*/
            
            /**Chaning the picture so that it shows that the passenger is no longer moving*/
            if( direction != "")
            {    
                direction = "";
                updatePicture();
            }
            waitTime = -1; /**Make this change so the following if statement in Move() will NOT execute*/
        }
        else /**There is space for the passenger to move*/
        {
            if(getOneObjectAtOffset(1,0,Person.class) == null)/**Only one simulation step to move over one seat*/
                moveRight();             
        
            waitTime = -1;/**Make this change so the following if statement in Move() will NOT execute*/
        
            /**Make sure the image shows that the passenger is moving*/
            if(direction != "right")
            {
                direction = "right";
                updatePicture();
            }
        }
    }

    public void checkBlockedSeats()
    /**Pre: The passenger has packed all of his bags or he has reacehed the end of the plane and has to be transported back to his seat.
     * Post: The waitTime is assigned a value.  This is the time the passenger has to wait before he can move to his seat.  This waitTime
     *       takes into account the number of people in his row that are blocking his seat, and the amount of rows, he has to travel back.
     */
    {
        if( y < 4) /**Finding how many people are blocking your path to your seat*/
            waitTime = checkSeatedPeople(getX() - x,-1); 
        else
            waitTime = checkSeatedPeople(getX() - x,1);
        
        if((getX() - x) > 0)/**If you have had to move past your row, you need to get back!*/
            waitTime += (getX() - x) * 2;
    }
    
    public int checkSeatedPeople(int xOffset, int direction)
    /**Pre: The passenger is already at the right row. Direction is passed in based
     *      off whether or not the passenger is on the left or right side of the aisle
     * Post: The time the passenger has to wait to get into his seat is returned.
     *       4 seconds = 1 blocking passenger
     */
    {
       int obstacles = 0;
       if ( y != (getY()+ (1*direction)))
       {
           /**This takes into account if the passenger is not in the right row. We want to check the passengers sitting in his row*/
           if(xOffset > 0)
            xOffset = xOffset * -1;
            
           if(getOneObjectAtOffset(xOffset,1 * direction,Person.class) != null)
                obstacles++;
           if(getOneObjectAtOffset(xOffset,2 * direction,Person.class) != null)
                obstacles++;
       }
       
       checked = true;/**Finding seated people in the way only happens once so checked is now true*/
       return obstacles * 4;/**4 steps per obstacle*/
    }
    
    public void moveToSeat()
    /**Pre: The passenger has either stored all of his bags or there is no space for all of his bags.
     * Post: The passenger will wait a set amount of time for the obstacles in his way to his seat.  Then he will move towards his seat
     *       until he has reached it.  A passenger reachin his seat will take multiple passes of this method.
     * 
     */
    {
        boolean changeDirec = false;/**Keeps track if the passenger changes direction*/
        if(!checked)/**Finds the waitTime caused by other passengers in their row only once*/
        {
           checkBlockedSeats();
        }
        if (waitTime > 0)/**Passenger "waits" for the other passengers to move*/
            waitTime--;
        else /**Find the right seat number*/
        {
            if ((getX() - x) > 0)/**The passenger's seat is back many rows*/
            {
                resetRow();
                inSeat = true;
            }

            if (getY() < y)/**The passenger's seat is down*/
            {
                if(direction != "down")
                {
                    changeDirec = true;
                    direction = "down";
                }
                moveDown();
            }
            else if (getY() > y)/**The passenger's seat is up*/
            {
                if(direction != "up")
                {
                    changeDirec = true;
                    direction = "up";
                }
                moveUp();
            }
            else /**The passenger should be in his spot*/
            {
                if(direction != "")
                {
                    changeDirec = true;
                    direction = "";
                    if (getX() == x && getY() == y)
                        inSeat = true;
                }
            }
            
            /**This is needed because I do not have pictures with all numbers and directions
             * Without this check, there could be exceptions thrown
             */
            if(tempBags != 0)
            {
                if(direction != "")
                {
                    changeDirec = true;
                    direction = "";
                }
            }
            
            if(changeDirec)/**Only if the passenger changes direction will we update his picture*/
            {
                updatePicture();
                changeDirec = false;
            }
        }
    }
    
    public void moveRight()
    /**Pre: The passenger is in the world.
     * Post: The passenger moves one spot to the right.
     */
    {
       setLocation(getX() + 1,getY()); 
    }
    
    public void moveUp()
    /**Pre: The passenger is in the world.
     * Post: The passenger moves one spot up.
     */
    {
       setLocation(getX(),getY() - 1);
    }
    
    public void moveDown()
    /**Pre: The passenger is in the world.
     * Post: The passenger moves one spot down.
     */
    {
       setLocation(getX(),getY() + 1);
    }
    
    public int storeLuggage(int bags, int binFilled)
    /**Pre: The number of luggage and their sizes have been declared.
     * Post: The loading time for the current bag will be returned.
     */
    {
        int bagLoadTime = 0;
        /**Different times for how many bags the passenger is holding*/
        if(bags == 3)
            bagLoadTime += 8;
        else if (bags == 2)
            bagLoadTime += 6;
        else
            bagLoadTime += 4;
        
        /**Adding more time if the bin is fuller*/
        if (binFilled > 30)
            bagLoadTime += 6;
        else if (binFilled > 20)
            bagLoadTime += 3;
               
        return bagLoadTime;  
    }
    
    public void resetRow()
    /**Pre: The passenger has had to go to a different row to store his luggagage.
     *      A time delay was put in the checkBlockedSeats method to handle the time needed for the passenger
     *      to get back to his seat.
     * Post: The passenger is reset back to his seat.
     */
    {
        setLocation(x,y);
        direction = "";
        updatePicture();
    }
           
}
