package index3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// 定义一个类来封装String和char
public class DataObject {
    String data;
    char value;

    // 构造函数
    public DataObject(String data, char value) {
        this.data = data;
        this.value = value;
    }

    // Getter 和 Setter（可选）
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    // toString方法，方便打印对象信息
    @Override
    public String toString() {
        return "DataObject{" +
                "data='" + data + '\'' +
                ", value=" + value +
                '}';
    }


    public static void main(String[] args) {
        // 创建一个列表来存储DataObject对象
        List<DataObject> list = new ArrayList<>();

        // 向列表中添加几个DataObject对象
        list.add(new DataObject("Hello", 'a'));
        list.add(new DataObject("World", 'b'));
        list.add(new DataObject("Java", 'c'));

        // 使用Collections.sort()方法和自定义比较器对列表进行排序
        // 根据data的字符串长度进行排序
        Collections.sort(list, new Comparator<DataObject>() {
            @Override
            public int compare(DataObject o1, DataObject o2) {
                return Integer.compare(o1.getData().length(), o2.getData().length());
            }
        });

        // 输出排序后的列表
        for (DataObject obj : list) {
            System.out.println(obj);
        }
    }
}