package index3;

import encryption.hash.HashHelper;
import encryption.hash.HashType;
import encryption.hmac.HmacHelper;
import encryption.hmac.HmacType;
import general_tools.LSH;
import general_tools.UniGramKeywordVectorGenerator;
import index2.ChainAddress.DataItem;
import index2.ChainAddress.HashTable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static index3.VerifyTree.buildTree;
import static index3.VerifyTree.checkTree;

public class EVBTree {
    private HashTable hashTable;    //哈希表
    private int maxsize;            //哈希表大小
    private HmacHelper hmac_sha256; //伪随机函数FK
    private HashHelper hashHelper;  //随机预言机H1
    private String KEY="zduishiggwefkhgwigiwihrryg";          //FK的密钥
    private LSH lsh;                //LSH
    private Stemmer stemmer;
    private int bits=0;

    public EVBTree(int maxsize, LSH lsh){
        this.maxsize = maxsize;
        hashTable = new HashTable(maxsize);
        hmac_sha256 = new HmacHelper();
        hashHelper = new HashHelper();
        this.lsh = lsh;
        this.stemmer = new Stemmer();
    }
    public EVBTree(int maxsize, LSH lsh, int bit ){
        this.maxsize = maxsize;
        hashTable = new HashTable(maxsize);
        hmac_sha256 = new HmacHelper();
        hashHelper = new HashHelper();
        this.lsh = lsh;
        this.stemmer = new Stemmer();
        this.bits = Integer.valueOf((int) Math.ceil(Math.log(bit) / Math.log(2)));;
    }

    public void IndexGen(List<List<String>> keywords, List<String> enc_data){
        //对于每一个文档，首先获得他的叶子结点的path，然后对它的每个关键字进行从根到叶子结点的计算，值存入哈希表中
        int bits = Integer.valueOf((int) Math.ceil(Math.log(keywords.size()) / Math.log(2)));
        this.bits = bits;
        for (int i=0;i<keywords.size();i++){
            //计算叶子节点的路径字符串
            String binaryPath = numTobinary(i,bits);

            for (String key:keywords.get(i)){
                //首先进行模糊处理：提取词根、向量化、LSH计算、LSH结果拼接成字符串S
                //提取词根、向量化
                int[] vector = UniGramKeywordVectorGenerator.generateUniGramVector(stemmer.dealKey(key));
                //LSH计算
                int[] res = lsh.h2(convertIntArrayToDoubleArray(vector));
                //LSH结果拼接成字符串S
                String key_S = arrayToString(res);

                //插入根节点
                BigInteger value = hashHelper.encryptHashToBigInteger(HashType.SHA_256,""+hmac_sha256.encryptHmacToString(HmacType.HMAC_SHA256,key_S,KEY));
                hashTable.insert(new DataItem(value,(int)1));
                //其他节点的计算
                for (int k = 0; k < binaryPath.length(); k++) {
                    String currentBinary = binaryPath.substring(0, k + 1);

                    BigInteger value2 = hashHelper.encryptHashToBigInteger(HashType.SHA_256,currentBinary+hmac_sha256.encryptHmacToString(HmacType.HMAC_SHA256,key_S,KEY));
                    hashTable.insert(new DataItem(value2,(int)1));

                }
            }
            //计算密文摘要信息
            BigInteger d_i = hmac_sha256.encryptHmacToBigInteger(HmacType.HMAC_SHA256,enc_data.get(i)+binaryPath,KEY);
            int decimalValue = Integer.parseInt(binaryPath, 2); // 将二进制字符串解析为十进制数
            hashTable.insert(new DataItem(new BigInteger(String.valueOf(decimalValue)),d_i));

        }

    }



    public void Update(List<String> keywords, int id,String enc_data){

        long start0 = System.currentTimeMillis();

        //对于每一个文档，首先获得他的叶子结点的path，然后对它的每个关键字进行从根到叶子结点的计算，值存入哈希表中
        String binaryPath = numTobinary(id,this.bits);
        for (String key:keywords){
            //首先进行模糊处理：提取词根、向量化、LSH计算、LSH结果拼接成字符串S
            //提取词根、向量化
            int[] vector = UniGramKeywordVectorGenerator.generateUniGramVector(stemmer.dealKey(key));
            //LSH计算
            int[] res = lsh.h2(convertIntArrayToDoubleArray(vector));
            //LSH结果拼接成字符串S
            String key_S = arrayToString(res);

            //插入根节点
            BigInteger value = hashHelper.encryptHashToBigInteger(HashType.SHA_256,""+hmac_sha256.encryptHmacToString(HmacType.HMAC_SHA256,key_S,KEY));
            hashTable.insert(new DataItem(value,(int)1));
            //其他节点的计算
            for (int k = 0; k < binaryPath.length(); k++) {
                String currentBinary = binaryPath.substring(0, k + 1);

                BigInteger value2 = hashHelper.encryptHashToBigInteger(HashType.SHA_256,currentBinary+hmac_sha256.encryptHmacToString(HmacType.HMAC_SHA256,key_S,KEY));
                hashTable.insert(new DataItem(value2,(int)1));

            }

        }

        //计算密文摘要信息
        BigInteger d_i = hmac_sha256.encryptHmacToBigInteger(HmacType.HMAC_SHA256,enc_data+binaryPath,KEY);
        int decimalValue = Integer.parseInt(binaryPath, 2); // 将二进制字符串解析为十进制数
        hashTable.insert(new DataItem(new BigInteger(String.valueOf(decimalValue)),d_i));

        long end0 = System.currentTimeMillis();
        System.out.println("更新时间："+(end0-start0)+"ms");

    }


    public List<String> TrapGen(List<String> query){
        List<String> res = new ArrayList<>();
        for (String keyword:query){
            //提取词根、向量化
            int[] vector = UniGramKeywordVectorGenerator.generateUniGramVector(stemmer.dealKey(keyword));
            //LSH计算
            int[] res2 = lsh.h2(convertIntArrayToDoubleArray(vector));
            //LSH结果拼接成字符串S
            String key_S = arrayToString(res2);
            //计算并保存FK值
            res.add(hmac_sha256.encryptHmacToString(HmacType.HMAC_SHA256,key_S,KEY));
        }

        return res;
    }


    public void search(List<String> trap, String path, List<String> results, List<DataObject> AP, List<BigInteger> D){


        for (String str:trap){
            BigInteger value = hashHelper.encryptHashToBigInteger(HashType.SHA_256,path+str);
            if(hashTable.find(value)==null){
                AP.add(new DataObject(path,'0'));
                return;
            }
        }

        AP.add(new DataObject(path,'1'));

        if (path.length()==this.bits){
            results.add(path);
            int decimalValue = Integer.parseInt(path, 2); // 将二进制字符串解析为十进制数
            DataItem dataitem = hashTable.find(new BigInteger(String.valueOf(decimalValue)));
            if (dataitem.getData() instanceof BigInteger) {
                D.add((BigInteger) dataitem.getData()); // 显式类型转换
            }
        }

        search(trap,path+'0',results,AP,D);
        search(trap,path+'1',results,AP,D);

    }



    public boolean Verify(List<String> results, List<DataObject> AP, List<BigInteger> D, List<String> enc_data){


        //判断完整性
        TreeNode root = buildTree(AP);
        if (root==null || !checkTree(root,this.bits)) {
            System.out.println("结果完整性验证不通过！！！");
            return false;
        }

        //判断正确性
        for (int i=0;i<results.size();i++){
            int decimalValue = Integer.parseInt(results.get(i), 2); // 将二进制字符串解析为十进制数
            //重计算密文摘要信息
            BigInteger d_i = hmac_sha256.encryptHmacToBigInteger(HmacType.HMAC_SHA256,enc_data.get(decimalValue)+results.get(i),KEY);
            if (!d_i.equals(D.get(i))){
                System.out.println("结果正确性验证不通过！！！");
                return false;
            }
        }
        return true;

    }



    public static String numTobinary(int i, int n){
//        int n = 8; // 指定二进制数的位数

        String binaryString = Integer.toBinaryString(i);

        // 补零操作
        if (binaryString.length() < n) {
            int numZeros = n - binaryString.length();
            for (int j = 0; j < numZeros; j++) {
                binaryString = "0" + binaryString;
            }
        }

//        System.out.println("转换结果：" + binaryString);
        return binaryString;
    }



    public static String arrayToString(int[] arr) {
        if (arr == null || arr.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int num : arr) {
            sb.append(num); // 直接将int类型转换为String并拼接
        }

        return sb.toString();
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
