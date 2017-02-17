package com.mitewater.net.socketbio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: MiteWater
 * Date: 2017/2/16
 * Time: 22:55
 * Description: class for netsocket try it self
 */
public class TcpBIOClient {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            //创建一个客户端
            socket = new Socket();
            //连接服务器
            socket.connect(new InetSocketAddress("127.0.0.1",8000));
            //创建写入器
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //向服务器写内容
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            printWriter.println("你好,服务器");
            printWriter.flush();
            //接受服务器返回信息
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("服务器返回信息："+bufferedReader.readLine());

            bufferedReader.close();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
