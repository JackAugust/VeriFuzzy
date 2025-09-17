package general_tools;

import javax.imageio.plugins.tiff.ExifInteroperabilityTagSet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KeywordExtractor {

    public static List<List<String>> extractKeywordsFromDirectory(String directoryPath) {
        List<List<String>> allKeywords = new ArrayList<>();

        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Invalid directory path.");
            return allKeywords;
        }

        File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));
        if (files == null || files.length == 0) {
            System.out.println("No .txt files found in the directory.");
            return allKeywords;
        }

        for (File file : files) {
            List<String> keywords = extractKeywordsFromFile(file.getPath());
            if (!keywords.isEmpty()) {
                allKeywords.add(keywords);
            }
        }

        return allKeywords;
    }

    // 从文件中提取关键字的方法
    private static List<String> extractKeywordsFromFile(String filePath) {
        List<String> keywords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // 逐行读取文件
            while ((line = br.readLine()) != null) {
                // 去除行首行尾的空白字符
                line = line.trim();
                if (!line.isEmpty()) {
                    // 使用正则表达式分割行内的单词
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        // 去除单词前后的空白字符
                        word = word.trim();
                        if (!word.isEmpty()) {
                            keywords.add(word);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }
        return keywords;
    }

    public static void main(String[] args) {
        String directoryPath = "E:\\keywords"; // 替换为实际的目录路径
       // List<String> allKeywords = extractKeywordsFromFile(directoryPath);
        List<List<String>> allKeywords = extractKeywordsFromDirectory(directoryPath);
        System.out.println(allKeywords);
        //看不重复的关键字数量
        List<String> keysss = new ArrayList<>();
        for (List<String> zz:allKeywords){
            keysss.addAll(zz);
        }
        System.out.println("去重前:"+keysss.size());

        Set<String> set1 = new HashSet<>(keysss);  // 将 List 转换为 Set，自动去重

        List<String> newList = new ArrayList<>(set1);    // 将 Set 转换回 List
        System.out.println("去重后的 List(newList1)：" + newList.size());
//        System.out.println(newList);
//        // 打印结果以验证
//        for (int i = 0; i < 10; i++) {
//            System.out.println("Keywords from file " + (i + 1) + ": " + allKeywords.get(i));
//        }
    }
}