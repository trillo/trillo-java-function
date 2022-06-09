/*
 * Copyright (c)  2018 Trillo Inc.
 * All Rights Reserved
 * THIS IS UNPUBLISHED PROPRIETARY CODE OF TRILLO INC.
 * The copyright notice above does not evidence any actual or
 * intended publication of such source code.
 *
 */

package com.collager.trillo.model;

import java.util.Map;

public class DataRequest {
  private String className;
  private String where;
  private String orderBy;
  private String groupBy;
  private String having;
  private int start = 1;
  private int size = 10;
  private String sql;
  private String sqlTemplate;
  private Map<String, Object> params = null;
  private boolean includeDeleted = false;
  private boolean usingRowLimits;
  private boolean usingView;
  private String viewName;

  public String getClassName() {
    return className;
  }
  public void setClassName(String className) {
    this.className = className;
  }
  public String getWhere() {
    return where;
  }
  public void setWhere(String where) {
    this.where = where;
  }
  public String getOrderBy() {
    return orderBy;
  }
  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }
  public String getGroupBy() {
    return groupBy;
  }
  public void setGroupBy(String groupBy) {
    this.groupBy = groupBy;
  }
  public String getHaving() {
    return having;
  }
  public void setHaving(String having) {
    this.having = having;
  }
  public int getStart() {
    return start;
  }
  public void setStart(int start) {
    this.start = start;
  }
  public int getSize() {
    return size;
  }
  public void setSize(int size) {
    this.size = size;
  }
  public String getSql() {
    return sql;
  }
  public void setSql(String sql) {
    this.sql = sql;
  }

  public String getSqlTemplate() {
    return sqlTemplate;
  }

  public void setSqlTemplate(String sqlTemplate) {
    this.sqlTemplate = sqlTemplate;
  }
  public Map<String, Object> getParams() {
    return params;
  }
  public void setParams(Map<String, Object> params) {
    this.params = params;
  }
  public boolean isIncludeDeleted() {
    return includeDeleted;
  }
  public void setIncludeDeleted(boolean includeDeleted) {
    this.includeDeleted = includeDeleted;
  }
  public boolean isUsingRowLimits() {
    return usingRowLimits;
  }
  public void setUsingRowLimits(boolean usingRowLimits) {
    this.usingRowLimits = usingRowLimits;
  }
  public boolean isUsingView() {
    return usingView;
  }
  public void setUsingView(boolean usingView) {
    this.usingView = usingView;
  }
  public String getViewName() {
    return viewName;
  }
  public void setViewName(String viewName) {
    this.viewName = viewName;
  }
}
