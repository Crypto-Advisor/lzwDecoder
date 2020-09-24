import java.util.*;
import java.io.*;

public class LZWDecoder{
	public static void decodeFile (String filename){ //File name of encoded file as parameter
		try{ //Tests code for errors while it's being executed
			File inputFile = new File (filename);
			FileReader fReader = new FileReader (inputFile);
			BufferedReader bReader = new BufferedReader (fReader);

			File outputFile = new File (filename.substring(0,filename.length()-4)); //Creates output file with encoded file name minus ".lzw"
			FileWriter fWriter = new FileWriter (outputFile); //Sets up file writer to edit new file
			BufferedWriter bWriter = new BufferedWriter (fWriter);
			
	        ArrayList<Integer> encoderOutputList = new ArrayList<Integer>();
	       // String currentLine = bReader.readLine();

	    //    while (currentLine.length()>1){ //Converts the String of codes into an Integer ArrayList
	    //    	output.add(Integer.parseInt(currentLine.substring(0,currentLine.indexOf(" "))));
	     //   	currentLine = currentLine.substring (currentLine.indexOf(" ")+1);
	       // }
	        
	        while(bReader.ready()){//read through the codestream, adding each code to encoderOutputList ArrayList
	        	char currentCodestreamChar = (char)bReader.read();
	        	String currentCodeString = "";
	        	while(currentCodestreamChar!=" "){
	        		currentCodeString += currentCodestreamChar;
	        		if(br.ready()){
	        			currentCodestreamChar = (char)bReader.read();
	        		}
	        		else{
	        			break;
	        		}
	        	}
	        	encoderOutputList.add(Integer.parseInt(currentCodeString));
	        }

        	int dictionarySize = 256; // Number of ASCII symbols 0-255

       		HashMap<Integer,String> dictionary = new HashMap<Integer,String>(); //Dictionary of ASCII symbols and any symbols added in the code
       		
        	for (int i = 0; i < 256; i++){ //Adding ASCII symbols to dictionary hashap
            	dictionary.put(i, "" + (char)i);
        	}
 
        	String currentString = "" + (char)(int)encoderOutputList.remove(0);
        	StringBuffer resultString = new StringBuffer(currentString); //The String we add to and eventually submit

        	for (int k : encoderOutputList){
            	String entry;
            	if (dictionary.containsKey(k)){ //Checking to see if the current code is already in the dictionary
                	entry = dictionary.get(k);
            	}else if (k == dictionarySize){ //Checking to see if the current code is outside the dictionary
                	entry = currentString + currentString.charAt(0);
            	}else{
                	throw new IllegalArgumentException("Bad compressed k: " + k);
            	}
 
            	bWriter.write(entry);
 
            	dictionary.put(dictionarySize++, currentString + entry.charAt(0));//Adds the next entry to the dictionary
 
            	currentString = entry;
        	}

			bWriter.close(); //Close readers and writers to release system resources
			bReader.close();    	

	    }catch (Exception exe){ //Executes exception if an error is found in the try block
	    	exe.printStackTrace();
	    }
	}
	public static void main (String [] args){
		String filename = "lzw-text0.txt.lzw"; //Sets filename variable
		LZWDecoder.decodeFile(filename); //Runs decode function
	}
}
