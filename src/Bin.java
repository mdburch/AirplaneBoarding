import greenfoot.*; 

/**
 * This Bin class keeps track of all of the overhead bins on the plane.  This class is responsible
 * for updating the image of the bin and keeping track of how full a bin is.
 * 
 * Matt D Burch
 * 4/3/2011
 */
public class Bin  extends Actor
{
    /**Keeps track of how many bags are in the bin
     * Acceptable values are between 0 & 40  
     */
    private int imageNum;
    
    /**CONSTRUCTOR*/
    public Bin(String picture)
    /**Pre: None
     * Post: imageNum is set to 0 meaning that there are no items in the bin.
     */
    {
        setImage(picture);
        imageNum = 0;
    }
    
    public void act() 
    /**Pre: None
     * Post: None
     */
    {
         /**The bin itself does not perform actions*/
    }   
    
    public void updateImage()
    /**Pre: The bin has been instantiated. The left side of the bin is passed in.
     * Post: The bin has its image updated with the num (size of the new bag) added to the bin
     */
    {
        GreenfootImage image = getImage();
        image.clear(); /**Remove old image*/
        
        this.setImage("binLeft" + imageNum + ".PNG"); /**Update left side of the bin*/
        Bin bin = (Bin)this.getOneObjectAtOffset(1,0, Bin.class);/**Gain access to right side of the bin*/
        bin.resetImageNum();
        bin.setImageNum(imageNum);
        //bin.setImage("binRight" + bin.getImageNum() + ".PNG");/**Update righ side of bin*/
        bin.setImage("binRight" + this.imageNum + ".PNG");/**Update righ side of bin*/
    }
    
   int getImageNum()
    /**Pre: The bin has been instantiated
     * Post: The imageNum is returned which corresponds to how full the bin is
     */
    {
        return imageNum;
    }
    
   void setImageNum(int num)
   /**Pre: The bin has been instantiated.
    * Post: num (bag size) will be added to the imageNum (fullness of bin) only if in doing so, 
    *       it does not exceed the bin size (40).
    */
   {
       if(imageNum + num <= Global.BIN_SIZE)
           imageNum += num;
   }
   
   void resetImageNum()
   {
       imageNum = 0;
   }
    
}
