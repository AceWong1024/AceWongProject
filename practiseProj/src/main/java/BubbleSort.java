import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @author AceWong
 * @create 2022/6/26 12:58
 */
public class BubbleSort {
        public static int maxSubArray(int[] nums) {
            int pre = 0;
            int res = nums[0];
            for (int num : nums) {
                pre = Math.max(pre + num, num);
                res = Math.max(res, pre);
            }
            return res;
        }
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(maxSubArray(new int[]{-1,-2,3,2,-1,3}));

    }

    private static void bubbleSort(int[] data) {
        for (int j = 0; j < data.length - 1; j++) {
            boolean swapped = false;
            for (int i = 0; i < data.length - 1 - j; ++i) {
                if(data[i] > data[i+1]){
                    int temp = data[i];
                    data[i] = data[i+1];
                    data[i+1] = temp;
                    swapped = true;
                }
            }
            if(!swapped) {
                return;
            }
        }
    }
}
