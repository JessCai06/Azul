import java.awt.*;
import java.awt.Font;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;

public class AzulPanel extends JPanel{

	BufferedImage background;
	Factory []allFactory;
	public BufferedImage []tileimage;
	private static AzulPanel azulPanel;
	private ArrayList<AnimatableObject> animatableObjectList;
	
	public AzulPanel(){
		animatableObjectList = new ArrayList<>();
		
		//tile pictures
		tileimage = new BufferedImage [6];		
		try{
			background = ImageIO.read(getClass().getResource("/images/FinalForMe.jpg"));
		    tileimage[0] = ImageIO.read(getClass().getResource("/images/red.png"));
		    tileimage[1] = ImageIO.read(getClass().getResource("/images/yellow.png"));
		    tileimage[2] = ImageIO.read(getClass().getResource("/images/white.png"));
		    tileimage[3] = ImageIO.read(getClass().getResource("/images/blue.png"));
		    tileimage[4] = ImageIO.read(getClass().getResource("/images/black.png"));
		    tileimage[5] = ImageIO.read(getClass().getResource("/images/1.jpg"));
		  } catch (Exception ex) {
		    System.out.println("IMAGE");
		  }
	}
//TODO	
	public void paint(Graphics g) {
		g.drawImage(background, -10, -10, getWidth(), getHeight(), null);
		
		g.setFont(new Font("Algerian", Font.PLAIN, 45));
		g.drawString("Player "+ 1, 1500,985);
		
		int tilesize = 66;
		int inc = 0;
		//g.drawImage(tileimage[1], 100+50*inc,100+50*inc,200,200,null);
		for (Factory f: allFactory) {
			for (int b = 0; b<4; b++) {
				int color = f.TileColors.get(b);
				int Factoryx = f.Factoryx;
				int Factoryy = f.Factoryy;
				if (b==0) 
					g.drawImage(tileimage[color],Factoryx,Factoryy,tilesize,tilesize,null);
				else if (b==1) 
					g.drawImage(tileimage[color],Factoryx,Factoryy+tilesize,tilesize,tilesize,null);
				else if (b==2) 
					g.drawImage(tileimage[color],Factoryx+tilesize,Factoryy,tilesize,tilesize,null);
				else 
					g.drawImage(tileimage[color],Factoryx+tilesize,Factoryy+tilesize,tilesize,tilesize,null);

				inc ++;
				//System.out.println(b + " "  + inc);
			}
		}	
		for(AnimatableObject ani : animatableObjectList) 
			g.drawImage(ani.getImage(), ani.getX(), ani.getY(), ani.getWidth(), ani.getHeight(), ani.getObserver());

		//guides
		g.drawLine(0,getHeight()/2,getWidth(),getHeight()/2);
		g.drawLine(getWidth()/2,0,getWidth()/2,getHeight());
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
}







