package index2.ChainAddress;


import java.math.BigInteger;

/**
 * @Auther: cty
 * @Date: 2020/6/15 19:42
 * @Description: 单链表
 * @version: 1.0
 */
public class LinkedList {
    private Node first;

    public LinkedList(){
        first = null;
    }

    public boolean isEmpty(){
        return (first == null);
    }

    public void displayList(){
        Node current = first;

        while(current != null)
            System.out.print(current.data.getKey() + " ");
        System.out.println();
    }

    /**
     * 查找指定关键字的节点  O(N)
     * @param key
     * @return
     */
    public DataItem find(BigInteger key){
        // 查找
        Node current = first;
        while(current!=null && !current.data.getKey().equals(key))
            current = current.next;

        // 处理查找结果
        if(current == null)
            return null;
        else
            return current.data;
    }  // end find()

    /**
     * 在表头插入  O(1)
     * @param item
     */
    public void insertFirst(DataItem item){
        Node newNode = new Node(item);
        newNode.next = first;
        first = newNode;
    }  // end insertFirst()

    /**
     * 删除指定关键字的节点  O(N)
     * @param key
     * @return
     */
    public DataItem delete(BigInteger key){
        if(isEmpty())
            return null;  // 空表

        // 查找
        Node current = first;
        Node previous = null;
        while(current!=null && !current.data.getKey().equals(key)){
            previous = current;
            current = current.next;
        }

        // 删除
        if(current == null)
            return null;  // 不存在关键字为key的节点
        else{
            if(previous == null)  // 表头
                first = first.next;
            else  // 非表头
                previous.next = current.next;
            return current.data;
        }
    }  // end delete()

}  // end LinkedList{}
