import io.netty.handler.codec.string.StringDecoder;
import sun.nio.cs.ext.GB18030;
import sun.nio.cs.ext.GBK;

import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * @author AceWong
 * @create 2022/6/26 12:48
 */
public class BinarySerach {
    private static final Character SPACE = ' ';

    public static String preProcess(String s){
        String upper = s.toUpperCase();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char character = upper.charAt(i);
            if(character >= '0' && character <= '9'){
                stringBuilder.append(character);
            }
            if(character >= 'A' && character <= 'Z'){
                stringBuilder.append(character);
            }
        }
        return stringBuilder.toString();
    }


    public static boolean isPalindrome(String s) {
        s = preProcess(s);
        int middleIndex = s.length() / 2;
        for (int i = s.length() - 1; i >= middleIndex; i--) {
            if (s.charAt(i) != s.charAt(s.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static int lengthOfLastWord(String s) {
        int startIndex = -1;
        for (int i = s.length(); i > 0; i--) {
            if (SPACE.equals(s.charAt(i - 1))) {
                if (startIndex != -1) {
                    return startIndex - i;
                }
            } else {
                if (startIndex == -1) {
                    startIndex = i;
                }
            }
        }
        return startIndex;
    }

    public static void main(String[] args) {
//        lengthOfLastWord("aas");
        isPalindrome("raceacar");

        GBK gbk = new GBK();
        CharsetEncoder charsetEncoder = gbk.newEncoder();
        GB18030 gb18030 = new GB18030();
        CharsetEncoder charsetEncoder1 = gb18030.newEncoder();
        charsetEncoder.canEncode("●");

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
