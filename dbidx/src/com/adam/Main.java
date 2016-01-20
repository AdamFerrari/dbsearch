package com.adam;

import java.util.HashMap;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Arrays;

public class Main {
    private static int recId = 0;
    private static int kwId = 0;
    private static int iiId = 0;
    private static HashMap<String,Integer> kwToId;
    private static PrintWriter recOut;
    private static PrintWriter kwOut;
    private static PrintWriter iiOut;


    public static void main(String[] args) {
        kwToId = new HashMap<String,Integer>();
        if(args.length != 2) {
            usage();
            System.exit(1);
        }
        try {
            recOut = new PrintWriter(new BufferedWriter(new FileWriter(args[1] + ".rec.csv")));
            iiOut = new PrintWriter(new BufferedWriter(new FileWriter(args[1] + ".ii.csv")));
            kwOut = new PrintWriter(new BufferedWriter(new FileWriter(args[1] + ".kw.csv")));
            BufferedReader f = new BufferedReader(new FileReader(args[0]));
            int i = 0;
            String l;
            while((l = f.readLine()) != null) {
                indexLine(l);
                i++;
            }
            recOut.close();
            iiOut.close();
            kwOut.close();
        }
        catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void indexLine(String line) {
        String keywords[] = line.toLowerCase().split("\\W+");
        Arrays.sort(keywords);
        if(keywords.length < 1) return;
        recId ++;
        indexRec(line);
        String curr = null;
        int count = 0;
        for(String kw : keywords) {
            if(kw.equals(curr)) {
                count++;
            }
            else {
                if(curr!=null) {
                    indexKw(curr,count);
                }
                curr = kw;
                count = 1;
            }
        }
        if(curr!=null) {
            indexKw(curr, count);
        }
    }

    private static void indexKw(String kw, int count) {
        Integer id = getKwId(kw);
        iiId++;
        iiOut.println("" + iiId + "," + id.toString() + "," + recId + "," + count );
        // System.out.println("Record[" + recId + "]  Keyword[" + id + "] " + kw + " (" + count + ")");
    }

    private static Integer getKwId(String kw) {
        Integer id = kwToId.get(kw);
        if(id == null) {
            kwId++;
            id = new Integer(kwId);
            kwToId.put(kw,id);
            kwOut.println(kw + "," + kwId);
        }
        return id;
    }

    private static void indexRec(String rec) {
        String words[] = rec.split("\\W+");
        recOut.print("" + recId + ",");
        for(int i=0;i<words.length;i++) {
            if(i>0) recOut.print(" ");
            recOut.print(words[i]);
        }
        recOut.println("," + rec.length());
    }

    private static void usage() {
        System.err.println("usage: dbidx <input file> <output prefix>");
    }

}
