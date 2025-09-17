package test_02;

import general_tools.LSH;
import index3.DataObject;
import index3.EVBTree;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static general_tools.KeywordExtractor.extractKeywordsFromDirectory;

public class Test_verify {
    public static String readFileToString(String filePath) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(filePath));
            return new String(encoded, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args){
        //LSH生成
        // 设置LSH的参数
        double w = 10.0; // 分段长度
        int d = 160; // 输入向量的维数
        int kkk = 7; // 哈希函数的个数
        LSH lsh = new LSH(w,d,kkk);

        //准备密文数据enc_data、索引关键字集keywords
        List<String> enc_data = new ArrayList<>();
        String directoryPath = "C:\\Users\\Dell\\Desktop\\keywords_extraction_rake-master\\example\\output"; // 替换为实际的目录路径
        List<List<String>> keywords = extractKeywordsFromDirectory(directoryPath);
        for (int i=1;i<=500;i++) {
//            System.out.println(keys);
            enc_data.add(readFileToString("C:\\Users\\Dell\\Desktop\\test_data\\word (" + i + ").txt"));

        }


    /*
        500     25
        1000    50
        1500    75
        2000    100
        2500    125

     */
        //构建EVBTree(n=100，n-200)

        EVBTree evbTree = new EVBTree(1000,lsh);

        //
        List<List<String>> keywordss = new ArrayList<>();
        int k=200;
        for (int i=0;i<k;i++){
            keywordss.add(keywords.get(i));
        }
        System.out.println("keywordss.size()：========"+keywordss.size());

        evbTree.IndexGen(keywordss,enc_data);

        List<String> q2 = new ArrayList<>();
        q2.add(keywords.get(1).get(1));
        q2.add(keywords.get(1).get(2));
        q2.add(keywords.get(1).get(3));
        q2.add(keywords.get(1).get(4));
        List<String> trap2 = evbTree.TrapGen(q2);

        List<String> results2 = new ArrayList<>();
        List<DataObject> AP2 = new ArrayList<>();
        List<BigInteger> D2 = new ArrayList<>();


        evbTree.search(trap2,"",results2,AP2,D2);
        //evbTree.Verify(results2,AP2,D2,enc_data);
        System.out.println("AP2。size()："+AP2.size());
        long start2 = System.currentTimeMillis();
        if(evbTree.Verify(results2,AP2,D2,enc_data)){
            System.out.println("results2.size()："+results2.size()+","+results2);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("q2的verify时间："+(end2-start2)+"ms");

    }
}
