/*
 * Copyright (c)  2020 Trillo Inc.
 * All Rights Reserved
 * THIS IS UNPUBLISHED PROPRIETARY CODE OF TRILLO INC.
 * The copyright notice above does not evidence any actual or
 * intended publication of such source code.
 *
 */

package com.collager.trillo.util;

public class GCPRestApi extends BaseApi {
  
  public static Object get(String requestUrl) {
    return remoteCall("GCPRestApi", "get", requestUrl);
  }
  
  public static Object post(String requestUrl, Object body) {
    return remoteCall("GCPRestApi", "post", requestUrl, body);
  }

  public static Object put(String requestUrl, Object body) {
    return remoteCall("GCPRestApi", "put", requestUrl, body);
  }
  
  public static Object delete(String requestUrl, Object body) {
    return remoteCall("GCPRestApi", "delete", requestUrl, body);
  }
  
  public static Object get(String requestUrl, String serviceAccountKeyPropName, String authenticationPath) {
    return remoteCall("GCPRestApi", "get", requestUrl, serviceAccountKeyPropName, authenticationPath);
  }
  
 
  public static Object post(String requestUrl, Object body, String serviceAccountKeyPropName, String authenticationPath) {
    return remoteCall("GCPRestApi", "post", requestUrl, body, serviceAccountKeyPropName, authenticationPath);
  }

  public static Object put(String requestUrl, Object body, String serviceAccountKeyPropName, String authenticationPath) {
    return remoteCall("GCPRestApi", "put", requestUrl, body, serviceAccountKeyPropName, authenticationPath);
  }
  
  public static Object delete(String requestUrl, Object body, String serviceAccountKeyPropName, String authenticationPath) {
    return remoteCall("GCPRestApi", "delete", requestUrl, body, serviceAccountKeyPropName, authenticationPath);
  }
  
  public static Object get(String requestUrl, String refreshToken, String clientId, String clientSecret) {
    return remoteCall("GCPRestApi", "get", requestUrl, refreshToken, clientId, clientSecret);
  }
  
  public static Object post(String requestUrl, Object body, String refreshToken, String clientId, String clientSecret) {
    return remoteCall("GCPRestApi", "post", requestUrl, body, refreshToken, clientId, clientSecret);
  }
  
  public static Object put(String requestUrl, Object body, String refreshToken, String clientId, String clientSecret) {
    return remoteCall("GCPRestApi", "put", requestUrl, body, refreshToken, clientId, clientSecret);
  }
  
  public static Object delete(String requestUrl, Object body, String refreshToken, String clientId, String clientSecret) {
    return remoteCall("GCPRestApi", "delete", body, refreshToken, clientId, clientSecret);
  }

  public static Object publish(String topicId, Object message) {
    return remoteCall("GCPRestApi", "publish", topicId, message);
  }
}


