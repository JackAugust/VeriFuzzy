package general_tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KeywordExtraction {
    public static void main(String[] args) {
        List<String> keywords = extractKeywords("C:\\Users\\Dell\\Desktop\\test_data\\word (2).txt");

        System.out.println("提取到的关键字集合："+keywords.size());
        for (String keyword : keywords) {
            System.out.println(keyword);
        }
    }

    public static List<String> extractKeywords(String str) {
        Set<String> keywords = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(str))) {
            String line;
            int num=0;
            while ((line = reader.readLine()) != null) {
                num++;
                if (num>5){
                    // 去除空格和特殊字符，并转换为小写
                    line = line.replaceAll("[^a-zA-Z ]", "").toLowerCase();
                    String[] words = line.split("\\s+");

                    for (String word : words) {
                        // 排除常见的无意义词汇
                        if (!word.equals("") && !word.equals("and") && !word.equals("is")
                                && !word.equals("of") && !word.equals("in") && !word.equals("for")) {
                            if (word.length() >= 5 &&word.length() <= 9) { // 添加对关键字长度的限制
                                keywords.add(word);
                            }

                        }
                    }

                    if (keywords.size()>=45){
                        break;
                    }

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> keywordList = new ArrayList<>(keywords);
        return keywordList;
    }

}