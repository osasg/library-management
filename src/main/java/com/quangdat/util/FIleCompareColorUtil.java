package com.quangdat.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.quangdat.entities.Book;

public class FIleCompareColorUtil {

    public static Boolean compare(File fileImage, int r, int g, int b) {
        File file = fileImage;
        ImageInputStream is;
		try {
			is = ImageIO.createImageInputStream(file);
	        Iterator iter = ImageIO.getImageReaders(is);
	
	        if (!iter.hasNext())
	        {
	            System.out.println("Cannot load the specified file "+ file);
	            System.exit(1);
	        }
	        ImageReader imageReader = (ImageReader)iter.next();
	        imageReader.setInput(is);
	
	        BufferedImage image = imageReader.read(0);
	
	        int height = image.getHeight();
	        int width = image.getWidth();
	
	        Map m = new HashMap();
	        for(int i=0; i < width ; i++)
	        {
	            for(int j=0; j < height ; j++)
	            {
	                int rgb = image.getRGB(i, j);
	                int[] rgbArr = getRGBArr(rgb);    
	//                System.out.println(rgbArr[0]);
	//                System.out.println(rgbArr[1]);
	//                System.out.println(rgbArr[2]);
	                // Filter out grays....                
	//                if (!isGray(rgbArr)) {                
	                        Integer counter = (Integer) m.get(rgb);   
	                        if (counter == null)
	                            counter = 0;
	                        counter++;                                
	                        m.put(rgb, counter);                
	//                }                
	            }
	        }        
	        
	        List<int[]> topThreeColor = getMostCommonColour(m);
	
	        for(int[] colourHex:topThreeColor) {
	        	double result = Math.sqrt(Math.pow(2, r - colourHex[0]) + Math.pow(2, g - colourHex[1]) + Math.pow(2, b - colourHex[2]));
	            if(result < 20) {
	            	System.out.println(result);
	            	return Boolean.TRUE;
	            }
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Boolean.FALSE;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
	public static List<int[]> getMostCommonColour(Map map) {
		List<int[]> topThreeColor = new ArrayList<>();
    	System.out.println(map.size());
		List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                  .compareTo(((Map.Entry) (o2)).getValue());
              }
        });    
        System.out.println(list.size());
        for(int i = 0; i < 2; i++) {
        	Map.Entry me = (Map.Entry )list.get(list.size() - i - 1);
            int[] rgb= getRGBArr((Integer)me.getKey());
            topThreeColor.add(rgb);
        }
        return topThreeColor;        
    }    

    public static int[] getRGBArr(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        return new int[]{red,green,blue};

  }
    public static boolean isGray(int[] rgbArr) {
        int rgDiff = rgbArr[0] - rgbArr[1];
        int rbDiff = rgbArr[0] - rgbArr[2];
        // Filter out black, white and grays...... (tolerance within 10 pixels)
        int tolerance = 10;
        if (rgDiff > tolerance || rgDiff < -tolerance) 
            if (rbDiff > tolerance || rbDiff < -tolerance) { 
                return false;
            }                 
        return true;
    }
}