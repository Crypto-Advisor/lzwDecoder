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

            int current = 0;//tracks the current value from reader

            HashMap<String, Integer> dictionary = new HashMap<String, Integer>();//dictionary for encoding

            String P = "";

            int currentKey = 0x100;//keeps track of current position in dictionary starting at 255

            for (int i = 0; i < currentKey; ++i) {//fills in first 255
                dictionary.put("" + (char)i, i);
            }

            int max = 0x10000;

            while ((current = reader.read()) != -1) {
                //  Is the string P + C present in the dictionary?
                // - If it is:    
                //       - P = concat(P,C);
                // - if Not:    
                //        - output the code word which denotes P to the codestream
                //        - add the string P+C) to the dictionary
                //        - P = C

                String PC = P + (char)current;
                
                if (dictionary.containsKey(PC)) {//if P+C is in the dictionary
                    P = PC;
                } else if (currentKey >= max) {//if P+C is not in the dictionary and the max has already been reached (doesn't add to dictionary anymore)
                    writer.write(dictionary.get(P) + " ");
                    P = "" + (char)current;
                } else {//if P+C is not in the dictionary and the max has not been reached yet (meaning you can still add to dictionary)
                    writer.write(dictionary.get(P) + " ");
                    dictionary.put(PC, currentKey);
                    currentKey += 1;
                    P = "" + (char)current;
                }
            }

            if (!P.equals("")) {//edge case after loop
                if (dictionary.containsKey(P)) {
                    writer.write(dictionary.get(P) + " ");
                } else {
                    write.write(P);
                }

            }

            reader.close();
            writer.close();


        } catch (IOException ex) {//catches errors
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String filename = "lzw-text0.txt";
        LZWEncoder.encodeFile(filename);
        //Outputs a set of space delimited integers corresponding to each character's location within the library
    }
}
