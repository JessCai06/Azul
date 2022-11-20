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

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class AzulWindow extends JFrame implements ItemListener, ActionListener{
	
	//window attributes
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
	
	//players
	ArrayList <PlainButton> patternButton;
	PlainButton FloorButton;
	static Player [] allPlayer;
	
	//animations
	private static AzulPanel azulPanel;
	private ArrayList<AnimatableObject> animatableObjectList;
	
	AzulPanel panel;
	
	//rule book
	public JScrollPane scrollPane;
	
	AzulWindow () throws InterruptedException{
		
		//scroll
		//scrollPane  = new JScrollPane(new JLabel(ruleBook[0]));
		
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
		
		//Container
		c = getContentPane();
		c.setLayout(null);
		
		//filling the bag and lid
		bag = new ArrayList<Integer>();
		for (int i = 0; i<= 4; i++) 
			for (int j = 0; j< 20; j++) 
				bag.add(i);
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
		int w = 0;
		int h = 0;
		panel.setBounds(0,0,width, height);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
		//adding button
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
				System.out.println("OFFER: Factory Tile Clicked " + temp.ID +"  " +temp.factoryID + "");
				//animate();
	//REPAINT EVERYTHING
				c.validate();
				c.repaint();
				c.add(panel);
				
				int x = temp.getBounds().x;
				int y = temp.getBounds().y;	
				int color = allFactory[temp.factoryID].TileColors.get(temp.ID);
	//BUTTON FUNCTION
				ArrayList <Integer>list = allFactory[temp.factoryID].takeAll();
				color = list.get(temp.ID);
				System.out.println(list);
				System.out.println(color);
				while(list.contains(color)) 
					allPlayer[0].addBufferZone(list.remove(list.indexOf(color)));
				System.out.println(list + "---"+allPlayer[0].BufferZone);	
				factoryFloor.add(list);	
				for (int i: factoryFloor.colors)
					System.out.print(i+" ");
	
	// ANIMATE
				/*AnimatableObject ani = new AnimatableObject(tileimage[0],0 , 0, tilesize, tilesize, null);
				
				try {
					Thread.sleep(1000);
					Animate.animate(ani, 1460,662,150,150, 2000, 120);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/

	//DISABLE ALL OF FACTORY AND ENABLE ALL OF CURRENT PLAYER PATTERNLINE & FLOOR
				
				for (Factory f: allFactory) 
					f.setEnabled(false);
				setEnabledPlayer(true);
				factoryFloor.setEnabled(false);
				System.out.print("allPlayer[0]"+allPlayer[0].ID);
				
				//REPAINT EVERYTHING
				c.validate();
				c.repaint();
				c.add(panel);
				//allPlayer[0]
				//c.removeAll(); 

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
				
				int x = temp.getBounds().x;
				int y = temp.getBounds().y;	
				//int color = allFactory[temp.factoryID].TileColors.get(temp.ID);
	//BUTTON FUNCTION
				int color = temp.ID;
				int num = factoryFloor.takeAll(color);
				for (int i =0; i<num; i++) 
					allPlayer[0].addBufferZone(color);
				
	//THE SPLIT SECOND OF ANIMATION
				for (Factory f: allFactory) 
					f.setEnabled(false);
				setEnabledPlayer(false);
				factoryFloor.setEnabled(false);
				
	// ANIMATE
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
				System.out.println("PLACEMENT: Factory Tile Clicked " + temp.ID +"  " +temp.factoryID + "");
				
				
				//REPAINT EVERYTHING
				c.validate();
				c.repaint();
				c.add(panel);
				
		if (allFactoryEmpty()) {
				int x = temp.getBounds().x;
				int y = temp.getBounds().y;	
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
				System.out.println("pattern line");
				for (int[] i: allPlayer[0].patternLine) {
					for (int n: i)
						System.out.print(n+" ");
					System.out.println();
					}
				System.out.println("Floorline");
				for (int n: allPlayer[0].floorLine)
					System.out.print(n+" ");
				System.out.println();
				System.out.println("wall");
				for (ArrayList<Integer> i: allPlayer[0].wall) {
					for (int n: i)
						System.out.print(n+" ");
					System.out.println();
					}
	//THE SPLIT SECOND OF ANIMATION
				for (Factory f: allFactory) 
					f.setEnabled(false);
				setEnabledPlayer(false);
				factoryFloor.setEnabled(false);
				
	// ANIMATE
				/*AnimatableObject ani = new AnimatableObject(tileimage[0],0 , 0, tilesize, tilesize, null);
				
				try {
					Thread.sleep(1000);
					Animate.animate(ani, 1460,662,150,150, 2000, 120);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/

				
	// NEXT ACTION
				rotateTurn();
				//DISABLE ALL OF FACTORY AND ENABLE ALL OF CURRENT PLAYER PATTERNLINE & FLOOR
				for (Factory f: allFactory) 
					f.setEnabled(true);
				setEnabledPlayer(false);
				factoryFloor.setEnabled(true);
				}
		//if factories & factory floor runs out
		else {
			//THE SPLIT SECOND OF ANIMATION
			for (Factory f: allFactory) 
				f.setEnabled(false);
			setEnabledPlayer(false);
			factoryFloor.setEnabled(false);
			//score
			for (Player p: allPlayer) {
				for (int[] i: p.patternLine) {
					
				}
				for (int i: p.floorLine) {
					
				}
			}
			
			//refill
			
		}		
	//REPAINT EVERYTHING
				c.validate();
				c.repaint();
				c.add(panel);
				//allPlayer[0]
				//c.removeAll(); 
			}});
	}
	public void setPlayerFloorFunction(PlainButton temp) {
		temp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("OFFER: Factory Tile Clicked " + temp.ID +"  " +temp.factoryID + "");
				//REPAINT EVERYTHING
				c.validate();
				c.repaint();
				c.add(panel);
				
	if (allFactoryEmpty()) {		
				int x = temp.getBounds().x;
				int y = temp.getBounds().y;	
				//int color = allFactory[temp.factoryID].TileColors.get(temp.ID);
	//BUTTON FUNCTION
				int row = temp.ID; 
					allPlayer[0].addFloorLine();

				System.out.println("patterline");
				for (int[] i: allPlayer[0].patternLine) {
					for (int n: i)
						System.out.print(n+" ");
					System.out.println();
					}
				System.out.println("Floorline");
				for (int n: allPlayer[0].floorLine)
					System.out.print(n+" ");
				System.out.println();
				System.out.println("wall");
				for (ArrayList<Integer> i: allPlayer[0].wall) {
					for (int n: i)
						System.out.print(n+" ");
					System.out.println();
					}
	//THE SPLIT SECOND OF ANIMATION
				for (Factory f: allFactory) 
					f.setEnabled(false);
				setEnabledPlayer(false);
				factoryFloor.setEnabled(false);
				
	// ANIMATE
				/*AnimatableObject ani = new AnimatableObject(tileimage[0],0 , 0, tilesize, tilesize, null);
				
				try {
					Thread.sleep(1000);
					Animate.animate(ani, 1460,662,150,150, 2000, 120);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/

				
	// NEXT ACTION
				rotateTurn();
				//DISABLE ALL OF FACTORY AND ENABLE ALL OF CURRENT PLAYER PATTERNLINE & FLOOR
				for (Factory f: allFactory) 
					f.setEnabled(true);
				setEnabledPlayer(false);
				factoryFloor.setEnabled(true);
				
			}
	//if factories & factory floor runs out
	else {
		//score
		
		//refill
	}
	//REPAINT EVERYTHING
				c.validate();
				c.repaint();
				c.add(panel);
				//allPlayer[0]
				//c.removeAll(); 
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
		
		//pline
		//floorline
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
				if(allPlayer[0].patternLine[row][allPlayer[0].patternLine[row].length-1]==-1) {
					//  empty pattern line row  ||  not empty              && first index has the same color
					if (allPlayer[0].patternLine[row][0]==-1 ||(allPlayer[0].patternLine[row][0]!=-1 && allPlayer[0].patternLine[row][0]==allPlayer[0].BufferZone.get(0)))
						b.setEnabled(bool);
				}
				if (allPlayer[0].floorLine[6]==-1)
					FloorButton.setEnabled(true);
			}
		}
		

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
		for (int i = 0; i < 4; i++) 
			temp[i] = allPlayer[(i+1)%4];
		allPlayer = temp;
		allPlayer[0] = allPlayer[0];
		System.out.println("---------------------------------player:" + allPlayer[0].ID);
		panel.updateAll(allFactory,factoryFloor,bag, allPlayer);
		c.validate();
		c.repaint();
		c.add(panel);
		
	}
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
