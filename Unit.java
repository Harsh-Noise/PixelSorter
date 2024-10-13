package sorter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Unit<T> {
	T value;
	int rotation; //n=0, )=1, u=2, (=3
	int xcoord;
	int ycoord;
	
	public Unit() {
		rotation = 0;
		xcoord = 0;
		ycoord = 0;
	}
	
	public Unit(int rot, int x, int y){
		rotation = rot;
		if(rotation > 3) {
			rotation -= 4;
		} else if (rotation < 0){
			rotation += 4;
		}
		xcoord = x;
		ycoord = y;
	}
	
	public void setValue(T newVal) {
		value = newVal;
	}
	
	public T getValue() {
		return(value);
	}
	
	public int getX() {
		return(xcoord);
	}
	
	public int getY() {
		return(ycoord);
	}
	
	public void printCoords() {
		System.out.print("(" + xcoord + ", " + ycoord + ")");
	}
	
	public ArrayList<Unit> iterate() {
		ArrayList<Unit> returnList = new ArrayList<Unit>();
		int newx = xcoord*2;
		int newy = ycoord*2;
		switch(rotation) {
		case 0:
			returnList.add(new Unit(rotation + 1, newx, newy));
			returnList.add(new Unit(rotation, newx, newy + 1));
			returnList.add(new Unit(rotation, newx + 1, newy + 1));
			returnList.add(new Unit(rotation - 1, newx + 1, newy));
			break;
		case 1:
			returnList.add(new Unit(rotation - 1, newx, newy));
			returnList.add(new Unit(rotation, newx + 1, newy));
			returnList.add(new Unit(rotation, newx + 1, newy + 1));
			returnList.add(new Unit(rotation + 1, newx, newy + 1));
			break;
		case 2:
			returnList.add(new Unit(rotation + 1, newx + 1, newy + 1));
			returnList.add(new Unit(rotation, newx + 1, newy));
			returnList.add(new Unit(rotation, newx, newy));
			returnList.add(new Unit(rotation - 1, newx, newy + 1));
			break;
		case 3:
			returnList.add(new Unit(rotation - 1, newx + 1, newy + 1));
			returnList.add(new Unit(rotation, newx, newy + 1));
			returnList.add(new Unit(rotation, newx, newy));
			returnList.add(new Unit(rotation + 1, newx + 1, newy));
			break;
		default:
			System.out.println("Got rotation " + rotation + " at switch!");
			return(null);
		}
		
		return(returnList);
	}
	
	public static void main(String[] args) {
		Unit start = new Unit();
		start.printCoords();
		System.out.println();
		
		ArrayList<Unit> Hilbert = start.iterate();
		
		for(int x = 0; x < Hilbert.size(); x++) {
			Hilbert.get(x).printCoords();
			System.out.print(", ");
		}
		System.out.println();
		
		ArrayList<Unit> Hilbert2 = new ArrayList<Unit>();
		for(int x = 0; x < Hilbert.size(); x++) {
			Hilbert2.addAll(Hilbert.get(x).iterate());
		}
		
		for(int x = 0; x < Hilbert2.size(); x++) {
			Hilbert2.get(x).printCoords();
			System.out.print(", ");
		}
		System.out.println();
		
		ArrayList<Unit> Hilbert3 = new ArrayList<Unit>();
		for(int x = 0; x < Hilbert2.size(); x++) {
			Hilbert3.addAll(Hilbert2.get(x).iterate());
		}
		
		for(int x = 0; x < Hilbert3.size(); x++) {
			Hilbert3.get(x).printCoords();
			System.out.print(", ");
		}
		System.out.println();
		
		ArrayList<Unit> Hilbert4 = new ArrayList<Unit>();
		for(int x = 0; x < Hilbert3.size(); x++) {
			Hilbert4.addAll(Hilbert3.get(x).iterate());
		}
		
		for(int x = 0; x < Hilbert4.size(); x++) {
			Hilbert4.get(x).printCoords();
			System.out.print(", ");
		}
		System.out.println();
		
		for(int x = 0; x < Hilbert4.size(); x++) {
			System.out.print(x);
			Hilbert4.get(x).setValue((int) x);
		}
		
		for(int x = 0; x < Hilbert4.size(); x++) {
			System.out.print(Hilbert4.get(x).getValue() + ", ");
		}
		System.out.println();
		
		BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_BYTE_GRAY);
		
		for(int x = 0; x < Hilbert4.size(); x++) {
			Color color = new Color((int)Hilbert4.get(x).getValue(), (int)Hilbert4.get(x).getValue(), (int)Hilbert4.get(x).getValue());
			img.setRGB(Hilbert4.get(x).getX(), Hilbert4.get(x).getY(), color.getRGB());
		}
		
	    try {
	    	ImageIO.write(img, "png", new File("hilbert.png"));
	    }catch(IOException e) {
	    	
	    }
	}
}
