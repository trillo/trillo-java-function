package com.collager.trillo.util;

public class GCPAuthApi extends BaseApi {
  
  public static GCPTokenInfo getTokenInfo(String authenticationPath) {
    String json = remoteCallAsString("GCPAuthApi", "getTokenInfo", authenticationPath);
    return Util.fromJSONString(json, GCPTokenInfo.class);
  }
  
  public static GCPTokenInfo refreshAccessToken(String refreshToken, String clientId, String clientSecret) {
    String json = remoteCallAsString("GCPAuthApi", "refreshAccessToken", refreshToken, clientId, clientSecret);
    return Util.fromJSONString(json, GCPTokenInfo.class);
  }
}
