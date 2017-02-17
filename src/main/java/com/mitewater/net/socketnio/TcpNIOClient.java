package com.mitewater.net.socketnio;

import com.sun.org.apache.bcel.internal.generic.Select;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.rmi.server.RemoteServer;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: MiteWater
 * Date: 2017/2/16
 * Time: 23:43
 * Description: class for netsocket try it self
 */
public class TcpNIOClient {


    public static void main(String[] args) {
        SocketAddress socketAddress = new InetSocketAddress("127.0.0.1",8000);
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(socketAddress);
            //非阻塞
            socketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            //注册感兴趣的时间
            socketChannel.register(selector, SelectionKey.OP_CONNECT|SelectionKey.OP_WRITE|SelectionKey.OP_READ);
            while(selector.isOpen()&&socketChannel.isConnected()){
                int key = selector.select();
                if(key>0){
                    Set<SelectionKey> selectionKeys = selector.keys();
                    for(SelectionKey selectionKey:selectionKeys){
                        if(selectionKey.isConnectable()){
                            System.out.println("已经连接上了!");
                        }else if(selectionKey.isWritable()){
                            SocketChannel sc = (SocketChannel) selectionKey.channel();
                            ByteBuffer buffer = ByteBuffer.wrap("你好服务器".getBytes());
                            sc.write(buffer);
//                            selector.close();
//                            socketChannel.finishConnect();
//                            socketChannel.close();
                        }else if(selectionKey.isReadable()){
                            SocketChannel sc = (SocketChannel) selectionKey.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(2048);
                            sc.read(buffer);
//                            while(sc.read(buffer)!=-1){
                                buffer.flip();
                                Charset charset = null;
                                CharsetDecoder charsetDecoder = null;
                                CharBuffer charBuffer = null;
                                charset = Charset.forName("UTF-8");
                                charsetDecoder = charset.newDecoder();
                                charBuffer = charsetDecoder.decode(buffer.asReadOnlyBuffer());
                                //输出传入的内容
                                System.out.println("服务器输入的内容是:"+charBuffer.toString());
                                buffer.clear();
//                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
