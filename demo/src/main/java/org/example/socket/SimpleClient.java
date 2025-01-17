package org.example.socket;

import java.io.*;
import java.net.*;
 
public class SimpleClient {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 12345); // 连接到本地主机的12345端口
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String userInputLine;
 
            while ((userInputLine = userInput.readLine()) != null) {
                out.println(userInputLine); // 发送用户输入到服务器
                System.out.println("Client sent: " + userInputLine);
 
                String serverResponseLine = in.readLine(); // 接收来自服务器的响应
                System.out.println("Server replied: " + serverResponseLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}