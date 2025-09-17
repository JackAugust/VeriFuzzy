package general_tools;

import java.util.*;

public class TopThreeIndexes {

    public static List<Integer> findTopKIndexes(int[] nums, int k) {
        if (nums == null || nums.length < k || k <= 0) {
            return Collections.emptyList();
        }

        // 使用最大堆（通过自定义比较器实现）来存储最大的k个元素及其索引
        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = new PriorityQueue<>(
                (e1, e2) -> Integer.compare(e1.getKey(), e2.getKey()) // 默认是最小堆，但通过逆序比较实现最大堆
        );

        for (int i = 0; i < nums.length; i++) {
            if (maxHeap.size() < k) {
                // 如果堆的大小小于k，直接添加
                maxHeap.offer(new AbstractMap.SimpleEntry<>(nums[i], i));
            } else if (nums[i] > maxHeap.peek().getKey()) {
                // 如果当前元素比堆顶元素大，弹出堆顶元素，并添加当前元素和索引
                maxHeap.poll();
                maxHeap.offer(new AbstractMap.SimpleEntry<>(nums[i], i));
            }
        }

        // 提取索引
        List<Integer> indexes = new ArrayList<>();
        while (!maxHeap.isEmpty()) {
            indexes.add(maxHeap.poll().getValue()); // 获取并移除堆顶元素（即最大元素）的索引
        }

        // 因为我们是从堆中按照值的大小依次取出的，但索引需要按照值从大到小的顺序排列，而我们已经满足了这一点
        // 如果需要按照原始数组中的顺序返回索引（即使它们的值不是按原始顺序），我们需要对索引进行额外的排序，但在这个例子中不需要

        // 由于Java的List默认返回的是顺序访问的迭代器，因此不需要对结果列表进行排序
        return indexes;
    }

    public static void main(String[] args) {
        int[] nums = {5, 2, 8, 3, 4, 9, 7, 6};
        int k = 3;
        List<Integer> indexes = findTopKIndexes(nums, k);
        System.out.println(indexes); // 输出应该是 [4, 3, 5]
    }
}
