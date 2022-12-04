import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
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
	public BufferedImage score;
	public ImageIcon[] ruleBook;
	Container c;
	
	//game essentials
	static Factory[] allFactory;
	static FactoryFloor factoryFloor;
	static ArrayList<Integer> bag, lid;
	static Boolean firstTake, endgame, roundscore, end;
	static ArrayList <Player> victors;
	static PlainButton startB;
	static String output;
	static int scoreRow, first, playerInc, maxRotation;
	
	
	//players
	ArrayList <PlainButton> patternButton;
	PlainButton FloorButton;
	JButton scoreButton ;
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
		
		roundscore = false;
		endgame = false;
		end = false;
		maxRotation = 5;
		
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
				//Collections.shuffle(bag);
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
		
		//adding start button
		scoreButton = new JButton ("score");
		//scoreButton.setLabel("Score");
		scoreButton.setSize(80,75);
		Icon score = null;;
		try {
			score = new ImageIcon(ImageIO.read(getClass().getResource("/images/arrow.png")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		scoreButton.setIcon(score);
		scoreButton.setLabel("SCORE");
		scoreButton.setHorizontalTextPosition(AbstractButton.CENTER);
		scoreButton.setLocation(1375,985-25-25);
		setScoreFunction(scoreButton);
		scoreButton.setEnabled(false);
		
		//adding start button
		startB = new PlainButton ("start");
		startB.setSize(629-10,243-10);
		startB.setLocation(1021-10,686-10);
		startB.setBorder(null);
		startB.setBorderPainted(false);
		startB.setContentAreaFilled(false);
		startB.setOpaque(false);
		startB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				output = "2";
				strt = false;
				panel.updateAll(allFactory,factoryFloor,bag, allPlayer, roundscore,endgame,strt, output,end);
				c.validate();
				c.repaint();
				startB.setEnabled(false);
		c.add(panel);
		c.add(scoreButton);
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
	public void setScoreFunction(JButton scoreButton2) {
	scoreButton2.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			//score
			output = "3";
			panel.updateAll(allFactory,factoryFloor,bag, allPlayer, roundscore,endgame,strt, output,end);
			//THE SPLIT SECOND OF ANIMATION - disable all buttons
						for (Factory f: allFactory) 
							f.setEnabled(false);
						setEnabledPlayer(false);
						factoryFloor.setEnabled(false);

				Player p = allPlayer[0];
				int i = scoreRow;	
				int score = 0;
				
				//scoring PATTERNLINES
						if (i < 5) {
								if (p.score(i) >-1) {
									score = p.score(i);
									int [] temp = p.patternLine[i];
									int col = p.idealwall.get(i).lastIndexOf(temp[0]);
									ArrayList <Integer> arr = p.wall.get(i);
									arr.set(col, p.patternLine[i][0]);
									p.wall.set(i,arr);
									//p.scoreWall.get(i).set(col, score);
									p.clearPattern(i);
									//System.out.println();
								}
							}
				//FLOOR LINES
						else if (i == 5) {	
							i = 0;
							while(i <7 && p.floorLine[i]!=-1 ) {
								if (i == 0  || i==1)
									score -=1;
								if (i == 2  || i==3 || i == 4)
									score -=2;
								if (i == 6  || i==5)
									score -=3;
								i++;
							}
							
							if (p.clearFloor())
								first = p.ID;
				
						}

					p.score += score;
					
					if (p.score < 0)
						p.score = 0;
					if(p.score>100)
						p.score = 100;
					
					panel.updateScoring(score,i, playerInc, victors, end);
					endgame = endgame || p.checkWall();
					end = end || (endgame && (playerInc == 4 && scoreRow == 5));
					
					c.validate();
					c.repaint();
					c.add(panel);
					System.out.println("playerinc-"+playerInc+ "row"+ scoreRow +"       end? "+endgame +" actually? "+ end);
			
					if (end) {
						for (int b = 0; b<allPlayer.length; b++) {
							int horizontal = 2*allPlayer[b].scoreHoriz();
							int vert = 7*allPlayer[b].scoreVerti();
							int color = 10*allPlayer[b].scorecolor();
							allPlayer[b].score += horizontal + vert + color;
							}
						victors = new ArrayList<>();
						
						if (victors.size()>0 && victors.get(0).score < p.score) {
								victors = new ArrayList<>();
								victors.add(p);
							}
						else if (victors.size()>0 && victors.get(0).score == p.score) {
							victors.add(p);
						}
						else
								victors.add(p);
						
					
						ArrayList <Integer> winscores = new ArrayList<>();
						for (Player d: allPlayer) {
							winscores.add(d.score);
							System.out.println("Player "+ d.ID+ " ," +d.score);
						}
						Collections.sort(winscores);
						int max = winscores.get(3);
						while (winscores.get(0)!=max)
							winscores.remove(0);
						System.out.println(winscores);
						ArrayList <Player> victors = new ArrayList<>();
							for (Player d: allPlayer) 
								if (d.score == max)
									victors.add(d);
						
						String s = "";
							for (Player b: victors)
								s+= "Player " + b.ID+ " "+b.score;
							System.out.println(s);
						panel.updateScoring(score,i, playerInc, victors, end);
					}	
						
					
					if (scoreRow == 5 && playerInc == 4){
						rotateRound(first);
						refillFactory();
						for (Factory f: allFactory) 
							f.setEnabled(true);
						setEnabledPlayer(false);
						factoryFloor.setEnabled(true);
						scoreButton.setEnabled(false);
						playerInc =0;
					}
					else if (scoreRow <= 5){
						if (scoreRow == 5) {
							playerInc ++;
							scoreRow =0;
							rotateTurn();
							//doesn't recpgnize endgame situations
						}
						else 
							scoreRow++;
						
					}


		}});}
	
	public void setFactoryTileFunction(PlainButton temp) {
		temp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				output = "1";
				panel.updateAll(allFactory,factoryFloor,bag, allPlayer, roundscore,endgame,strt, output,end);
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
				while(list.contains(color)) 
					allPlayer[0].addBufferZone(list.remove(list.indexOf(color)));
				factoryFloor.add(list);	

	// ANIMATE LUC
				//from: 
				int x = temp.getBounds().x;
				int y = temp.getBounds().y;	

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
				output = "1";
				panel.updateAll(allFactory,factoryFloor,bag, allPlayer, roundscore,endgame,strt, output,end);
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
			}});
	}
	
	public void setPlayerPatternFunction(PlainButton temp) {
		temp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("PLACEMENT: Placement Clicked " + temp.ID +"  " +temp.factoryID + "");			
				output = "2";
				
				//REPAINT EVERYTHING
				c.validate();
				c.repaint();
				c.add(panel);
	//BUTTON FUNCTION
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
				
	//THE SPLIT SECOND OF ANIMATION
				for (Factory f: allFactory) 
					f.setEnabled(false);
				setEnabledPlayer(false);
				factoryFloor.setEnabled(false);
				
	// ANIMATE LUC
			int x = temp.getBounds().x;
			int y = temp.getBounds().y;	
				
	// NEXT ACTION
				//DISABLE ALL OF FACTORY AND ENABLE ALL OF CURRENT PLAYER PATTERNLINE & FLOOR
				for (Factory f: allFactory) 
					f.setEnabled(true);
				setEnabledPlayer(false);
				factoryFloor.setEnabled(true);

	//CHECKS END ROUND / GAME CONDITIONS
		if (!allFactoryEmpty()) {
						roundscore = true;
						scoreButton.setEnabled(true);
						scoreRow = 0;
						playerInc = 1;
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
				for (Factory f: allFactory) 
					f.setEnabled(false);
				setEnabledPlayer(false);
				factoryFloor.setEnabled(false);
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
				output = "2";
				
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

					//CHECKS END ROUND / GAME CONDITIONS
					if (!allFactoryEmpty()) {
									roundscore = true;
									scoreButton.setEnabled(true);
									scoreRow = 0;
									playerInc = 1;
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
							for (Factory f: allFactory) 
								f.setEnabled(false);
							setEnabledPlayer(false);
							factoryFloor.setEnabled(false);
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
		panel.updateAll(allFactory,factoryFloor,bag, allPlayer, roundscore,endgame,strt, output,end);
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
		panel.updateAll(allFactory,factoryFloor,bag, allPlayer, roundscore,endgame,strt, output,end);
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