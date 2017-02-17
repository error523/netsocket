package com.mitewater.net.socketbio;

import java.io.IOException;
import java.net.*;

/**
 * Created with IntelliJ IDEA.
 * User: MiteWater
 * Date: 2017/2/17
 * Time: 13:15
 * Description: class for netsocket try it self
 */
public class UDPBIOClient {

    public static void main(String[] args) {
        try {
            //创建客户端
            DatagramSocket socket = new DatagramSocket();
            //创建发送数据
            byte[] data = new byte[65535];
            //放入数据
            data = "我是客户端发送的数据".getBytes();

            //创建发送的packet
            SocketAddress address = new InetSocketAddress("127.0.0.1",16000);

            DatagramPacket packet = new DatagramPacket(data,data.length,address);

            socket.send(packet);

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
