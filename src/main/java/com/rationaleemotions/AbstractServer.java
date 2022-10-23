package com.rationaleemotions;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public abstract class AbstractServer {

  public final void boot() throws InterruptedException {
    ServerBootstrap bootstrap = new ServerBootstrap();
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup worker = new NioEventLoopGroup();
    try {
      ChannelFuture future = bootstrap.group(boss, worker)
          .channel(NioServerSocketChannel.class)
          .handler(new LoggingHandler(LogLevel.INFO))
          .childHandler(handler())
          .bind(8080)
          .sync();
      future.channel().closeFuture().sync();
    } finally {
      worker.shutdownGracefully();
      boss.shutdownGracefully();
    }
  }

  protected abstract ChannelHandler handler();

}
