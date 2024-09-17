package leetcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AceWong
 * @create 2024/5/7 11:53
 */
public class MergeArray {
    public static void merge1(int[] nums1, int m, int[] nums2, int n) {
        List<Integer> integerList1 = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            integerList1.add(nums1[i]);
        }
        List<Integer> integerList2 = Arrays.stream(nums2).boxed().collect(Collectors.toList());
        integerList1.addAll(integerList2);
        Collections.sort(integerList1);
        int[] ints = integerList1.stream().mapToInt(value -> value).toArray();
        System.arraycopy(ints, 0, nums1, 0, m + n);
    }

    public static int maxProfit(int[] prices) {
        int maxPro = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            for (int j = i+1; j < prices.length; j++) {
                if(prices[j] - prices[i] > maxPro){
                    maxPro = prices[j] - prices[i];
                }
            }
        }
        return maxPro;
    }

    public static void main(String[] args) {
        int i = maxProfit(new int[]{7,6,4,3,1});
    }
}
