package com.collager.trillo.util;

import java.util.HashMap;
import java.util.Map;
import com.collager.trillo.pojo.RTContext;
import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;

public class ServerlessFunction implements TrilloFunction, Loggable {
  
  private RTContext ctx;

  public RTContext getFunctionContext() {
    return ctx;
  }

  public void setFunctionContext(RTContext ctx) {
    this.ctx = ctx;
  }
  
  public long getIdOfUser() {
    return ctx.getIdOfUser();
  }
  public String getUserId() {
    return ctx.getUserId();
  }
  public String getFirstName() {
    return ctx.getFirstName();
  }
  public String getLastName() {
    return ctx.getLastName();
  }
  public String getFullName() {
    return ctx.getFullName();
  }
  public String getEmail() {
    return ctx.getEmail();
  }
  public String getOrgName() {
    return ctx.getOrgName();
  }
  public String getAppName() {
    return ctx.getAppName();
  }
  public String getExternalId() {
    return ctx.getExternalId();
  }
  public String getRole() {
    return ctx.getRole();
  }
  public String getUserOrgName() {
    return ctx.getUserOrgName();
  }
  public boolean isEmailVerified() {
    return ctx.isEmailVerified();
  }
  public String getTenantId() {
    return ctx.getTenantId();
  }
  public String getTenantName() {
    return ctx.getTenantName();
  }
  public long getUserOrgId() {
    return ctx.getUserOrgId();
  }
  public String getPictureUrl() {
    return ctx.getPictureUrl();
  }
  public Object getV() {
    return ctx.getV();
  }
  public String getTaskName() {
    return ctx.getTaskName();
  }
  public long getExecutionId() {
    return ctx.getExecutionId();
  }
  public Map<String, Object> getStateMap() {
    return ctx.getStateMap();
  }
  @SuppressWarnings("unchecked")
  public Map<String, Object> getParameters() {
    Object v = getV();
    if (v instanceof Map<?, ?>) {
      return (Map<String, Object>) v;
    } else {
      return new HashMap<String, Object>();
    }
  }
  
  @Override
  public Object handle(ScriptParameter params) {
   return  Result.getFailedResult("In order to implement it, override in the subclass of your function extending this class");
  }
}
