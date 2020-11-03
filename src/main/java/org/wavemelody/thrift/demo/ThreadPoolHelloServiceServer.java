package org.wavemelody.thrift.demo;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * 4.2 TThreadPoolServer 服务模型
 * 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
 */
public class ThreadPoolHelloServiceServer {
    public void startServer(){
        try {
            System.out.println("HelloWorldServer start ... ");
            TServerSocket serverTransport = new TServerSocket(8888);

            TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(serverTransport);
            TProcessor tProcessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldServiceImpl());
            tArgs.processor(tProcessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            // 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
            TThreadPoolServer server = new TThreadPoolServer(tArgs);
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        ThreadPoolHelloServiceServer serviceServer = new ThreadPoolHelloServiceServer();
        serviceServer.startServer();
    }
}
