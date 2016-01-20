package com.adam;

import java.util.HashMap;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Arrays;

public class Main {
    private static int recordId = 0;
    private static int keywordId = 0;
    private static int iiId = 0;
    private static HashMap<String,Integer> kwToId;

    public static void main(String[] args) {
        kwToId = new HashMap<String,Integer>();
        if(args.length != 1) {
            usage();
            System.exit(1);
        }
        try {
            BufferedReader f = new BufferedReader(new FileReader(args[0]));
            int i = 0;
            String l;
            while((l = f.readLine()) != null) {
                indexLine(l);
                i++;
            }
        }
        catch (Exception e) {
            System.err.println(e.toString());
            System.exit(1);
        }
    }

    private static void indexLine(String line) {
        String keywords[] = line.toLowerCase().split("\\W+");
        Arrays.sort(keywords);
        if(keywords.length < 1) return;
        recordId ++;
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
        int kwId = keywordId(kw);
        System.out.println("Record[" + recordId + "]  Keyword[" + kwId + "] " + kw + " (" + count + ")");
    }

    private static int keywordId(String kw) {
        Integer kwId = kwToId.get(kw);
        if(kwId == null) {
            keywordId++;
            kwId = new Integer(keywordId);
            kwToId.put(kw,kwId);
        }
        return kwId.intValue();
    }

    private static void usage() {
        System.err.println("usage: dbidx <input file>");
    }

}
