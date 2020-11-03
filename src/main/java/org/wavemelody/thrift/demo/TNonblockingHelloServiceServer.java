package org.wavemelody.thrift.demo;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * 4.3  TNonblockingServer 服务模型
 * 使用非阻塞式IO，服务端和客户端需要指定 TFramedTransport 数据传输的方式。
 */
public class TNonblockingHelloServiceServer {
    public static void main(String[] args) {
        TNonblockingHelloServiceServer serviceServer = new TNonblockingHelloServiceServer();
        serviceServer.startServer();
    }

    public void startServer() {
        try {
            System.out.println("HelloWorldServer start ... ");
            TNonblockingServerSocket tnbSocket = new TNonblockingServerSocket(8888);
            TNonblockingServer.Args tArgs = new TNonblockingServer.Args(tnbSocket);
            TProcessor tProcessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldServiceImpl());
            tArgs.processor(tProcessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            tArgs.transportFactory(new TFramedTransport.Factory());
            // 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
            TNonblockingServer server = new TNonblockingServer(tArgs);
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }


}
