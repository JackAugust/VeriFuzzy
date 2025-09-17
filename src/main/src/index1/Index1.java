package index1;

import general_tools.LSH;
import general_tools.TopThreeIndexes;
import general_tools.UniGramKeywordVectorGenerator;
import index3.Stemmer;

import java.util.ArrayList;
import java.util.List;

public class Index1 {
    private BloomFilter[] BF;
    private int len;
    private LSH lsh;
    private Stemmer stemmer;

    public Index1(int m,int len, LSH lsh){
        this.lsh = lsh;
        this.len=len;
        BF = new BloomFilter[len];
        for (int i=0;i< BF.length;i++){
            BF[i] = new BloomFilter(m,lsh);
        }
        stemmer = new Stemmer();
    }

    public void insert(List<List<String>> keywords){
        for (int i=0;i<keywords.size();i++){
        //每一文档关键字集keys

            for (String keyword:keywords.get(i)) {
                //提取词根、向量化

               // System.out.println("stemmer.dealKey(keyword)："+stemmer.dealKey(keyword));
                int[] vector = UniGramKeywordVectorGenerator.generateUniGramVector(stemmer.dealKey(keyword));
                //对应文档i的BF插入向量

                BF[i].insert(vector);
            }
        }

    }



    public List<Integer> search(BloomFilter bloomFilter, int K){
        int[] Score = new int[BF[0].getSize()];
        for (int i=0;i<BF.length;i++){
            int S=0;
//            for (int[] pos:query){
//                for (int pp:pos){
//                    S += BF[i].getC().get(pp);
//                }
//            }
            for(int j=0;j<BF[i].getC().size();j++){
                S += BF[i].getC().get(j)*bloomFilter.getC().get(j);
            }


            Score[i]=S;
        }
        List<Integer> res = TopThreeIndexes.findTopKIndexes(Score,K);
        return res;
    }


    public BloomFilter TrapGen(List<String> query){
        List<int[]> res = new ArrayList<>();
        BloomFilter bff = new BloomFilter(1000,lsh);
        for (String keyword:query){
            //词干提取、向量化
            int[] vector = UniGramKeywordVectorGenerator.generateUniGramVector(stemmer.dealKey(keyword));
            //bloom加密
            res.add(this.BF[0].hash(convertIntArrayToDoubleArray(vector)));
            bff.insert(vector);
        }
        return bff;
    }


    public static double[] convertIntArrayToDoubleArray(int[] intArray) {
        // 创建一个新的double数组，长度与输入的int数组相同
        double[] doubleArray = new double[intArray.length];

        // 遍历int数组，将每个元素转换为double并存储到新的数组中
        for (int i = 0; i < intArray.length; i++) {
            doubleArray[i] = (double) intArray[i];
        }

        // 返回转换后的double数组
        return doubleArray;
    }


}
