import java.util.ArrayList;

public class Player {

	int [][] patternLine; 
	ArrayList <ArrayList <Integer>>wall;
	int [] floorLine;
	ArrayList <Integer> BufferZone;
	int score, ID;
	
	//
	public static ArrayList<Integer> lid = AzulWindow.lid;
	
	Player (int id){
		floorLine = new int[7];
		// adding first player marker to floor line
		if (id == 1) {
			//floorLine[0]= 5;
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
		for (int r = 0; r <5; r++) {
			ArrayList <Integer> arr = new ArrayList <Integer>();
			for (int c = 0; c <5; c++) 
				arr.add(-1);
			wall.add(arr);
		}
		for (int r = 0; r <7; r++) {
			floorLine [r] = -1;
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

	

}
