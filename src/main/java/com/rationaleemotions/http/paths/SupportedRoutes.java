package com.rationaleemotions.http.paths;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rationaleemotions.http.Route;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

enum SupportedRoutes implements Route {

  PEOPLE() {
    @Override
    public boolean canHandle(String path) {
      QueryStringDecoder decoder = new QueryStringDecoder(path);
      return decoder.path().equals("/people");
    }

    @Override
    public JsonElement process(FullHttpRequest request) {
      JsonObject json = new JsonObject();
      if (!request.method().equals(HttpMethod.GET)) {
        json.addProperty("not-found", true);
        return json;
      }
      QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
      List<String> names = Optional.ofNullable(decoder.parameters().get("names"))
          .orElse(Collections.emptyList());
      if (names.isEmpty()) {
        json.addProperty("not-found", true);
      } else {
        JsonArray array = new JsonArray();
        names.forEach(array::add);
        json.add("names", array);
      }
      return json;
    }
  },
  ANIMALS() {
    @Override
    public boolean canHandle(String path) {
      QueryStringDecoder decoder = new QueryStringDecoder(path);
      return decoder.path().equals("/animals");
    }

    @Override
    public JsonElement process(FullHttpRequest request) {
      JsonObject json = new JsonObject();
      if (!request.method().equals(HttpMethod.POST)) {
        json.addProperty("not-found", true);
        return json;
      }
      String content = request.content().toString(StandardCharsets.UTF_8);
      HttpHeaders headers = request.headers();
      if (headers.get(HttpHeaderNames.CONTENT_TYPE).equals("application/json")) {
        JsonObject jsonInput = JsonParser.parseString(content).getAsJsonObject();
        JsonArray animals = jsonInput.get("animals").getAsJsonArray();
        JsonArray array = new JsonArray();
        for (JsonElement jsonElement : animals) {
          String animal = jsonElement.getAsJsonPrimitive().getAsString();
          JsonObject eachAnimal = new JsonObject();
          eachAnimal.addProperty("animalName", animal);
          array.add(eachAnimal);
        }
        json.add("names", array);
      } else {
        json.addProperty("not-found", true);
      }
      return json;
    }
  },

  DEFAULT() {
    @Override
    public boolean canHandle(String path) {
      return true;
    }

    @Override
    public JsonElement process(FullHttpRequest request) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("received", "ok");
      return jsonObject;
    }
  }
}
