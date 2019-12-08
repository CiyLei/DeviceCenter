package com.ciy.device_center;

import com.ciy.device_center.handler.DCActionHandler;
import com.ciy.device_center.handler.DCDecoderHandler;
import com.ciy.device_center.handler.DCEncoderHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeviceCenterApplication implements CommandLineRunner {

    @Value("${netty.port}")
    private int port;

    public static void main(String[] args) {
        SpringApplication.run(DeviceCenterApplication.class, args);
    }

    /**
     * 添加一个model并实现CommandLineRunner接口，实现功能的代码放在实现的run方法中。
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new DCDecoderHandler());
                        channel.pipeline().addLast(new DCEncoderHandler());
                        channel.pipeline().addLast(new DCActionHandler());
                    }
                });

        // 添加jvm关闭的时候钩子
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            }
        });

        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
        channelFuture.channel().closeFuture().sync();
    }
}
