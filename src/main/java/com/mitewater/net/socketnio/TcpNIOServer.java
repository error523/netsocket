package com.mitewater.net.socketnio;

import com.sun.corba.se.impl.presentation.rmi.DynamicMethodMarshallerImpl;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: MiteWater
 * Date: 2017/2/16
 * Time: 23:42
 * Description: class for netsocket try it self
 */
public class TcpNIOServer {


    public static void main(String[] args) {
        try {
            //创建一个NIO通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //设置非阻塞
            serverSocketChannel.configureBlocking(false);
            //通过通道创建一个socket服务
            ServerSocket serverSocket = serverSocketChannel.socket();
            //绑定端口
            serverSocket.bind(new InetSocketAddress(8000));
            //创建一个选择器，用于选择事件
            Selector selector = Selector.open();
            //注册这个选择器，并监听相应的事件
            //监听连接建立的时候
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            //轮询
            while(serverSocketChannel.isOpen()&&selector.isOpen()){
                int nkey = selector.select();
                //说明注册的事件发生了
                if (nkey>0){
                    Set<SelectionKey> keyStroke = selector.keys();
                    for(SelectionKey key:keyStroke){
                        if(key.isAcceptable()){
                            ServerSocketChannel socketChannel = (ServerSocketChannel) key.channel();
                            SocketChannel sc = socketChannel.accept();
                            if (null==sc){
                                continue;
                            }
                            sc.configureBlocking(false);
                            sc.register(key.selector(),SelectionKey.OP_READ);
                        }else if(key.isReadable()){
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            SocketChannel sc = (SocketChannel) key.channel();
                            try {
                                sc.read(buffer);
//                                while(!=-1){
                                    buffer.flip();
                                    Charset charset = null;
                                    CharsetDecoder charsetDecoder = null;
                                    CharBuffer charBuffer = null;
                                    charset = Charset.forName("UTF-8");
                                    charsetDecoder = charset.newDecoder();
                                    charBuffer = charsetDecoder.decode(buffer.asReadOnlyBuffer());
                                    //输出传入的内容
                                    System.out.println("客户端输入的内容是:"+charBuffer.toString());
//                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }finally {
                                buffer.clear();
                            }
                            sc.register(key.selector(),SelectionKey.OP_WRITE);
                        }else if(key.isWritable()){
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            ByteBuffer buffer = ByteBuffer.wrap("服务器输入内容是：哈哈哈".getBytes());
                            socketChannel.write(buffer);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
