package com.acewong.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;
import org.springframework.http.HttpRequest;

import java.nio.charset.StandardCharsets;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] reqByte = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(reqByte);
        String reqStr = new String(reqByte, StandardCharsets.UTF_8);
        System.out.println("接收到请求：" + reqStr);
        ctx.writeAndFlush(Unpooled.copiedBuffer("来自服务端的响应".getBytes(StandardCharsets.UTF_8)));
    }
}
