package com.rationaleemotions.http;

import com.google.gson.JsonElement;
import com.rationaleemotions.http.paths.RouteFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import java.nio.charset.StandardCharsets;

public class HttpReadHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    if (msg instanceof FullHttpRequest) {
      channelRead0(ctx, (FullHttpRequest) msg);
    } else {
      ctx.fireChannelRead(msg);
    }
  }
  protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) {
    boolean keepOpen = HttpUtil.isKeepAlive(msg);
    if (HttpUtil.is100ContinueExpected(msg)) {
      ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE));
    }
    JsonElement processed = RouteFactory.process(msg);
    write(ctx, keepOpen, processed);
  }

  private static void write(ChannelHandlerContext ctx, boolean isAlive, JsonElement json) {
    DefaultFullHttpResponse response = okResponse(json);
    ChannelFuture future = ctx.channel().write(response);
    if (!isAlive) {
      future.addListener(ChannelFutureListener.CLOSE);
    }
  }

  private static DefaultFullHttpResponse okResponse(JsonElement payload) {
    ByteBuf bytes = Unpooled.wrappedBuffer(payload.toString().getBytes(StandardCharsets.UTF_8));
    DefaultFullHttpResponse response =
        new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, bytes);
    HttpHeaders headers = response.headers();
    headers.add(HttpHeaderNames.CONTENT_TYPE, "application/json");
    headers.add(HttpHeaderNames.CONTENT_LENGTH, bytes.readableBytes());
    return response;
  }
}
