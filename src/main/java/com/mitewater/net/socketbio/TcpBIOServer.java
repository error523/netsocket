package com.mitewater.net.socketbio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: MiteWater
 * Date: 2017/2/16
 * Time: 22:55
 * Description: class for netsocket try it self
 */
public class TcpBIOServer {

    public static void main(String[] args) {
        try {
            //创建服务器
            ServerSocket serverSocket = new ServerSocket(8000);
            //接受客户端请求并返回一个socket对象
            Socket socket = serverSocket.accept();
            //接收到server收到的输入流
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //打印收到的流
            System.out.println("我接受到的是:"+bufferedReader.readLine());
            //返回一个向欧盟向
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.write("我是服务器返回的信息");
            printWriter.flush();
            bufferedReader.close();
            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
