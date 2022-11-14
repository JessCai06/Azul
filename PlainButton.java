import javax.swing.JButton;

public class PlainButton extends JButton {
	int ID, factoryID;
	public static final int tilesize = 60;
	
	/**
	 * only for factory Buttons
	 * @param id
	 * @param fact
	 */
    public PlainButton (int id, int fact){
		setSize(tilesize, tilesize);
    	ID = id;
    	factoryID = fact;
        setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
    }
    /**
     * any other type of button
     * @param id
     */
    public PlainButton (int id) {
    	ID = id;
		setSize(tilesize, tilesize);
        setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
    }
}
