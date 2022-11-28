import java.awt.Color;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PlainButton extends JButton {
	public int ID, factoryID;
	public static int tilesize = 66;
	
	/**
	 * only for factory Buttons
	 * @param id
	 * @param fact
	 */
    public PlainButton (int id, int fact){
		setSize(tilesize, tilesize);
    	ID = id;
    	factoryID = fact;
    	setBorder(BorderFactory.createBevelBorder(10));
        setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
    }
    /**
     * any other type of button
     * @param id
     */
    //factory floor
    public PlainButton (int id) {
    	setSize(81, 81);
    	ID = id;
    	setBorder(BorderFactory.createBevelBorder(10));
        setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
    }
    //patternline
    public PlainButton (int id, int playerid, int row) {
    	setSize(30, 57);
    	ID = id;
    	//setBorder(BorderFactory.createBevelBorder(0));
    	/*try {
    	    Image img = ImageIO.read(getClass().getResource("/images/arrow1.png"));
    	    Image img2 = ImageIO.read(getClass().getResource("/images/1.jpg"));
    	    setIcon(new ImageIcon(img));
    	    setDisabledIcon(new ImageIcon(img2));
    	  } catch (Exception ex) {
    	    System.out.println(ex);
    	  }*/
    	setBorder(BorderFactory.createBevelBorder(10));
        setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
    }
    
    //floorline
    public PlainButton () {
    	setSize(465, 70);
    	setBorder(BorderFactory.createBevelBorder(10));
        setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
    }
    //whole screen
    public PlainButton (String str) {
    	setBorder(BorderFactory.createBevelBorder(10));
    	setBorder(BorderFactory.createBevelBorder(10));
        setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
    }

}
