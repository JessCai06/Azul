import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PlainButton extends JButton {
	int ID, factoryID;
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
        /*try {
            Image img = ImageIO.read(getClass().getResource("/images/transparent.png"));
            setIcon(new ImageIcon(img));
          } catch (Exception ex) {
            System.out.println(ex);
          }*/
        setOpaque(false);
    }
    /**
     * any other type of button
     * @param id
     */
    public PlainButton (int id) {
    	setSize(81, 81);
    	ID = id;
    	setBorder(BorderFactory.createBevelBorder(0));
        /*setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);*/
    }
    public PlainButton () {
    	setSize(465, 70);
    	setBorder(BorderFactory.createBevelBorder(0));
        /*setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);*/
    }
}
