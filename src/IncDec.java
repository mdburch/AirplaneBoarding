import greenfoot.*; 
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
/**
 * The IncDec class keeps track of the buttons that will be used to increment or decrement the values of the buttons
 * This class is an altered version of the Button class.
 * 
 * By Matt D Burch
 * 4/5/2011
 */

public class IncDec extends Actor
{
    private int sizeX, sizeY;
    private String text;
    private boolean hover;/**If the mouse is over the button*/
    private boolean clicked;/**If the button has been clicked*/
    private boolean enabled = true;/**If you are allowed to press the button*/
    
    /**CONSTRUCTOR*/
    public IncDec(String str, int x, int y)
    /**Pre: The text to be placed on the button as well as the size of the button is passed in.
     * Post: The text and size of the button are set. The image is displayed on the screen.
     */
    {
        sizeX = x;
        sizeY = y;
        text = str;
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
     *       button will appear with a black border, otherwise it will be a gray border.
     */ 
    {
        GreenfootImage pic = new GreenfootImage(sizeX, sizeY);
       
        if (!enabled)
            pic.setColor(new Color(102, 102, 102));
        
        /**Nested system to access all aspects of the graphics*/
        Graphics2D graphics = (new GreenfootImage(1, 1)).getAwtImage().createGraphics();
        graphics.setFont(new Font("New Times Roman", 20, 12));/**Setting the font*/
        FontMetrics fm = graphics.getFontMetrics();
        pic.setFont(new Font("New Times Roman", 20, 12));/**Setting the font*/
        pic.drawString(text, (sizeX - fm.charsWidth((text).toCharArray(), 0, (text).length())) / 2, 25);/**Drawing the string*/
        graphics.dispose();/**Delete the Graphics2D object*/
        
        if (clicked)/**Change picture color to gray if clicked*/
            pic.setColor(Color.GRAY);

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
    
    public void setEnable(boolean e)
    /**Pre: The button has been created.
     * Post: enabled is set to e and then the picture is updated.
     */
    {
        enabled = e;
        updateImage();
    }
}