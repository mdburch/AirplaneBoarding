import greenfoot.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * This class is responsible for creating the floor plan of the airplane or in other words, the world.
 * 
 * Matt D Burch
 * 4/5/2011
 */

public class Airplane  extends World
{
    private IncDec up1, down1, up5, down5;
    private Button start, bag1, bag2, bag3, occ;
    private String currPolicy = "100/0/0", currOccupancy = "100";
    
    /** Constructor for objects of class background.*/
    public Airplane()
    {    
        /** Create a new world with 10x20 cells with a cell size of 35x35 pixels.*/
        super(Global.COLS, Global.ROWS, 35);

        /**Adding the timer into the world*/
        addObject(new Counter("Steps: "), 18, 10);
        
        /**Sets the order in which the different classes are painted on the screen*/
        setPaintOrder(Person.class, Path.class, Seat.class, Counter.class, Timer.class);
        
        /**Sets the order in which the class perform their act function at every step*/
        setActOrder(Person.class, Counter.class, Timer.class, PersonAdder.class);

        /**Adding the seats of the plane into the world*/       
        for(int i = 1; i < 4; i++)
            for(int j = 0; j < Global.COLS; j++)
                addObject(new Seat(),j,i);
        
        for(int i = 5; i < Global.ROWS-3; i++)
            for(int j = 0; j < Global.COLS; j++)
                addObject(new Seat(),j,i); 
     
        /**Adding the aisle of the plane into the world*/
        for(int j = 0; j < Global.COLS; j++)
            addObject(new Path(),j,4);
        
        /**Adding the bins to the outside of each side of the row of seats*/
        for(int j = 0; j < Global.COLS; j++)
        {
            if( j % 2 == 0)
            {
                addObject(new Bin("binLeft0.PNG"), j, 0);
                addObject(new Bin("binLeft0.PNG"), j, 8);
            }
            else
            {
                addObject(new Bin("binRight0.PNG"), j, 0);
                addObject(new Bin("binRight0.PNG"), j, 8);
            }
          
        }
        
        /**This adds an object that is responsible for adding new people in the world*/
        addObject(new PersonAdder(),0,4);
        
        /**Buttons for the different bag policy*/
        bag1 = new Button(60, 70, 35);
        addObject(bag1, 7, 10);
        bag1.setSelected(true);
        bag1.setEnable(false);
        
        bag2 = new Button(30, 70, 35);
        addObject(bag2, 9, 10);
        bag3 = new Button(10, 70, 35);
        addObject(bag3, 11, 10);
        
        /**Start button*/
        start = new Button("Start", 70, 35);
        addObject(start, 15, 10);
        
        /**Occupancy button*/
        occ = new Button(100, 70, 35);
        addObject(occ, 13, 10);
        
        /**Control buttons to change the values of the bin and occupancy buttons*/
        up1 = new IncDec("+1", 70, 35);
        addObject(up1, 1, 9);
        down1 = new IncDec("-1", 70, 35);
        addObject(down1, 1, 10);
        up5 = new IncDec("+5", 70, 35);
        addObject(up5, 3, 9);
        down5 = new IncDec("-5", 70, 35);
        addObject(down5, 3, 10);
        
        /**Labels to place above the buttons*/
        addObject(new Label("1 bag (%)"), 8, 9);
        addObject(new Label("2 bag (%)"), 10, 9);
        addObject(new Label("3 bag (%)"), 12, 9);
        addObject(new Label("Occupancy"), 14, 9); 
        
    }
    
    public void act()
    {
        if(!Global.started)
        {
            if(bag1.getValue() + bag2.getValue() + bag3.getValue() != 100)
                start.setEnable(false);
            else
                start.setEnable(true);
                    
            if(start.wasClicked() && start.getEnabled())
            {
                start.setEnable(false);
                start.setText("Running...");
                bag1.setEnable(false);
                bag2.setEnable(false);
                bag3.setEnable(false);
                occ.setEnable(false);
                bag1.setSelected(false);
                bag2.setSelected(false);
                bag3.setSelected(false);
                occ.setSelected(false);
                up1.setEnable(false);
                down1.setEnable(false);
                up5.setEnable(false);
                down5.setEnable(false);
                Global.started = true;
                
                Global.occupancy(occ.getValue());
                Global.setPolicy(bag1.getValue(), bag2.getValue(), bag3.getValue());
            }
            if(bag1.wasClicked())
                update(bag1, bag2, bag3, occ);
            else if(bag2.wasClicked())
                update(bag2, bag1, bag3, occ);
            else if(bag3.wasClicked())
                update(bag3, bag1, bag2, occ);
            else if(occ.wasClicked())
                update(occ, bag1, bag2, bag3);
            else if (up1.wasClicked())
                findSelected(true, 1);
            else if (down1.wasClicked())
                findSelected(false, 1);
            else if (up5.wasClicked())
                findSelected(true, 5);
            else if (down5.wasClicked())
                findSelected(false, 5);
        }
    }
    
    public void update (Button select, Button one, Button two, Button three)
    {
        /**This button is now selected and cannot be clicked again for now*/
        select.setSelected(true);
        select.setEnable(false);
        
         /**These buttons are not selected anymore*/
        one.setSelected(false);
        two.setSelected(false);
        three.setSelected(false);
        
         /**These buttons can be clicked again*/
        one.setEnable(true);
        two.setEnable(true);
        three.setEnable(true); 
    }
    
    public void findSelected(boolean increase, int val)
    {
        Button select = new Button(0, 70, 35);
        
        if(bag1.getSelected())
            select = bag1;
        else if (bag2.getSelected())
            select = bag2;
        else if (bag3.getSelected())
            select = bag3;
        else if (occ.getSelected())
            select = occ;
            
        if(increase)
            select.addValue(val);
        else
            select.decValue(val);
    }  
}
