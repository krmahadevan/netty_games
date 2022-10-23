package com.rationaleemotions.http.paths;

import com.google.gson.JsonElement;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import java.util.Arrays;

public final class RouteFactory {

  public static JsonElement process(FullHttpRequest request) {
    final HttpMethod method = HttpMethod.valueOf(request.method().name().toUpperCase());
    final String path = request.uri();

    return Arrays.stream(SupportedRoutes.values())
        .filter(each -> each.canHandle(path))
        .findFirst()
        .map(found -> found.process(request))
        .orElseThrow(() -> new UnsupportedOperationException("not supported"));
  }

}
