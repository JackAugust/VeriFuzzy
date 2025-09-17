package general_tools;

import index3.Stemmer;
import javafx.util.Pair;

import java.util.*;

import static index1.BloomFilter.convertIntArrayToDoubleArray;
import static index3.EVBTree.arrayToString;

public class LSH {



    private double w;
    private int d;
    private int kkk;
    private List<double[]> aParams;
    private List<Double> bParams;
    private List<Integer> r1;
    private List<Integer> r2;
    private Map<Integer, List<Pair<Integer, Integer>>> table;
    private static final int TABLE_SIZE = 100005;
    private static final int PRIME = 1_000_000_007;

    public LSH(double w, int d, int kkk) {

        this.w = w; //设置分段长度w
        this.d = d; //设置输入向量的维数d
        this.kkk = kkk; //哈希函数的个数kkk
        aParams = new ArrayList<>();
        bParams = new ArrayList<>();
        r1 = new ArrayList<>();
        r2 = new ArrayList<>();
        table = new HashMap<>();

        bParams = GenUniform(kkk,w);
        r1 = GenUniform(kkk,PRIME);  //r1,r2随机整数集
        r2 = GenUniform(kkk,PRIME);
        for (int i = 0; i < kkk; i++) {
            aParams.add(genNormal(d));  //生成一个长度为d的随机向量，满足标准正态分布，并将其添加到aParams列表中。这个向量用于计算每个维度的加权和
        }

//        for (int i = 0; i < features.length; i++) {
//            add(features[i], i);
//        }
    }


    public List<Double> getbParams(){
        return this.bParams;
    }
    public List<double[]> getaParams(){
        return this.aParams;
    }

    public LSH(double w, int d, int kkk, double[][] features) {

        this.w = w; //设置分段长度w
        this.d = d; //设置输入向量的维数d
        this.kkk = kkk; //哈希函数的个数kkk
        aParams = new ArrayList<>();
        bParams = new ArrayList<>();
        r1 = new ArrayList<>();
        r2 = new ArrayList<>();
        table = new HashMap<>();

        bParams = GenUniform(kkk,w);
        r1 = GenUniform(kkk,PRIME);  //r1,r2随机整数集
        r2 = GenUniform(kkk,PRIME);
        for (int i = 0; i < kkk; i++) {
            aParams.add(genNormal(d));  //生成一个长度为d的随机向量，满足标准正态分布，并将其添加到aParams列表中。这个向量用于计算每个维度的加权和
        }

        for (int i = 0; i < features.length; i++) {
            add(features[i], i);
        }
    }

    // 向哈希表中插入一个点
    public void add(double[] vec, int index) {
        Pair<Integer, Integer> hashPair = h(vec);
        table.computeIfAbsent(hashPair.getKey(), k -> new ArrayList<>()).add(new Pair<>(hashPair.getValue(), index));
    }

    // 获取邻居
    public List<Integer> getNeighbours(double[] vec) {
        Pair<Integer, Integer> hashPair = h(vec);
        List<Pair<Integer, Integer>> entries = table.getOrDefault(hashPair.getKey(), Collections.emptyList());
        List<Integer> neighbours = new ArrayList<>();
        for (Pair<Integer, Integer> entry : entries) {
            if (entry.getKey().equals(hashPair.getValue())) {
                neighbours.add(entry.getValue());
            }
        }
        return neighbours;
    }

    // 计算哈希值
    public Pair<Integer, Integer> h(double[] vec) {
        int[] hash1 = new int[kkk];
        for (int i = 0; i < kkk; i++) {
            double sum = 0;
            for (int j = 0; j < d; j++) {
                sum += vec[j] * aParams.get(i)[j];
            }
            int hash = (int) Math.ceil((sum + bParams.get(i)) / w);
            hash1[i] = hash;    //hash1=(vector*a+b)/w; hash1=g_i(v)
        }

        int first = 0, second = 0;
        for (int i = 0; i < kkk; i++) {
//            first = (first + (hash1[i] % PRIME) * (r1.get(i) % PRIME)) % PRIME;
//            second = (second + (hash1[i] % PRIME) * (r2.get(i) % PRIME)) % PRIME;
            first += hash1[i] * r1.get(i);
            second += hash1[i] * r2.get(i);

        }

        first %= PRIME;
        second %= PRIME;

        first %= TABLE_SIZE;

        return new Pair<>(first, second);
    }

    //kkk个哈希函数，计算得到的k个结果
    public int[] h2(double[] vec) {
        int[] hash1 = new int[kkk];
        for (int i = 0; i < kkk; i++) {
            double sum = 0;
            for (int j = 0; j < d; j++) {
                sum += vec[j] * aParams.get(i)[j];
            }
            int hash = (int) Math.ceil((sum + bParams.get(i)) / w);
            hash1[i] = hash;    //hash1=(vector*a+b)/w; hash1=g_i(v)
//            System.out.println("Math.ceil((sum + bParams.get(i)) / w)："+Math.ceil((sum + bParams.get(i)) / w));
        }


        return hash1;
    }

    // 生成标准正态分布的随机向量
    public double[] genNormal(int n) {
        double[] ans = new double[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
       //     ans[i] = random.nextGaussian(); // Java中的标准正态分布（高斯分布）
            ans[i] = 5.0+2.0*random.nextGaussian(); // Java中的标准正态分布（高斯分布）
        }
        return ans;
    }

    //生成均匀分布的随机小数b
    public static List<Double> GenUniform(int n, double upperBound) {
        List<Double> result = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            result.add(random.nextDouble() * upperBound);
        }
        return result;
    }

    // 生成均匀分布的随机整数
    public static List<Integer> GenUniform(int n, int upperBound) {
        List<Integer> result = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            result.add(random.nextInt(upperBound + 1)); // 包括upperBound
        }
        return result;
    }


    public static void main(String[] args) {
        // 设置LSH的参数
        double w = 10.0; // 分段长度
        int d = 5; // 输入向量的维数
        int kkk = 7; // 哈希函数的个数


        String key1="education";
        String key2="educati";

        LSH lsh = new LSH(100.0,160,3);
        Stemmer stemmer = new Stemmer();
        //提取词根、向量化
        int[] vector = UniGramKeywordVectorGenerator.generateUniGramVector(stemmer.dealKey(key1));
        //LSH计算
        int[] res = lsh.h2(convertIntArrayToDoubleArray(vector));
        //LSH结果拼接成字符串S
        String key_S = arrayToString(res);
        System.out.println("key_S: "+key_S);


        //提取词根、向量化
        int[] vector1 = UniGramKeywordVectorGenerator.generateUniGramVector(stemmer.dealKey(key2));
        //LSH计算
        int[] res1 = lsh.h2(convertIntArrayToDoubleArray(vector1));
        //LSH结果拼接成字符串S
        String key_S1 = arrayToString(res1);
        System.out.println("key_S2: "+key_S1);





//        // 创建一些随机特征向量用于测试
//        double[][] features = new double[10][d];
//        Random random = new Random();
//        for (int i = 0; i < features.length; i++) {
//            for (int j = 0; j < d; j++) {
//                features[i][j] = random.nextGaussian(); // 生成标准正态分布的随机数
//            }
//        }
//
//        // 创建LSH对象
//        LSH lsh = new LSH(w, d, kkk, features);
//
//        // 假设我们要查找第一个向量的邻居
//        double[] queryVec = features[0];
//        System.out.println("Querying neighbors for vector: " + Arrays.toString(queryVec));
//
//        // 获取邻居
//        List<Integer> neighbors = lsh.getNeighbours(queryVec);
//        System.out.println("Neighbors found: " + neighbors);
//
//        // 注意：由于LSH的随机性和哈希表的初始为空，除非你添加了一些与查询向量在哈希空间内碰撞的向量，
//        // 否则你可能找不到任何邻居。为了更好地测试，你可能需要手动添加一些已知会碰撞的向量，
//        // 或者增加特征向量的数量并多次运行测试来观察结果。
//
//        // 例如，我们可以手动添加一个与queryVec在哈希表中具有相同哈希值的向量来测试
//        double[] similarVec = Arrays.copyOf(queryVec, queryVec.length);
//        similarVec[0] += 0.1; // 修改一个维度使其略有不同但仍可能哈希到相同位置
//        lsh.add(similarVec, 10); // 假设索引为10
//
//        // 再次查询
//        System.out.println("Querying neighbors again after adding a similar vector:");
//        neighbors = lsh.getNeighbours(queryVec);
//        System.out.println("Neighbors found: " + neighbors);
    }

}
