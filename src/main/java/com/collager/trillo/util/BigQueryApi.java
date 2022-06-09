package com.collager.trillo.util;

import java.util.List;
import java.util.Map;

import com.collager.trillo.model.BigQueryIterator;
import com.collager.trillo.pojo.Result;

public class BigQueryApi extends BaseApi {
  
  
  public static Result createTable(String datasetName, String tableName, List<Map<String, Object>> classAttrs) {
    return remoteCallAsResult("BigQueryApi", "createTable", datasetName, tableName, classAttrs);
  }
  
  public static Result createTable(String datasetName, String tableName,
      List<Map<String, Object>> csvSchema, List<Map<String, Object>> classAttrs, List<Map<String, Object>> mappings) {
    return remoteCallAsResult("BigQueryApi", "createTable", datasetName, tableName, classAttrs, mappings);
  }
 
  public static Object getBQDataSets() {
    return remoteCall("BigQueryApi", "getBQDataSets");
  }

  public static Object getBQTables(String datasetName) {
    return remoteCall("BigQueryApi", "getBQTables", datasetName);
  }
  
  public static Object getTable(String datasetAndTableName) {
    return remoteCall("BigQueryApi", "getTable", datasetAndTableName);
  }
  
  public static Object getTable(String datasetName, String tableName) {
    return remoteCall("BigQueryApi", "getTable", datasetName, tableName);
  }
  
  public static List<Object> getTableFields(String datasetAndTableName) {
    return remoteCallAsList("BigQueryApi", "getTableFields", datasetAndTableName);
  }
  
  public static List<Object> getTableFields(String datasetName, String tableName) {
    return remoteCallAsList("BigQueryApi", "getTableFields", datasetName, tableName);
  }
  
  public static Result executeQuery(String queryString) {
    return remoteCallAsResult("BigQueryApi", "executeQuery", queryString);
  }
  
  /**
   *
   * data iterator for application and data source
   *
   * @param query
   * @param startIndex
   * @param pageSize
   * Example in Trillo Function:
   *    Object iter = getBigQueryIterator(query, 0, 100);
   *    Object page;
   *    while (iter.hasNext()) {
   *      page = iter.getPage();
   *    }
   * @return object
   */
  public static BigQueryIterator getBigQueryIterator(String query, int startIndex, int pageSize) {
    return new BigQueryIterator(query, startIndex, pageSize);
  }
  
  public static Result insertRows(
      String datasetName, String tableName, List<Map<String, Object>> list) {
    return remoteCallAsResult("BigQueryApi", "insertRows", datasetName, tableName, list);
  }
  
  public static Result importCSVIntoTable(String datasetName, String tableName, String pathToFile, 
      List<Map<String, Object>> csvSchema, List<Map<String, Object>> classAttrs, List<Map<String, Object>> mappings, int numberOfRowsToSkip) {
    return remoteCallAsResult("BigQueryApi", "importCSVIntoTable", datasetName, tableName, pathToFile, csvSchema, classAttrs, mappings, numberOfRowsToSkip);
  }
  
  public static Result exportTableToCSV(
      String datasetName,
      String tableName,
      String pathToFile) {
    return remoteCallAsResult("BigQueryApi", "exportTableToCSV", datasetName, tableName, pathToFile);
  }
  
  public static Result exportTableToCSV(
      String datasetName,
      String tableName,
      String path, String fileName) {
    return remoteCallAsResult("BigQueryApi", "exportTableToCSV", datasetName, tableName, path, fileName);
  }
  
  public static Result exportTableToCSVByURI(
      String datasetName,
      String tableName,
      String destinationUri) {
    return remoteCallAsResult("BigQueryApi", "exportTableToCSVByURI", datasetName, tableName, destinationUri);
  }
  
  public static Result exportTable(
      String datasetName,
      String tableName,
      String destinationUri,
      String dataFormat) {
    return remoteCallAsResult("BigQueryApi", "exportTable", datasetName, tableName, destinationUri, dataFormat);
  }
  
  public static int bigQueryToCSV(String filePath, String[] columnNames, String datasetName, String tableName,
      String query) {
    Object res = remoteCall("BigQueryApi", "bigQueryToCSV", datasetName, tableName, query);
    if (res instanceof Result) {
      return -1;
    }
    try {
      int n = Integer.parseInt("" + res);
      return n;
    } catch(Exception exc) {
      return -1;
    }
  }
  
  public static int bigQueryToCSV(String filePath, String[] columnNames, String datasetName, String tableName,
        String query, String functionName) {
    Object res = remoteCall("BigQueryApi", "bigQueryToCSV", datasetName, tableName, query, functionName);
    if (res instanceof Result) {
      return -1;
    }
    try {
      int n = Integer.parseInt("" + res);
      return n;
    } catch(Exception exc) {
      return -1;
    }
  }
  
  public static List<Object> getPage(String query, long start, long size) {
    return remoteCallAsList("BigQueryApi", "getPage", query, start, size);
  }

}
