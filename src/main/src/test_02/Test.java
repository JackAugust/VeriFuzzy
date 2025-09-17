package test_02;

import general_tools.KeywordExtraction;
import general_tools.LSH;
import index1.Index1;
import index2.Index2;
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

//import sun.jvm.hotspot.gc.cms.LinearAllocBlock;

public class Test {
    public static void main(String[] args){
        //从1000个文档中，抽取关键字2000个不同的关键字

        List<List<String>> keywords = new ArrayList<>();
        List<String> enc_data = new ArrayList<>();
        List<String> update_keys = new ArrayList<>();

        String directoryPath = "C:\\Users\\Dell\\Desktop\\keywords_extraction_rake-master\\example\\output"; // 替换为实际的目录路径
        List<List<String>> allKeywords = extractKeywordsFromDirectory(directoryPath);

        for (int i=1;i<=100;i++) {
            List<String> keys = KeywordExtraction.extractKeywords("C:\\Users\\Dell\\Desktop\\test_data\\word (" + i + ").txt");
            keywords.add(keys);
//            System.out.println(keys);
            enc_data.add(readFileToString("C:\\Users\\Dell\\Desktop\\test_data\\word (" + i + ").txt"));

        }

//        for (List<String> keysssss:keywords){
//            for (String key_12:keysssss){
//                if (update_keys.size()<2500){
//                    update_keys.add(key_12);
//                }else {
//                    break;
//                }
//            }
//        }
//
//        System.out.println("更新关键字集update_keys.size(): "+update_keys.size());

//
//        for (int i=0;i<keywords.size();i++){
//            System.out.println(keywords.get(i));
//        }
//
//
//
//
//
//
//
//
//        keywords.get(5).add("binghuaxin");
//        keywords.get(5).add("xusanduo");
//        keywords.get(5).add("liuxiang");
//        keywords.get(5).add("questions");


        long indexGen1 = System.currentTimeMillis();
        //LSH生成
        // 设置LSH的参数
        double w = 10.0; // 分段长度
        int d = 160; // 输入向量的维数
        int kkk = 7; // 哈希函数的个数
        LSH lsh = new LSH(w,d,kkk);



        //构建index1


        Index1 index1 = new Index1(1000,500,lsh);
        index1.insert(keywords);

        long indexGen2 = System.currentTimeMillis();

        //构建Index2
        Index2 index2 = new Index2(1000);
        index2.IndexGen(keywords);

        long indexGen3 = System.currentTimeMillis();

        long start = System.currentTimeMillis();

        //构建EVBTree
        LSH lsh1 = new LSH(10.0,160,7);
        EVBTree evbTree = new EVBTree(1000,lsh);
        evbTree.IndexGen(keywords,enc_data);

        long indexGen4 = System.currentTimeMillis();

//
//        long  zz1 = System.currentTimeMillis();
//        index2.Update(update_keys,5);
//        long zz2 = System.currentTimeMillis();
//
//        evbTree.Update(update_keys,5);
//
//        long zz3 = System.currentTimeMillis();
//
//        System.out.println("index2_update: "+(zz2-zz1)+"ms");
//        System.out.println("evbtree_update: "+(zz3-zz2)+"ms");




//
//        System.out.println("indexGen1 time: "+(indexGen2-indexGen1)+"ms");
//        System.out.println("indexGen2 time: "+(indexGen3-indexGen2)+"ms");
//        System.out.println("indexGen3 time: "+(indexGen4-start)+"ms");
        //
        System.out.println("=========================搜索时间==================================");

 /*
        direct, program, herbert, geology, issues, vehicle, division, principal, sciences, number, bozeman, total, current, award, interest, instr, introduct, montana, course, david, state, human, class, levitan,
         */
        List<String> query0 = new ArrayList<>();

        List<String> query = new ArrayList<>();

        List<String> query3 = new ArrayList<>();
//        query.add("binghuaxin");
//        query.add("xusanduo");
//        query.add("liuxiang");
//        query.add("questions");

        query0.add("bozeman");
        query0.add("herbert");


        query.add("direct");
        query.add("herbert");
        query.add("geology");
//        query.add("division");
        query0.add("bozeman");

        query3.add("direct");
        query3.add("herbert");
        query3.add("geology");
        query3.add("division");
        query3.add("principal");
        query3.add("bozeman");



//        query.add("september");
//        query.add("observed");
//        query.add("sustained");
//        query.add("february");
//        query.add("physical");
//        query.add("transport");

//
//        long a1 = System.nanoTime();
////        List<int[]> trap1 = index1.TrapGen(query);
//        BloomFilter trap1 = index1.TrapGen(query);
//        long a2 = System.nanoTime();
//
//        List<String> trap2 = index2.TrapGen(query);
//
//        long a3 = System.nanoTime();
        List<String> trap0 = evbTree.TrapGen(query0);
        List<String> trap3 = evbTree.TrapGen(query);
        List<String> trap_3 = evbTree.TrapGen(query3);

//
//        long a4 = System.nanoTime();
//
////        System.out.println("trap1 time: "+(a2-a1)+"ms");
////        System.out.println("trap2 time: "+(a3-a2)+"ms");
////        System.out.println("trap3 time: "+(a4-a3)+"ms");
//
//        long b1 = System.currentTimeMillis();
//
//        List<Integer> results1 = index1.search(trap1,3);
//
//        System.out.println("results1："+results1);
//
//        long b2 = System.currentTimeMillis();
//
//        List<String> results2 = new ArrayList<>();
//        index2.search(trap2,"",results2);
//        System.out.println("results2："+results2.size()+","+results2);
//
        System.out.println("=============================query000000============================");
        long b30 = System.currentTimeMillis();

        List<String> results30 = new ArrayList<>();
        List<DataObject> AP0 = new ArrayList<>();
        List<BigInteger> D0 = new ArrayList<>();
        evbTree.search(trap0,"",results30,AP0,D0);
        long b40 = System.currentTimeMillis();

//        System.out.println("search1 time: "+(b2-b1)+"ms");
//        System.out.println("search2 time: "+(b3-b2)+"ms");
        System.out.println("search3 time: "+(b40-b30)+"ms");
        System.out.println("结果集合："+results30);
        System.out.println("=============================query==================================");
        long b3 = System.currentTimeMillis();

        List<String> results3 = new ArrayList<>();
        List<DataObject> AP = new ArrayList<>();
        List<BigInteger> D = new ArrayList<>();
        evbTree.search(trap3,"",results3,AP,D);
        long b4 = System.currentTimeMillis();

//        System.out.println("search1 time: "+(b2-b1)+"ms");
//        System.out.println("search2 time: "+(b3-b2)+"ms");
        System.out.println("search3 time: "+(b4-b3)+"ms");
        System.out.println("结果集合："+results3);
        System.out.println("=============================query333333============================");
        long b5 = System.currentTimeMillis();

        List<String> results33 = new ArrayList<>();
        List<DataObject> AP3 = new ArrayList<>();
        List<BigInteger> D3 = new ArrayList<>();
        evbTree.search(trap_3,"",results33,AP3,D3);
        long b6 = System.currentTimeMillis();

//        System.out.println("search1 time: "+(b2-b1)+"ms");
//        System.out.println("search2 time: "+(b3-b2)+"ms");
        System.out.println("search3 time: "+(b6-b5)+"ms");
        System.out.println("结果集合："+results33);
//
        /*
        direct, program, herbert, geology, issues, vehicle, division, principal, sciences, number, bozeman, total, current, award, interest, instr, introduct, montana, course, david, state, human, class, levitan,
         */

//        long ver1 = System.nanoTime();
//
//        if(evbTree.Verify(results3,AP,D,enc_data)){
//            System.out.println("results3.size()："+results3.size()+","+results3);
//        }
//
//
//        long ver2 = System.nanoTime();
//
//        System.out.println("Verify: "+(ver2-ver1)+"ms");




    }





    /**
     * 将文件内容读取为一个String。
     *
     * @param filePath 文件的路径
     * @return 文件内容的String表示，如果发生错误则返回null
     */
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
