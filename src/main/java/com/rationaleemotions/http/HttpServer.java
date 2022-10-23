package com.rationaleemotions.http;

import com.rationaleemotions.AbstractServer;
import io.netty.channel.ChannelHandler;

public class HttpServer extends AbstractServer {

  public static void main(String[] args) throws InterruptedException {
    new HttpServer().boot();
  }

  @Override
  protected ChannelHandler handler() {
    return new HttpInitializer();
  }
}
