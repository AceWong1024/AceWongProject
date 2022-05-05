package com.acewong.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author AceWong
 * @date 2022/1/8
 */
public class BioSever {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            while (true){
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                System.out.println("success" + inputStream.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handler(){

    }
}
