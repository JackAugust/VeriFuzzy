package index3;



public class TreeNode {
    public String data;
    public char res;
    public TreeNode left;  // 左子节点
    public TreeNode right; // 右子节点

    public TreeNode() {}

    public TreeNode(String data, char res) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.res = res;
    }
    public TreeNode(String data) {
        this.data = data;
        this.left = null;
        this.right = null;

    }
    // 可以添加其他方法，如添加子节点、搜索节点等
}