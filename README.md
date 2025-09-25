# VeriFuzzy: A Dynamic Verifiable Fuzzy Search Service for Encrypted Cloud Data

Cloud storage introduces critical privacy challenges for encrypted data retrieval, where fuzzy multi-keyword search enables approximate matching while preserving confidentiality. Existing solutions force undesirable trade-offs: linear-search schemes achieve adaptive security at prohibitive computational cost, while tree-based indexes offer sublinear efficiency but expose **branch leakage** vulnerabilities under dynamic operations.  

We propose **VeriFuzzy**, an IND-CKA2 secure service that simultaneously resolves three core limitations:  
(1) *Adaptive fuzzy search* integrating locality-sensitive hashing with virtual binary trees, eliminating branch leakage while enabling `O(log n)` multi-keyword queries;  
(2) *Forward-private dynamics* via dual-repository version control, preventing update-triggered information leakage;  
(3) *Sublinear verifiability* for correctness and completeness through the combination of VBTree and blockchain.  
VeriFuzzy achieves fuzzy search, dynamic updates, and efficient verification under IND-CKA2 security—eliminating historical trade-offs between functionality and security.  

## Project Overview

This project is a Java-based research project implementing multiple searchable encryption schemes with different indexing approaches, designed for privacy-preserving document search with verification capabilities.

This project demonstrates the practical implementation of our VeriFuzzy in a privacy-preserving document search system, including Local Sensitive Hashing, Bloom Filters, and Verifiable Search Trees.

**Please note that this project only involves the searchable encryption component of the paper's core content. The blockchain chaincode source code and testing scripts cannot be provided due to commercial confidentiality protection.**

This project implements three distinct searchable encryption index schemes:
- **Index1**: Bloom filter-based approximate search with LSH (Locality Sensitive Hashing)
- **Index2**: Hash table-based exact search with cryptographic security
- **Index3 (our VeriFuzzy)**: Enhanced Verifiable Binary Tree (EVBTree) with search result verification [Test.java:81-101](#testjava81-101)

## Project Structure 

```
src/main/src/
├── ChainAddress/          # Hash table implementation with chaining
├── encryption/            # Cryptographic utilities
│   ├── hash/             # Hash functions (MD5, SHA-256)
│   ├── hmac/             # HMAC implementations
│   └── utils/            # Encryption utilities
├── general_tools/         # Core algorithms and utilities
├── index1/               # Bloom filter-based indexing
├── index2/               # Hash table-based indexing  
├── index3/               # Verifiable binary tree indexing
└── test_02/              # Test suites and benchmarks
``` 
### Test Dataset
- **Document data**: There are 1000+ test document keyword data in the `src/test_data/` directory.
- **Keyword data**: Corresponding keyword files are located in the `src/keywords_extraction_rake-master/example/input/` directory.

## Technologies and Dependencies

### Core Technologies
- **Java**: Primary programming language
- **Cryptography**: MD5, SHA-256, HMAC for secure operations [EncryptionHelper.java:23-50](#encryptionhelperjava23-50) 
- **LSH (Locality Sensitive Hashing)**: For approximate similarity search [LSH.java:26-47](#lshjava26-47) 
- **Bloom Filters**: For efficient membership testing [Index1.java:12-25](#index1java12-25) 
- **Binary Trees**: For verifiable search operations [EVBTree.java:19-36](#evbtreejava19-36) 

### Key Algorithms
- **Keyword Extraction**: Text processing and keyword identification [KeywordExtraction.java:21-49](#keywordextractionjava21-49) 
- **Stemming**: Word normalization for consistent indexing
- **Vector Generation**: UniGram-based keyword vectorization

## Installation and Setup

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, or VS Code with Java extensions)

### Setup Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/JackAugust/VeriFuzzy.git
   cd VeriFuzzy
   ```

2. Navigate to the source directory:
   ```bash
   cd src/main/src  
   ```

3. Compile the Java files:
   ```bash
   javac -cp . test_02/*.java general_tools/*.java index1/*.java index2/*.java index3/*.java encryption/*/*.java ChainAddress/*.java   
   ```

## How to Run

### Basic Test Execution
The main test class demonstrates all three indexing schemes: [Test.java:22-39](#testjava22-39) 

```bash
java -cp . test_02.Test
```

**Note**: Before running, update the file paths in the test files `test_02/Test.java` to match your local environment: 
- Update `directoryPath` for keyword extraction
- Update document paths for test data

### Available Test Modules

1. **Main System Test**: [Test.java:1](#testjava1) 
   ```bash
   java -cp . test_02.Test
   ```

2. **Accuracy Testing**: [Test_accuracy.java:1](#test_accuracyjava1) 
   ```bash
   java -cp . test_02.Test_accuracy
   ```

3. **Search Performance**: [Test_search.java:1](#test_searchjava1) 
   ```bash
   java -cp . test_02.Test_search
   ```

4. **Verification Testing**: [Test_verify.java:1](#test_verifyjava1) 
   ```bash
   java -cp . test_02.Test_verify
   ```

## Configuration Parameters

### LSH Parameters
Configure in your test files: [Test.java:73-77](#testjava81-101) 
- `w = 10.0`: Segment length for LSH
- `d = 160`: Input vector dimension
- `kkk = 7`: Number of hash functions

### Index Parameters
- **Index1**: Bloom filter size (default: 1000), filter count (default: 500)
- **Index2**: Hash table maximum size (default: 1000)
- **Index3**: Maximum size and LSH configuration

### Keyword Extraction
Extract keywords of 5-9 characters in length from the document

## How to Debug

### Debug Configuration
1. **Enable Debug Output**: Uncomment debug print statements in test files
2. **Logging**: Add custom logging in main components:
   - Index generation timing
   - Search operation details
   - Verification results

### Common Debug Points
- **Keyword Extraction**: Verify keyword extraction results [Test.java:33-39](#testjava22-39) 
- **Index Construction**: Monitor index generation timing [Test.java:71-102](#testjava81-101) 
- **Search Results**: Validate search output and performance [Test.java:197-235](#testjava197-235) 

### Performance Monitoring
The system includes built-in timing measurements: 
- Index generation time
- Search operation time
- Verification time

## Testing

### Test Data Requirements
Prepare test documents and keyword files:
- Document collection for indexing
- Query keywords for search testing
- Expected results for accuracy validation

### Test Categories

1. **Functionality Tests**: [Test_add.java:1](#test_addjava1) 
   - Index construction
   - Keyword insertion
   - Search operations

2. **Accuracy Tests**: [Test_accuracy.java:27-48](#test_accuracyjava27-48) 
   - Search precision and recall
   - False positive rates
   - Verification correctness

3. **Performance Tests**: [Test_tarp.java:1](#test_tarpjava1) 
   - Index generation speed
   - Search latency
   - Memory usage

### Running All Tests
Execute individual test files based on your testing needs:
```bash
# Run specific test modules
java -cp . test_02.Test_accuracy
java -cp . test_02.Test_search
java -cp . test_02.Test_verify
```

## System Architecture

### Index Schemes Comparison
- **Index1**: Fast approximate search with potential false positives
- **Index2**: Exact search with cryptographic security guarantees  
- **Index3**: Verifiable search with authenticity proofs

### Security Features
The system implements multiple cryptographic primitives: [EncryptionHelper.java:14-50](#encryptionhelperjava23-50) 
- Secure hash functions
- HMAC for data integrity
- Pseudorandom functions for key derivation

## Troubleshooting

### Common Issues
1. **File Path Errors**: Update all hardcoded paths in test files to match your environment
2. **Memory Issues**: Adjust heap size for large datasets: `java -Xmx4g -cp . test_02.Test`
3. **Compilation Errors**: Ensure all dependencies are in classpath

### Data Preparation
- Prepare document collections in the expected format
- Ensure keyword extraction directories exist
- Verify file encoding (UTF-8 expected)

## License

This project is licensed under the Apache License 2.0.


### Citations
#### Test.java
##### Test.java:1
**File:** src/main/src/test_02/Test.java (L1-1)
```java
package test_02;
```

##### Test.java:22-39
**File:** src/main/src/test_02/Test.java (L22-39)
```java
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
```

##### Test.java:81-101
**File:** src/main/src/test_02/Test.java (L71-102)
```java
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
```

##### Test.java:197-235
**File:** src/main/src/test_02/Test.java (L197-235)
```java
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
```

**File:** src/main/src (L1-1)
```text
[{"name":"ChainAddress","path":"src/main/src/ChainAddress","sha":"752e8881cea6ae4fa6281db15b9692004895a9dc","size":0,"url":"https://api.github.com/repos/JackAugust/ICSOC25/contents/src/main/src/ChainAddress?ref=master","html_url":"https://github.com/JackAugust/ICSOC25/tree/master/src/main/src/ChainAddress","git_url":"https://api.github.com/repos/JackAugust/ICSOC25/git/trees/752e8881cea6ae4fa6281db15b9692004895a9dc","download_url":null,"type":"dir","_links":{"self":"https://api.github.com/repos/JackAugust/ICSOC25/contents/src/main/src/ChainAddress?ref=master","git":"https://api.github.com/repos/JackAugust/ICSOC25/git/trees/752e8881cea6ae4fa6281db15b9692004895a9dc","html":"https://github.com/JackAugust/ICSOC25/tree/master/src/main/src/ChainAddress"}},{"name":"encryption","path":"src/main/src/encryption","sha":"cec722daf0a413d0533bbf0d5cf717f360885b28","size":0,"url":"https://api.github.com/repos/JackAugust/ICSOC25/contents/src/main/src/encryption?ref=master","html_url":"https://github.com/JackAugust/ICSOC25/tree/master/src/main/src/encryption","git_url":"https://api.github.com/repos/JackAugust/ICSOC25/git/trees/cec722daf0a413d0533bbf0d5cf717f360885b28","download_url":null,"type":"dir","_links":{"self":"https://api.github.com/repos/JackAugust/ICSOC25/contents/src/main/src/encryption?ref=master","git":"https://api.github.com/repos/JackAugust/ICSOC25/git/trees/cec722daf0a413d0533bbf0d5cf717f360885b28","html":"https://github.com/JackAugust/ICSOC25/tree/master/src/main/src/encryption"}},{"name":"general_tools","path":"src/main/src/general_tools","sha":"953620d5764eeb620c75b1a258f368510daa8ed4","size":0,"url":"https://api.github.com/repos/JackAugust/ICSOC25/contents/src/main/src/general_tools?ref=master","html_url":"https://github.com/JackAugust/ICSOC25/tree/master/src/main/src/general_tools","git_url":"https://api.github.com/repos/JackAugust/ICSOC25/git/trees/953620d5764eeb620c75b1a258f368510daa8ed4","download_url":null,"type":"dir","_links":{"self":"https://api.github.com/repos/JackAugust/ICSOC25/contents/src/main/src/general_tools?ref=master","git":"https://api.github.com/repos/JackAugust/ICSOC25/git/trees/953620d5764eeb620c75b1a258f368510daa8ed4","html":"https://github.com/JackAugust/ICSOC25/tree/master/src/main/src/general_tools"}},{"name":"index1","path":"src/main/src/index1","sha":"b332bd0ef7e5885bfb119557a39781f38805154a","size":0,"url":"https://api.github.com/repos/JackAugust/ICSOC25/contents/src/main/src/index1?ref=master","html_url":"https://github.com/JackAugust/ICSOC25/tree/master/src/main/src/index1","git_url":"https://api.github.com/repos/JackAugust/ICSOC25/git/trees/b332bd0ef7e5885bfb119557a39781f38805154a","download_url":null,"type":"dir","_links":{"self":"https://api.github.com/repos/JackAugust/ICSOC25/contents/src/main/src/index1?ref=master","git":"https://api.github.com/repos/JackAugust/ICSOC25/git/trees/b332bd0ef7e5885bfb119557a39781f38805154a","html":"https://github.com/JackAugust/ICSOC25/tree/master/src/main/src/index1"}},{"name":"index2","path":"src/main/src/index2","sha":"520ab354f7ba71f08bc590d34bdaaa572c679988","size":0,"url":"https://api.github.com/repos/JackAugust/ICSOC25/contents/src/main/src/index2?ref=master","html_url":"https://github.com/JackAugust/ICSOC25/tree/master/src/main/src/index2","git_url":"https://api.github.com/repos/JackAugust/ICSOC25/git/trees/520ab354f7ba71f08bc590d34bdaaa572c679988","download_url":null,"type":"dir","_links":{"self":"https://api.github.com/repos/JackAugust/ICSOC25/contents/src/main/src/index2?ref=master","git":"https://api.github.com/repos/JackAugust/ICSOC25/git/trees/520ab354f7ba71f08bc590d34bdaaa572c679988","html":"https://github.com/JackAugust/ICSOC25/tree/master/src/main/src/index2"}},{"name":"index3","path":"src/main/src/index3","sha":"69681022818bfe44b6560a047686604f1961f905","size":0,"url":"https://api.github.com/repos/JackAugust/ICSOC25/contents/src/main/src/index3?ref=master","html_url":"https://github.com/JackAugust/ICSOC25/tree/master/src/main/src/index3","git_url":"https://api.github.com/repos/JackAugust/ICSOC25/git/trees/69681022818bfe44b6560a047686604f1961f905","download_url":null,"type":"dir","_links":{"self":"https://api.github.com/repos/JackAugust/ICSOC25/contents/src/main/src/index3?ref=master","git":"https://api.github.com/repos/JackAugust/ICSOC25/git/trees/69681022818bfe44b6560a047686604f1961f905","html":"https://github.com/JackAugust/ICSOC25/tree/master/src/main/src/index3"}},{"name":"test_02","path":"src/main/src/test_02","sha":"e05b15c2545715a669cd2c58c38afb69cbb8fbb0","size":0,"url":"https://api.github.com/repos/JackAugust/ICSOC25/contents/src/main/src/test_02?ref=master","html_url":"https://github.com/JackAugust/ICSOC25/tree/master/src/main/src/test_02","git_url":"https://api.github.com/repos/JackAugust/ICSOC25/git/trees/e05b15c2545715a669cd2c58c38afb69cbb8fbb0","download_url":null,"type":"dir","_links":{"self":"https://api.github.com/repos/JackAugust/ICSOC25/contents/src/main/src/test_02?ref=master","git":"https://api.github.com/repos/JackAugust/ICSOC25/git/trees/e05b15c2545715a669cd2c58c38afb69cbb8fbb0","html":"https://github.com/JackAugust/ICSOC25/tree/master/src/main/src/test_02"}}]
```

##### EncryptionHelper.java:23-50
**File:** src/main/src/encryption/EncryptionHelper.java (L14-50)
```java
public class EncryptionHelper {

    private static final String VERSION = "0.1.1";

    public static String getVersion() {
        return VERSION;
    }

    /**
     * MD5 加密
     *
     * @param data 加密数据
     * @return 加密结果
     */
    public static String getMd5Param(String data) {
        return HashHelper.encryptHashToString(HashType.MD5, data);
    }

    /**
     * SHA256 加密
     *
     * @param data 加密数据
     * @return 加密结果
     */
    public static String getSha256Param(String data) {
        return HashHelper.encryptHashToString(HashType.SHA_256, data);
    }

    /**
     * HmacMd5 加密
     *
     * @param data 加密数据
     * @param key  密码
     * @return 加密结果
     */
    public static String getHmacMd5Param(String data, String key) {
        return HmacHelper.encryptHmacToString(HmacType.HMAC_MD5, data, key);
```

##### LSH.java:26-47
**File:** src/main/src/general_tools/LSH.java (L26-47)
```java
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
```

##### Index1.java:12-25
**File:** src/main/src/index1/Index1.java (L12-25)
```java
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
```

##### EVBTree.java:19-36
**File:** src/main/src/index3/EVBTree.java (L19-36)
```java
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
```

##### KeywordExtraction.java:21-49
**File:** src/main/src/general_tools/KeywordExtraction.java (L21-49)
```java
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
```

##### Test_accuracy.java:1
**File:** src/main/src/test_02/Test_accuracy.java (L1-1)
```java
package test_02;
```

##### Test_accuracy.java:27-48
**File:** src/main/src/test_02/Test_accuracy.java (L27-48)
```java
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

        System.out.println("enc_data.size()："+enc_data.size());
```

##### Test_search.java:1
**File:** src/main/src/test_02/Test_search.java (L1-1)
```java
package test_02;
```

##### Test_verify.java:1
**File:** src/main/src/test_02/Test_verify.java (L1-1)
```java
package test_02;
```

##### Test_add.java:1
**File:** src/main/src/test_02/Test_add.java (L1-1)
```java
package test_02;
```

##### Test_tarp.java:1
**File:** src/main/src/test_02/Test_tarp.java (L1-1)
```java
package test_02;
```
