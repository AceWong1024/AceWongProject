package com.cgb.tomcat.netty;

import com.cgb.tomcat.http.GPRequest;
import com.cgb.tomcat.http.GPResponse;
import com.cgb.tomcat.http.GPServlet;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.util.concurrent.EventExecutorGroup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author AceWong
 */
public class NettyTomcat {
    private int port = 8880;
    private ServerSocket serverSocket;
    private Map<String, GPServlet> servletMap = new HashMap<>();
    private Properties webxml = new Properties();

    private void init() {
        String WEB_INF = this.getClass().getResource("/").getPath();
        try {
            FileInputStream fileInputStream = new FileInputStream(WEB_INF + "web.properties");

            webxml.load(fileInputStream);

            for (Object k : webxml.keySet()) {
                String key = k.toString();
                if (key.endsWith(".url")) {
                    String servletName = key.replaceAll("\\.url$", "");
                    String url = webxml.getProperty(key);
                    String className = webxml.getProperty(servletName + ".className");
                    GPServlet gpServlet = (GPServlet) Class.forName(className).newInstance();
                    servletMap.put(url, gpServlet);
                }
            }
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void start() {
        init();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new HttpResponseEncoder());
                        socketChannel.pipeline().addLast(new HttpRequestDecoder());
                        socketChannel.pipeline().addLast(new NettyTomcatHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        try {
            ChannelFuture future = serverBootstrap.bind(port).sync();
            System.out.println("NettyTomcat has Started...");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class NettyTomcatHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof HttpRequest) {
                System.out.println("hello");
                HttpRequest request = (HttpRequest) msg;

                GPRequest gpRequest = new GPRequest(ctx,request);
                GPResponse gpResponse = new GPResponse(ctx,request);

                String url  = gpRequest.getUrl();
                if(servletMap.containsKey(url)){
                    servletMap.get(url).service(gpRequest,gpResponse);
                }else{
                    gpResponse.write("404 - NOT FOUND");
                }
            }
        }
    }

    public static void main(String[] args) {
        new NettyTomcat().start();
    }
}
