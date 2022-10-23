package com.rationaleemotions.telnet;

import com.rationaleemotions.AbstractServer;
import io.netty.channel.ChannelHandler;

public class TelnetServer extends AbstractServer {

  public static void main(String[] args) throws InterruptedException {
    new TelnetServer().boot();
  }

  @Override
  protected ChannelHandler handler() {
    return new TelnetInitializer();
  }
}
