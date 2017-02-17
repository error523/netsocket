package com.mitewater.net.socketbio;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created with IntelliJ IDEA.
 * User: MiteWater
 * Date: 2017/2/17
 * Time: 13:15
 * Description: class for netsocket try it self
 */
public class UDPBIOServer {


    public static void main(String[] args) {
        try {
            //创建服务器
            DatagramSocket socket = new DatagramSocket(16000);
            //创建一个缓冲区
            byte[] buffer = new byte[65535];
            //创建接收的缓冲区
            DatagramPacket receivePacket = new DatagramPacket(buffer,buffer.length);
            //接收数据
            socket.receive(receivePacket);

            String str = new String(buffer,"utf-8");

            System.out.println(str);

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
