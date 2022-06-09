/*
 * Copyright (c) 2020 Trillo Inc. All Rights Reserved THIS IS UNPUBLISHED PROPRIETARY CODE OF TRILLO
 * INC. The copyright notice above does not evidence any actual or intended publication of such
 * source code.
 *
 */

package com.collager.trillo.util;

import java.util.List;
import java.util.Map;
import com.collager.trillo.model.CSVIterator;
import com.collager.trillo.pojo.Result;

public class CSVApi extends BaseApi {

  public static List<Map<String, Object>> csvGetAllRows(String filePath) {
    return remoteCallAsListOfMaps("CSVApi", "csvGetAllRows", filePath);
  }

  public static List<Map<String, Object>> csvGetAllRows(String filePath, String separator) {
    return remoteCallAsListOfMaps("CSVApi", "csvGetAllRows", filePath, separator);
  }

  public static List<Map<String, Object>> csvGetAllRows(String filePath, String separator,
      String[] columnNames) {
    return remoteCallAsListOfMaps("CSVApi", "csvGetAllRows", filePath, separator, columnNames);
  }

  public static List<Map<String, Object>> csvGetAllRows(String filePath, String separator,
      String[] columnNames, int columnNameLine) {
    return remoteCallAsListOfMaps("CSVApi", "csvGetAllRows", filePath, separator, columnNames,
        columnNameLine);
  }
  
  public static List<Map<String, Object>> csvGetPage(String fileName, char separatorChar, List<String> columnNames,
      int columnNameLine, String query, int startIndex, int pageSize) {
    return remoteCallAsListOfMaps("CSVApi", "csvGetPage", fileName, separatorChar, columnNames,
        columnNameLine, query, startIndex, pageSize);
  }
  
  public static Result csvWriteFile(String fileName, char separatorChar, 
      List<String> columnNames, int columnNameLine, List<Map<String, Object>> rows) {
    return remoteCallAsResult("CSVApi", "csvWriteFile", fileName, separatorChar, columnNames,
        columnNameLine, rows);
  }
  
  public static CSVIterator getCSVIterator(String fileName, int startIndex,
      int pageSize) {
    return new CSVIterator(fileName, "", startIndex, pageSize);
  }

  public static CSVIterator getCSVIterator(String fileName, String query, int startIndex,
      int pageSize) {
    return new CSVIterator(fileName, query, startIndex, pageSize);
  }

  public static CSVIterator getCSVIterator(String fileName, char separatorChar, String query,
      int startIndex, int pageSize) {
    return new CSVIterator(fileName, separatorChar, query, startIndex, pageSize);
  };

  public static CSVIterator getCSVIterator(String fileName, List<String> columnNames, String query,
      int startIndex, int pageSize) {
    return new CSVIterator(fileName, columnNames, query, startIndex, pageSize);
  }


  public static CSVIterator getCSVIterator(String fileName, char separatorChar,
      List<String> columnNames, String query, int startIndex, int pageSize) {
    return new CSVIterator(fileName, separatorChar, columnNames, query, startIndex, pageSize);
  }

  public static CSVIterator getCSVIterator(String fileName, List<String> columnNames,
      int columnNameLine, String query, int startIndex, int pageSize) {
    return new CSVIterator(fileName, columnNames, columnNameLine, query, startIndex, pageSize);
  }

  public static CSVIterator getCSVIterator(String fileName, char separatorChar,
      List<String> columnNames, int columnNameLine, String query, int startIndex, int pageSize) {
    return new CSVIterator(fileName, separatorChar, columnNames, columnNameLine, query, startIndex,
        pageSize);
  }
}
