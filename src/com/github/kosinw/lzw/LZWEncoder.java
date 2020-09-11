package com.github.kosinw.lzw;

import java.io.*;
import java.util.*;

// 32-bit words

public class LZWEncoder {
    public static void encodeFile(String filename) {
        try {
            File file = new File(filename);
            BufferedReader reader = new BufferedReader(new FileReader(file));

            File encoded = new File(file.getAbsolutePath() + ".lzw");
            BufferedWriter writer = new BufferedWriter(new FileWriter(encoded));

            int current = 0;

            HashMap<String, Integer> dictionary = new HashMap<String, Integer>();

            String P = "";
            String encodedString = "";

            for (int i = 0; i < 128; ++i) {
                dictionary.put("" + (char)i, i);
            }

            int currentKey = 128;

            while ((current = reader.read()) != -1) {
                //  Is the string P + C present in the dictionary?
                // - If it is:    
                //       - P = concat(P,C);
                // - if Not:    
                //        - output the code word which denotes P to the codestream
                //        - add the string P+C) to the dictionary
                //        - P = C 

                String PC = P + (char)current;
                
                if (dictionary.containsKey(PC)) {
                    P = PC;
                } else {
                    encodedString += (dictionary.get(P))+ " ";
                    dictionary.put(PC, currentKey);
                    currentKey += 1;
                    P = "" + (char)current;
                }
            }
            
            writer.write(encodedString);
   
            reader.close();
            writer.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String filename = args[0];
        
        LZWEncoder.encodeFile(filename);
        //Outputs a set of space delimited integers corresponding to each character's location within the library
    }
}
