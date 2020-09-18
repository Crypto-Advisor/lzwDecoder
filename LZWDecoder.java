import java.util.*;
import java.io.*;

public class LZWDecoder{
	public static void encodeFile (String filename){
		try{
			File inputFile = new File (filename);
			FileReader fReader = new FileReader (inputFile);
			BufferedReader bReader = new BufferedReader (fReader);

			File outputFile = new File (filename.substring(0,filename.length()-4));
			FileWriter fWriter = new FileWriter (outputFile);
			BufferedWriter bWriter = new BufferedWriter (fWriter);
			
	        ArrayList<Integer> output = new ArrayList<Integer>();
	        String currentLine = bReader.readLine();
	        while (currentLine.length()>1){
	        	output.add(Integer.parseInt(currentLine.substring(0,currentLine.indexOf(" "))));
	        	currentLine = currentLine.substring (currentLine.indexOf(" ")+1);
	        }
        	int dictSize = 256;
       		HashMap<Integer,String> dictionary = new HashMap<Integer,String>();
        	for (int i = 0; i < 256; i++){
            	dictionary.put(i, "" + (char)i);
        	}
 
        	String w = "" + (char)(int)output.remove(0);
        	StringBuffer result = new StringBuffer(w);
        	for (int k : output) {
            	String entry;
            	if (dictionary.containsKey(k))
                	entry = dictionary.get(k);
            	else if (k == dictSize)
                	entry = w + w.charAt(0);
            	else
                	throw new IllegalArgumentException("Bad compressed k: " + k);
 
            	result.append(entry);
 
            	dictionary.put(dictSize++, w + entry.charAt(0));
 
            	w = entry;
        	}
			bWriter.write(result.toString());
			bWriter.close();
			bReader.close();    	

	    }catch (Exception exe){
	    	exe.printStackTrace();
	    }
	}
	public static void main (String [] args){
		String filename = "smol-movie.txt.lzw";
		LZWDecoder.encodeFile(filename);
	}
}