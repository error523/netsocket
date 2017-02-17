package com.mitewater.net.mina;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: MiteWater
 * Date: 2017/2/17
 * Time: 15:00
 * Description: class for netsocket try it self
 */
public class MinaSocketServer {
    public static void main(String[] args) {
        //创建一个单线程的服务器
        IoAcceptor ioAcceptor = new NioSocketAcceptor(1);
        //添加序列化，反序列话解析器
        ioAcceptor.getFilterChain().addLast("stringserialize",
                new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        IoHandler handler = new IoHandlerAdapter(){
            @Override
            public void messageReceived(IoSession session, Object message){
                System.out.println("服务器收到了:"+message.toString());
                session.write("返回一条信息给客户端");
            }
        };
        SocketAddress address = new InetSocketAddress("127.0.0.1",8000);
        try {
            //绑定处理句柄，这个放在bind前面
            ioAcceptor.setHandler(handler);
            //绑定监听地址
            ioAcceptor.bind(address);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
