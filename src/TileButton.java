import java.awt.event.ActionListener;

import javax.swing.JButton;

public class TileButton extends JButton{
	int ID;
	
	public TileButton (int id) {
		ID = id;
		
	}
	
	public int getid() {
		return ID;
	}
}
