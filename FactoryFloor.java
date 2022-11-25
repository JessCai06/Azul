import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.BorderFactory;

public class FactoryFloor {
	
	ArrayList <PlainButton> ButtonList;
	int [] colors;
	Boolean firstMarker, firsttaken;

//~
//	NORMAL METHODS
//~	
	FactoryFloor (){
		ButtonList = new ArrayList <PlainButton>();
		colors = new int [5];
		firstMarker = true;
		firsttaken = false;
		setCoordinates();
	}
	/**
	 * addes this arraylist of tiles to the big array
	 * @param tiles
	 */
	public void add(ArrayList <Integer> tiles) {
		for (int i = 0; i < 5; i++) {
			while (tiles.contains(i)) {
				colors [i] += 1;
				tiles.remove(tiles.indexOf(i));
			}
		}
	}
	public void add(int color) {
				colors [color] += 1;

	}
	public int takeAll(int color) {
		int num = colors[color];
		colors[color] = 0;
		return num;
	}
	/**
	 * sets the coordinates of the factoryFloor buttons 
	 * @param i
	 * @return 
	 */
//TODO	
	public void setCoordinates () {
		for (int i = 0; i < ButtonList.size(); i++) {
			int x = 0;
			int y = 0;
			switch(i) {
				case 0:{
					x = 715;
					y = 86;
					break;
				}
				case 1:{
					x = 0;
					y = 0;
					break;
				}
				case 2:{
					x = 0;
					y = 0;
					break;
				}
				case 3:{
					x = 0;
					y = 0;
					break;
				}
				case 4:{
					x = 0;
					y = 0;
					break;
				}
			}
			ButtonList.get(i).setLocation(x,y);
		}
	}
//~
//----------BUTTON SECTION
//~
	/**
	 * 
	 * @param bool
	 */
		public void setEnabled (Boolean bool) {
			for (int i = 0; i < colors.length; i++) {
				if (colors[i]<=0)
					ButtonList.get(i).setEnabled(false);
				else
					ButtonList.get(i).setEnabled(bool);
			}
		}
//TODO
//need the algorithm for each button coordinate
		public void addButtons(ArrayList <PlainButton> buttons) {
			for (int r = 0; r < buttons.size(); r ++) {
				PlainButton temp = buttons.get(r);
				if (r == 0)
					temp.setLocation(715-10, 86-10);
				if (r ==1)
					temp.setLocation(907-10, 91-10);
				if (r ==2)
					temp.setLocation(1110-10, 91-10);
				if (r ==3)
					temp.setLocation(715-10, 199-10);
				if (r ==4)
					temp.setLocation(907-10, 199-10);
				if (r ==5)
					temp.setLocation(1110-10, 199-10);

				//temp.setBorder(BorderFactory.createBevelBorder(10));
				ButtonList.add(temp);	
			}
		}	
}
