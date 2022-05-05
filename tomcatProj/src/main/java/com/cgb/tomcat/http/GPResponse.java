package com.cgb.tomcat.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.springframework.util.StringUtils;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author AceWong
 */
public class GPResponse {
    private ChannelHandlerContext channelHandlerContext;

    private HttpRequest request;

    private OutputStream out;

    public GPResponse(ChannelHandlerContext channelHandlerContext, HttpRequest request) {
        this.channelHandlerContext = channelHandlerContext;
        this.request = request;
    }

    public void write(String out) {
        try {
            if (StringUtils.isEmpty(out)) {
                return;
            }

            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(out.getBytes(StandardCharsets.UTF_8)));

            response.headers().set("Content-Type","text/html;");

            channelHandlerContext.write(response);
        }finally {
            channelHandlerContext.flush();
            channelHandlerContext.close();
        }
    }

    public GPResponse(OutputStream out) {
        this.out = out;
    }

//    public void write(String s) throws Exception {
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("HTTP/1.1 200 OK\n")
//                .append("Content-Type: text/html;\n")
//                .append("\r\n")
//                .append(s);
//        out.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
//    }
}
