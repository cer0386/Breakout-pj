package commons;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LvL {
	
	public int nOfBricks;
	public int [] dim;
	public int [] bricksState;
	
	
	public LvL() {
		int l = getLevel();
		loadLevel(l);
	}

	private int getLevel() {
		int lvl = 1;
		String[] levels = {"1","2","3","4","5"}; 
		
		JFrame frame = new JFrame("Input Dialog Example 3");
	    String s = (String) JOptionPane.showInputDialog(frame, 
	        "Vyber si úroveò",
	        "Výbìr úrovnì",
	        JOptionPane.QUESTION_MESSAGE, 
	        null, 
	        levels, 
	        levels[0]);
		lvl = Integer.parseInt(s);
		return lvl;
	}
	
	private void loadLevel(int lvl) {
			
			try (BufferedReader br = new BufferedReader(new FileReader(String.format("levels/%dl.txt",lvl)))){
				String line = null;
				String[] array = null;
				dim = new int[2];
				while((line = br.readLine()) != null) {
					array = line.split(",");
					
				}
				dim[0] = Integer.parseInt(array[0]);
				dim[1] = Integer.parseInt(array[1]);
				nOfBricks = dim[0]*dim[1];
			} catch (FileNotFoundException e) {
				System.out.println("FileWithDimensionsNotFound");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("IODimensions");
				e.printStackTrace();
			}
			
			try(BufferedReader br = new BufferedReader(new FileReader(String.format("levels/%d.txt",lvl)))){
				String line = null;
				String [] array = null;
				bricksState = new int[ dim[0]*dim[1] ];
				int count = 0;
				while((line = br.readLine()) != null) {
					array = line.split(",");
					for (int i = 0; i < array.length; i++) {
						bricksState[count] = Integer.parseInt(array[i]);
						count++;
					}
				}
				
			} catch (FileNotFoundException e) {
				System.out.println("FileWithPatternNotFound");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("IOPattern");
				e.printStackTrace();
			}
			
			
			
		}
}
