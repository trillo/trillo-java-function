package com.collager.trillo.util;

import java.nio.charset.Charset;
import java.util.Map;

import com.collager.trillo.pojo.Result;


public class HttpApi extends BaseApi {
  
  public static Result get(String requestUrl) {
    return remoteCallAsResult("HttpApi", "get", requestUrl);
  }
  
  public static Result get2(String requestUrl) {
    return remoteCallAsResult("HttpApi", "get2", requestUrl);
  }
  
  public static Result get(String requestUrl, Map<String, String> headers) {
    return remoteCallAsResult("HttpApi", "get", requestUrl, headers);
  }
 
  public static Result post(String requestUrl, Object body) {
    return remoteCallAsResult("HttpApi", "post", requestUrl, body);
  }

  public static Result post(String requestUrl, Object body, Map<String, String> headers) {
    return remoteCallAsResult("HttpApi", "post", requestUrl, body, headers);
  }
  
  public static Result put(String requestUrl, Object body) {
    return remoteCallAsResult("HttpApi", "put", requestUrl, body, null);
  }
  
  public static Result put(String requestUrl, Object body, Map<String, String> headers) {
    return remoteCallAsResult("HttpApi", "put", requestUrl, body, headers);
  }
  
  public static Result patch(String requestUrl, Object body) {
    return remoteCallAsResult("HttpApi", "patch", requestUrl, body, null);
  }
  
  public static Result patch(String requestUrl, Object body, Map<String, String> headers) {
    return remoteCallAsResult("HttpApi", "patch", requestUrl, body, headers);
  }
  
  public static Result delete(String requestUrl, Object body) {
    return remoteCallAsResult("HttpApi", "delete", requestUrl, body);
  }
  
  public static Result delete(String requestUrl, Object body, Map<String, String> headers) {
    return remoteCallAsResult("HttpApi", "delete", requestUrl, body, headers);
  }
  
  public static Result getAsString(String requestUrl, String contentType) {
    return remoteCallAsResult("HttpApi", "getAsString", requestUrl);
  }
  
  public static Result getAsString(String requestUrl, String contentType, Map<String, String> headers) {
    return remoteCallAsResult("HttpApi", "getAsString", requestUrl, contentType, headers);
  }
  
  public static Result getAsString(String requestUrl, String contentType, Object body) {
    return remoteCallAsResult("HttpApi", "getAsString", requestUrl, contentType, body);
  }

  public static Result getAsString(String requestUrl, String contentType, Object body, Map<String, String> headers) {
    return remoteCallAsResult("HttpApi", "getAsString", requestUrl, contentType, body, headers);
  }
 
  public static Result postAsString(String requestUrl, String contentType, Object body) {
    return remoteCallAsResult("HttpApi", "postAsString", requestUrl, contentType, body);
  }

  public static Result postAsString(String requestUrl, String contentType, Object body, Map<String, String> headers) {
    return remoteCallAsResult("HttpApi", "postAsString", requestUrl, contentType, body, headers);
  }
  
  public static Result putAsString(String requestUrl, String contentType, Object body) {
    return remoteCallAsResult("HttpApi", "putAsString", requestUrl, contentType, body, null);
  }
  
  public static Result putAsString(String requestUrl, String contentType, Object body, Map<String, String> headers) {
    return remoteCallAsResult("HttpApi", "putAsString", requestUrl, contentType, body, headers);
  }
  
  public static Result patchAsString(String requestUrl, String contentType, Object body) {
    return remoteCallAsResult("HttpApi", "patchAsString", requestUrl, contentType, body, null);
  }
  
  public static Result patchAsString(String requestUrl, String contentType, Object body, Map<String, String> headers) {
    return remoteCallAsResult("HttpApi", "patchAsString", requestUrl, contentType, body, headers);
  }
  
  public static Result deleteAsString(String requestUrl, String contentType, Object body) {
    return remoteCallAsResult("HttpApi", "deleteAsString", requestUrl, contentType, body);
  }
  
  public static Result deleteAsString(String requestUrl, String contentType, Object body, Map<String, String> headers) {
    return remoteCallAsResult("HttpApi", "deleteAsString", requestUrl, contentType, body, headers);
  }
  
  public static Result readFileAsJSON(String url, Map<String, String> headerMap) {
    return remoteCallAsResult("HttpApi", "readFileAsJSON", url, headerMap);
  }
  
  public static Result readFileAsJSON(String url, Charset encoding, Map<String, String> headerMap) {
    return remoteCallAsResult("HttpApi", "readFileAsJSON", url, encoding, headerMap);
  }
  
  public static Result readFileAsString(String url, Map<String, String> headerMap) {
    return remoteCallAsResult("HttpApi", "readFileAsString", url, headerMap);
  }
  
  public static Result readFileAsString(String url, Charset encoding, Map<String, String> headerMap) {
    return remoteCallAsResult("HttpApi", "readFileAsString", url, encoding, headerMap);
  }
  
  public static Result readFileAsBytes(String url, Map<String, String> headerMap) {
    return remoteCallAsResult("HttpApi", "readFileAsBytes", url, headerMap);
  }
  
  public static Result writeFile(String url, String filePath, String fileFieldName, Map<String, String> headerMap) {
    return remoteCallAsResult("HttpApi", "writeFile", url, filePath, fileFieldName, headerMap);
  }
  
  public static Result writeFileBytes(String url,
      String fileName, byte[] fileContent, String fileFieldName, Map<String, String> headerMap) {
    return remoteCallAsResult("HttpApi", "writeFileBytes", url, fileName, fileContent, fileFieldName, headerMap);
  }
  
  public static Result writeFile(String url, String filePath, String fileFieldName, 
      Map<String, String> headerMap,  Map<String, Object> additionalFields) {
    return remoteCallAsResult("HttpApi", "writeFile", url, filePath, fileFieldName, 
        headerMap, additionalFields);
  }
  
  public static Result writeFileBytes(String url,
      String fileName, byte[] fileContent, String fileFieldName, Map<String, 
      String> headerMap, Map<String, Object> additionalFields) {
    return remoteCallAsResult("HttpApi", "writeFileBytes", url, fileName, fileContent, 
        fileFieldName, headerMap, additionalFields);
  }
  
  public static Result writeFileBytes(String url,
      String fileName, String fileContentAsString, String fileFieldName, Map<String, String> headerMap) {
    return remoteCallAsResult("HttpApi", "writeFileBytes", url, fileName, fileContentAsString, fileFieldName, headerMap);
  }
  
  public static Result writeFileBytes(String url,
      String fileName, String fileContentAsString, String fileFieldName, Map<String, 
      String> headerMap, Map<String, Object> additionalFields) {
    return remoteCallAsResult("HttpApi", "writeFileBytes", url, fileName, fileContentAsString, 
        fileFieldName, headerMap, additionalFields);
  }
}


