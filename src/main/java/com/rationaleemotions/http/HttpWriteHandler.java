package com.rationaleemotions.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class HttpWriteHandler extends ChannelOutboundHandlerAdapter {

  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
    promise.addListener(f -> {
      if (f.isSuccess()) {
        System.err.println("Success");
      } else {
        System.err.println("Failed");
        f.cause().printStackTrace();
      }
    });
    ctx.writeAndFlush(msg, promise);
  }
}
