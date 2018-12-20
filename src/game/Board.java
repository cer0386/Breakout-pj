package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import commons.Ball;
import commons.Brick;
import commons.IProperties;
import commons.LvL;
import commons.Paddle;
import score.SaveScore;

public class Board extends JPanel implements IProperties {
	
	private Timer timer;
	private String msg = "Game Over";
	private Ball ball;
	private Paddle paddle;
	private Brick bricks[];
	private boolean running = true;
	private int score;
	LvL lvl;


	public Board() {
		initBoard();
	}

	private void initBoard() {
		
		addKeyListener(new TAdapter());
		setFocusable(true);
		lvl = new LvL();
		bricks = new Brick[lvl.nOfBricks];
		timer = new Timer();
		timer.scheduleAtFixedRate(new ScheduleTask(), DELAY, PERIOD);
		
	}
	
	
	
	@Override
	public void addNotify() {
		
		super.addNotify();
		gameInit();
	}
	

	
	
	private void gameInit() {
		
		ball = new Ball();
		paddle = new Paddle();
		int k = 0;
		score = 0;


		for (int i = 0; i < lvl.dim[0]; i++) {
			for(int j = 0; j < lvl.dim[1]; j++) {
				if(lvl.bricksState[k] != 0)
					bricks[k] = new Brick(j*50+30, i*20+50, lvl.bricksState[k]);
				k++;
			}
		}

	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
		
		if(running) {
			drawObjects(g2d);
		}
		else {
			gameFinished(g2d);
		}
		//Synchronizace obrazu, aby se to vykreslilo spravne
		Toolkit.getDefaultToolkit().sync(); 
	}

	private void drawObjects(Graphics2D g2d) {

		g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(), ball.getiWidth(), ball.getiHeight(), this);
		g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(), paddle.getiWidth(), paddle.getiHeight(), this);

		
		for (int i = 0; i < lvl.nOfBricks; i++) {
			if(bricks[i].isDestroyed() != 0) {
				g2d.drawImage(bricks[i].getImage(), bricks[i].getX(), bricks[i].getY(), bricks[i].getiWidth(), bricks[i].getiHeight(), this);
			}
		}
		
		Font currentFont = g2d.getFont();
		Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.4F);
		g2d.setFont(newFont);
		g2d.setColor(Color.RED);
		g2d.drawString(Integer.toString(score), 1, 15);
	}
	
	private void gameFinished(Graphics2D g2d) {
		
		Font font = new Font("Arial", Font.BOLD, 20);
		FontMetrics m = this.getFontMetrics(font);
		
		g2d.setColor(Color.DARK_GRAY);
		g2d.setFont(font);
		g2d.drawString(msg, (IProperties.WIDTH - m.stringWidth(msg))/2, IProperties.WIDTH / 2 );
		
 		
		
		
	}
	
	
	
	private class TAdapter extends KeyAdapter{
		
		@Override
		public void keyReleased(KeyEvent ke) {
			paddle.keyReleased(ke);
		}
		@Override
		public void keyPressed(KeyEvent ke) {
			paddle.keyPressed(ke);
		}

		
	}
	
	private class ScheduleTask extends TimerTask{
		
		@Override 
		public void run() {
			
			
			if(running) {
			ball.move();
			paddle.move();
			checkCollision();
			repaint();
			}
		}

		private void checkCollision() {
			//balon padl
			if(ball.getRect().getMaxY() > BOTTOM) {
				stopGame();
			}
			
			for (int i = 0, j = 0; i < lvl.nOfBricks; i++) {
				
				if(bricks[i].isDestroyed() == 0) {
					j++;
				}
				if(j == lvl.nOfBricks) {
					msg = "cg";
					stopGame();
				}
			}
			
			//kolize s plosinou
			
			if((ball.getRect()).intersects(paddle.getRect())) {
				int pLPos = (int) paddle.getRect().getMinX();
				int bLPos = (int) ball.getRect().getMinX();
				int first = pLPos + 8;
				int second = pLPos + 16;
				int third = pLPos + 24;
				int fourth = pLPos + 32;
				
				if(bLPos < first) {
					ball.setXD(-1);
					ball.setYD(-1);
				}
				
				if(bLPos >= first && bLPos < second) {
					ball.setXD(-1);
					ball.setYD(-1* ball.getYD());
				}
				
				if(bLPos >= second && bLPos < third) {
					ball.setXD(0);
					ball.setYD(-1);
				}
				
				if(bLPos >= third && bLPos < fourth) {
					ball.setXD(1);
					ball.setYD(-1 * ball.getYD());
				} 
				
				if(bLPos >= fourth) {
					ball.setXD(1);
					ball.setYD(-1);
				}
			}
			
			//kolize s cihlama
			
			for (int i = 0; i < lvl.nOfBricks; i++) {
				//jestli narazil mic do cihly
				if((ball.getRect()).intersects(bricks[i].getRect())) {
					
					int bLeft = (int) ball.getRect().getMinX();
					int bHeight = (int) ball.getRect().getHeight();
					int bWidth = (int) ball.getRect().getWidth();
					int bTop = (int) ball.getRect().getMinY();
					Point pR = new Point(bLeft + bWidth +1, bTop);
					Point pL = new Point(bLeft -1, bTop);
					Point pT = new Point(bLeft, bTop - 1);
					Point pB = new Point(bLeft, bTop + bHeight + 1);
					//jestli neni cihla znicena
					if(bricks[i].isDestroyed() != 0) {
						
						if(bricks[i].isDestroyed() == 1)
							score += 10;
						if(bricks[i].getRect().contains(pR)) {
							ball.setXD(-1);
						}
						else if(bricks[i].getRect().contains(pL)) {
							ball.setXD(1);
						}
						
						if(bricks[i].getRect().contains(pT)) {
							ball.setYD(1);
						}
						else if (bricks[i].getRect().contains(pB)){
							ball.setYD(-1);
						}
						//zmena stavu cihly po dopadu
						bricks[i].setDamageState(bricks[i].isDestroyed()-1);
						if(bricks[i].getState() == 1)
							bricks[i].loadImage(2);
						if(bricks[i].getState() == 3)
							bricks[i].loadImage(4);
					}
				}
			}
		
			
		}
		
		private void stopGame() {
			running = false;
			timer.cancel();
			String name= JOptionPane.showInputDialog("Zadej své jméno: ");
			new SaveScore(score, name);
			
		}
		

	}
	


	
	
}
