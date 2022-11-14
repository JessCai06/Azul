import java.util.ArrayList;

public class Factory {
	ArrayList <PlainButton> ButtonList;
	ArrayList <Integer> TileColors;
	int Factoryx, Factoryy;
//~
//	NORMAL METHODS
//~	
		Factory (ArrayList <Integer> color, int i){
			ButtonList = new ArrayList <PlainButton>();
			TileColors = new ArrayList <Integer>();
			for (int r = 0; r < 4; r ++) {
				TileColors.add(color.get(r));
			}
			setFactoryCoordinates(i);
		}
		
		/**
		 * takes all the tiles from the TileColors arraylist, disables all of the buttons, and returns the arraylist removed
		 * @return
		 */
		public ArrayList <Integer> takeAll (){
			ArrayList <Integer> temp = new ArrayList <Integer>();
			while(!TileColors.isEmpty()) {
				temp.add(TileColors.remove(0));
			}
			for (PlainButton b: ButtonList) {
				b.setEnabled(false);
			}
			return temp;
		}
		/**
		 * fills TileColor based on the given tiles, enables all of the buttons
		 * @param tiles
		 */
		public void fill (ArrayList <Integer> tiles) {
			TileColors = new ArrayList <Integer>();
			while(!tiles.isEmpty()) {
				TileColors.add(tiles.remove(0));
			}
			for (PlainButton b: ButtonList) {
				b.setEnabled(true);
			}
		}
		/**
		 * returns if tile colors is empty
		 * @return
		 */
		public Boolean isEmpty () {
			return TileColors.isEmpty();
		}
		/**
		 * sets the coordinates of the factory based on its given index
		 * HELPFUL INFO:
		 * 		factory with index 0 is 1385; 75
				a tile image is 55 for now
				each factory is about 162 X 164
		 * @param i
		 */
//TODO
//need the algorithm for each factory coordinate - top left corner
		public void setFactoryCoordinates (int i) {

			switch(i) {
			case 0:{
				Factoryx = 1385;
				Factoryy = 75;
				break;
			}
			case 1:{
				Factoryx = 1385+i*160+2;
				Factoryy = 75;
			}
			case 2:{
				Factoryx = 1385+i*160+2;
				Factoryy = 75;
				break;
			}
			case 3:{
				Factoryx = 1385;
				Factoryy = 75 +(i%2)*160;
				break;
			}
			case 4:{
				Factoryx = 1385+2*160+2;
				Factoryy = 75 +i*160+2;
				break;
			}
			case 5:{
				Factoryx = 1395;
				Factoryy = 80;
				break;
			}
			case 6:{
				Factoryx = 1395;
				Factoryy = 80;
				break;
			}
			case 7:{
				Factoryx = 1395;
				Factoryy = 80;
				break;
			}
			case 8:{
				Factoryx = 1395;
				Factoryy = 80;
				break;
			}
			}
		}
//~
//----------BUTTON SECTION
//~
		public void setEnabled (Boolean bool) {
			for (PlainButton b: ButtonList) {
				b.setEnabled(bool);
			}
		}

//TODO
//need the algorithm for each button coordinate
		public void addButtons(ArrayList <PlainButton> buttons) {
			for (int r = 0; r < 4; r ++) {
				PlainButton temp = buttons.get(r);
				if (r == 0)
					temp.setLocation(Factoryx, Factoryy);
				if (r ==1)
					temp.setLocation(Factoryx, Factoryy);
				if (r ==2)
					temp.setLocation(Factoryx, Factoryy);
				if (r ==3)
					temp.setLocation(Factoryx, Factoryy);
				ButtonList.add(temp);	
			}
		}
}
