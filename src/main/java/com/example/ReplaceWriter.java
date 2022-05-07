package com.example;

import java.io.*;

/**
 * @author Qing2514
 * @data 22-5-6
 */
public class ReplaceWriter extends OutputStreamWriter {

    public ReplaceWriter(File file) throws IOException {
        super(new FileOutputStream(file));
    }

    public void write(File sourceFile, File sensitiveWords) throws IOException {
        String str = ReplaceUtil.replaceSensitiveWords(sourceFile, sensitiveWords);
        super.write(str);
    }

    @Override
    public void flush() throws IOException {
        super.flush();
    }

    @Override
    public void close() throws IOException {
        super.close();
    }

}
