package com.acewong.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author AceWong
 * @date 2022/1/16
 */
public class GroupChatClient {
    private static final String HOST = "localhost";
    private static final int SERVER_PORT = 6666;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public GroupChatClient() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, SERVER_PORT));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(username + " is OK.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendInfo(String info){
        info = username + " said:" + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes(StandardCharsets.UTF_8)));
            System.out.println("Client sent:" + info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Socket socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress(6666);
        try {
            socket.bind(socketAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readInfo(){
        try {
            int readChannels = selector.select();
            if(readChannels > 0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    if (next.isReadable()) {
                        SocketChannel sc = (SocketChannel) next.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
                        sc.read(byteBuffer);
                        System.out.println("Client read:" + new String(byteBuffer.array()));
                    }
                    iterator.remove();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupChatClient groupChatClient = new GroupChatClient();
        new Thread(() -> {
            while (true){
                groupChatClient.readInfo();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);

        while(scanner.hasNextLine()){
            String msg = scanner.nextLine();
            groupChatClient.sendInfo(msg);
        }
    }
}
