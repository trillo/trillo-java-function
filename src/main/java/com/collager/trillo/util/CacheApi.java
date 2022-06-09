package com.collager.trillo.util;

public class CacheApi extends BaseApi {
  
  public static void put(String cacheName, String key, Object value) {
    remoteCall("CacheApi", "put", cacheName, key, value);
  }
  
  public static Object get(String cacheName, String key) {
    return remoteCall("CacheApi", "get", cacheName, key);
  }
  
  public static Object remove(String cacheName, String key) {
    return remoteCall("CacheApi", "remove", cacheName, key);
  }
  
  public static void clear(String cacheName) {
    remoteCall("CacheApi", "clear", cacheName);
  }
  
  public static void putNoLock(String cacheName, String key, Object value) {
    remoteCall("CacheApi", "putNoLock", cacheName, key, value);
  }
  
  public static Object getNoLock(String cacheName, String key) {
    return remoteCall("CacheApi", "getNoLock", cacheName, key);
  }
  
  public static Object removeNoLock(String cacheName, String key) {
    return remoteCall("CacheApi", "removeNoLock", cacheName, key);
  }
  
  public static long publishMessage(String topic, String message){
    Object obj = remoteCall("CacheApi", "publishMessage", topic, message);
    if (obj instanceof Long) {
      return ((Long)obj).longValue();
    }
    return -1;
  }
}


