package com.novellatonyatt.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author: Zhuang HaoTang
 * @create: 2019-10-30 15:57
 * @description:
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当一个新的Channel建立时被调用
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        ByteBufAllocator byteBufAllocator = ctx.alloc();
        ByteBuf byteBuf = byteBufAllocator.buffer(4); // 初始化容量为4个字节的Buffer
        byteBuf.writeInt((int) (System.currentTimeMillis() / 1000L));
        ChannelFuture channelFuture = ctx.writeAndFlush(byteBuf); // 将Buffer中的数据写入到Channel
        // Netty里的所有操作都是异步的
        // 因此关闭连接需要在ChannelFuture.operationComplete里完成(避免数据还未写入成功,连接就被关闭了)
        // 或者直接使用ChannelFutureListener中预定义的匿名内部类
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                String clientAddress = channelFuture.channel().localAddress().toString();
                System.out.println(clientAddress + " 处理完毕");
                channelFuture.channel().close();
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //发生异常关闭Channel
        ctx.close();
    }
}
