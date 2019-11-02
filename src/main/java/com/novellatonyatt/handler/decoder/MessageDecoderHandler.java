package com.novellatonyatt.handler.decoder;

import com.novellatonyatt.pojo.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author: Zhuang HaoTang
 * @create: 2019-10-31 10:26
 * @description: 数据拆分/转换Handler
 */
public class MessageDecoderHandler extends ByteToMessageDecoder {

    /**
     * 每当有数据到达时都会调用此方法，一旦将数据放入list中，那么ByteToMessageDecoder会清空累积在缓冲区的数据
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        StringBuilder content = new StringBuilder();
        while (byteBuf.isReadable()) {
            content.append((char) byteBuf.readByte());
        }
        Message message = new Message(content.toString());
        list.add(message);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
