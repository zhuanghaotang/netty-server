package com.novellatonyatt.handler;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @Author: Zhuang HaoTang
 * @Date: 2019-11-04 10:30
 * @Description:
 */
public class HttpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest httpRequest = (FullHttpRequest) msg;
        String url = httpRequest.uri(); // URL
        ByteBuf byteBuf = httpRequest.content(); // 请求体
        HttpMethod httpMethod = httpRequest.method(); // 请求方法
        HttpHeaders httpHeaders = httpRequest.headers(); // 请求头

        System.out.println("url = " + url);
        System.out.println("method = " + httpMethod.name());
        System.out.println("headers = " + JSON.toJSONString(httpHeaders.entries()));
        System.out.println("body = " + byteBuf.toString(CharsetUtil.UTF_8));

        FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8));
        httpResponse.headers().set("Content-Length",httpResponse.content().readableBytes());
        ctx.writeAndFlush(httpResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
