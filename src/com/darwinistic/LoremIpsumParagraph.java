package com.darwinistic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class LoremIpsumParagraph {

    private static String loremipsum;
    private static String[] paragraphs;

    static {
        try {
            loremipsum = new String(Files.readAllBytes(Paths.get("loremipsum.txt")));
            paragraphs = loremipsum.split("\n\n");
        } catch (IOException ioe) {
            System.out.println("Unable to open input file for Lorem Ipsum data");
            System.exit(1);
        }

    }

    public static String getRandomParagraph(int len) {
        if (len == 0) {
            return paragraphs[new Random().nextInt(32)];
        } else {
            return paragraphs[new Random().nextInt(32)].substring(0, len);
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(new File(".").getAbsolutePath());
        System.out.println(getRandomParagraph(0));
    }

}
