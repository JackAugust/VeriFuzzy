package index2.ChainAddress;


import java.math.BigInteger;

/**
 * @Auther: cty
 * @Date: 2020/6/15 21:01
 * @Description: 链地址法 哈希表
 * 应用场景：
 *      当数据量大时，考虑哈希表或树
 *          当数据量可预测且对查找、插入速度要求高时，考虑哈希表
 *              若创建哈希表时要填入项数未知，建议选择链地址法，因为链地址法受装填因子影响小
 *              若创建哈希表时要填入项数可预测时，也建议选择链地址法，虽然实现较复杂，但当实际增加比预期更多的数据时，不会导致性能快速下降
 * 重要操作：
 *      ① find(long): DataItem    O(1)  当链表中节点较少时
 *      ② insert(DataItem): void    O(1)  当链表中节点较少时
 *      ③ delete(long): DataItem    O(1)  当链表中节点较少时
 * @version: 1.0
 */
public class HashTable {
    private int maxSize;
    private LinkedList[] lists;

    public HashTable(int maxSize){
        this.maxSize = maxSize;
        lists = new LinkedList[maxSize];
        for(int i=0; i<maxSize; i++)
            lists[i] = new LinkedList();
    }

    private int hashFunc(BigInteger key){
        BigInteger bigIntegerMaxSize = BigInteger.valueOf(maxSize);

        // 使用 BigInteger 的 mod 方法进行取模
        BigInteger resultBigInteger = key.mod(bigIntegerMaxSize);

        // 将结果从 BigInteger 转换回 int。由于 maxSize 是 int 类型的，我们可以安全地进行转换
        // 但要注意，如果 resultBigInteger 大于 Integer.MAX_VALUE，这里会发生溢出
        int result = resultBigInteger.intValue();
        return result;
    }

    /**
     * 查找指定关键字的节点  O(1)  当链表中节点较少时
     * @param key
     * @return
     */
    public DataItem find(BigInteger key){
        int hashValue = hashFunc(key);
        return lists[hashValue].find(key);
    }

    /**
     * 插入  O(1)  当链表中节点较少时
     * @param item
     */
    public void insert(DataItem item){
        int hashValue = hashFunc(item.getKey());
        lists[hashValue].insertFirst(item);
    }

    /**
     * 删除指定关键字的节点  O(1)  当链表中节点较少时
     * @param key
     * @return
     */
    public DataItem delete(BigInteger key){
        int hashValue = hashFunc(key);
        return lists[hashValue].delete(key);
    }

}  // end HashTable{}
