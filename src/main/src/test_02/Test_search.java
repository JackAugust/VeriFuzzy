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

public class Test_search {
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
        String directoryPath = "E:\\keywords"; // 替换为实际的目录路径
        List<List<String>> keywords = extractKeywordsFromDirectory(directoryPath);
        for (int i=1;i<=3500;i++) {
//            System.out.println(keys);
            enc_data.add(readFileToString("E:\\data\\文档" + i + ".txt"));

        }

        System.out.println("keywords.size()："+keywords.size());
        System.out.println(keywords.get(2));
        System.out.println("enc_data.size()："+enc_data.size());
    /*
        500     25
        1000    50
        1500    75
        2000    100
        2500    125

     */
        //构建EVBTree(n=100，n-200)

        EVBTree evbTree = new EVBTree(2000,lsh);
/*
imessagelist
imessagepart
imailboxlist
 */

        List<List<String>> keywordss = new ArrayList<>();
        int k=3498;
        for (int i=0;i<k;i++){
            keywordss.add(keywords.get(i));
        }
        System.out.println("keywordss.size()：========"+keywordss.size());

        evbTree.IndexGen(keywordss,enc_data);
        System.out.println("EVBTree构建完成！！！");
//
//
//
//        keywords.get(2).add("imessagelist");
//        keywords.get(2).add("imessagepart");
//        keywords.get(2).add("imailboxlist");

        List<String> q0 = new ArrayList<>();
        q0.add(keywords.get(2).get(1));
        q0.add(keywords.get(2).get(4));
        q0.add(keywords.get(2).get(5));
//
//        q0.add(keywords.get(2).get(1));
//        q0.add(keywords.get(2).get(2));
//        q0.add(keywords.get(2).get(3));
//
//        q0.add(keywords.get(2).get(1));
//        q0.add(keywords.get(2).get(2));
//        q0.add(keywords.get(2).get(3));
//
//        q0.add(keywords.get(2).get(1));
//        q0.add(keywords.get(2).get(2));
//        q0.add(keywords.get(2).get(3));
//
//        q0.add(keywords.get(2).get(1));
//        q0.add(keywords.get(2).get(2));
//        q0.add(keywords.get(2).get(3));
//
//        q0.add(keywords.get(2).get(1));
//        q0.add(keywords.get(2).get(2));
//        q0.add(keywords.get(2).get(3));

        System.out.println("q0.size()："+q0);



        List<String> trap0 = evbTree.TrapGen(q0);

        List<String> results0 = new ArrayList<>();
        List<DataObject> AP0 = new ArrayList<>();
        List<BigInteger> D0 = new ArrayList<>();

        long start0 = System.currentTimeMillis();
        evbTree.search(trap0,"",results0,AP0,D0);
        long end0 = System.currentTimeMillis();
        System.out.println("q1的search时间："+(end0-start0)+"ms");
        System.out.println("结果是："+results0);
//
//
//        List<String> q1 = new ArrayList<>();
//        q1.add(keywords.get(1).get(1));
//        q1.add(keywords.get(1).get(2));
//        List<String> trap1 = evbTree.TrapGen(q1);
//
//        List<String> results1 = new ArrayList<>();
//        List<DataObject> AP1 = new ArrayList<>();
//        List<BigInteger> D1 = new ArrayList<>();
//
//        long start1 = System.currentTimeMillis();
//        evbTree.search(trap1,"",results1,AP1,D1);
//        long end1 = System.currentTimeMillis();
//        System.out.println("q1的search时间："+(end1-start1)+"ms");
//        System.out.println("结果是："+results1);
//
//
//
//
//
//
//        List<String> q2 = new ArrayList<>();
//        q2.add(keywords.get(1).get(1));
//        q2.add(keywords.get(1).get(2));
//        q2.add(keywords.get(1).get(3));
//        q2.add(keywords.get(1).get(4));
//        List<String> trap2 = evbTree.TrapGen(q2);
//
//        List<String> results2 = new ArrayList<>();
//        List<DataObject> AP2 = new ArrayList<>();
//        List<BigInteger> D2 = new ArrayList<>();
//
//        long start2 = System.currentTimeMillis();
//        evbTree.search(trap2,"",results2,AP2,D2);
//        long end2 = System.currentTimeMillis();
//        System.out.println("q2的search时间："+(end2-start2)+"ms");
//        System.out.println("结果是："+results2);
//
//
//        List<String> q3 = new ArrayList<>();
//        q3.add(keywords.get(1).get(1));
//        q3.add(keywords.get(1).get(2));
//        q3.add(keywords.get(1).get(3));
//        q3.add(keywords.get(1).get(4));
//        q3.add(keywords.get(1).get(5));
//        q3.add(keywords.get(1).get(6));
//        List<String> trap3 = evbTree.TrapGen(q3);
//        List<String> results3 = new ArrayList<>();
//        List<DataObject> AP3 = new ArrayList<>();
//        List<BigInteger> D3 = new ArrayList<>();
//
////        evbTree.search(trap3,"",results3,AP3,D3);
////        results3.clear();
//        long start3 = System.currentTimeMillis();
//        evbTree.search(trap3,"",results3,AP3,D3);
//        long end3 = System.currentTimeMillis();
//        System.out.println("q3的search时间："+(end3-start3)+"ms");
//
//        System.out.println("结果是："+results3);
    }
}
