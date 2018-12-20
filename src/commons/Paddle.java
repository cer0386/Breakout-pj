package commons;

import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Paddle extends Item implements IProperties{
	
	private int dx;
	
	public Paddle() {
		initPaddle();
	}
	
	private void initPaddle() {
		
		loadImage();
		getImageDimensions();
		resetState();
	}

	private void resetState() {
		x = DEF_PADDLE_X;
		y = DEF_PADDLE_Y;
		
	}

	private void loadImage() {
		
		ImageIcon ii = new ImageIcon("src/resources/paddle.png");
		image = ii.getImage();
	}
	
	public void move() {
		x += dx;
		
		if(x <= 0)
			x = 0;
		
		if(x >= WIDTH - iWidth)
			x = WIDTH - iWidth;
	}
	
	
	public void keyPressed(KeyEvent ke) {
		
		int key = ke.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT)
			dx = -1;
		if(key == KeyEvent.VK_RIGHT)
			dx = 1;
		
	}

	public void keyReleased(KeyEvent ke) {
		
		int key = ke.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT)
			dx = 0;
		if(key == KeyEvent.VK_RIGHT)
			dx = 0;
	}
}
