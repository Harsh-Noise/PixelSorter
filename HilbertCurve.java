package sorter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class HilbertCurve {
	ArrayList<Unit> curve = new ArrayList<Unit>();
	
	public HilbertCurve(int iterations) {
		iterate(iterations);
	}
	
	private void iterate(int iterations) {
		curve.add(new Unit());
		
		for(int x = 0; x < iterations; x++) {
			int curveLength = curve.size();
			
			for(int y = 0; y < curveLength; y++) {
				curve.addAll(curve.get(y).iterate());
			}
			
			for(int y = 0; y < curveLength; y++) {
				curve.remove(0);
			}
		}
	}
	
	public int getSize(){
		return(curve.size());
	}
	
	public ArrayList<Unit> getCurve(){
		return(curve);
	}

	public static void main(String[] args) {
		/*
		HilbertCurve curve = new HilbertCurve(9);
		
		System.out.println(curve.getCurve().size());
		
		for(int x = 0; x < 256; x++) {
			System.out.print(x);
			for(int y = 0; y < 256; y++) {
				curve.getCurve().get(x + (256*y)).setValue(new Color(0, x, y));
			}
		}
		System.out.println();
		
		BufferedImage img = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
		
		for(int x = 0; x < curve.getCurve().size(); x++) {
			Color color = (Color) curve.getCurve().get(x).getValue();
			System.out.print(x + ", ");
			System.out.println(color.getRGB());
			img.setRGB(curve.getCurve().get(x).getX(), curve.getCurve().get(x).getY(), color.getRGB());
		}
		
	    try {
	    	ImageIO.write(img, "png", new File("newHilbert.png"));
	    }catch(IOException e) {
	    	
	    }
	    */
	}
}

