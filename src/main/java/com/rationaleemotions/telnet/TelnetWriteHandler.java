package com.rationaleemotions.telnet;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class TelnetWriteHandler extends ChannelOutboundHandlerAdapter {

  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
    promise.addListener(future -> {
      if (future.isSuccess()) {
        System.err.println("Write successful");
      } else {
        System.err.println("Write failed");
        future.cause().printStackTrace();
      }
    });
    ctx.writeAndFlush(msg, promise);
  }
}
