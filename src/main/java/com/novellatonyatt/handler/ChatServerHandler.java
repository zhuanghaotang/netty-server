package com.novellatonyatt.handler;

import com.novellatonyatt.pojo.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author: Zhuang HaoTang
 * @create: 2019-10-31 11:59
 * @description:
 */
public class ChatServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        System.out.println(message.getContent());
        Message response = new Message("nice to meet you");
        ctx.writeAndFlush(response);
    }
}
