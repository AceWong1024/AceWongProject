/**
 * @author AceWong
 * @create 2022/6/26 12:48
 */
public class BinarySerach {
    public static void main(String[] args) {
        int[] data = {-1, 0, 3, 4, 5, 8, 9, 12};
        int i = binarySerach(data, 9);
        System.out.println(i);
    }

    private static int binarySerach(int[] data, int target) {
        int lPtr = 0, rPtr = data.length - 1;
        int tPtr;
        while (lPtr < rPtr) {
            tPtr = lPtr + rPtr >>> 1;
            if (data[tPtr] == target) {
                return tPtr;
            } else if (data[tPtr] < target) {
                lPtr = tPtr + 1;
            } else {
                rPtr = tPtr - 1;
            }
        }
        return -1;
    }
}
