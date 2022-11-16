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

public class AzulWindow extends JFrame implements ItemListener, ActionListener{
	
	//window attributes
	public static final int width = 1920;
	public static final int height = 1080;
	public static final int tilesize = 60;
	Container c;
	static Factory[] allFactory;
	static FactoryFloor factoryFloor;
	static ArrayList<Integer> bag, lid;
	
	static Player currentPlayer;
	static Player [] allPlayer;
	
	AzulPanel panel;
	
	AzulWindow (){
		
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
		
		int inc = 0;
		
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
			ArrayList <PlainButton> pline = new ArrayList <PlainButton>();
			PlainButton fline = new PlainButton ();
			setPlayerFloorFunction(fline);
			// instantiating the buttons within the player class
			for (int b = 0; b < 5; b++) {
				PlainButton button = new PlainButton (b,i);
				setPlayerPatternFunction(button);
				pline.add(button);
			}
			Player temp = new Player(i);
			temp.addButtons(pline,fline);
			allPlayer[i]= temp;
		}
		currentPlayer = allPlayer[0];
		
		//adding panels
		panel = new AzulPanel ();
		panel.setLayout(null);
		int w = 0;
		int h = 0;
		panel.setBounds(0,0,width, height);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
		//adding button
		for (Factory f: allFactory) 
			for (PlainButton b: f.ButtonList) 
				c.add(b);
				
		for (PlainButton b: factoryFloor.ButtonList) 
				c.add(b);
		
		for (PlainButton b: currentPlayer.patternButton) 
			c.add(b);
		c.add(currentPlayer.FloorButton);
			
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
					allFactory[temp.factoryID].takeAll();
				//CurrentPlayer
				//c.removeAll(); 
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
				
				//CurrentPlayer
				//c.removeAll(); 
				c.validate();
				c.repaint();
				c.add(panel);
			}});
	}
	public void setPlayerPatternFunction(PlainButton temp) {
		temp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("OFFER: Factory Tile Clicked " + temp.ID +"  " +temp.factoryID + "");
					allFactory[temp.factoryID].takeAll();
				//CurrentPlayer
				//c.removeAll(); 
				c.validate();
				c.repaint();
				c.add(panel);
			}});
	}
	public void setPlayerFloorFunction(PlainButton temp) {
		temp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("OFFER: Factory Tile Clicked " + temp.ID +"  " +temp.factoryID + "");
					allFactory[temp.factoryID].takeAll();
				//CurrentPlayer
				//c.removeAll(); 
				c.validate();
				c.repaint();
				c.add(panel);
			}});
	}
	
	public void pause (int sec)  throws InterruptedException {
		Thread.sleep(sec*100);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		
	}

}
