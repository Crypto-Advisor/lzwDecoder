//package com.github.kosinw.lzw;

import java.io.*;
import java.util.*;

// 32-bit words

public class LZWEncoder {
    public static void encodeFile(String filename) {
        try {
            File originalFile = new File(filename);
            BufferedReader reader = new BufferedReader(new FileReader(originalFile));

            File encoded = new File(originalFile.getAbsolutePath() + ".lzw");
            BufferedWriter writer = new BufferedWriter(new FileWriter(encoded));

            int current = 0;

            HashMap<String, Integer> dictionary = new HashMap<String, Integer>();

            String P = "";
            String encodedString = "";

            int currentKey = 0x100;

            for (int i = 0; i < currentKey; ++i) {
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
                
                if (dictionary.containsKey(PC)) {
                    P = PC;
                } else if (currentKey >= max) {
                    encodedString += dictionary.get(P) + " ";
                    P = "" + (char)current;
                } else {
                    encodedString += dictionary.get(P) + " ";
                    dictionary.put(PC, currentKey);
                    currentKey += 1;
                    P = "" + (char)current;
                }
            }

            if (!P.equals("")) {
                if (dictionary.containsKey(P)) {
                    encodedString += dictionary.get(P) + " ";
                } else {
                    encodedString += P;
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
        String filename = "lzw-text0.txt";
        LZWEncoder.encodeFile(filename);
        //Outputs a set of space delimited integers corresponding to each character's location within the library
    }
}
