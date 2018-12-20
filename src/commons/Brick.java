package commons;
import javax.swing.ImageIcon;

public class Brick extends Item {
	// 0 = destroyed, 1 = damaged, 2 = fullHP
	private int damageState;
	private int state;
	
	public Brick(int x, int y, int state) {
		initBrick(x,y,state);
	}
	
	private void initBrick(int x, int y, int state) {
		this.x = x;
		this.y = y;
		this.state = state;
		if(state == 1 || state == 3)
			damageState = 2;
		else if(state == 2 || state == 4)
			damageState = 1;
		else
			damageState = 0;
		
		loadImage(state);
		getImageDimensions();
		
	}

	public void loadImage(int stat) {
		
		ImageIcon ii;
		
		switch (stat) {
			case 1:
				ii = new ImageIcon("src/resources/tile1.png");
				break;
			case 2:
				ii = new ImageIcon("src/resources/tile1B.png");
				break;
			case 3:
				ii = new ImageIcon("src/resources/tile2.png");
				break;
			case 4: 
				ii = new ImageIcon("src/resources/tile2B.png");
				break;
			default:
				ii = new ImageIcon("src/resources/tile1.png");
				break;
		}
			
		image = ii.getImage();
		
	}
	
	public int isDestroyed() {
		return damageState;
	}
	public int getState() {
		return state;
	}
	
	public void setDamageState(int val) {
		damageState = val;
	}
	
}
