import greenfoot.*; 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
/**
 * This Counter class is used to run the timer at the bottom right of the simulation
 * It is responsible for incrementing the timer and printing out the current value as
 * well as printing out the total time of the simulation at the end of execution
 * 
 * Matt D Burch
 * Adopted from code originally written by Michael Kölling
 * 4/3/2011
 */

public class Counter extends Actor
{
    public static final float FONT_SIZE = 12.0f;
    private static int steps;
    private String text;
    private int stringLength;
    private Font font;

    /**CONSTRUCTOR*/
    public Counter(String prefix)
    /**Pre: The string prefix is passed in.
     * Post: A counter is created with the desired text(prefix)
     *       placed in front of the counter.  
     *       The font attributes are also set (font, size)
     */
    {       
        this.steps = 0;
        text = prefix;
        stringLength = (text.length() + 4) * 10;

        GreenfootImage image = new GreenfootImage(stringLength, 22); /**GreenfootImage(width, height)*/
        setImage(image);
        font = image.getFont();
        font = font.deriveFont(FONT_SIZE);/**Creates a new font object with the specified size*/
        updateImage();
    }

    public void act()
    /**Pre: None
     * Post: Steps is incremented by 1. steps counts the number of simulation steps.
     *       The image is updated with the new value.
     */
    {
       if(Global.started)
       {
            steps++;
            updateImage();
       }
    }

    public int getTime()
    /**Pre: None
     * Post: The value of time is returned.
     */
    {
        return steps;
    }
    
    private void updateImage()
    /**Pre: None
     * Post: The new time is displayed on the screen.
     */    
    {       
        GreenfootImage image = getImage();
        image.clear(); /**Remove old image*/
        image.setFont(font);
        image.setColor(Color.BLACK);/**Set text to black*/
  
        image.drawString(text + steps, 6, (int)FONT_SIZE);
    }
    
    public static void print()
    /**Pre: The simulation has completed.
     * Post: The total time to run the simulaton is printed out.
     */
    {  
        System.out.println("# of steps: " + steps);
    }
}