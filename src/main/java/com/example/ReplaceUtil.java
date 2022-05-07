package com.example;

import java.io.*;
import java.util.*;

/**
 * @author Qing2514
 * @data 22-5-6
 */
public class ReplaceUtil {

    private static Set<String> sensitiveWordSet;
    private static String[] strWithoutEnter;
    private static String[][] strWithoutSpace;

    public static String replaceSensitiveWords(File sourceFile, File sensitiveWords) {
        String s = initSentence(sourceFile, sensitiveWords);
        return getTargetSentence(s);
    }

    protected static String initSentence(File sourceFile, File sensitiveWords) {
        sensitiveWordSet = getSensitiveWords(sensitiveWords);
        String sentence = getStringFromFile(sourceFile);
        // 将跳格转化为空格，统一处理，简化逻辑
        sentence = sentence.trim().replaceAll("\t", " ");
        // 将句子按行分割
        strWithoutEnter = sentence.split("\n");
        strWithoutSpace = new String[strWithoutEnter.length][];
        // 将按行分割后的句子继续按空格分割
        for(int i = 0; i < strWithoutEnter.length; i++) {
            strWithoutSpace[i] = strWithoutEnter[i].trim().split(" ");
        }
        // 将分割后的多个句子合并成一个句子
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < strWithoutEnter.length; i++) {
            String[] str = strWithoutSpace[i];
            for (String string : str) {
                text.append(string);
            }
        }
        return text.toString();
    }

    protected static String getTargetSentence(String s) {
        // 将句子中的敏感词汇转换为 *
        for (String word : sensitiveWordSet) {
            s = s.replaceAll(word, replaceWordWithSign(word));
        }
        StringBuilder sb = new StringBuilder(s);
        int index = 0;
        // 将处理敏感词汇后的句子还原成原来的格式，即恢复空格和换行字符
        for (int i = 0; i < strWithoutEnter.length; i++) {
            for (int j = 0; j < strWithoutSpace[i].length; j++) {
                index += strWithoutSpace[i][j].length();
                if(j + 1 < strWithoutSpace[i].length) {
                    sb.insert(index, " ");
                    index += 1;
                }
            }
            if(i + 1 < strWithoutEnter.length) {
                sb.insert(index, "\n");
                index += 1;
            }
        }
        return sb.toString();
    }

    public static String replaceWordWithSign(String word) {
        StringBuilder target = new StringBuilder("*");
        int num = word.length();
        for (int i = 1; i < num; i++) {
            target.append("*");
        }
        return target.toString();
    }

    public static Set<String> getSensitiveWords(File file) {
        Set<String> keyWordSet = new LinkedHashSet<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (line != null) {
                keyWordSet.add(line);
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keyWordSet;
    }

    public static String getStringFromFile(File file) {
        StringBuilder sentence = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (line != null) {
                sentence.append(line).append("\n");
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sentence.toString();
    }

}
