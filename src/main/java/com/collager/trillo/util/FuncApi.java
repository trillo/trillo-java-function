package com.collager.trillo.util;

import java.util.Map;

import com.collager.trillo.pojo.Result;

public class FuncApi extends BaseApi {
 
 
  public static Result executeFunction(String functionName, Map<String, Object> params) {
    return remoteCallAsResult("FuncApi", "executeFunction", functionName, params);
  }

 
  public static Result executeFunction(String appName, String functionName, Map<String, Object> params) {
    return remoteCallAsResult("FuncApi", "executeFunction", appName, functionName, params);
  }
  
  public static Result executeFunctionWithMethod(String functionName, String methodName, Map<String, Object> params) {
    return remoteCallAsResult("FuncApi", "executeFunctionWithMethod", functionName, methodName, params);
  }

  public static Result executeFunctionWithMethod(String appName, String functionName, String methodName, Map<String, Object> params) {
    return remoteCallAsResult("FuncApi", "executeFunctionWithMethod", appName, functionName, methodName, params);
  }


  public static Result executeSSH(String hostName, String command) {
    return remoteCallAsResult("FuncApi", "executeSSH", hostName, command);
  }

  
  public static Result executeSSH(String hostName, String command, boolean async) {
    return remoteCallAsResult("FuncApi", "executeSSH", hostName, command, async);
  }
  
  public static Result executeSSH(String command, boolean async) {
    return remoteCallAsResult("FuncApi", "executeSSH", command, async);
  }
  
  public static Object pingTask(String id) {
    return remoteCall("FuncApi", "pingTask", id);
  }
  
  public static Result createTask(String taskName, String taskType, String functionName, Map<String, Object> params) {
    return remoteCallAsResult("FuncApi", "createTask", taskName, taskType, functionName, params);
  }
 
  public static Result createTask(String taskName, String taskType, String appName, String functionName, Map<String, Object> params) {
    return remoteCallAsResult("FuncApi", "createTask", taskName, taskType, appName, functionName, params);
  }
  
  public static Result createTaskBySourceUid(String taskName, String taskType, String sourceUid, String functionName, Map<String, Object> params) {
    return remoteCallAsResult("FuncApi", "createTaskBySourceUid", taskName, taskType, sourceUid, functionName, params);
  }

 
  public static Result createTaskBySourceUid(String taskName, String taskType, String sourceUid, String appName, String functionName, Map<String, Object> params) {
    return remoteCallAsResult("FuncApi", "createTaskBySourceUid", taskName, taskType, sourceUid, appName, functionName, params);
  }

}


