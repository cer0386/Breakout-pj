package score;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SaveScore {
	private int[] top5;
	private String[] names;
	private int points;
	private String name;
	
	private ArrayList<Integer> cisla;
	private ArrayList<String> jmena;
	
	
	public SaveScore(int p, String n) {
		top5 = new int[6];
		names = new String[6];
		cisla = new ArrayList<Integer>(6);
		jmena = new ArrayList<String> (6);
		this.points = p;
		this.name = n;
		saveTop();
		
	}
	
	private void readTop5() {
		try (BufferedReader br = new BufferedReader(new FileReader("ladder/top.txt"))){
			String line = null;
			String[] array = null;
			while((line = br.readLine()) != null) {
				array = line.split(":");
				String j = array[0];
				jmena.add(j);
				int n = Integer.parseInt(array[1]);
				cisla.add(n);
				

			}
		} catch (FileNotFoundException e) {
			System.out.println("FileWithDimensionsNotFound");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IODimensions");
			e.printStackTrace();
		}
		
	}
	
	
	
	private void addScore() {
		
		cisla.add(points);
		jmena.add(name);
		for (int i = 0; i < 6; i++) {
			top5[i] = cisla.get(i);
			names[i] = jmena.get(i);
		}
		sortIt(top5,names);
	}
	
	void sortIt(int arr[], String arr2[]) 
    { 
        int n = arr.length; 
        for (int i = 0; i < n-1; i++) 
            for (int j = 0; j < n-i-1; j++) 
                if (arr[j] < arr[j+1]) 
                { 
                    int temp = arr[j]; 
                    arr[j] = arr[j+1]; 
                    arr[j+1] = temp; 
                    String temp2 = arr2[j]; 
                    arr2[j] = arr2[j+1]; 
                    arr2[j+1] = temp2; 
                } 
    }  
	
	
	private void saveTop() {
		readTop5();
		addScore();
		BufferedWriter outputWriter = null;
		try {
			outputWriter = new BufferedWriter(new FileWriter("ladder/top.txt"));
		
		  for (int i = 0; i < 5; i++) {

		    outputWriter.write(names[i]+":"+top5[i]);

		    outputWriter.newLine();
		  }
		  outputWriter.flush();  
		  outputWriter.close();  
		} catch (IOException e) {

			e.printStackTrace();
		}
		printTop5();
		
	}
	
	private void printTop5() {
		cisla.clear();
		jmena.clear();
		readTop5();
		for (int i = 0; i < 5; i++) {
			System.out.println(jmena.get(i)+" - "+cisla.get(i));
		}
		
		
	}
}
