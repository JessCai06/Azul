import java.util.ArrayList;

public class Player {

	int [][] patternLine; 
	ArrayList <ArrayList <Integer>>wall,idealwall,scoreWall;
	int [] floorLine;
	ArrayList <Integer> BufferZone;
	int score, ID;
	
	//
	public static ArrayList<Integer> lid = AzulWindow.lid;
	public static FactoryFloor factoryFloor = AzulWindow.factoryFloor;
	
	Player (int id){
		score = 0;
		floorLine = new int[7];
		// adding first player marker to floor line
		if (id == 1) {
			floorLine[0]= 5;
		}
		// creating a jagged matrix filled with -1s - to not mix up with the normal tile numbers: 0 - 4
		patternLine = new int [5][];
			for (int i = 0; i <5; i++) {
				int []temp = new int[i+1];
				for (int n = 0; n < i+1; n++)
					temp[n]=-1;
				patternLine [i]=temp;
			}
		// creating a normal matrix filled with -1s	
		wall = new ArrayList <ArrayList <Integer>>();
		scoreWall = new ArrayList <ArrayList <Integer>>();
		for (int r = 0; r <5; r++) {
			ArrayList <Integer> arr = new ArrayList <Integer>();
			ArrayList <Integer> arr2 = new ArrayList <Integer>();
			for (int c = 0; c <5; c++) {
				arr.add(-1);
				arr2.add(-1);
			}
			wall.add(arr);
			scoreWall.add(arr2);
		}
		for (int r = 0; r <7; r++) {
			floorLine [r] = -1;
		}
		//ideal wall
		idealwall = new ArrayList <ArrayList <Integer>>();
		for (int r = 0; r< 5; r++) {
			ArrayList <Integer> arr = new ArrayList <Integer>();
			for (int c = 0; c<5; c++) {
				int i = (c-r)%5;
				if (i == 0)
					arr.add(i);
				else if (i == 1 || i == -4)
					arr.add(1);
				else if (i == 2 || i ==-3 )
					arr.add(3);
				else if (i == 3 || i ==-2)
					arr.add(4);
				else if (i == 4 || i == -1)
					arr.add(2);
			}
			idealwall.add(arr);
		}
		//miscellaneous
		BufferZone = new ArrayList<>();
		score = 0;
		ID = id+1;
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

	// next empty for patternline
	int nextEmpty(int row) {
		if (patternLine[row][patternLine[row].length-1]==-1) {
			for (int r = 0; r <patternLine[row].length; r++) {
				if (patternLine[row][r]==-1)
					return r;
			}
		}
		return -1;
	}
	// next empty for floor
	int nextEmpty() {
		if (floorLine[6]==-1) {
			for (int r = 0; r <floorLine.length; r++) {
				if (floorLine[r]==-1)
					return r;
			}
		}
		return -1;
	}
	void addFloorLine () {
		while (!BufferZone.isEmpty()) {
			if (nextEmpty() !=-1) {
				floorLine[nextEmpty()]= BufferZone.remove(0);
			}
			else
				lid.add(BufferZone.remove(0));
		}
		//wall.set(row,BufferZone);
	
}
	void clearPattern() {
		for(int r = 0; r< 5; r++) {
			for (int c = 0; c<patternLine[r].length; c++) {
				if (patternLine[r][patternLine[r].length-1]==-1)
					break;
				lid.add(patternLine[r][c]);
				patternLine[r][c] = -1;
				
			}
		}
	}
	void clearFloor() {
		for (int r = 0; r <7; r++) { 
			if (floorLine [r] == 5)
				factoryFloor.firstMarker = true;
			else if (floorLine [r] != -1)
				lid.add(floorLine[r]);
			floorLine [r] = -1;
		}
	}
	// returns true if there's 5 a row
	Boolean checkWall() {
		for (int r = 0; r< 5; r++) {
			for (int c = 0; c<5; c++) {
				if(wall.get(r).get(c) == -1)
					break;
				else if (c==4)
					return true;
			}}
		return false;
	}
	int score (int row) {
		int p = 1;
		int t;
		int [] temp = patternLine[row];
		if (temp[temp.length-1] == -1)
			return -1;
		else {
			int col = idealwall.get(row).lastIndexOf(temp[0]);
			//checks
			System.out.println(" PLAYER"+ID+" ROW"+row + " COL," + col + idealwall.get(row));
				for (int c = 1; c < 5; c++) {
					t = col+c;
					if (c+ col < 5) {
						if (wall.get(row).get(c+ col) == -1)
							break;
						else
							p ++;
					}
				}
				for (int c = 1; c < 5; c++) {
					t = col-c;
					if (col-c >=0) {
						if (wall.get(row).get(col-c) == -1)
							break;
						else
							p ++;
					}
				}
				for (int c = 1; c < 5; c++) {
					t = c+ row;
					if (c+ row < 5) {
						if (wall.get(c+ row).get(col) == -1)
							break;
						else
							p ++;
					}
				}
				for (int c = 1; c < 5; c++) {
					t = row-c;
					if (row-c >=0) {
						if (wall.get(row-c).get(col) == -1)
							break;
						else
							p ++;
					}
				}
			
		}
		return p;
	}

	

}
