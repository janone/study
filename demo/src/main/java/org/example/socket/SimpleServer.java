package org.example.socket;

import java.io.*;
import java.net.*;
 
public class SimpleServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(12345); // 监听12345端口
            Socket clientSocket = serverSocket.accept(); // 等待客户端连接
            System.out.println("Client connected!");
 
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
 
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Server received: " + inputLine);
                out.println(inputLine); // 将接收到的消息发送回客户端
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
}