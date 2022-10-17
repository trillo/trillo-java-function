package com.collager.trillo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.collager.trillo.model.DataIterator;
import com.collager.trillo.model.DataRequest;
import com.collager.trillo.model.DataResult;

public class DSApi extends BaseApi {
  
  public static DataIterator getDataIterator(int start, int size, String sqlQuery) {
    return new DataIterator(start, size, sqlQuery);
  }
 
  public static DataIterator getDataIterator(int start, int size, String sqlQuery, boolean orderById) {
    return new DataIterator(start, size, sqlQuery, orderById);
  }
  
  public static DataIterator getDataIterator(int start, int size, String sqlQuery, String idAttrName, boolean orderById) {
    return new DataIterator(start, size, sqlQuery, idAttrName, orderById);
  }
  
  public static Object getPage(DataRequest dsr) {
    return remoteCall("DSApi", "getPage", dsr);
  }
 
  public static Object get(String className, String id) {
    return remoteCall("DSApi", "get", className, id);
  }
  
  public static Object getMany(String className, List<String> ids) {
    return remoteCall("DSApi", "getMany", className, ids);
  }
  
  public static Object queryOne(String className, String whereClause) {
    return remoteCall("DSApi", "queryOne", className, whereClause);
  }
  
  public static Object queryOne(String sqlQuery) {
    return remoteCall("DSApi", "queryOne", sqlQuery);
  }
  
  public static Object queryOne(String className, String whereClause, boolean includeDeleted) {
    return remoteCall("DSApi", "queryOne", className, whereClause, includeDeleted);
  }

  public static Object queryMany(String sqlQuery) {
    return remoteCall("DSApi", "queryMany", sqlQuery);
  }
  
  public static Object queryMany(String className, String whereClause) {
    return remoteCall("DSApi", "queryMany", className, whereClause);
  }
  
  public static Object queryMany(String className, String whereClause, boolean includeDeleted) {
    return remoteCall("DSApi", "queryMany", className, whereClause, includeDeleted);
  }
  
  public static Object queryOneBySqlStatement(String sqlQuery) {
    return remoteCall("DSApi", "queryOneBySqlStatement", sqlQuery);
  }
  
  public static Object queryBySqlStatement(String sqlQuery) {
    return remoteCall("DSApi", "queryBySqlStatement", sqlQuery);
  }
  
  public static Object queryOneBySqlStatement(String dsName, String sqlQuery) {
    return remoteCall("DSApi", "queryOneBySqlStatement", dsName, sqlQuery);
  }
  
  public static Object queryBySqlStatement(String dsName, String sqlQuery) {
    return remoteCall("DSApi", "queryBySqlStatement", dsName, sqlQuery);
  }

  public static Object save(String className, Object entity) {
    return remoteCall("DSApi", "save", className, entity);
  }

  public static Object saveMany(String className, Iterable<Object> entities) {
    return remoteCall("DSApi", "saveMany", className, entities);
  }

  public static Object saveMapList(String className, List<Map<String, Object>> entities) {
    return remoteCall("DSApi", "saveMapList", className, entities);
  }

  public static Object saveManyIgnoreError(String className, Iterable<Object> entities) {
    return remoteCall("DSApi", "saveManyIgnoreError", className, entities);
  }
  
  public static Object update(String className, String id, String attrName, Object value) {
    return remoteCall("DSApi", "update", className, id, attrName, value);
  }
  
  public static Object updateMany(String className, List<String> ids, String attrName, Object value) {
    return remoteCall("DSApi", "updateMany", className, ids, attrName, value);
  }
  
  public static Object update(String className, String id, Map<String, Object> updateAttrs) {
    return remoteCall("DSApi", "update", className, id, updateAttrs);
  }
  
  public static Object updateMany(String className, List<String> ids, Map<String, Object> updateAttrs) {
    return remoteCall("DSApi", "updateMany", className, ids, updateAttrs);
  }
 
  public static Object updateByQuery(String className, String query, Map<String, Object> updateAttrs) {
    return remoteCall("DSApi", "updateByQuery", className, query, updateAttrs);
  }
  
  public static Object delete(String className, String id, boolean permanent) {
    return remoteCall("DSApi", "delete", className, id, permanent);
  }
  
  public static Object deleteMany(String className, Iterable<String> ids, boolean permanent) {
    return remoteCall("DSApi", "deleteMany", className, ids, permanent);
  }


  public static Object deleteByQuery(String className, String query, boolean permanent) {
    return remoteCall("DSApi", "deleteByQuery", className, query, permanent);
  }
  
  public static Object bulkOp(String className, String opName, List<Map<String, Object>> list) {
    return remoteCall("DSApi", "bulkOp", className, opName, list);
  }
  
  public static Object executeNamedQuery(String queryName) {
    return convertToDataResult(remoteCall("DSApi", "executeNamedQuery", queryName));
  }
  
  
  public static Object executeNamedQuery(String queryName, boolean onlyOneRow) {
    return convertToDataResult(remoteCall("DSApi", "executeNamedQuery", queryName, onlyOneRow));
  }
  
  public static Object executeNamedQuery(String queryName, Map<String, Object> params) {
    return convertToDataResult(remoteCall("DSApi", "executeNamedQuery", queryName, params));
  }

  public static Object executeNamedQuery(String queryName,
      Map<String, Object> params, boolean onlyOneRow) {
    return convertToDataResult(remoteCall("DSApi", "executeNamedQuery", queryName, params, onlyOneRow));
  }
  
  @Deprecated 
  /* dsName is not required, instead named query stores the dsName. */
  public static Object executeNamedQuery(String dsName, String queryName) {
    return convertToDataResult(remoteCall("DSApi", "executeNamedQuery", dsName, queryName));
  }
  
  @Deprecated 
  /* dsName is not required, instead named query stores the dsName. */
  public static Object executeNamedQuery(String dsName, String queryName, boolean onlyOneRow) {
    return convertToDataResult(remoteCall("DSApi", "executeNamedQuery", dsName, queryName, onlyOneRow));
  }
  
  @Deprecated 
  /* dsName is not required, instead named query stores the dsName. */
  public static Object executeNamedQuery(String dsName, String queryName, Map<String, Object> params) {
    return convertToDataResult(remoteCall("DSApi", "executeNamedQuery", dsName, queryName, params));
  }

  @Deprecated 
  /* dsName is not required, instead named query stores the dsName. */
  public static Object executeNamedQuery(String dsName, String queryName,
      Map<String, Object> params, boolean onlyOneRow) {
    return convertToDataResult(remoteCall("DSApi", "executeNamedQuery", dsName, queryName, params, onlyOneRow));
  }
  
  public static Object executePrepareStatement(String dsName, String sqlStatement) {
    return remoteCall("DSApi", "executePrepareStatement", dsName, sqlStatement);
  }
  
  public static Object executePrepareStatement(String dsName, String sqlStatement, Map<String, Object> params) {
    return remoteCall("DSApi", "executePrepareStatement", dsName, sqlStatement, params);
  }
  
  public static Object executePrepareStatement(String dsName, String sqlStatement,
      List<Object> params) {
    if (params == null) {
      params = new ArrayList<Object>();
    }
    return remoteCall("DSApi", "executePrepareStatement", dsName, sqlStatement, params);
  }
  
  public static Object executeSqlWriteStatement(String dsName, String sqlStatement) {
    return remoteCall("DSApi", "executeSqlWriteStatement", dsName, sqlStatement);
  }

  public static Object executeSqlWriteStatement(String dsName, String sqlStatement,
      Map<String, Object> params) {
    return remoteCall("DSApi", "executeSqlWriteStatement", dsName, sqlStatement, params);
  }

  public static Object tenantByName(String tenantName) {
    return remoteCall("DSApi", "tenantByName", tenantName);
  }

  public static Object tenantByQuery(String query) {
    return remoteCall("DSApi", "tenantByQuery", query);
  }

  public static Object getUser(String id) {
    return remoteCall("DSApi", "getUser", id);
  }

  public static Object userByEmail(String email) {
    return remoteCall("DSApi", "userByEmail",  email);
  }
 
  public static Object userByUserId(String userId) {
    return remoteCall("DSApi", "userByUserId",  userId);
  }

  public static Object valueByKey(String key, String type) {
    return remoteCall("DSApi", "valueByKey",  key, type);
  }
  
  public static Object keyValueList(String type) {
    return remoteCall("DSApi", "keyValueList", type);
  }
  
  public static Object emptyTable(String className) {
    return remoteCall("DSApi", "emptyTable", className);
  }

  public static Object saveFileOp(String fileName, String folderName, 
      String op, String functionName, String status, String message) {
    return remoteCall("DSApi", "saveFileOp", fileName, folderName, op, functionName, status, message);
  }
  
  public static Object saveFileOp(String fileId, String fileName, String folderName, 
      String op, String functionName, String status, String message) {
    return remoteCall("DSApi", "saveFileOp", fileId, fileName, folderName, op, functionName, status, message);
  }
  
  public static Object getFileOp(String fileId, 
      String op) {
    return remoteCall("DSApi", "getFileOp", fileId, op);
  }
  
  public static Object getFileOp(String fileName, String folderName, 
      String op) {
    return remoteCall("DSApi", "getFileOp", fileName, folderName, op);
  }
  
  public static Object getFileOp(String fileName, String folderName, 
      String op, String functionName) {
    return remoteCall("DSApi", "getFileOp", fileName, folderName, op, functionName);
  }
  
  public static Object getFileOpByFileId(String fileId, String op) {
    return remoteCall("DSApi", "getFileOpByFileId", fileId, op);
  }
  
  public static Object getFileOpByFileId(String fileId, String op, String functionName) {
    return remoteCall("DSApi", "getFileOpByFileId", fileId, op, functionName);
  }
  
 
  public static boolean isFileProcessed(String fileName, String folderName, 
      String op) {
    Object res = getFileOp(fileName, folderName, op);
    return res instanceof Map<?, ?>;
  }
  
  public static boolean isFileProcessed(String fileName, String folderName, String functionName,
      String op) {
    Object res = getFileOp(fileName, folderName, op, functionName);
    return res instanceof Map<?, ?>;
  }
  
  public static boolean isFileProcessedByFileId(String fileId, String op) {
    Object res = getFileOpByFileId(fileId, op);
    return res instanceof Map<?, ?>;
  }
  
  public static boolean isFileProcessedByFileId(String fileId, String functionName,
      String op) {
    Object res = getFileOpByFileId(fileId, op, functionName);
    return res instanceof Map<?, ?>;
  }
  
  public static void commitTx() {
    /* In dev-env, all DB calls are transactional therefore a transaction can't be committed (not needed).
     * This limitation may be removed in the future.
     */
  }
  
  public static void roolbackTx() {
    /* In dev-env, all DB calls are transactional therefore a transaction can't be rolled back.
     * This limitation may be removed in the future.
     */
  }
  
  public static Object incrementCounter(String className, 
      String counterName) {
    return remoteCall("DSApi", "incrementCounter", className, counterName);
  }
  
  @SuppressWarnings("unchecked")
  public static long getCounter(String className, 
      String counterName) {
    Object res = DSApi.incrementCounter("Counter", counterName);
    DSApi.commitTx();
    long counter = -1;
    if (res instanceof Map<?, ?>) {
      try {
        counter = Long.parseLong("" + ((Map<String, Object>)res).get("value"));
      } catch(Exception exc) {
        
      }
    }
    return counter;
  }
  
  public Object getDistinctValues(String className) {
    return remoteCall("DSApi", "getDistinctValues", className);
  }
  
  @SuppressWarnings("unchecked")
  private static Object convertToDataResult(Object obj) {
    if (obj instanceof Map<?, ?>) {
      DataResult dataResult = Util.fromMap((Map<String, Object>)obj, DataResult.class);
      return dataResult;
    }
    return obj;
  }

}


