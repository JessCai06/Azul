import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

public class AzulWindow extends JFrame implements ItemListener, ActionListener{
	
	//window attributes
	public static boolean strt = AzulPanel.strt;
	public static final int width = 1920;
	public static final int height = 1080;
	public static final int tilesize = 60;
	public BufferedImage []tileimage;
	public ImageIcon[] ruleBook;
	Container c;
	
	//game essentials
	static Factory[] allFactory;
	static FactoryFloor factoryFloor;
	static ArrayList<Integer> bag, lid;
	static Boolean firstTake, endgame, roundscore;
	static ArrayList <Player> victors;
	static PlainButton startB;
	
	
	//players
	ArrayList <PlainButton> patternButton;
	PlainButton FloorButton;
	static Player [] allPlayer;
	
	//animations
	private static AzulPanel azulPanel;
	private ArrayList<AnimatableObject> animatableObjectList;
	//Screen s;
	AzulPanel panel;
	
	//rule book
	public JScrollPane scrollPane;
	
	AzulWindow () throws InterruptedException{
		
		//Container
		c = getContentPane();
		c.setLayout(null);
		
		//scroll
		//scrollPane  = new JScrollPane(new JLabel(ruleBook[0]));
		
		roundscore = false;
		endgame = false;
		
		//images
		tileimage = new BufferedImage [6];	
		ruleBook = new ImageIcon [6];	
		try{
		    tileimage[0] = ImageIO.read(getClass().getResource("/images/blue.png"));
		    tileimage[1] = ImageIO.read(getClass().getResource("/images/yellow.png"));
		    tileimage[2] = ImageIO.read(getClass().getResource("/images/white.png"));
		    tileimage[3] = ImageIO.read(getClass().getResource("/images/red.png"));
		    tileimage[4] = ImageIO.read(getClass().getResource("/images/black.png"));
		    tileimage[5] = ImageIO.read(getClass().getResource("/images/1.jpg"));
		    ruleBook[1] = new ImageIcon(ImageIO.read(getClass().getResource("/images/1.jpg")));
		  } catch (Exception ex) {
		    System.out.println("IMAGE");
		  }
		
		//animations
		animatableObjectList = new ArrayList<>();
		
		//filling the bag and lid
		bag = new ArrayList<Integer>();
		for (int i = 0; i<= 4; i++) 
			for (int j = 0; j< 20; j++) 
				bag.add(i);
		Collections.shuffle(bag);
		Collections.shuffle(bag);
		
		lid = new ArrayList<Integer>();
		
		//creating factories & its buttons
		allFactory = new Factory [9];
		for (int i = 0; i < 9; i++) {
			ArrayList <PlainButton> Buttonlist = new ArrayList <PlainButton>();
			ArrayList <Integer> tile = new ArrayList <Integer>();
			// instantiating the buttons within the factory class
			for (int b = 0; b < 4; b++) {
				PlainButton button = new PlainButton (b,i);
				setFactoryTileFunction(button);
				Buttonlist.add(button);
				Collections.shuffle(bag);
				tile.add(bag.remove(0));
			}
			Factory temp = new Factory(tile,i);
			temp.addButtons(Buttonlist);
			allFactory[i]= temp;
		}
		
		//adding factoryFloor
		ArrayList <PlainButton> Buttonlist = new ArrayList <PlainButton>();
			for (int b = 0; b < 6; b++) {
				PlainButton button = new PlainButton (b);
				setFactFloorFunction(button);
				Buttonlist.add(button);
			}
		factoryFloor = new FactoryFloor ();
		factoryFloor.addButtons(Buttonlist);
		
		// adding the players
		allPlayer = new Player [4];
		for (int i = 0; i < 4 ; i++) {
			Player temp = new Player(i);
			allPlayer[i]= temp;
		}
		
		//Players
		patternButton = new ArrayList <PlainButton>();
		FloorButton = new PlainButton();
		addPlayerButtons();
		
		//adding panels
		panel = AzulPanel.get();
		panel.addFactories(allFactory);
		panel.setLayout(null);

		panel.setBounds(0,0,width, height);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//adding button
		startB = new PlainButton ("start");
		startB.setSize(629-10,243-10);
		startB.setLocation(1021-10,686-10);
		startB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				strt = false;
				panel.updateAll(allFactory,factoryFloor,bag, allPlayer, roundscore,endgame,strt);
				c.validate();
				c.repaint();
				
		c.add(panel);
		System.out.println(strt);
			}});
		
		//adding button
		c.add(startB);
		
		for (Factory f: allFactory) 
			for (PlainButton b: f.ButtonList) 
				c.add(b);
				
		for (PlainButton b: factoryFloor.ButtonList) {
				b.setEnabled(false);
				c.add(b);
		}
		
		for (PlainButton a: patternButton) {
			a.setEnabled(false);
			c.add(a);
		}
		FloorButton.setEnabled(false);
		setPlayerFloorFunction(FloorButton);
		c.add(FloorButton);

				
				c.validate();
				c.repaint();
				
		c.add(panel);
		
				
		setSize(width, height);
		setTitle("Azul Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
		setVisible(true);
		
		

	}


	
	public void setFactoryTileFunction(PlainButton temp) {
		temp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
	//REPAINT EVERYTHING
				c.validate();
				c.repaint();
				c.add(panel);
				
				int color = allFactory[temp.factoryID].TileColors.get(temp.ID);
	//THE SPLIT SECOND OF ANIMATION
				
				for (Factory f: allFactory) 
					f.setEnabled(false);
				setEnabledPlayer(false);
				factoryFloor.setEnabled(false);				
            	
	//BUTTON FUNCTION
				ArrayList <Integer>list = allFactory[temp.factoryID].takeAll();
				color = list.get(temp.ID);
				/*System.out.println(list);
				System.out.println(color);*/
				while(list.contains(color)) 
					allPlayer[0].addBufferZone(list.remove(list.indexOf(color)));
				//System.out.println(list + "---"+allPlayer[0].BufferZone);	
				factoryFloor.add(list);	
				/*for (int i: factoryFloor.colors)
					System.out.print(i+" ");*/
	
	// ANIMATE LUC
				//from: 
				int x = temp.getBounds().x;
				int y = temp.getBounds().y;	
				//tile size:
					//tilesize or 66
				//color of tile: color
				
				//to: 1460,662
				//final size: 150
							/*AnimatableObject ani = new AnimatableObject(tileimage[0],0 , 0, tilesize, tilesize, null);
							
							try {
								Thread.sleep(1000);
								Animate.animate(ani, 1460,662,150,150, 2000, 120);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}*/
							/*s = new Screen(tileimage[color],x,y,1460,662,60,150);
							s.setBounds(0,0,width, height);
							c.add(s);
							s = null;*/
				

	//DISABLE ALL OF FACTORY AND ENABLE ALL OF CURRENT PLAYER PATTERNLINE & FLOOR
				
				for (Factory f: allFactory) 
					f.setEnabled(false);
				setEnabledPlayer(true);
				factoryFloor.setEnabled(false);
				//System.out.print("allPlayer[0]"+allPlayer[0].ID);
				
	//REPAINT EVERYTHING
				c.validate();
				c.repaint();
				c.add(panel);

			}});
	}

	public void setFactFloorFunction(PlainButton temp) {
		temp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("OFFER: Factory Floor Tile clicked " + temp.ID +"  ");
				
				//REPAINT EVERYTHING
				c.validate();
				c.repaint();
				c.add(panel);
		
		if (allFactoryEmpty()) {		

	//BUTTON FUNCTION
				int color = temp.ID;
				int num = factoryFloor.takeAll(color);
				if (factoryFloor.firstMarker && allPlayer[0].nextEmpty()>-1) {
					allPlayer[0].floorLine[allPlayer[0].nextEmpty()] = 5;
					factoryFloor.firstMarker = false;
					//factoryFloor.firsttaken = true;
				}
				for (int i =0; i<num; i++) 
					allPlayer[0].addBufferZone(color);
				
	//THE SPLIT SECOND OF ANIMATION
				for (Factory f: allFactory) 
					f.setEnabled(false);
				setEnabledPlayer(false);
				factoryFloor.setEnabled(false);
				
	// ANIMATE LUC
				//from: 
				int x = temp.getBounds().x;
				int y = temp.getBounds().y;	
				//tile size:
					//81
				//color of tile: color
				//to: 1460,662
				//final size: 150
							/*AnimatableObject ani = new AnimatableObject(tileimage[0],0 , 0, tilesize, tilesize, null);
							
							try {
								Thread.sleep(1000);
								Animate.animate(ani, 1460,662,150,150, 2000, 120);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}*/

				
	// NEXT ACTION
				//DISABLE ALL OF FACTORY AND ENABLE ALL OF CURRENT PLAYER PATTERNLINE & FLOOR
				for (Factory f: allFactory) 
					f.setEnabled(false);
				setEnabledPlayer(true);
				factoryFloor.setEnabled(false);
		}
				
				
	//REPAINT EVERYTHING
				c.validate();
				c.repaint();
				c.add(panel);
				//allPlayer[0]
				//c.removeAll(); 
			}});
	}
	
	public void setPlayerPatternFunction(PlainButton temp) {
		temp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("PLACEMENT: Placement Clicked " + temp.ID +"  " +temp.factoryID + "");			
				
				//REPAINT EVERYTHING
				c.validate();
				c.repaint();
				c.add(panel);
	//BUTTON FUNCTION
				int first = -1;
				int row = temp.ID;
				while (!allPlayer[0].BufferZone.isEmpty()) {
					//adding to patternLine
					if (allPlayer[0].patternLine[row][allPlayer[0].patternLine[row].length-1]==-1) {
						//normal adding
						if (allPlayer[0].nextEmpty (row)!=-1)
							allPlayer[0].patternLine[row][allPlayer[0].nextEmpty (row)] = allPlayer[0].BufferZone.remove(0);
					}
					else 
						allPlayer[0].addFloorLine();

					}
				/*System.out.println("pattern line");
				for (int[] i: allPlayer[0].patternLine) {
					for (int n: i)
						System.out.print(n+" ");
					System.out.println();
					}
				System.out.println("Floorline");
				for (int n: allPlayer[0].floorLine)
					System.out.print(n+" ");
				System.out.println();
				System.out.println("wall");*/
	//THE SPLIT SECOND OF ANIMATION
				for (Factory f: allFactory) 
					f.setEnabled(false);
				setEnabledPlayer(false);
				factoryFloor.setEnabled(false);
				
	// ANIMATE LUC
				//from: 1460,662
				//size: 150
			//to:	
			int x = temp.getBounds().x;
			int y = temp.getBounds().y;	
				/*AnimatableObject ani = new AnimatableObject(tileimage[0],0 , 0, tilesize, tilesize, null);
				
				try {
					Thread.sleep(1000);
					Animate.animate(ani, 1460,662,150,150, 2000, 120);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (ArrayList<Integer> d: allPlayer[0].wall) {
					for (int n: d)
						System.out.print(n+" ");
					System.out.println();
					}*/
				
	// NEXT ACTION
				//DISABLE ALL OF FACTORY AND ENABLE ALL OF CURRENT PLAYER PATTERNLINE & FLOOR
				for (Factory f: allFactory) 
					f.setEnabled(true);
				setEnabledPlayer(false);
				factoryFloor.setEnabled(true);

	//CHECKS END ROUND / GAME CONDITIONS
		if (!allFactoryEmpty()) {
						roundscore = true;
						
						c.validate();
						c.repaint();
		//THE SPLIT SECOND OF ANIMATION - disable all buttons
					for (Factory f: allFactory) 
						f.setEnabled(false);
					setEnabledPlayer(false);
					factoryFloor.setEnabled(false);
					
		//scoring + ANIMATE LUC
					for (Player p: allPlayer) {
						for (int i = 0; i < 5; i++) {
							int score = p.score(i);
								if (score >-1) {
									p.score += score;
									int [] temp = p.patternLine[i];
									int col = p.idealwall.get(i).lastIndexOf(temp[0]);
									ArrayList <Integer> arr = p.wall.get(i);
									arr.set(col, p.patternLine[i][0]);
									p.wall.set(i,arr);
									p.scoreWall.get(i).set(col, score);
								}

								c.validate();
								c.repaint();
						}
						int i = 0;
						while(i <7 && p.floorLine[i]!=-1 ) {
							if (i == 0  || i==1)
								p.score -=1;
							if (i == 2  || i==3 || i == 4)
								p.score -=2;
							if (i == 6  || i==5)
								p.score -=3;
							i++;
						}
						if (p.score < 0)
							p.score = 0;
						if(p.score>100)
							p.score = 100;
						
						if (p.clearFloor())
							first = p.ID;
						p.clearPattern();
						if (p.checkWall()) {
							endgame = true;
							//roundscore = true;
						}
					}
				//checks end game conditions
				if (endgame) {
					System.out.println("--------END GAME");	
					victors = new ArrayList<>();
					//score
					for (Player p: allPlayer) {
						for (int i = 0; i < 5; i++) {
							// number of horizontal, vertical, colors completed in the wall
							int horizontal = p.scoreHoriz();
							p.score += 2*horizontal;
							
							int vertical = p.scoreVerti();
							p.score += 7*vertical;
							
							int color = p.scorecolor();
							p.score += 10*color;
							
							//determining ties - victors contain the final winners
							if (victors.size()>0 && victors.get(0).score < p.score) {
									victors = new ArrayList<>();
									victors.add(p);
								}
							else if (victors.size()>0 && victors.get(0).score == p.score) {
								victors.add(p);
							}
							else
									victors.add(p);
							}
						}
					//if tied
					if (victors.size()>2) {
						ArrayList <Player> temp = new ArrayList<>();
						for(Player p: temp) {
							if (temp.size()>0 && temp.get(0).score < p.score) {
								temp = new ArrayList<>();
								temp.add(p);
							}
						else if (temp.size()>0 && temp.get(0).score == p.score) {
							temp.add(p);
						}
						else
								temp.add(p);
						
						}
						victors = temp;
					}
				}			
			
			roundscore = true;
		}	
		if (endgame) {
			for (Factory f: allFactory) 
				f.setEnabled(false);
			setEnabledPlayer(false);
			factoryFloor.setEnabled(false);
			c.validate();
			c.repaint();
			c.add(panel);
		}
		else{
			if (roundscore) {
				rotateRound(first);
				refillFactory();
			}
			else 
				rotateTurn();
			
			for (Factory f: allFactory) 
				f.setEnabled(true);
			setEnabledPlayer(false);
			factoryFloor.setEnabled(true);
		
	//REPAINT EVERYTHING
				c.validate();
				c.repaint();
				c.add(panel);
				roundscore = false;
		}	

			}});
	}
	
	
	public void refillFactory() {
		int i = 0;
		while(i < 9) {
			Collections.shuffle(bag);
			ArrayList <Integer> tile = new ArrayList <Integer>();
			// instantiating the buttons within the factory class
			for (int b = 0; b < 4; b++) {
				if(bag.size()==0) {
					if(lid.size() > 0) {
						for(int j = 0; j< lid.size(); j++) {
							bag.add(lid.remove(0));
							Collections.shuffle(lid);
							j--;
						}
					}
					else
						break;
				}
				tile.add(bag.remove(0));
			}
			allFactory[i].fill(tile);;
			i++;
		}
	}
	
	public void setPlayerFloorFunction(PlainButton temp) {
		temp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("PLACEMENT: player floor Clicked " + temp.ID +"  " +temp.factoryID + "");
	//REPAINT EVERYTHING
				c.validate();
				c.repaint();
				c.add(panel);
				
	//BUTTON FUNCTION
				int row = temp.ID; 
					allPlayer[0].addFloorLine();
				
	//THE SPLIT SECOND OF ANIMATION
				for (Factory f: allFactory) 
					f.setEnabled(false);
				setEnabledPlayer(false);
				factoryFloor.setEnabled(false);
				
	// ANIMATE LUC
				int x = temp.getBounds().x;
				int y = temp.getBounds().y;	
				
	// NEXT ACTION
				rotateTurn();
				//DISABLE ALL OF FACTORY AND ENABLE ALL OF CURRENT PLAYER PATTERNLINE & FLOOR
				for (Factory f: allFactory) 
					f.setEnabled(true);
				setEnabledPlayer(false);
				factoryFloor.setEnabled(true);
				
	//if factories & factory floor runs out
		if (!allFactoryEmpty()) {
				//THE SPLIT SECOND OF ANIMATION
				for (Factory f: allFactory) 
					f.setEnabled(false);
				setEnabledPlayer(false);
				factoryFloor.setEnabled(false);
				//score
				for (Player p: allPlayer) {
					for (int i = 0; i < 5; i++) {
						int score = p.score(i);
							if (score >-1) {
								p.score += score;
								int [] temp = p.patternLine[i];
								int col = p.idealwall.get(i).lastIndexOf(temp[0]);
								p.wall.get(i).set(col, p.patternLine[i][0]);
							}
						System.out.println("+++++++"+ score);
						for (ArrayList<Integer> d: p.wall) {
							for (int n: d)
								System.out.print(n+" ");
							System.out.println();
							}
					}
					int i = 0;
					while(i< 7&&p.floorLine[i]!=-1) {
						if (i == 0  || i==1)
							p.score -=1;
						if (i == 2  || i==3 || i == 4)
							p.score -=2;
						if (i == 6  || i==5)
							p.score -=3;
						i++;
					}
					if (p.score < 0)
						p.score = 0;	
					System.out.println("++++++++++++++++++++++++"+ p.score);
					p.clearFloor();
					p.clearPattern();
				}
				refillFactory();
				
				for (Factory f: allFactory) 
					f.setEnabled(true);
				setEnabledPlayer(false);
				factoryFloor.setEnabled(true);
			}	
	//REPAINT EVERYTHING
				c.validate();
				c.repaint();
				c.add(panel);

				}});
		}
	
	/**
	 * set factory and factory floor buttons to this switch command boolean
	 */
	public void setEnabledFacts(Boolean b) {
		//factory buttons
		for (Factory f: allFactory) {
			if (f.isEmpty())
				f.setEnabled(false);
			else
				f.setEnabled(b);
		}
		// factory floor
		factoryFloor.setEnabled(b);
	}
	
	/**
	 * set factory and factory floor buttons to this switch command boolean
	 */
	public void setEnabledPlayer(Boolean bool) {
		
		Boolean pattern = false;
		for (PlainButton b: patternButton) {
			
			if (!bool) {
				b.setEnabled(false);
				FloorButton.setEnabled(false);
			}
			else {
				int row = b.ID;
				ArrayList <Integer> temp = allPlayer[0].wall.get(row);
				//- wall has color on same row
				if (temp.contains(allPlayer[0].BufferZone.get(0)))
					b.setEnabled(false);
				else if(allPlayer[0].patternLine[row][allPlayer[0].patternLine[row].length-1]==-1) {
					//  empty pattern line row  ||  not empty              && first index has the same color
					if (allPlayer[0].patternLine[row][0]==-1 ||(allPlayer[0].patternLine[row][0]!=-1 && allPlayer[0].patternLine[row][0]==allPlayer[0].BufferZone.get(0)))
						b.setEnabled(bool);
				}
				//if pattern is all false, it means no pattern button is enabled
				pattern = pattern || b.isEnabled();
			}
		}
		if (allPlayer[0].floorLine[6]==-1) {
			FloorButton.setEnabled(true);
		}
		//edge case: when all patternline has been filled, floor button will be enabled no matter what
		if (!pattern)
			FloorButton.setEnabled(true);

	}
	
	public void addPlayerButtons() {
				for (int b = 0; b < 5; b++) {
					PlainButton temp = new PlainButton (b,0,b);
					temp.setLocation(973, 580+b*57+b*3);
					setPlayerPatternFunction(temp);
					patternButton.add(temp);
				}
			FloorButton.setLocation(665, 920);

	}
	
	public void rotateTurn() {
		//int id = allPlayer[0].ID;
		Player [] temp = new Player[4];
		for (int i = 0; i < 4; i++) {
			temp[i] = allPlayer[(i+1)%4];
		}
		allPlayer = temp;
		System.out.println("---------------------------------player:" + allPlayer[0].ID);
		panel.updateAll(allFactory,factoryFloor,bag, allPlayer, roundscore,endgame,strt);
		c.validate();
		c.repaint();
		c.add(panel);
		
	}
	
	public void rotateRound(int first) {
		int index = 0;
		Player [] temp = new Player[4];
		for (int i = 0; i < 4; i++) {
			if (allPlayer[i].ID == first)
				index = i;
		}
		
		for (int j = 0; j < 4; j++) {
			temp[j] = allPlayer[(index+j)%4];
		}
		allPlayer = temp;
		panel.updateAll(allFactory,factoryFloor,bag, allPlayer, roundscore,endgame,strt);
		c.validate();
		c.repaint();
		c.add(panel);
		
	}
	
	//tru if its not empty false if it is
	public Boolean allFactoryEmpty() {
		for (Factory f: allFactory) {
			if (!f.isEmpty())
				return true;
		}
		for (int i: factoryFloor.colors)
			if (i>0)
				return true;
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		
	}
	

}
