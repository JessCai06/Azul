import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Screen extends JPanel {

    private int startX, startY, endX, endY, sizeinit,size, sizefinal, inc;
    private double delta = 200;
    double posX, posY;
    Image i;
    Timer timer;
    
    public Screen(BufferedImage img, int x, int y, int finalx, int finaly, int s) {
		super();
		setBackground(null);
		setOpaque(false);
        setDoubleBuffered(true);
        startX = x;
        startY = y;
        posX = x;
        posY = y;
        endX = finalx;
        endY = finaly;
        size = s;
        i = img;
        timer = new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incMove();
                repaint();
            }
        });
        timer.start();

    }
    public Screen(BufferedImage img, int x, int y, int finalx, int finaly, int s, int sf) {
		super();
		setBackground(null);
		setOpaque(false);
        setDoubleBuffered(true);
        posX = x;
        posY = y;
        endX = finalx;
        endY = finaly;
        sizeinit = s;
        size = s;
        sizefinal = sf;
        i = img;
        timer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
					Thread.sleep(15);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                incMove();
                repaint();
            }
        });
        timer.start();
    }

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(i, (int)this.posX, (int)this.posY, size,size, null, null);
    }


    public void incMove() {
    	inc ++;
        posX += ((endX-startX)/delta);
        posY += ((endY-startY)/delta);
        size += ((sizefinal-sizeinit)/delta);
        if (inc>delta) {
            timer.stop();
            removeAll();
            setVisible(false);
        } 
    }

}