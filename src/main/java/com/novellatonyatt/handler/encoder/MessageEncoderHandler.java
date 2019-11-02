package com.novellatonyatt.handler.encoder;

import com.novellatonyatt.pojo.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author: Zhuang HaoTang
 * @create: 2019-10-31 16:14
 * @description:
 */
public class MessageEncoderHandler extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        Message message = (Message) o;
        byteBuf.writeBytes(message.getContent().getBytes());
    }

}
