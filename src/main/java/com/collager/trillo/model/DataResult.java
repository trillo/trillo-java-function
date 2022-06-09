/*
 * Copyright (c)  2018 Trillo Inc.
 * All Rights Reserved
 * THIS IS UNPUBLISHED PROPRIETARY CODE OF TRILLO INC.
 * The copyright notice above does not evidence any actual or
 * intended publication of such source code.
 *
 */

package com.collager.trillo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataResult {
  private List<Object> items;
  private int totalItems = 0;
  private int start;
  public List<Object> getItems() {
    return items;
  }
  public void setItems(List<Object> items) {
    this.items = items;
  }
  public int getTotalItems() {
    return totalItems;
  }
  public void setTotalItems(int totalItems) {
    this.totalItems = totalItems;
  }
  public int getStart() {
    return start;
  }
  public void setStart(int start) {
    this.start = start;
  }
  public boolean is_paginated_() {
    return true;
  }
}
