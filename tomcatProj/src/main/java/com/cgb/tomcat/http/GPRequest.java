package com.cgb.tomcat.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GPRequest {
    private ChannelHandlerContext channelHandlerContext;
    private HttpRequest request;
    private String method;
    private String url;

    public Map<String, List<String>> getParameters() {
        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
        return decoder.parameters();
    }

    public String getParameter(String name) {
        Map<String, List<String>> params = getParameters();
        List<String> param = params.get(name);
        if (Objects.isNull(param)) {
            return null;
        } else {
            return param.get(0);
        }
    }

    public GPRequest(ChannelHandlerContext channelHandlerContext, HttpRequest request) {
        this.channelHandlerContext = channelHandlerContext;
        this.request = request;
    }

    public GPRequest(InputStream in) {
        String content = "";
        try {
            byte[] buff = new byte[1024];
            int len = 0;
            if ((len = in.read(buff)) > 0) {
                content = new String(buff, 0, len);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String line = content.split("\\n")[0];
        String[] arr = line.split("\\s");
        this.method = arr[0];
        this.url = arr[1].split("\\?")[0];
    }

    public String getMethod() {
        return request.getMethod().name();
    }

    public String getUrl() {
        return request.uri();
    }
}
