import java.awt.*;
import java.awt.Font;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;

public class AzulPanel extends JPanel{

	BufferedImage background;
	public BufferedImage []tileimage;
	public static int tilesize = 66;
	
	public Factory[] allFactory = AzulWindow.allFactory;
	public static FactoryFloor factoryFloor = AzulWindow.factoryFloor;
	public static ArrayList<Integer> bag = AzulWindow.bag;
	
	public Player [] allPlayer = AzulWindow.allPlayer;
	
	//animations
	private static AzulPanel azulPanel;
	private ArrayList<AnimatableObject> animatableObjectList;
	
	public AzulPanel(){
		
		animatableObjectList = new ArrayList<>();
		
		//tile pictures
		tileimage = new BufferedImage [6];		
		try{
			background = ImageIO.read(getClass().getResource("/images/FinalForMe.jpg"));
		    tileimage[0] = ImageIO.read(getClass().getResource("/images/blue.png"));
		    tileimage[1] = ImageIO.read(getClass().getResource("/images/yellow.png"));
		    tileimage[2] = ImageIO.read(getClass().getResource("/images/white.png"));
		    tileimage[3] = ImageIO.read(getClass().getResource("/images/red.png"));
		    tileimage[4] = ImageIO.read(getClass().getResource("/images/black.png"));
		    tileimage[5] = ImageIO.read(getClass().getResource("/images/1.jpg"));
		  } catch (Exception ex) {
		    System.out.println("IMAGE");
		  }
	}

	public void paint(Graphics g) {
		 super.paintComponent(g);
		 
		//1. BACKGROUND - FINAL
			g.drawImage(background, -10, -10, getWidth(), getHeight(), null);
		
		//2. CURRENT PLAYER TAG	 - FINAL
			g.setFont(new Font("Algerian", Font.PLAIN, 45));
			int id = allPlayer[0].ID;
			g.drawString("Player "+ id, 1500,985);
			
		//3. FACTORY FLOOR NUMBER INDECATORS - FINAL
			int xinc = 85+10;
			int yinc = 55;
			g.drawString("  " + factoryFloor.colors[0],715-10+xinc,yinc+ 86-10);
			g.drawString("  " + factoryFloor.colors[1],907-10+xinc,yinc+91-10);
			g.drawString("  " + factoryFloor.colors[2],1110-10+xinc,yinc+91-10);
			g.drawString("  " + factoryFloor.colors[3],715-10+xinc,yinc+ 199-10);
			g.drawString("  " + factoryFloor.colors[4],907-10+xinc,yinc+199-10);
			if (factoryFloor.firstMarker)
				g.drawImage(tileimage[5],1110-10,199-10, 81,81,null);
			
		//4. FACTORY TILES - FINAL
			for (Factory f: allFactory) {
				for (int b = 0; b<4; b++) {
					if (!f.isEmpty()) {
					int color = f.TileColors.get(b);
					int Factoryx = f.Factoryx;
					int Factoryy = f.Factoryy;
					if (b==0) 
						g.drawImage(tileimage[color],Factoryx,Factoryy,tilesize,tilesize,null);
					else if (b==1) 
						g.drawImage(tileimage[color],Factoryx+tilesize,Factoryy,tilesize,tilesize,null);
					else if (b==2) 
						g.drawImage(tileimage[color],Factoryx,Factoryy+tilesize,tilesize,tilesize,null);
					else 
						g.drawImage(tileimage[color],Factoryx+tilesize,Factoryy+tilesize,tilesize,tilesize,null);
					}
				}
			}	
	
	//5. BUFFER ZONE BIG TILE + NUMBER INDECATOR - FINAL
			if (!allPlayer[0].BufferZone.isEmpty()) {
				g.setFont(new Font("Algerian", Font.PLAIN, 60));
				g.drawImage(tileimage[allPlayer[0].BufferZone.get(0)], 1460,662,150,150,null);
				g.drawString("x" + allPlayer[0].BufferZone.size(),1460+150+50,662+150/2);
				g.setFont(new Font("Algerian", Font.PLAIN, 45));
			}	
			
	//6. CURRENT PLAYER WALL - FINAL	
			ArrayList<Integer>col = new ArrayList<>();
			for(int i = 0; i < 5; i++) 
				col.add(i);
			
			
			int x = 1018-10;
			int y = 592-10;
			int w = 52;
			int h = 53;
			
			for(int r = 0; r < 5; r++){
				for(int c = 0; c < allPlayer[0].wall.get(r).size(); c++){
					if (allPlayer[0].wall.get(r).contains(c)) {
					if(col.get(c)==0) 
						g.drawImage(tileimage[0], x, y, w, h, null);
	
					if(col.get(c)==1) 
						g.drawImage(tileimage[1], x, y, w, h, null);
	
					if(col.get(c)==2) 
						g.drawImage(tileimage[3], x, y, w, h, null);
	
					if(col.get(c)==3) 
						g.drawImage(tileimage[4], x, y, w, h, null);
	
					if(col.get(c)==4) 
						g.drawImage(tileimage[2], x, y, w, h, null);
	                                                                     
				}}
				x += 61;
				x = 1019 - 10;
				y += 61;
				rotate(col);

	//OTHER PLAYER TAGS
				drawRotate(g, 70,315, 270, "Player "+ allPlayer[1].ID); 
				drawRotate(g, 70,655, 270, "Player "+ allPlayer[2].ID); 
				drawRotate(g, 70,1000, 270, "Player "+ allPlayer[3].ID); 
				
	//last. animations
				for(AnimatableObject ani : animatableObjectList) 
					g.drawImage(ani.getImage(), ani.getX(), ani.getY(), ani.getWidth(), ani.getHeight(), ani.getObserver());

		}
		}

		public void addFactories(Factory[] allFact) {
			allFactory = allFact;
		}

		public static AzulPanel get() {
			if(azulPanel==null) {
				azulPanel = new AzulPanel();
			}
			return azulPanel;
		}

		public void addAnimatableObject(AnimatableObject ani) {
			animatableObjectList.add(ani);
		}
	
	
	public void rotate(ArrayList<Integer> x) {
		int a = x.get(0);
		int b = x.get(1);
		int c = x.get(2);
		int d = x.get(3);
		int e = x.get(4);
		x.set(0, b);
		x.set(1, c);
		x.set(2, d);
		x.set(3, e);
		x.set(4, a);
	}
	public static void drawRotate(Graphics g, double x, double y, int angle, String text) 
	{    
		Graphics2D g2d = (Graphics2D)g;
	    g2d.translate((float)x,(float)y);
	    g2d.rotate(Math.toRadians(angle));
	    g2d.drawString(text,0,0);
	    g2d.rotate(-Math.toRadians(angle));
	    g2d.translate(-(float)x,-(float)y);
	}  
	public void updateAll(Factory[] af, FactoryFloor f, ArrayList<Integer> b, Player [] a) {
		allFactory = af;
		factoryFloor = f;
		bag = b;
		allPlayer = a;
	}

}
