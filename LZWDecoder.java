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

			ArrayList<String> dictionary = new ArrayList<String>();

			for (int i = 0; i < 256; i++) {
	        	dictionary.add("" + (char)i);
	        }

	        String currentLine = bReader.readLine();
	        String nextLine = bReader.readLine();

	        while (nextLine!=null){
	        	dictionary.add(currentLine);
	        	currentLine = nextLine;
	        	nextLine = bReader.readLine();
	        }

	        ArrayList<Integer> output = new ArrayList<Integer>();

	        while (currentLine.length()>1){
	        	output.add(Integer.parseInt(currentLine.substring(0,currentLine.indexOf(" "))));
	        	currentLine = currentLine.substring (currentLine.indexOf(" ")+1);
	        }

	        for (int k = 0; k < output.size(); k++){
	        	int intValue = output.get(k);
	        	bWriter.write (""+dictionary.get(intValue));
	        }

	        bReader.close();
	        bWriter.close();

	    }catch (Exception exe){
	    	exe.printStackTrace();
	    }
	}
	public static void main (String [] args){
		String filename = "lzw-file1.txt.lzw";
		LZWDecoder.encodeFile(filename);
	}
}