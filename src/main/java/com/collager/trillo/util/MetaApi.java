package com.collager.trillo.util;

import java.util.List;
import java.util.Map;

public class MetaApi extends BaseApi {
  
  @SuppressWarnings("unchecked")
  public static Map<String, Object> getClassM(String className) {
    Object res = remoteCall("MetaApi", "getClassM", className);
    if (res instanceof Map<?, ?>) {
      return (Map<String, Object>) res;
    }
    return null;
  }
  
  @SuppressWarnings("unchecked")
  public static List<Map<String, Object>> getSchemaForDataStudio(String className, boolean includeAllSysAttrs) {
    Object res = remoteCall("MetaApi", "getSchemaForDataStudio", className, includeAllSysAttrs);
    if (res instanceof List<?>) {
      return (List<Map<String, Object>>) res;
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  public static Map<String, Object> getDomainFileAsMap(String fileName) {
    Object res = remoteCall("MetaApi", "getDomainFileAsMap", fileName);
    if (res instanceof Map<?, ?>) {
      return (Map<String, Object>) res;
    }
    return null;
  }
  
  @SuppressWarnings("unchecked")
  public static List<Map<String, Object>> getDomainFileAsList(String fileName) {
    Object res = remoteCall("MetaApi", "getDomainFileAsList", fileName);
    if (res instanceof List<?>) {
      return (List<Map<String, Object>>) res;
    }
    return null;
  }
  
  public static Object getKeyValue(String key) {
    return remoteCall("MetaApi", "getKeyValue", key);
  }
  
  public static Object getKeyValue(String key, String type) {
    return remoteCall("MetaApi", "getKeyValue", key, type);
  }
  
  public static Object makeClassFromData(String className, String pkAttrName, Map<String, Object> data) {
    return remoteCall("MetaApi", "makeClassFromData", className, pkAttrName, data);
  }
  
  public static Object makeClassFromData(String className, String tableName, String pkAttrName, Map<String, Object> data) {
    return remoteCall("MetaApi", "makeClassFromData", className, tableName, pkAttrName, data);
  }
  
  public static Object makeClassFromData(String className, String pkAttrName, 
      Map<String, Object> data, Map<String, String> attrTypeByNames) {
    return remoteCall("MetaApi", "makeClassFromData", className, pkAttrName, data, attrTypeByNames);
  }
  
  public static Object makeClassFromData(String className, String tableName, String pkAttrName, 
      Map<String, Object> data, Map<String, String> attrTypeByNames) {
    return remoteCall("MetaApi", "makeClassFromData", className, tableName, pkAttrName, data, attrTypeByNames);
  }
}


