package sorter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class ImgSorter {
	String fileName;
	int sideLength;	//must be such that sideLength = 2^x (2, 4, 8, 16...)
	int hilbertIterations;	//must be such that 2^iterations = sideLength
	String outputName;
	static int sortMethod; //0 for hue, 1 for saturation, 2 for whatever v stands for
	
	ImgSorter(String name, int side, int iterations, int method, String output){
		fileName = name;
		sideLength = side;
		hilbertIterations = iterations;
		outputName = output;
		sortMethod = method;
	}
	
	public static void mergeSort(int[] a, int n) {
	    if (n < 2) {
	        return;
	    }
	    int mid = n / 2;
	    int[] l = new int[mid];
	    int[] r = new int[n - mid];

	    for (int i = 0; i < mid; i++) {
	        l[i] = a[i];
	    }
	    for (int i = mid; i < n; i++) {
	        r[i - mid] = a[i];
	    }
	    mergeSort(l, mid);
	    mergeSort(r, n - mid);

	    merge(a, l, r, mid, n - mid);
	}
	
	public static void merge(
		int[] a, int[] l, int[] r, int left, int right) {
			 
		int i = 0, j = 0, k = 0;
			while (i < left && j < right) {
				if (getVariable(l[i]) <= getVariable(r[j])) {
					a[k++] = l[i++];
			    } else {
			    	a[k++] = r[j++];
			    }
			}
			while (i < left) {
			    a[k++] = l[i++];
			}
			while (j < right) {
			    a[k++] = r[j++];
			}
	}
	
	/*
	public static double getHue(int rgb) {
		Color color = new Color(rgb);
		double R = color.getRed() / 255.0;
		double G = color.getGreen() / 255.0;
		double B = color.getBlue() / 255.0;
		
		double min = Math.min(Math.min(R, G), B);
		double max = Math.max(Math.max(R, G), B);
		
		double hue;
		if (max == R) {
		    hue = (G - B) / (max - min);

		} else if (max == G) {
		    hue = 2f + (B - R) / (max - min);

		} else {
			hue = 4f + (R - G) / (max - min);
		}
		
		hue = hue * 60;
		
	    if (hue < 0) hue = hue + 360;
		return(hue);
	}
	*/
	
	public static double getVariable(int rgb) {
		return(RGBtoHSV(rgb)[sortMethod]);
	}
	
	public static double[] RGBtoHSV(int rgb) {
		// I stole this from www.java2s.com xd
		Color color = new Color(rgb);
		double r = color.getRed();
		double g = color.getGreen();
		double b = color.getBlue();
		
		
        double h, s, v;

        double min, max, delta;

        min = Math.min(Math.min(r, g), b);
        max = Math.max(Math.max(r, g), b);

        // V
        v = max;

        delta = max - min;
        
        // S
        if (max != 0 && delta != 0)
            s = delta / max;
        else {
            s = 0;
            h = 0;
            return new double[] { h, s, v };
        }

        // H
        if (r == max)
            h = (g - b) / delta; // between yellow & magenta
        else if (g == max)
            h = 2 + (b - r) / delta; // between cyan & yellow
        else
            h = 4 + (r - g) / delta; // between magenta & cyan

        h *= 60; // degrees
        
        if (h < 0) {
            h += 360;
        }
        
        h = h * 1.0;
        s = s * 100.0;
        v = (v / 256.0) * 100.0;
        return new double[] { h, s, v };
    }
	
	public void export() throws IOException {
		System.out.println("Loading img");
		BufferedImage img = ImageIO.read(new File(fileName));
		
		System.out.println("preparing list if pixels");
		int[] pixels = new int[sideLength*sideLength];
		
		System.out.println("importing from img");
		for(int x = 0; x < sideLength; x++) {
			for(int y = 0; y < sideLength; y++) {
				//System.out.println((y + (x*512)) + " ");
				pixels[x + (y*sideLength)] = img.getRGB(x, y);
			}
		}
		
		System.out.println("sorting pixels");
		mergeSort(pixels, sideLength*sideLength);
		//Arrays.sort(pixels);
		
		System.out.println("iterating curve");
		HilbertCurve curve = new HilbertCurve(hilbertIterations);
		//System.out.println(curve.getSize());
		
		System.out.println("exporting to curve");
		for(int x = 0; x < sideLength*sideLength; x++) {
			curve.getCurve().get(x).setValue(pixels[x]);
		}
		
		System.out.println("drawing");
		for(int x = 0; x < sideLength*sideLength; x++) {
			img.setRGB(curve.getCurve().get(x).getX(), curve.getCurve().get(x).getY(), (int)curve.getCurve().get(x).getValue());
		}
		
		System.out.println("saving to file");
		try {
	    	ImageIO.write(img, "png", new File(outputName));
	    }catch(IOException e) {
	    	
	    }
	}
	
	public static void main(String[] args) throws IOException {
		
		ImgSorter sorter = new ImgSorter("lug.png", 512, 9, 0, "sorted.png");
		
		sorter.export();
		
		
	}
}
