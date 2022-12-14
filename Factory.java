import java.util.ArrayList;

public class Factory {
	ArrayList <PlainButton> ButtonList;
	ArrayList <Integer> TileColors;
	int Factoryx, Factoryy;
	public static int tilesize = 66;
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
			System.out.println("-----------------------------REFILLING"+tiles);
			TileColors = new ArrayList <Integer>();
			int s = tiles.size();
			int i;
			for(i = 0; i< tiles.size();i ++) {
				System.out.println("------"+TileColors+"--------" + tiles.size());
				if (tiles.get(i)!=-1) {
					TileColors.add(tiles.remove(0));
					ButtonList.get(i).setEnabled(true);
					i--;
				}
			}
			for (int j = i; j<4; j++) {
				ButtonList.get(j).setEnabled(false);
			}
				
			//System.out.println("------"+TileColors+"--------");
			
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
				Factoryy = 68;
				break;
			}
			case 1:{
				Factoryx = 1385+i*160+4;
				Factoryy = 68;
			}
			case 2:{
				Factoryx = 1385+i*160+7;
				Factoryy = 68;
				break;
			}
			case 3:{
				Factoryx = 1383;
				Factoryy = 75 +(i%2)*160;
				break;
			}
			case 4:{
				Factoryx = 1385+1*160+9;
				Factoryy = 75 +(1)*160;
				break;
			}
			case 5:{
				Factoryx = 1385+2*160+12;
				Factoryy = 75 +(1)*160;
				break;
			}
			case 6:{
				Factoryx = 1383;
				Factoryy =  75 +(2)*160+10;
				break;
			}
			case 7:{
				Factoryx = 1385+1*160+9;
				Factoryy =  75 +(2)*160+10;
				break;
			}
			case 8:{
				Factoryx = 1385+2*160+12;
				Factoryy = 75 +(2)*160+10;
				break;
			}
			}
		}
//~
//----------BUTTON SECTION
//~
		public void setEnabled (Boolean bool) {
			for (PlainButton b: ButtonList) {
				if (isEmpty())
					b.setEnabled(false);
				else
					b.setEnabled(bool);
			}
		}

//need the algorithm for each button coordinate
		public void addButtons(ArrayList <PlainButton> buttons) {
			for (int r = 0; r < 4; r ++) {
				PlainButton temp = buttons.get(r);
				if (r == 0)
					temp.setLocation(Factoryx, Factoryy);
				if (r ==1)
					temp.setLocation(Factoryx+tilesize, Factoryy);
				if (r ==2)
					temp.setLocation(Factoryx, Factoryy+tilesize);
				if (r ==3)
					temp.setLocation(Factoryx+tilesize, Factoryy+tilesize);
				ButtonList.add(temp);	
			}
		}
}
