package test_02;

import general_tools.LSH;
import index3.EVBTree;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static general_tools.KeywordExtractor.extractKeywordsFromDirectory;

public class Test_tarp {
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
        for (int i=1;i<=100;i++) {
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

        EVBTree evbTree_1 = new EVBTree(1500,lsh,200);
        EVBTree evbTree_2 = new EVBTree(1500,lsh,400);


//        long start50 = System.currentTimeMillis();
//        for(int i=0;i<25;i++){
//            evbTree_1.Update(keywords.get(i),i);
//
//        }
//        long end50 = System.currentTimeMillis();
//
//        System.out.println("测试：：：：：：："+(end50-start50)+"ms");

//        long start02 = System.currentTimeMillis();
//        for(int i=0;i<25;i++){
//            evbTree_2.Update(keywords.get(i),i);
//        }
//        long end02 = System.currentTimeMillis();
//
//        System.out.println("测试：：：：：：："+(end02-start02)+"ms");

//        //25个文档，keywords=500
//        System.out.println("================================25个文档，keywords=500=============================");
//        long start51 = System.currentTimeMillis();
//        for(int i=0;i<25;i++){
//            evbTree_1.Update(keywords.get(i),i);
//        }
//        long end51 = System.currentTimeMillis();
//        System.out.println("25个文档，keywords=500的evbtree_1的时间："+(end51-start51)+"ms");
//
//        long start52 = System.currentTimeMillis();
//        for(int i=0;i<25;i++){
//            evbTree_2.Update(keywords.get(i),i);
//        }
//        long end52 = System.currentTimeMillis();
//        System.out.println("25个文档，keywords=500的evbtree_2的时间："+(end52-start52)+"ms");
//        //50个文档，keywords=1000
//        System.out.println("================================50个文档，keywords=1000=============================");
//        long start101 = System.currentTimeMillis();
//        for(int i=0;i<50;i++){
//            evbTree_1.Update(keywords.get(i),i);
//        }
//        long end101 = System.currentTimeMillis();
//        System.out.println("keywords=1000的evbtree_1的时间："+(end101-start101)+"ms");
//
//        long start102 = System.currentTimeMillis();
//        for(int i=0;i<50;i++){
//            evbTree_2.Update(keywords.get(i),i);
//        }
//        long end102 = System.currentTimeMillis();
//        System.out.println("keywords=1000的evbtree_2的时间："+(end102-start102)+"ms");
//
//        System.out.println("================================75个文档，keywords=1500=============================");
//        long start151 = System.currentTimeMillis();
//        for(int i=0;i<75;i++){
//            evbTree_1.Update(keywords.get(i),i);
//        }
//        long end151 = System.currentTimeMillis();
//        System.out.println("keywords=1500的evbtree_1的时间："+(end151-start151)+"ms");
//
//        long start152 = System.currentTimeMillis();
//        for(int i=0;i<75;i++){
//            evbTree_2.Update(keywords.get(i),i);
//        }
//        long end152 = System.currentTimeMillis();
//        System.out.println("keywords=1500的evbtree_2的时间："+(end152-start152)+"ms");
//
//        System.out.println("================================100个文档，keywords=2000=============================");
//        long start201 = System.currentTimeMillis();
//        for(int i=0;i<100;i++){
//            evbTree_1.Update(keywords.get(i),i);
//        }
//        long end201 = System.currentTimeMillis();
//        System.out.println("keywords=2000的evbtree_1的时间："+(end201-start201)+"ms");
//
//        long start202 = System.currentTimeMillis();
//        for(int i=0;i<100;i++){
//            evbTree_2.Update(keywords.get(i),i);
//        }
//        long end202 = System.currentTimeMillis();
//        System.out.println("keywords=2000的evbtree_2的时间："+(end202-start202)+"ms");

//        System.out.println("================================125个文档，keywords=2500=============================");
//        long start251 = System.currentTimeMillis();
//        for(int i=0;i<125;i++){
//            evbTree_1.Update(keywords.get(i),i);
//        }
//        long end251 = System.currentTimeMillis();
//        System.out.println("keywords=1000的evbtree_1的时间："+(end251-start251)+"ms");

//        long start252 = System.currentTimeMillis();
//        for(int i=0;i<125;i++){
//            evbTree_2.Update(keywords.get(i),i);
//        }
//        long end252 = System.currentTimeMillis();
//        System.out.println("keywords=1000的evbtree_2的时间："+(end252-start252)+"ms");


        System.out.println(Integer.valueOf((int) Math.ceil(Math.log(100) / Math.log(2))));
        System.out.println(Integer.valueOf((int) Math.ceil(Math.log(200) / Math.log(2))));
        System.out.println(Integer.valueOf((int) Math.ceil(Math.log(300) / Math.log(2))));
        System.out.println(Integer.valueOf((int) Math.ceil(Math.log(400) / Math.log(2))));
        System.out.println(Integer.valueOf((int) Math.ceil(Math.log(500) / Math.log(2))));

    }

    public static String readFileToString(String filePath) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(filePath));
            return new String(encoded, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
