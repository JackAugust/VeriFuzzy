package index2;

import encryption.hash.HashHelper;
import encryption.hash.HashType;
import encryption.hmac.HmacHelper;
import encryption.hmac.HmacType;
import index2.ChainAddress.DataItem;
import index2.ChainAddress.HashTable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Index2 {
    private HashTable hashTable;    //哈希表
    private int maxsize;            //哈希表大小
    private HmacHelper hmac_sha256; //伪随机函数FK
    private HashHelper hashHelper;  //随机预言机H1
    private String KEY="zduishiggwefkhgwigiwihrryg";          //FK的密钥
    private int bits=0;


    public Index2(int maxsize){
        this.maxsize = maxsize;
        hashTable = new HashTable(maxsize);
        hmac_sha256 = new HmacHelper();
        hashHelper = new HashHelper();
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


    public void IndexGen(List<List<String>> keywords){
        //对于每一个文档，首先获得他的叶子结点的path，然后对它的每个关键字进行从根到叶子结点的计算，值存入哈希表中
        int bits = Integer.valueOf((int) Math.ceil(Math.log(keywords.size()) / Math.log(2)));
        this.bits = bits;
        for (int i=0;i<keywords.size();i++){
            //计算叶子节点的路径字符串
            String binaryPath = numTobinary(i,bits);

            for (String key:keywords.get(i)){
                //插入根节点
                BigInteger value = hashHelper.encryptHashToBigInteger(HashType.SHA_256,""+hmac_sha256.encryptHmacToString(HmacType.HMAC_SHA256,key,KEY));
                hashTable.insert(new DataItem(value,(int)1));
                //其他节点的计算
                for (int k = 0; k < binaryPath.length(); k++) {
                    String currentBinary = binaryPath.substring(0, k + 1);

                    BigInteger value2 = hashHelper.encryptHashToBigInteger(HashType.SHA_256,currentBinary+hmac_sha256.encryptHmacToString(HmacType.HMAC_SHA256,key,KEY));
                    hashTable.insert(new DataItem(value2,(int)1));

                }

            }
        }
    }


    public void Update(List<String> keywords, int id){
        //对于每一个文档，首先获得他的叶子结点的path，然后对它的每个关键字进行从根到叶子结点的计算，值存入哈希表中
        String binaryPath = numTobinary(id,this.bits);
        for (String key:keywords){


                //插入根节点
                BigInteger value = hashHelper.encryptHashToBigInteger(HashType.SHA_256,""+hmac_sha256.encryptHmacToString(HmacType.HMAC_SHA256,key,KEY));
                hashTable.insert(new DataItem(value,(int)1));
                //其他节点的计算
                for (int k = 0; k < binaryPath.length(); k++) {
                    String currentBinary = binaryPath.substring(0, k + 1);

                    BigInteger value2 = hashHelper.encryptHashToBigInteger(HashType.SHA_256,currentBinary+hmac_sha256.encryptHmacToString(HmacType.HMAC_SHA256,key,KEY));
                    hashTable.insert(new DataItem(value2,(int)1));

                }

        }
    }




    public List<String> TrapGen(List<String> query){
        List<String> trap = new ArrayList<>();
        for (String key : query){
            trap.add(hmac_sha256.encryptHmacToString(HmacType.HMAC_SHA256,key,KEY));
        }

//        System.out.println("this.bits:"+this.bits);
        return trap;

    }



    public void search(List<String> trap, String path, List<String> results){
        for (String str:trap){
            BigInteger value = hashHelper.encryptHashToBigInteger(HashType.SHA_256,path+str);
            if(hashTable.find(value)==null){
                return;
            }
        }

        if (path.length()==this.bits){
            results.add(path);
            return;
        }

        if (path.length()<bits){
            search(trap,path+'0',results);
            search(trap,path+'1',results);
        }




    }



}
