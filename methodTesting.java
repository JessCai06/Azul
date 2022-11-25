import java.awt.Container;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class methodTesting {
	
	static ArrayList <ArrayList <Integer>> wall, idealwall;
	static ArrayList <Integer> TileColors;
	
	//window attributes
	public static final int width = 1920;
	public static final int height = 1080;
	public static final int tilesize = 60;
	public BufferedImage []tileimage;
	public ImageIcon[] ruleBook;
	Container c;
	
	//game essentials

	static ArrayList<Integer> bag, lid;
	Boolean firstTake;

	public static void main (String arg[]){
    	Scanner input = new Scanner (System.in);

    	//filling the bag and lid
    			bag = new ArrayList<Integer>();
    			for (int i = 0; i<= 4; i++) 
    				for (int j = 0; j< 20; j++) 
    					bag.add(i);
    			Collections.shuffle(bag);
    			lid = new ArrayList<Integer>();
    	
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
    		wall = new ArrayList <ArrayList <Integer>>();
				ArrayList <Integer> arr = new ArrayList <Integer>();
				arr.add(-1);
				arr.add(1);
				arr.add(1);
				arr.add(1);
				arr.add(0);
				wall.add(arr);
				
				arr = new ArrayList <Integer>();
				arr.add(2);
				arr.add(2);
				arr.add(3);
				arr.add(0);
				arr.add(4);
				wall.add(arr);
				
				arr = new ArrayList <Integer>();
				arr.add(1);
				arr.add(2);
				arr.add(3);
				arr.add(0);
				arr.add(-1);
				wall.add(arr);
				
				arr = new ArrayList <Integer>();
				arr.add(1);
				arr.add(0);
				arr.add(0);
				arr.add(-1);
				arr.add(0);
				wall.add(arr);
				
				arr = new ArrayList <Integer>();
				arr.add(1);
				arr.add(4);
				arr.add(0);
				arr.add(-1);
				arr.add(1);
				wall.add(arr);

				for (ArrayList<Integer> i: wall) {
					for (int n: i)
						System.out.print(n+" ");
					System.out.println("");
					}

				
			/*	for (ArrayList<Integer> i: idealwall) {
					for (int n: i)
						System.out.print(n+" ");
					System.out.println();
					}*/
			
    			System.out.println();
    			ArrayList <Integer> arr1 = new ArrayList <Integer>();
				arr1.add(2);
				arr1.add(1);
				arr1.add(-1);
				arr1.add(-1);
				arr1.add(-1);
				int i = 0;
				System.out.println("color" + scorecolor());
				System.out.println("horiz" + scoreHoriz());
				System.out.println("verti" + scoreVerti());
				System.out.println(false||false||false||true);

        }
	public static int scorecolor() {
		int cum = 0;
		for (int r = 0; r< 5; r++) {
			for (int c = 0; c<5; c++) {
				if(!wall.get(c).contains(r))
					break;
				else if (c==4)
					cum ++;
			}}
		return cum;
	}
	public static int scoreVerti() {
		int cum = 0;
		for (int r = 0; r< 5; r++) {
			for (int c = 0; c<5; c++) {
				if(wall.get(c).get(r) == -1)
					break;
				else if (c==4)
					cum ++;
			}}
		return cum;
	}
	public static int scoreHoriz() {
		int cum = 0;
		for (int r = 0; r< 5; r++) {
			for (int c = 0; c<5; c++) {
				if(wall.get(r).get(c) == -1)
					break;
				else if (c==4)
					cum ++;
			}}
		return cum;
	}
}
