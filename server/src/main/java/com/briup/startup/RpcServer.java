package com.briup.startup;

import com.briup.config.ChannelInit;
import com.briup.config.ConfigProperties;
import com.briup.handler.RpcServerHandler;
import com.briup.impl.UserServiceImpl;
import com.briup.interfaces.UserService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


import java.net.InetSocketAddress;

public class RpcServer {
    public static void main(String[] args) throws Exception {
        RpcServerHandler.addService("1.0",UserService.class,new UserServiceImpl());
        new RpcServer().start();
    }

    public void start() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup(); //创建 EventLoopGroup,即创建处理连接的线程
        NioEventLoopGroup work = new NioEventLoopGroup(); //创建 EventLoopGroup ，作为处理消息的线程
//        OioEventLoopGroup eventExecutors = new OioEventLoopGroup();//如果是同步，则是创建Oio
        try {
            ServerBootstrap b = new ServerBootstrap();//创建 ServerBootstrap
            ChannelFuture sync = b.group(group,work) //4绑定NioEventLoopGroup，一个处理连接，一个处理消息，可以只传一个NioEventLoopGroup，用单个线程处理连接和读写
                    .channel(NioServerSocketChannel.class)//设置通道
                    .localAddress(new InetSocketAddress(ConfigProperties.getInstanse().getServerPort()))    //绑定端口
                    .option(ChannelOption.SO_BACKLOG, 128) //当socket连接处理线程满时，用于存放连接请求的队列最大值，默认50
                    .handler(new ChannelInitializer<NioServerSocketChannel>() {
                        //指定在服务端启动过程中的一些逻辑，通常情况下我们用不着这个方法
                        protected void initChannel(NioServerSocketChannel nioServerSocketChannel) throws Exception {
                            System.out.println("config port is :"+ConfigProperties.getInstanse().getServerPort()+" server is startup ......");
                        }
                    })
                    .childHandler(new ChannelInit())//传入自定义的channel,channel是核心，就是通过channel进行添加handler,handler就相当于一条处理链
                    .bind()
                    .addListener((future)->{
                        //相当于 new GenericFutureListener<Future<? super Void>>()
                        if(future.isSuccess()){
                            System.out.println("port bind successfully！");
                        }else{
                            System.out.println("port bind error！");
                        }
                    })
                    .sync();//设置完值后进行绑定端口;sync等待服务器关闭（目测异步的意思）

            System.out.println(RpcServer.class.getName() + " started and listen on " + sync.channel().localAddress());
            sync.channel().closeFuture().sync();//关闭channel
        } finally {
            group.shutdownGracefully().sync();//关机的 EventLoopGroup，释放所有资源。
        }
    }

}
