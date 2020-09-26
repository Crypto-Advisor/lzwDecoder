//package com.github.kosinw.lzw;

import java.io.*;
import java.util.*;

// 32-bit words

public class LZWEncoder {
    public static void encodeFile(String filename) {//file name of encoded file as parameter
        try {//tests code for errors while is being executed
            File originalFile = new File(filename);
            BufferedReader reader = new BufferedReader(new FileReader(originalFile));

            File encoded = new File(originalFile.getAbsolutePath() + ".lzw");//create .lzw output file
            BufferedWriter writer = new BufferedWriter(new FileWriter(encoded));//sets up file writer to edit new file

            int currentCharValue = 0;

            HashMap<String, Integer> dictionary = new HashMap<String, Integer>();//dictionary for encoding

            String previousCharacters = "";

            int currentDictionaryPosition = 0x100;//starting at 256

            for (int i = 0; i < currentDictionaryPosition; ++i) {//fills in first 255 entries in dictionary
                dictionary.put("" + (char)i, i);
            }

            final int MAX_DICT_ENTRIES = 0x10000;//0x1000=65536

            while ((currentCharValue = reader.read()) != -1) {
                //  Is the string P + C present in the dictionary?
                // - If it is:    
                //       - P = concat(P,C);
                // - if Not:    
                //        - output the code word which denotes P to the codestream
                //        - add the string P+C) to the dictionary
                //        - P = C

                String prevAndCurrent = previousCharacters + (char)currentCharValue;
                
                if (dictionary.containsKey(prevAndCurrent)) {//if P+C is in the dictionary
                    previousCharacters = prevAndCurrent;
                } else if (currentDictionaryPosition >= MAX_DICT_ENTRIES) {//if P+C is not in the dictionary and the max has already been reached (doesn't add to dictionary anymore)
                    writer.write(dictionary.get(previousCharacters) + " ");
                    previousCharacters = "" + (char)currentCharValue;
                } else {//if P+C is not in the dictionary and the max has not been reached yet (meaning you can still add to dictionary)
                    writer.write(dictionary.get(previousCharacters) + " ");
                    dictionary.put(prevAndCurrent, currentDictionaryPosition);
                    currentDictionaryPosition += 1;
                    previousCharacters = "" + (char)currentCharValue;
                }
            }

            if (!previousCharacters.equals("")) {//if, at the end of the while loop, you still have a previous group of characters that needs to be added to the codestream
                if (dictionary.containsKey(previousCharacters)) {
                    writer.write(dictionary.get(previousCharacters) + " ");
                } else {
                    writer.write(previousCharacters);
                }

            }

            reader.close();
            writer.close();


        } catch (IOException ex) {//catches errors
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final String FILE_NAME = "lzw-text0.txt";//variable type should be final if it is never changed
        LZWEncoder.encodeFile(FILE_NAME);
        //Outputs a set of space delimited integers corresponding to each character's location within the library
    }
}
