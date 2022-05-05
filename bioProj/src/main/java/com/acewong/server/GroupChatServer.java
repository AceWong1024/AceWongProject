package com.acewong.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author AceWong
 * @date 2022/1/16
 */
public class GroupChatServer {
    private Selector selector;
    private static final int PORT = 6666;
    private ServerSocketChannel serverSocketChannel;
    private ByteBuffer byteBuffer;

    public GroupChatServer() {
        try {
            byteBuffer = ByteBuffer.allocate(100);
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            // 非阻塞
            serverSocketChannel.configureBlocking(false);
            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            while (true) {
                int count = selector.select(2000);
                if (count > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey next = iterator.next();
                        if (next.isAcceptable()) {
                            SocketChannel accept = serverSocketChannel.accept();
                            accept.configureBlocking(false);
                            accept.register(selector, SelectionKey.OP_READ);
                            System.out.println("accept:" + accept.getRemoteAddress());
                        }
                        if (next.isReadable()) {
                            //SocketChannel accept = serverSocketChannel.accept();
                            readData(next);
                            //accept.read(byteBuffer);
                            //System.out.println("read:" + byteBuffer);
                        }
                        // 移除
                        iterator.remove();
                    }
                } else {
                    System.out.println("waiting..");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readData(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = null;
        try {
            socketChannel = (SocketChannel) selectionKey.channel();
            byteBuffer.clear();
            int count = socketChannel.read(byteBuffer);
            if (count > 0) {
                String msg = new String(byteBuffer.array());
                System.out.println("from client:" + msg);
                sendMsgToAll(msg, socketChannel);
            }
        } catch (IOException e) {
            e.printStackTrace();
            selectionKey.cancel();
            socketChannel.close();
        }
    }

    private void sendMsgToAll(String msg, SocketChannel self) throws IOException {
        for (SelectionKey key : selector.keys()) {
            Channel channel = key.channel();
            if (channel instanceof SocketChannel && channel != self) {
                SocketChannel target = (SocketChannel) channel;
                ByteBuffer data = ByteBuffer.wrap(msg.getBytes());
                target.write(data);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
