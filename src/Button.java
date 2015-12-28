import greenfoot.*; 
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
/**
 * The Button class keeps track of each button on the screen.  It will update the text that is placed on the
 * button. 
 * 
 * By Matt D Burch
 * Adopted from code originally written by Taylor Born
 * 4/5/2011
 */

public class Button extends Actor
{
    private int sizeX, sizeY, value;
    private String text;
    private boolean hover;/**If the mouse is over the button*/
    private boolean clicked;/**If the button has been clicked*/
    private boolean selected = false;/**If this button is the current one selected*/
    private boolean enabled = true;/**If you are allowed to press the button*/

    /**CONSTRUCTOR*/
    public Button(int val, int x, int y)
    /**Pre: Three values have been passed in.  The first is a numerical value to be place on the button and the 
     *      other two are values for the size of the buttons. The numerical value is used to tell what % of passengers
     *      will fall under that amount of bags.  
     * Post: The value and size of the button are set. The image is displayed on the screen with the current value.
     *       val will be stored in value and will be maintained throughout the execution.  This value is important
     *       becuase it will be used to make sure the simulation can start and is used as a point of reference for other
     *       classes.
     */
    {
        sizeX = x;
        sizeY = y;
        text = Integer.toString(val);
        value = val;
        hover = false;
        updateImage();
    }
    
    /**CONSTRUCTOR*/
    public Button(String str, int x, int y)
    /**Pre: The text to be placed on the button as well as the size of the button is passed in.
     * Post: This constructor is for the start button so the value variable is irrelevant.  A button will be created
     *       with the str printed on it.
     */
    {
        sizeX = x;
        sizeY = y;
        text = str;
        value = -1;
        hover = false;
        updateImage();
    }
    
    public void act() 
    /**Pre: The button has been created. 
     * Post: The image of the button will be updated if the mouse scrolls over the it or the button is clicked.
     */
    {
        if (enabled)/**As long as the button can be altered*/
        {
            boolean last = hover;/**Tracks changes*/
            
            if (Greenfoot.mouseMoved(this))/**If the mouse is on the button*/
                hover = true;
            else if (Greenfoot.mouseMoved(null))/**If the mouse is not on the button*/
                hover = false;
                
            if (Greenfoot.mouseClicked(this) && hover)/**If the button has been clicked and the mouse is over the button*/
                clicked = true;
            
            if (last != hover)/**If there are changes since the last iteration*/
                updateImage();
        }
    }
    
    public void updateImage()
    /**Pre: The button has been created
     * Post: The button gets recreated everytime this method is called. A gray background will appear if the mouse
     *       is over the button, otherwise the button will have a white background. If the button is enabled, then the
     *       button will appear with a black border, otherwise it will be a gray border.  If the button is selected, then
     *       the button and text will appear blue to let the user know it has been selected
     */ 
    {
        GreenfootImage pic = new GreenfootImage(sizeX, sizeY);
        
        if (!enabled)
            pic.setColor(new Color(102, 102, 102));
        
        if(selected)/**If the button is selected, change the color to blue for the font*/
            pic.setColor(Color.BLUE);
        
        /**Nested system to access all aspects of the graphics*/
        Graphics2D graphics = (new GreenfootImage(1, 1)).getAwtImage().createGraphics();
        graphics.setFont(new Font("New Times Roman", 20, 12));/**Setting the font*/
        FontMetrics fm = graphics.getFontMetrics();
        pic.setFont(new Font("New Times Roman", 20, 12));/**Setting the font*/
        pic.drawString(text, (sizeX - fm.charsWidth((text).toCharArray(), 0, (text).length())) / 2, 25);
        graphics.dispose();/**Delete the Graphics2D object*/
        
        if (clicked)/**Change picture color to gray if clicked*/
            pic.setColor(Color.GRAY);
        
        if(selected)/**If the button is selected, change the color to blue*/    
            pic.setColor(Color.BLUE);
        
        pic.drawRect(0, 0, sizeX - 1, sizeY - 1);/**Drawing the button*/
        
        if (hover)/**If your mouse is over the icon*/
        {
            GreenfootImage back = new GreenfootImage(sizeX, sizeY);
            back.fill();/**Fills the background of the button gray*/
            
            if(enabled)/**Changes the appearance of the picture whether or not it is enabled*/
                back.setTransparency(25);
            else
                back.setTransparency(0);
            
            /**Drawing the final image*/    
            GreenfootImage pic2 = new GreenfootImage(sizeX, sizeY);
            pic2.drawImage(back, 0, 0);
            pic2.drawImage(pic, 0, 0);
            pic = pic2;
        }
        setImage(pic);
    }
    
    public boolean wasClicked()
    /**Pre: The button has been created.
     * Post: Returns whether or not the button has been clicked.
     */
    {
        boolean c = clicked;
        if(c)
          updateImage();
            
        clicked = false;
        return c;
    }
    
    public void setText(String str)
    /**Pre: The button has been created. A string is passed in.
     * Post: text will be set to the string passed in and the image will be updated
     *       with the new text on the button.
     */
    {
        text = str;
        updateImage();
    }
    
    public void setSelected(boolean s)
    /**Pre: The button has been created. 
     * Post: The button will be updated to show that it is either selected (blue) or not selected (black).
     */
    {
        selected = s;
        updateImage();
    }
    
    public boolean getSelected()
    /**Pre: The button has been created.
     * Post: Will return true if the button is currently selected, otherwise it will return false.
     */
    {
        return selected;
    }
    
    public void addValue(int val)
    /**Pre: The button has been created. 
     * Post: The value is incremented by the val only if the result is less
     *       than 100.  Then the image of the button is updated with the new value.
     */
    {
        if(value + val > 100)
            value = 100;
        else
            value += val;
        
        setText(Integer.toString(value));    
        updateImage();
    }
    
    public void decValue(int val)
    /**Pre: The button has been created. 
     * Post: The value is decremented by the val only if the result is greater
     *       than 0.  Then the image of the button is updated with the new value.
     */
    {
        if(value - val < 0)
            value = 0;
        else
            value -= val;
        
        setText(Integer.toString(value));
        updateImage();    
    }
    
    public int getValue()
    /**Pre: The button has been created.
     * Post: The value associated with the button is returned. 
     */
    {
        return value;
    }
    
    public String getText()
    /**Pre: The button has been created.
     * Post: The text associated with the button will be returned.
     */
    {
        return text;
    }
    
    public void setEnable(boolean e)
    /**Pre: The button has been created.
     * Post: enabled is set to e and then the picture is updated.
     */
    {
        enabled = e;
        updateImage();
    }
    
    public boolean getEnabled()
    /**Pre: The button has been created
     * Post: Returns true if the button is enabled, otherwise it trturns false.
     */
    {
        return enabled;
    }
}