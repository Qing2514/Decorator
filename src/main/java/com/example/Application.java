package com.example;

import java.io.File;
import java.io.IOException;

/**
 * @author Qing2514
 * @data 22-5-6
 */
public class Application {

    static File sensitiveWords = new File("badWords.txt");
    static File sentences = new File("sentences.txt");
    static File result = new File("result.txt");

    public static void main(String[] args) {
        try {
            ReplaceWriter replaceWriter = new ReplaceWriter(result);
            replaceWriter.write(sentences, sensitiveWords);
            replaceWriter.flush();
            replaceWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
