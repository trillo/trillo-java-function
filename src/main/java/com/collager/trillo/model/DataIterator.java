package com.collager.trillo.model;

import java.util.Map;
import com.collager.trillo.util.CoreDSUtil;
import com.collager.trillo.util.DSApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.Util;

public class DataIterator implements Loggable {
 
  private DataRequest dataRequest = null;
  private DataResult dataResult = null;
  private Object result = null;
  private String appName = Util.SHARED_APP_NAME;
  private String dsName = Util.COMMON_DS_NAME;
  private int index = 0;
  private boolean iteratorEnded = false;
  private boolean noMorePage = false;
  private boolean orderById = false;
  private String originalQuery = null;
  private Object lastId;
  private String idAttrName = "id";
  private String dsType = CoreDSUtil.MYSQL;
  
  public DataIterator(String className) {
    this(className, "", null, 1, 10);
  }
  
  public DataIterator(String className, int start, int size) {
    this(className, "", null, start, size);
  }
  
  public DataIterator(String className, String where, String orderBy) {
    this(className, where, orderBy, 1, 10);
  }
  
  public DataIterator(String className, String where, String orderBy, int start, int size) {
    dataRequest = new DataRequest();
    dataRequest.setClassName(className);
    dataRequest.setWhere(where);
    dataRequest.setOrderBy(orderBy);
    dataRequest.setStart(start);
    dataRequest.setSize(size);
    dataRequest.setUsingRowLimits(true);
  }
  
  public DataIterator(String className, String sqlTemplate) {
    this(className, sqlTemplate, 1, 10);
  }
  
  public DataIterator(String className, String sqlTemplate, int start, int size) {
    dataRequest = new DataRequest();
    dataRequest.setSqlTemplate(sqlTemplate);
    dataRequest.setStart(start);
    dataRequest.setSize(size);
    dataRequest.setUsingRowLimits(true);
  }
  
  public DataIterator(int start, int size) {
    this(start, size, "");
  }
  
  public DataIterator(int start, int size, String sqlQuery) {
    // using sql query
    dataRequest = new DataRequest();
    dataRequest.setSql(sqlQuery);
    dataRequest.setStart(start);
    dataRequest.setSize(size);
    dataRequest.setUsingRowLimits(true);
  }
  
  public DataIterator(int start, int size, String sqlQuery, boolean orderById) {
    // using sql query
    this(start, size, sqlQuery, "id", orderById);
  }
  
  public DataIterator(int start, int size, String sqlQuery, String idAttrName, boolean orderById) {
    // providing idAttrName (when there is an ambiguity in sql or it is not default as "id"
    dataRequest = new DataRequest();
    dataRequest.setSql(sqlQuery);
    dataRequest.setStart(start);
    dataRequest.setSize(size);
    dataRequest.setUsingRowLimits(true);
    this.orderById = orderById;
    this.originalQuery = sqlQuery;
    this.idAttrName = idAttrName;
  }
  
  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getDsName() {
    return dsName;
  }

  public void setDsName(String dsName) {
    this.dsName = dsName;
  }
  
  public String getDsType() {
    return dsType;
  }

  public void setDsType(String dsType) {
    this.dsType = dsType;
  }

  public DataRequest getDataRequest() {
    return dataRequest;
  }
  
  public DataResult getDataResult() {
    return dataResult;
  }

  public Object getResult() {
    return result;
  }

  public int getIndex() {
    return index;
  }

  @SuppressWarnings("unchecked")
  public Object getNext() {
    if (iteratorEnded) {
      return null;
    } else {
      if (dataResult == null) {
        fetch();
        if (iteratorEnded) {
          return null;
        } 
      }
      Object obj = dataResult.getItems().get(index);
      lastId = ((Map<String, Object>)obj).get("id");
      index++;
      if (isFetchNeeded()) {
        fetch();
      }
      return obj;
    }
  }
  
  private boolean isFetchNeeded() {
    return (index >= dataResult.getItems().size());
  }
  
  private void fetch() {
    if (noMorePage) {
      // if no more pages, end the iterator
      iteratorEnded = true;
      return;
    }
    if (dataResult == null) {
      if (originalQuery != null && orderById) {
        dataRequest.setUsingRowLimits(false);
        dataRequest.setSql(CoreDSUtil.updateLimitClause(originalQuery + " order by " + idAttrName, 
            0,  dataRequest.getSize(), dsType));
      }
      dataResult = retrievePage();
      index = 0;
    } else if (index >= dataResult.getItems().size()) {
      // all items of the current page have been iterated over
      // Get the next page
      if (originalQuery != null && orderById) {
        if (lastId != null) {
          dataRequest.setUsingRowLimits(false);
          String query = originalQuery.toLowerCase().indexOf(" where ") > 0 ? 
              originalQuery + " and (" + idAttrName + " > " + lastId + ")" :  
                originalQuery + " where " + idAttrName + " > " + lastId;
          dataRequest.setSql(CoreDSUtil.updateLimitClause(query + " order by " + idAttrName, 
              0,  dataRequest.getSize(), dsType));
        } else {
          dataRequest.setStart(dataRequest.getStart() + dataResult.getItems().size());
          dataRequest.setSql(originalQuery + " order by " + idAttrName);
        }
      } else {
        dataRequest.setStart(dataRequest.getStart() + dataResult.getItems().size());
      }
      dataResult = retrievePage();
      index = 0;
    }
    if (dataResult == null || dataResult.getItems().size() == 0) {
      // if dataResult is null then it is an error and end the iteration.
      noMorePage = true;
      iteratorEnded = true;
    }
  }
  
  public Object getPage() {
    if (noMorePage) {
      return null;
    }
    if (dataResult == null) {
      dataResult = retrievePage();
    } else {
      dataRequest.setStart(dataRequest.getStart() + dataResult.getItems().size());
      dataResult = retrievePage();
    }
    if (dataResult == null) {
      iteratorEnded = true;
      noMorePage = true;
      return result;
    }
    return dataResult.getItems();
  }
  
  public boolean hasPage() {
    if (noMorePage) {
      return false;
    }
    if (dataResult == null) {
      fetch();
    }
    return !iteratorEnded;
  }
  
  public boolean hasNext() {
    if (iteratorEnded) {
      return false;
    }
    if (dataResult == null) {
      fetch();
    }
    return !iteratorEnded;
  }
  
  @SuppressWarnings("unchecked")
  private DataResult retrievePage() {
    //log().info("Sql: " + dataRequest.getSql());
    Object res = DSApi.getPage(dataRequest);
    Map<String, Object> m = null;
    if (res instanceof Map<?, ?>) {
      m = (Map<String, Object>) res;
    }
    if (m == null || (m.containsKey("_rtag") && "_r_".equals("" + m.get("_rtag")))) {
      if (m != null) {
        result = m;
        log().error("Failed to retrieve data, error message: " + m.get("message"));
      }
      noMorePage = true;
      iteratorEnded = true;
      return null;
    }
    
    DataResult dataResult = Util.fromMap(m, DataResult.class);
    //log().info("Number of entries: " + dataResult.getItems().size());
    if (dataResult.getItems().size() < dataRequest.getSize()) {
      noMorePage = true;
    }
    return dataResult;
  }

}
