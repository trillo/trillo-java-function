package com.collager.trillo.util;

import java.io.File;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import com.collager.trillo.pojo.Result;
import com.fasterxml.jackson.core.type.TypeReference;
import io.trillo.util.Proxy;

public class StorageApi extends BaseApi {
  
  public static String getFilePath(String fileId) {
    return BaseApi.remoteCallAsString("StorageApi", "getFilePath", fileId);
  }

  public static Long getFileIdByPath(String absoluteFilePath) {
    String str = BaseApi.remoteCallAsString("StorageApi", "getFileIdByPath", absoluteFilePath);
    try {
      return Long.parseLong(str);
    } catch(Exception exc) {
      return -1L;
    }
  }

  public static String getFolderPath(String folderId) {
    return BaseApi.remoteCallAsString("StorageApi", "getFolderPath", folderId);
  }
  
  public static String getSignedUrl(String filePath) {
    return BaseApi.remoteCallAsString("StorageApi", "getSignedUrl", filePath);
  }
  
  public static String getSignedUrl(String filePath, long duration, TimeUnit unit) {
    return BaseApi.remoteCallAsString("StorageApi", "getSignedUrl", filePath, duration, unit);
  }
  
  public static Result copyFileToBucket(String sourceFilePath, String targetFilePath) {
    return uploadFile(Proxy.getServerUrl() + "/ds/copyFileToBucket", "", sourceFilePath, targetFilePath);
  }
  
  public static Result copyFileFromBucket(String sourceFilePath, String targetFilePath) {
    return downloadFile(Proxy.getServerUrl() + "/ds/copyFileFromBucket", "", sourceFilePath, targetFilePath);
  }
  
  public static Result copyFileWithinBucket(String sourceFilePath, String targetFilePath) {
    return BaseApi.remoteCallAsResult("StorageApi", "copyFileWithinBucket", sourceFilePath, targetFilePath);
  }
  
  public static Result copyFileWithinBucket(String sourceFilePath, String targetFilePath, boolean makeCopy) {
    return BaseApi.remoteCallAsResult("StorageApi", "copyFileWithinBucket", sourceFilePath, targetFilePath, makeCopy);
  }
  
  public static Result writeToBucket(byte[] bytes, String targetFilePath, String contentType) {
    return BaseApi.remoteCallAsResult("StorageApi", "writeToBucket", bytes, targetFilePath, contentType);
  }
  
  public static Result readFromBucket(String sourceFilePath) {
    return BaseApi.remoteCallAsResult("StorageApi", "readFromBucket", sourceFilePath);
  }
  
  public static List<Map<String, Object>> listFiles(String pathName, Boolean versioned) {
    return BaseApi.remoteCallAsListOfMaps("StorageApi", "listFiles", pathName, versioned);
  }
  
  
  /* ------------- */
  
  public static String getSignedUrl(String bucketName, String filePath) {
    return BaseApi.remoteCallAsString("StorageApi", "getSignedUrl", bucketName, filePath);
  }
  
  public static String getSignedUrl(String bucketName, String filePath, long duration, TimeUnit unit) {
    return BaseApi.remoteCallAsString("StorageApi", "getSignedUrl", bucketName, filePath, duration, unit);
  }
  
  public static Result copyFileToBucket(String bucketName, String sourceFilePath, String targetFilePath) {
    return uploadFile(Proxy.getServerUrl() + "/ds/copyFileToBucket", bucketName, sourceFilePath, targetFilePath);
  }
  
  public static Result copyFileFromBucket(String bucketName, String sourceFilePath, String targetFilePath) {
    return downloadFile(Proxy.getServerUrl() + "/ds/copyFileFromBucket", bucketName, sourceFilePath, targetFilePath);
  }
  
  public static Result copyFileWithinBucket(String bucketName, String sourceFilePath, String targetFilePath) {
    return BaseApi.remoteCallAsResult("StorageApi", "copyFileWithinBucket", bucketName, sourceFilePath, targetFilePath);
  }
  
  public static Result copyFileWithinBucket(String bucketName, String sourceFilePath, String targetFilePath, boolean makeCopy) {
    return BaseApi.remoteCallAsResult("StorageApi", "copyFileWithinBucket", bucketName, sourceFilePath, targetFilePath, makeCopy);
  }
  
  public static Result writeToBucket(String bucketName, byte[] bytes, String targetFilePath, String contentType) {
    return BaseApi.remoteCallAsResult("StorageApi", "writeToBucket", bucketName, bytes, targetFilePath, contentType);
  }
  
  public static Result readFromBucket(String bucketName, String sourceFilePath) {
    return BaseApi.remoteCallAsResult("StorageApi", "readFromBucket", bucketName, sourceFilePath);
  }
  
  public static List<Map<String, Object>> listFiles(String bucketName, String pathName, Boolean versioned) {
    return BaseApi.remoteCallAsListOfMaps("StorageApi", "listFiles", bucketName, pathName, versioned);
  }
  
  public static String getBucketName() {
    return BaseApi.remoteCallAsString("StorageApi", "getBucketName");
  }
  
  /* ------------- */
  
  public static Object saveFileObject(Map<String, Object> fileObject) {
    return BaseApi.remoteCall("StorageApi", "saveFileObject", fileObject);
  }
  
  private static Result uploadFile(String url, String bucketName, String sourceFilePath, String targetFilePath) {
    CloseableHttpClient httpclient = Proxy.getHttpClient();
    try {
      HttpPost httpPost = new HttpPost(url);
      httpPost.addHeader("Authorization", "Bearer " + Proxy.getAccessToken());
      httpPost.addHeader("x-org-name", Proxy.getOrgName());
      httpPost.addHeader("x-app-name", Proxy.getAppName());
      File file = new File(sourceFilePath);
      FileBody bin = new FileBody(file);
      StringBody bucketNameBody = new StringBody(bucketName, ContentType.TEXT_PLAIN);
      StringBody targetFileBody = new StringBody(targetFilePath, ContentType.TEXT_PLAIN);

      HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", bin)
          .addPart("bucketName", bucketNameBody)
          .addPart("targetFilePath", targetFileBody).build();

      httpPost.setEntity(reqEntity);

      CloseableHttpResponse response = httpclient.execute(httpPost);
      try {
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null && 
            (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)) {
          String responseString = EntityUtils.toString(resEntity, "UTF-8");
          return Result.getSuccessResultWithData(responseString);
          //System.out.println(responseString);
        }  else {
          if (resEntity == null) {
            return Result.getFailedResult("No response received, HTTP code: " 
                + response.getStatusLine().getStatusCode());
          }
          try {
            String content = IOUtils.toString(resEntity.getContent(), StandardCharsets.UTF_8);
            Map<String, Object> m = Util.fromJSONString(content, new TypeReference<Map<String, Object>>() {});
            if (m.containsKey("_rtag")) {
              Result result = Util.fromMap(m, Result.class);
              return result;
            }
            return Result.getFailedResult("Invalid response received, HTTP code: " 
                + response.getStatusLine().getStatusCode() + " \n " + content);
          } catch (Exception exc) {
            
          }
          return Result.getFailedResult("Invalid response received, HTTP code: " 
              + response.getStatusLine().getStatusCode());
        }
      } catch (Exception exc1) {
        return Result.getFailedResult(exc1.toString());
      } finally {
        try {
          response.close();
        } catch (Exception exc2) {
        }
      }
    } catch (Exception exc3) {
      return Result.getFailedResult(exc3.toString());
    } finally {
      try {
        httpclient.close();
      } catch (Exception exc4) {
      }
    }
  }
  
  private static Result downloadFile(String url, String bucketName, String sourceFilePath, String targetFilePath) {
    CloseableHttpClient httpclient = Proxy.getHttpClient();
    try {
      HttpGet httpGet = new HttpGet(url);
      httpGet.addHeader("Authorization", "Bearer " + Proxy.getAccessToken());
      httpGet.addHeader("x-org-name", Proxy.getOrgName());
      httpGet.addHeader("x-app-name", Proxy.getAppName());
      
      URI uri = new URIBuilder(httpGet.getURI())
          .addParameter("bucketName", bucketName)
          .addParameter("sourceFilePath", sourceFilePath)
          .build();
       
      ((HttpRequestBase) httpGet).setURI(uri);
     
      CloseableHttpResponse response = httpclient.execute(httpGet);
      try {
        HttpEntity resEntity = response.getEntity();
        if ((resEntity != null) && 
            (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)) {
          FileUtil.copyFile(resEntity.getContent(), new File(targetFilePath));
        } else {
          if (resEntity == null) {
            return Result.getFailedResult("No response received, HTTP code: " 
                + response.getStatusLine().getStatusCode());
          }
          try {
            String content = IOUtils.toString(resEntity.getContent(), StandardCharsets.UTF_8);
            Map<String, Object> m = Util.fromJSONString(content, new TypeReference<Map<String, Object>>() {});
            if (m.containsKey("_rtag")) {
              Result result = Util.fromMap(m, Result.class);
              return result;
            }
            return Result.getFailedResult("Invalid response received, HTTP code: " 
                + response.getStatusLine().getStatusCode() + " \n " + content);
          } catch (Exception exc) {
            
          }
          return Result.getFailedResult("Invalid response received, HTTP code: " 
              + response.getStatusLine().getStatusCode());
        }
      } catch (Exception exc1) {
        return Result.getFailedResult(exc1.toString());
      } finally {
        try {
          response.close();
        } catch (Exception exc2) {
        }
      }
    } catch (Exception exc3) {
      return Result.getFailedResult(exc3.toString());
    } finally {
      try {
        httpclient.close();
      } catch (Exception exc4) {
      }
    }
    return Result.getSuccessResult();
  }
  
  public static Object shareWithTenants(Map<String, Object> params) {
    return BaseApi.remoteCall("StorageApi", "shareWithTenants", params);
  }
  
  /* 
   private static Result uploadFile(String url, String filePath) {
    CloseableHttpClient httpclient = Proxy.getHttpClient();
    try {
      File file = new File(filePath);
      FileBody bin = new FileBody(file);
      
      Request request = Request.Post(url)
          .addHeader("Authorization", "Bearer " + Proxy.getAccessToken())
          .addHeader("x-org-name", Proxy.getOrgName()).addHeader("x-app-name", Proxy.getAppName())
          .bodyFile(file, bin.getContentType());

      Content content = Executor.newInstance(httpclient).execute(request).returnContent();
      System.out.println(content.toString());
    } catch (Exception exc3) {
      return Result.getFailedResult(exc3.toString());
    } finally {
      try {
        httpclient.close();
      } catch (Exception exc4) {
      }
    }
    return Result.getSuccessResult();
  }
   */
}

