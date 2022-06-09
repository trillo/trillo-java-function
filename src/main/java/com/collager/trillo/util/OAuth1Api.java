package com.collager.trillo.util;

import java.util.Map;
import com.collager.trillo.pojo.Result;

public class OAuth1Api extends BaseApi {
  
  public static Result get(String clientKey, String clientSecret, String token,
      String tokenSecret, String realm, String restUtl) {
    return remoteCallAsResult("OAuth1Api", "get", clientKey, clientSecret, token, 
        tokenSecret, realm, restUtl);
  }
  
  public static Result get(String clientKey, String clientSecret, String token,
      String tokenSecret, String realm, String restUtl, Map<String, String> headers) {
    return remoteCallAsResult("OAuth1Api", "get", clientKey, clientSecret, token, 
        tokenSecret, realm, restUtl, headers);
  }
  
  public static Result post(String clientKey, String clientSecret, String token,
      String tokenSecret, String realm, String restUtl, Object body) {
    return remoteCallAsResult("OAuth1Api", "post", clientKey, clientSecret, token, 
        tokenSecret, realm, restUtl, body);
  }
  
  public static Result post(String clientKey, String clientSecret, String token,
      String tokenSecret, String realm, String restUtl, Object body, Map<String, String> headers) {
    return remoteCallAsResult("OAuth1Api", "post", clientKey, clientSecret, token, 
        tokenSecret, realm, restUtl, body, headers);
  }
}
