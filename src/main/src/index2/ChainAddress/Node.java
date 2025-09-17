package index2.ChainAddress;


/**
 * @Auther: cty
 * @Date: 2020/6/15 19:41
 * @Description:
 * @version: 1.0
 */
public class Node {
    public DataItem data;
    public Node next;

    public Node(){}

    public Node(DataItem data){
        this.data = data;
    }
}  // end Node{}
