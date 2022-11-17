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
	public static ArrayList<Integer> lid = AzulWindow.lid;
	
	public static Player currentPlayer = AzulWindow.currentPlayer;
	public static Player [] allPlayer = AzulWindow.allPlayer;
	
	public AzulPanel(){
		
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
//TODO	
	public void paint(Graphics g) {
		g.drawImage(background, -10, -10, 1920, 1080, null);
		
		g.setFont(new Font("Algerian", Font.PLAIN, 45));
		g.drawString("Player "+ 1, 1500,985);
		
		int inc = 0;
		//g.drawImage(tileimage[1], 100+50*inc,100+50*inc,200,200,null);
		//for ()
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

				inc ++;
				//System.out.println(b + " "  + inc);
			}
		}	

		if (!currentPlayer.BufferZone.isEmpty()) {
			g.drawImage(tileimage[currentPlayer.BufferZone.get(0)], 100+50*1,100+50*1,200,200,null);
		}
	}
}
