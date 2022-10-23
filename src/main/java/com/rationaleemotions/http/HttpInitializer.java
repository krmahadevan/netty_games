package com.rationaleemotions.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel ch) {
    ch.pipeline()
        .addLast(
            // This will ensure that HttpRequestDecoder and HttpResponseEncoder get added before
            new HttpServerCodec()
        )
        .addLast(new HttpObjectAggregator(Integer.MAX_VALUE)) // This will give us FullHttpRequest
        .addLast(new HttpReadHandler())
        .addLast(new HttpWriteHandler());
  }
}
