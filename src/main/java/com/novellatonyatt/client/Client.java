package com.novellatonyatt.client;

import com.novellatonyatt.handler.TimeClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author: Zhuang HaoTang
 * @create: 2019-10-30 16:35
 * @description:
 */
public class Client {

    public void start() {
        EventLoopGroup worker = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new TimeClientHandler());
                    }
                }); // 配置父Channel与Handler的关系

        try {
            bootstrap.connect(new InetSocketAddress(8888)).sync();
        } catch (InterruptedException e) {
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

}
