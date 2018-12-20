package commons;

import javax.swing.ImageIcon;

public class Ball extends Item implements IProperties{
	private float xD, yD;
	
	public Ball() {
		initBall();
	}
	
	private void initBall() {
		xD = 1;
		yD = 1;
		
		loadImage();
		getImageDimensions();
		resetState();
	}
	
	private void loadImage() {
		
		ImageIcon ii = new ImageIcon("src/resources/ball.png");
		image = ii.getImage();
	}
	
	private void resetState() {
		x = DEF_BALL_X;
		y = DEF_BALL_Y;
	}
	
	public void move() {
		x += xD;
		y += yD;
		
		if(x == 0) {
			setXD(1);
		}
		else if(x == WIDTH - iWidth) {
			setXD(-1);
		}
		else if(y == 0) {
			setYD(1);
		}
	}
	
	public void setXD(float x) {
		xD = x;
	}
	public void setYD(float y) {
		yD = y;
	}
	public float getYD() {
        return yD;
    }
	public float getXD() {
        return xD;
    }
	
	
}
