package com.collager.trillo.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class GCPTokenInfo {
  
  private String projectId = null;
  private String tokenValue = null;
  private long expirationTime = 0L;
  private String authenticationType = null;
  private boolean error = false;
  private String message = null;
  public String getProjectId() {
    return projectId;
  }
  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }
  public String getTokenValue() {
    return tokenValue;
  }
  public void setTokenValue(String tokenValue) {
    this.tokenValue = tokenValue;
  }
  public long getExpirationTime() {
    return expirationTime;
  }
  public void setExpirationTime(long expirationTime) {
    this.expirationTime = expirationTime;
  }
  public String getAuthenticationType() {
    return authenticationType;
  }
  public void setAuthenticationType(String authenticationType) {
    this.authenticationType = authenticationType;
  }
  public boolean isError() {
    return error;
  }
  public void setError(boolean error) {
    this.error = error;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
}
