package com.mitewater.net.mina;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: MiteWater
 * Date: 2017/2/17
 * Time: 15:00
 * Description: class for netsocket try it self
 */
public class MinaSocketClient {

    public static void main(String[] args) {
        //创建连接器
        IoConnector connector = new NioSocketConnector(1);
        //创建消息序列化器
        connector.getFilterChain().addLast("stringserilize",
                new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        connector.setHandler(new IoHandlerAdapter(){
            @Override
            public void messageReceived(IoSession session, Object message) throws Exception {
                System.out.println("从服务器接收到的消息是:"+message);
            }
        });
        ConnectFuture connectFuture = connector.connect(new InetSocketAddress("127.0.0.1",8000));
        connectFuture.awaitUninterruptibly();
        connectFuture.getSession().write("访问服务器");
    }

}
