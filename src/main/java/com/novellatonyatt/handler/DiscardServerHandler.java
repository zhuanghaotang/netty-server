package com.novellatonyatt.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author: Zhuang HaoTang
 * @create: 2019-10-30 11:43
 * @description:
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * 当Channel有数据可读时调用(到达channelRead不处理的数据将会丢失,同时不会重复触发读就绪)
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byteBuf.release();
    }

    /**
     * Handler在处理事件抛出异常时调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //发生异常关闭Channel
        ctx.close();
    }
}
