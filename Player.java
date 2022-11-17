import java.util.ArrayList;

public class Player {
	ArrayList <PlainButton> patternButton;
	PlainButton FloorButton;
	int [][] patternLine, wall;
	int [] floorLine;
	ArrayList <Integer> BufferZone;
	int score, ID;
	
	Player (int id){
		patternButton = new ArrayList <PlainButton>();

		// adding first player marker to floor line
		if (id == 1) {
			//floorLine[0]= 5;
		}
		// creating a jagged matrix filled with -1s - to not mix up with the normal tile numbers: 0 - 4
		patternLine = new int [5][];
			for (int i = 1; i <=5; i++) {
				int []temp = new int[i];
				for (int n = 1; n < i; n++)
					temp[n]=-1;
				patternLine [i-1]=temp;
			}
		// creating a normal matrix filled with -1s	
		wall = new int [5][5];
		for (int r = 0; r <5; r++) {
			for (int c = 0; c <5; c++) {
				wall [r][c]= -1;
			}
		}
		//miscellaneous
		BufferZone = new ArrayList<>();
		score = 0;
		ID = id;
	}
	/**
	 * adds arraylist of tiles to bufferzone
	 * @param tiles
	 */
	void addBufferZone (int c) {
			BufferZone.add(c);
	}
	
	/**
	 * adds everything in bufferzone into the patternline (given row)
	 * @param row
	 */
// TODO
	void addPatternLine (int row) {
			while (!BufferZone.isEmpty()) {
				//adding to floor line
				if (row > 5) {
					
				}
				//adding to patternLine
				if (row<5) {
					//normal adding
					
					//floorline is full
				}
			}
		
	}
//TODO	
	public void setEnabled (Boolean bool) {
		// conditions needed to fulfill:
		/*
		 * pline - - may not add if
		 * - wall has color on same row
		 * - already filled
		 * - already has another color
		 * 
		 * floor line - may not ad d if
		 * - already filled
		 */
		for (PlainButton b: patternButton) {
			b.setEnabled(bool);
		}
	}
//TODO	
	public void addButtons(ArrayList <PlainButton> buttons, PlainButton floor) {
		patternButton = new ArrayList<>();
		for (int r = 0; r < 5; r ++) {
			PlainButton temp = buttons.get(r);
			temp.setLocation(973, 580+r*57+r*3);
			
			patternButton.add(temp);
		}
		FloorButton = floor;
		FloorButton.setLocation(665, 920);
		
		}

	

}
