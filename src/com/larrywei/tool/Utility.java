package com.larrywei.tool;

/**
 * @description Utility class. ASCII <-->UTF-8
 * @author Larry.Wei(weilaiman@gmail.com)
 * @createTime 18/08/2016
 * */
public class Utility {
    private static char ascii2Char(int ASCII) {  
        return (char) ASCII;  
    }
    
    private static int char2ASCII(char c) {  
        return (int) c;  
    }
    
    private static String getIntArrayString(int[] intArray) {  
        return getIntArrayString(intArray, ",");  
    }
    
    private static String getIntArrayString(int[] intArray, String delimiter) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < intArray.length; i++) {  
            sb.append(intArray[i]).append(delimiter);  
        }  
        return sb.toString();  
    }
    
    private static int[] string2ASCII(String s) {
        if (s == null || "".equals(s)) {  
            return null;  
        }  
  
        char[] chars = s.toCharArray();  
        int[] asciiArray = new int[chars.length];  
  
        for (int i = 0; i < chars.length; i++) {  
            asciiArray[i] = char2ASCII(chars[i]);  
        }  
        return asciiArray;  
    }
    
	public static String normalString2ASCIIString(String s) {
		int[] arr = string2ASCII(s);
		return getIntArrayString(arr);
	}
	
    public static String ascii2String(String ASCIIs) {  
        String[] ASCIIss = ASCIIs.split(",");  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < ASCIIss.length; i++) {  
            sb.append((char) ascii2Char(Integer.parseInt(ASCIIss[i])));  
        }  
        return sb.toString();  
    }
}
