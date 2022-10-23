package com.rationaleemotions.telnet;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class TelnetInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel ch) {
    ch.pipeline()
        .addLast(new StringDecoder())
        .addLast(new StringEncoder())
        .addLast(new TelnetReadHandler())
        .addLast(new TelnetWriteHandler());
  }

}
