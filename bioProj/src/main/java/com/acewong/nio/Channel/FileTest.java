package com.acewong.nio.Channel;

import sun.nio.ch.FileChannelImpl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author AceWong
 * @date 2022/1/9
 */
public class FileTest {
    public static void main(String[] args) throws IOException {
        String str = "hello,acewong";
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\AceWong\\Desktop\\test01.txt");
        FileChannel fileChannel = fileOutputStream.getChannel();
        fileChannel.write(ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_8)));
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes(StandardCharsets.UTF_8));
        byteBuffer.flip();
        fileChannel.write(byteBuffer);
    }
}
