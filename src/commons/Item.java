package commons;

import java.awt.Rectangle;
import java.awt.Image;

public class Item {

	protected int x, y, iWidth, iHeight;
	protected Image image;

	public int getX() {
		return x;
	}

	protected void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	protected void setY(int y) {
		this.y = y;
	}

	public int getiWidth() {
		return iWidth;
	}

	public int getiHeight() {
		return iHeight;
	}
	
	public Image getImage() {
        return image;
    }
	
	public Rectangle getRect() {
		return new Rectangle(x, y, image.getWidth(null),  image.getHeight(null));
	}
	
	public void getImageDimensions() {
		iWidth = image.getWidth(null);
		iHeight = image.getHeight(null);
	}
}
