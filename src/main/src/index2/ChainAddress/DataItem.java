package index2.ChainAddress;

import java.math.BigInteger;

/**
 * @Auther: cty
 * @Date: 2020/6/13 15:53
 * @Description:
 * @version: 1.0
 */
public class DataItem {
    private BigInteger key;
    private Object data;

    public DataItem(BigInteger key){
        this.key = key;
    }

    public DataItem(BigInteger key, Object data){
        this.key = key;
        this.data = data;
    }

    public void displayItem(){
        System.out.print(key);
    }

    public void setItem(DataItem item){
        this.key = item.getKey();
        this.data = item.getData();
    }

    public DataItem getItem(){
        DataItem item = new DataItem(this.key);
        item.setData(this.data);
        return item;
    }

    public BigInteger getKey() {
        return key;
    }

    public void setKey(BigInteger key) {
        this.key = key;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "key=" + key +
                ", data=" + data +
                '}';
    }
}  // end DataItem{}
