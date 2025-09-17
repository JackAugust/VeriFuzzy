package index3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class KeywordExtractor {

    public static String[] extractKeywords(String filePath, int numKeywords) throws IOException {
        // 读取文件内容到字符串
        String content = readFile(filePath);

        // 使用HashMap来统计词频
        Map<String, Integer> wordCount = new HashMap<>();

        // 分割字符串为单词，并统计词频
        String[] words = content.split("\\s+"); // 使用空格分割
        for (String word : words) {
            word = word.toLowerCase().replaceAll("[^a-zA-Z0-9]", ""); // 转换为小写并去除非字母数字字符
            if (!word.isEmpty()) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }

        // 使用PriorityQueue来根据词频排序
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>((a, b) -> b.getValue().compareTo(a.getValue()));
        pq.addAll(wordCount.entrySet());

        // 抽取前numKeywords个关键字
        String[] keywords = new String[numKeywords];
        for (int i = 0; i < numKeywords && !pq.isEmpty(); i++) {
            Map.Entry<String, Integer> entry = pq.poll();
            keywords[i] = entry.getKey();
        }

        return keywords;
    }

    private static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    public static void main(String[] args) {
        try {
            String filePath = "C:\\Users\\Dell\\Desktop\\test_data\\word (1).txt"; // 替换为你的文件路径
            int numKeywords = 5; // 你想抽取的关键字数量
            String[] keywords = extractKeywords(filePath, numKeywords);
            for (String keyword : keywords) {
                System.out.println(keyword);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}