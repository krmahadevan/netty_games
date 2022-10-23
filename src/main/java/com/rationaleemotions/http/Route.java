package com.rationaleemotions.http;

import com.google.gson.JsonElement;
import io.netty.handler.codec.http.FullHttpRequest;

public interface Route {

  boolean canHandle(String path);

  JsonElement process(FullHttpRequest request);

}
