import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Animation extends JPanel{
	BufferedImage img;
	int X, Y, endx, endy, size, time;
	private static Animation panel;
	
	Animation (BufferedImage img1, int x, int y, int finalx, int finaly, int sizex, int milsec){
		super();
		setBackground(null);
		setOpaque(false);
		img = img1;
		X = x;
		Y = y;
		size = sizex;
		time = milsec;
		endx = finalx;
		endy = finaly;
		
	}
	public void paint(Graphics g) {
		 super.paintComponent(g);
			for(int i = 0; i<60; i++) {
				g.drawImage(img,X+(i*endx-X),Y+(i*endy-Y), size, size,null);
				try {
					Thread.sleep(time/60);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setLayout(null);
				removeAll();
			}
	}

	

}
