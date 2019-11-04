package com.novellatonyatt.server;

import com.novellatonyatt.handler.HttpServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @author: Zhuang HaoTang
 * @create: 2019-10-30 11:53
 * @description:
 */
public class Server {

    private void start() {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class) // 配置父Channel
                .childHandler(new ChannelInitializer<SocketChannel>() { // 配置子Channel与Handler的关系
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        // 增加多个ChannelHandler到子Channel的Pipeline里面
                        socketChannel.pipeline().addLast(
                                new HttpRequestDecoder(),
                                new HttpObjectAggregator(65535),
                                new HttpResponseEncoder(),
                                new HttpServerHandler()
                        );
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128) // 给父Channel配置参数
                .childOption(ChannelOption.SO_KEEPALIVE, true); // 给子Channel配置参数

        try {
            // 绑定端口，启动服务
            System.out.println("start server and bind 8888 port ...");
            serverBootstrap.bind(8888).sync();
        } catch (InterruptedException e) {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

}
