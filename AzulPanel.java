import java.awt.*;
import java.awt.Font;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;

public class AzulPanel extends JPanel{

	BufferedImage background, strtscrn, gameSum;
	public static boolean strt;
	public BufferedImage []tileimage;
	public static int tilesize = 66;
	public static int currentScore, currentRow, playerInc;
	
	public Factory[] allFactory = AzulWindow.allFactory;
	public static FactoryFloor factoryFloor = AzulWindow.factoryFloor;
	public static ArrayList<Integer> bag = AzulWindow.bag;
	
	public Player [] allPlayer = AzulWindow.allPlayer;
	
	static Boolean firstTake = AzulWindow.firstTake;
	static Boolean endgame = AzulWindow.endgame;
	static Boolean end;
	static Boolean roundscore = AzulWindow.roundscore;
	String output;
	static ArrayList <Player> victors;
	
	//animations
	private static AzulPanel azulPanel;
	private ArrayList<AnimatableObject> animatableObjectList;
	
	
	public AzulPanel(){
		
		animatableObjectList = new ArrayList<>();
		end = false;
		strt = true;
		//tile pictures
		tileimage = new BufferedImage [6];		
		try{
			strtscrn = ImageIO.read(getClass().getResource("/images/cover page.png"));
			gameSum = ImageIO.read(getClass().getResource("/images/Finalscrn.png"));
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
		
		if(strt) {
			g.drawImage(strtscrn, 0, 0, getWidth(), getHeight(), null);
			
		}
		if(!strt) {
			 
			//1. BACKGROUND - FINAL
				g.drawImage(background, -10, -10, getWidth(), getHeight(), null);

			//2. CURRENT PLAYER TAG	 - FINAL
				g.setFont(new Font("Algerian", Font.PLAIN, 45));
				int id = allPlayer[0].ID;
				g.drawString("Player "+ id + " ", 1500,985);
				
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
					for (int b = 0; b<f.TileColors.size(); b++) {
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
		//4.5 CURRENT PLAYER SCORES
				if (allPlayer[0].score == 0)
					g.fillRect(685-10, 350-10, 30, 30);
				else {
					for (int c = 1; c<= 20; c++) {
						for (int r = 1; r<=5; r++) {
							if (allPlayer[0].score == (r-1)*20+c)
								g.fillRect(673+(c-1)*2+(c-1)*30, 378+8*(r-1)+(r-1)*30, 26, 26);
						}
					}
				}
		
		
		//5. BUFFER ZONE BIG TILE + NUMBER INDECATOR - FINAL
				if (!allPlayer[0].BufferZone.isEmpty()) {
					g.setFont(new Font("Algerian", Font.PLAIN, 60));
					g.drawImage(tileimage[allPlayer[0].BufferZone.get(0)], 1480,650,120,120,null);
					g.drawString("x" + allPlayer[0].BufferZone.size(),1460+150+40,650+120/2+20);
					g.setFont(new Font("Algerian", Font.PLAIN, 45));
				}	
				if (output!=null) {
					g.setFont(new Font("Algerian", Font.PLAIN, 25));
					//g.drawString(output + output.length(),1420,650+200);
					if(output.equals("2")) {
						g.drawString("Selection:",1420,650+200-10);
						g.setFont(new Font("Dialog", Font.PLAIN, 15));
						g.drawString("any colored tiles",1420,650+200+20-10);
					}
					else if(output.equals("1")) {
						g.drawString("Placement:",1420,650+200-10);
						g.setFont(new Font("Dialog", Font.PLAIN, 15));
						g.drawString("the arrows near the pattern lines or the area in the floor line",1420,650+200+20-10);
					}
					else if(output.equals("3")) {
						String str = "Scoring:";
						if (currentRow <5)
							str += "   Pattern Line "+ currentRow;
						else if (currentRow == 5)
							str += "   Floor Line";
						else
							str += "   End Game Bonuses";
						g.drawString(str,1420,650+200-10);
						g.setFont(new Font("Dialog", Font.PLAIN, 15));
						g.drawString("Click on the score button to score the next row",1420,650+200+20-10);
						g.setFont(new Font("Algerian", Font.PLAIN, 60));
						if (currentScore>0)	
							g.drawString("+" + currentScore,1519,750);
						else	
							g.drawString("" + currentScore,1519,750);
					}

					g.setFont(new Font("Algerian", Font.PLAIN, 45));
				}					
				g.setColor(Color.GRAY);
				g.setFont(new Font("Algerian", Font.PLAIN, 20));
				g.drawString("Version 12-2",1375,565+10);
				/*g.drawString("Verti    "+allPlayer[0].scoreVerti(),1375,985-20);
				g.drawString("Horiz    "+allPlayer[0].scoreHoriz(),1375,1000-20);
				g.drawString("Color    "+allPlayer[0].scorecolor(),1375,1015-20);*/
				g.setFont(new Font("Dialog", Font.PLAIN, 15));
				g.drawString("Jess Luc Omar Josh",1375+220,565+10);
			g.setFont(new Font("Algerian", Font.PLAIN, 45));
			g.setColor(Color.BLACK);
			
		//5.5 FINAL GAME VICTORS AND SCORE COMPARISON		
				if (end) {

					g.drawImage(gameSum, -10, -10, getWidth(), getHeight(), null);
					g.setFont(new Font("Algerian", Font.ITALIC, 50));
					g.drawString("Game Summary ",1427,165);
					g.setFont(new Font("Algerian", Font.PLAIN, 20));
					g.drawString(String.format("Bonus:"),1550-105,550+60);	
					g.drawString(String.format("Bonus:"),1550-105,420+60);	
					g.drawString(String.format("Bonus:"),1550-105,680+60);	
					g.drawString(String.format("Final Score:"),1550-160,680+60+45);	
					g.setFont(new Font("Algerian", Font.PLAIN, 25));
					for (int b = 0; b<allPlayer.length; b++) {
						// score
						g.drawString(String.format("%3d",allPlayer[b].score),1550+b*80,323);
						int horizontal = 2*allPlayer[b].scoreHoriz();
						g.drawString(String.format("+%3d",horizontal),1550+b*80,420+60);
						// Vertical
						g.drawString(String.format("%3d",allPlayer[b].scoreVerti()),1550+b*80,550);
						int vert = 7*allPlayer[b].scoreVerti();
						g.drawString(String.format("+%3d",vert),1550+b*80,550+60);
						// horizontal rows filled
						g.drawString(String.format("%3d",allPlayer[b].scoreHoriz()),1550+b*80,420);
						int color = 10*allPlayer[b].scorecolor();
						g.drawString(String.format("+%3d",color),1550+b*80,680+60);
						// all spaces of this color filled - check rulebook
						g.drawString(String.format("%3d",allPlayer[b].scorecolor()),1550+b*80,680);
						g.drawString(String.format("%3d",allPlayer[b].score),1550+b*80,680+60+45);	
					}
					String s = "";
					for (Player b: victors)
						s+= "Player " + b.ID+ " ";
					g.drawString(s,1600,850);

				}
					g.setFont(new Font("Algerian", Font.PLAIN, 45));
				
		//6. CURRENT PLAYER WALL - FINAL	
				ArrayList<Integer>col = new ArrayList<>();
				for(int i = 0; i < 5; i++) 
					col.add(i);
				
				
				int x = 1018-10;
				int y = 592-10;
				int w = 53;
				int h = 53;
				
				//g.drawImage(tileimage[0],  x-91,y-2,w+2,h+2,null);
				for(int r = 0; r < 5; r++)
						for(int c = 0; c < 5; c++) {
							if(allPlayer[0].wall.get(c).get(r)>-1) {
							g.drawImage(tileimage[allPlayer[0].wall.get(c).get(r)], x+61*r, y+61*c, w, h, null);
							/*if (AzulWindow.roundscore) {
								g.drawString(" "+allPlayer[0].scoreWall.get(c).get(r), x+61*r+3, y+61*c+40);
							}*/
							}

						}
				for(int r = 0; r < 5; r++)
					for(int c = 0; c < allPlayer[0].patternLine[r].length; c++) {
						if(allPlayer[0].patternLine[r][c]>-1)					
						g.drawImage(tileimage[allPlayer[0].patternLine[r][c]], x-91-(w+2)*c-c*6,y-2+(h+2)*r+ r*6,w+2,h+2, null);
					}
				for(int r = 0; r < 7; r++) {
					if(allPlayer[0].floorLine[r]>-1)				//309 56	391 = 335
						g.drawImage(tileimage[allPlayer[0].floorLine[r]], 671+(w+14)*r,925,w,h, null);
					}

		//OTHER PLAYER TAGS
					drawRotate(g, 70,315, 270, "Player "+ allPlayer[1].ID + " ");
					g.drawString(String.format("%3d",allPlayer[1].score),493,330); 
					
					drawRotate(g, 70,655, 270, "Player "+ allPlayer[2].ID+ " "); 
					g.drawString(String.format("%3d",allPlayer[2].score),493,667); 
					
					drawRotate(g, 70,980, 270, "Player "+ allPlayer[3].ID+ " ");
					g.drawString(String.format("%3d",allPlayer[3].score),493,1000); 
					
					for(int p = 1; p < 4; p++) {
						
						x = 392;
						y = 45 + (p-1)*310+27*(p-1);
						w = 39;
						h = 39;
						
						//g.drawImage(tileimage[0],  x-91,y-2,w+2,h+2,null);
						for(int r = 0; r < 5; r++)
								for(int c = 0; c < 5; c++) {
									if(allPlayer[p].wall.get(c).get(r)>-1)
									g.drawImage(tileimage[allPlayer[p].wall.get(c).get(r)], 367+r*6+r*w, 46+c*6+c*(h+1)+(p-1)*336, w+1, h+1, null);
								}
						for(int r = 0; r < 5; r++)
							for(int c = 0; c < allPlayer[p].patternLine[r].length; c++) {
								if(allPlayer[p].patternLine[r][c]>-1)					
								g.drawImage(tileimage[allPlayer[p].patternLine[r][c]], x-91-(w+2)*c-c*4,y-2+(h+2)*r+ r*5,w+2,h+2, null);
							}
						for(int f = 0; f < 7; f++) {
							if(allPlayer[p].floorLine[f]>-1)					
								g.drawImage(tileimage[allPlayer[p].floorLine[f]], 129+(w)*f+(f)*10-10,300+335*(p-1)+8,w,h, null);
							}
					}
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
	public void updateAll(Factory[] af, FactoryFloor f, ArrayList<Integer> b, Player [] a,Boolean s, Boolean e, Boolean start, String out,Boolean en) {
		allFactory = af;
		factoryFloor = f;
		bag = b;
		allPlayer = a;
		roundscore = s;
		endgame = e;
		strt = start;
		output = out;
		end = e;
	}
	public void updateScoring(int score, int row, int play,ArrayList <Player> vict, Boolean e) {
		currentScore = score;
		currentRow = row;
		playerInc = play;
		victors = vict;
		end = e;
	}

}