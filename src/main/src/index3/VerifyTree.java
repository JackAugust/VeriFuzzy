package index3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VerifyTree {
    public TreeNode root; // 二叉树的根节点

    public VerifyTree() {
        this.root = null;
    }

    public static TreeNode buildTree(List<DataObject> paths){
        // 使用Comparator.comparingInt函数根据字符串长度进行比较
        // 使用Collections.sort()方法和自定义比较器对列表paths进行排序
        // 根据data的字符串长度进行排序
        Collections.sort(paths, new Comparator<DataObject>() {
            @Override
            public int compare(DataObject o1, DataObject o2) {
                return Integer.compare(o1.getData().length(), o2.getData().length());
            }
        });

//        for (DataObject obj :paths){
//            System.out.println(obj.data+"，"+obj.value);
//        }
        if (!paths.get(0).data.equals("")){
            return null;
        }


        //创建根节点
        TreeNode root = new TreeNode("");
        for (int i=1;i<paths.size();i++){
            if (!add(root,paths.get(i))){
                return null;
            }
        }

        return root;

    }

    // 添加节点
    public static boolean add(TreeNode root, DataObject data) {

       TreeNode pre_node = findPreNode(root, data.data);
       if (pre_node != null){
           TreeNode newnode = new TreeNode(data.data,data.value);
           if (data.data.charAt(data.data.length()-1)=='0'){
               pre_node.left = newnode;
           }else {
               pre_node.right = newnode;
           }
           return true;
       }
       return false;
    }

    // 找到父节点
    private static TreeNode findPreNode(TreeNode node, String data) {
//        if (node == null||data==null) {
//            return null;
//        }
//        if (node.data.equals(data.substring(0,node.data.length()))){
//            if (data.length()-node.data.length()>1){
//                //继续找父节点
//                findPreNode(node.left,data);
//                findPreNode(node.right,data);
//            }else if (data.length()-node.data.length()==1){
//                //找到父节点了
//
//                return node;
//            }
//        }
//        return null;

        if (node == null) {
            return null;
        }

        // 检查当前节点是否是父节点
        if (data.startsWith(node.data) && (data.length() == node.data.length() + 1)) {
            return node;
        }

        // 递归查找左子树和右子树
        TreeNode leftResult = findPreNode(node.left, data);
        if (leftResult != null) {
            return leftResult;
        }
        return findPreNode(node.right, data);

    }

    public static void preOrderTraversal(TreeNode root) {
        if (root != null) {
            // 访问根节点
            if (root.data.equals("")){
                System.out.println("根节点的空字符串！"+root.res);
            }else {
                System.out.println(root.data+"，"+root.res);
            }
            // 遍历左子树
            preOrderTraversal(root.left);
            // 遍历右子树
            preOrderTraversal(root.right);
        }
    }

    public static boolean checkTree(TreeNode root, int bits){
        if (root != null) {
            // 访问非终端节点
            if (root.left!=null || root.right!=null){
//                System.out.println("访问非终端节点"+root.data+"，"+root.res);
                if (root.res=='0'){
//                    System.out.println("访问非终端节点"+root.data+"，"+root.res);
                    return false;
                }
            }else {
                //非叶节点的终端节点
                if (root.data.length()!=bits){
//                    System.out.println("终端节点！"+root.data+"，"+root.res);
                    if (root.res!='0'){
//                        System.out.println("终端节点！"+root.data+"，"+root.res);
                        return false;
                    }
                }
            }
            // 遍历左子树
            boolean res1 = checkTree(root.left,3);
            // 遍历右子树
            boolean res2 =  checkTree(root.right,3);

            if (res1==false || res2==false){
                return false;
            }

        }
       return true;
    }

/*
访问非终端节�?�?
访问非终端节�?0�?1
终端节点�?00�?0
终端节点�?01�?0
访问非终端节�?1�?1
访问非终端节�?10�?1
终端节点�?11�?1
 */

    public static void main(String[] args){
        List<DataObject> lists = new ArrayList<>();
        lists.add(new DataObject("",'1'));
        lists.add(new DataObject("01",'0'));
        lists.add(new DataObject("0",'1'));
        lists.add(new DataObject("1",'1'));
        lists.add(new DataObject("00",'0'));
        lists.add(new DataObject("10",'1'));
        lists.add(new DataObject("11",'0'));
        lists.add(new DataObject("101",'1'));
        TreeNode root = buildTree(lists);
        if (root!=null){
            preOrderTraversal(root);
        }else {
            System.out.println("建树失败！");
        }

        if (checkTree(root,3)){
            System.out.println("检查通过22222222");
        }else {
            System.out.println("检查不通过1111111");
        }

    }



    // 可以在这里添加其他方法，如遍历树、搜索节点、删除节点等
}