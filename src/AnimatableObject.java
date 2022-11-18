import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class AnimatableObject {
	private BufferedImage image;
	private int x, y, width, height;
	private ImageObserver observer;
	private AzulPanel azulPanel;
	
	
	//constructor, makes new image on screen based on variables inputed
	public AnimatableObject(BufferedImage img, int x1, int y1, int wid, int hei, ImageObserver obs) {
		image = img;
		x = x1;
		y = y1;
		width = wid;
		height = hei;
		observer = obs;
		azulPanel = AzulPanel.get();
		azulPanel.addAnimatableObject(this);
		azulPanel.repaint();
	}
	
	
	//get methods
	public BufferedImage getImage() {return image;}
	public int getX() {return x;}
	public int getY() {return y;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	public ImageObserver getObserver() {return observer;}
	
	
	//sets the image data and repaints
	public void setVariables(BufferedImage img, int x1, int y1, int wid, int hei, ImageObserver obs) {
		image = img;
		x = x1;
		y = y1;
		width = wid;
		height = hei;
		observer = obs;
		azulPanel.repaint();
	}
}















