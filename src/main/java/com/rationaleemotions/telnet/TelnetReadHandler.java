package com.rationaleemotions.telnet;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TelnetReadHandler extends SimpleChannelInboundHandler<String> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String msg) {
    String cmd = msg.toLowerCase().trim();
    String result;
    boolean bye = false;

    switch (cmd) {
      case "help":
        result = "Supported Commands : (hi, bye, help)";
        break;
      case "hi":
        result = "Ahoy there.. Howdy !";
        break;
      case "bye":
        result = "C ya!";
        bye = true;
        break;
      default:
        result = "Unrecognized command";
    }
    ChannelFuture future = ctx.channel().writeAndFlush(result + "\r\n");
    if (bye) {
      future.addListener(ChannelFutureListener.CLOSE);
    }
  }
}
