import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Animate {
	
	
	//animates multiple objects at once
	public void animateObjects(AnimatableObject[] ani, int[] endingX, int[] endingY, int[] endingWidth, int[] endingHeight, int[] operationLength, int[] stepNum) throws InterruptedException {
		BufferedImage[] image = new BufferedImage[ani.length];
		int[] startingX = new int[ani.length];
		int[] startingY = new int[ani.length];
		int[] width = new int[ani.length];
		int[] height = new int[ani.length];
		ImageObserver[] observer = new ImageObserver[ani.length];
		
		for(int i = 0; i<ani.length; i++) {
			image[i] = ani[i].getImage();
			startingX[i] = ani[i].getX();
			startingY[i] = ani[i].getY();
			width[i] = ani[i].getWidth();
			height[i] = ani[i].getHeight();
			observer[i] = ani[i].getObserver();
		}
		
		for(int i = 0; i<ani.length; i++) {
			double xStepLength, yStepLength;
			xStepLength = (double)(endingX[i] - startingX[i])/stepNum[i];
			yStepLength = (double)(endingY[i] - startingY[i])/stepNum[i];
		
			double tempX, tempY;
			tempX = startingX[i];
			tempY = startingY[i];
		
			for(int j = 0; j<stepNum[i]; j++) {
				ani[i].setVariables(image[i], (int)tempX, (int)tempY, width[i], height[i], observer[i]);
				tempX += xStepLength;
				tempY += yStepLength;
				Thread.sleep(operationLength[i]/stepNum[i]);
			}	
			ani[i].setVariables(image[i], endingX[i], endingY[i], width[i], height[i], observer[i]);
		}
	}
	
	
	//animates multpile objects at once
	public static void animate(AnimatableObject ani, int endingX, int endingY, int endingWidth, int endingHeight, int operationLength, int stepNum) throws InterruptedException {
		BufferedImage image = ani.getImage();
		int startingX = ani.getX();
		int startingY = ani.getY();
		int width = ani.getWidth();
		int height = ani.getHeight();
		ImageObserver observer = ani.getObserver();
				
		double xStepLength, yStepLength;
		xStepLength = (double)(endingX - startingX)/stepNum;
		yStepLength = (double)(endingY - startingY)/stepNum;
		
		double tempX, tempY;
		tempX = startingX;
		tempY = startingY;
		
		for(int i = 0; i<stepNum; i++) {
			ani.setVariables(image, (int)tempX, (int)tempY, width, height, observer);
			tempX += xStepLength;
			tempY += yStepLength;
			Thread.sleep(operationLength/stepNum);
		}
		ani.setVariables(image, endingX, endingY, width, height, observer);
	}
}