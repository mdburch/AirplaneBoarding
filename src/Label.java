import greenfoot.*; 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
/**
 * This Label class is used create the titles to specify what each button does.
 * This class is an alteration of the Counter class.
 * 
 * Matt D Burch
 * 4/5/2011
 */

public class Label extends Actor
{
    public static final float FONT_SIZE = 12.0f;
    private String text;
    private int stringLength;
    private Font font;

    /**CONSTRUCTOR*/
    public Label(String str)
    /**Pre: The string prefix is passed in.
     * Post: A label is created with the desired text(str)  
     *       The font attributes are also set (font, size)
     */
    {       
        text = str;
        stringLength = (text.length() + 4) * 10;

        GreenfootImage image = new GreenfootImage(stringLength, 22);
        setImage(image);
        font = image.getFont();
        font = font.deriveFont(FONT_SIZE);
        updateImage();
    }

    public void act()
    /**Pre: None
     * Post: None
     */
    {
    }
    
    private void updateImage()
    /**Pre: None
     * Post: The label is displayed on the screen.
     */    
    {       
        GreenfootImage image = getImage();
        image.clear(); /**Remove old image*/
        image.setFont(font);
        image.setColor(Color.BLACK);/**Set text to black*/
  
        image.drawString(text, 6, (int)FONT_SIZE);/**Print the image*/
    }
}