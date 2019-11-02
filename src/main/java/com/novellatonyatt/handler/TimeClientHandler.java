package com.novellatonyatt.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @author: Zhuang HaoTang
 * @create: 2019-10-30 16:57
 * @description:
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        long currentTimeStamp = (byteBuf.readUnsignedInt()) * 1000;
        System.out.println(currentTimeStamp);
        System.out.println(new Date(currentTimeStamp));
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //发生异常关闭Channel
        ctx.close();
    }
}
