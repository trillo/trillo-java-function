/*
 * Copyright (c)  2020 Trillo Inc.
 * All Rights Reserved
 * THIS IS UNPUBLISHED PROPRIETARY CODE OF TRILLO INC.
 * The copyright notice above does not evidence any actual or
 * intended publication of such source code.
 *
 */



package com.collager.trillo.util;

import java.lang.invoke.MethodHandles;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;
import com.collager.trillo.model.AuditLogUtil;
import com.collager.trillo.pojo.Result;
import io.trillo.util.Proxy;

/** Log Api. */
//Using this site to convert its javadocs into markdown
//https://delight-im.github.io/Javadoc-to-Markdown/

public class LogApi {
  
  static String _className = MethodHandles.lookup().lookupClass().getName();
  static LocationAwareLogger _log = (LocationAwareLogger) LoggerFactory.getLogger(_className);
  
  private static CallLogger _cl = new CallLogger();

  
  public static final CallLogger callLogger() {
    return _cl;
  }
  
  
  /**
   * Sets log level
   * @param logLevel - logLevel (as string) to be set. The valid values are as follows:
   * debug
   * info - this is the default value
   * warning
   * error
   */
  public static void setLogLevel(String logLevel) {
    callLogger().setLogLevel(logLevel);
  }
  
  /**
   * Disable log collections for the thread. Logs are collected for a call and sent back the client
   * in the result. This is useful for debugging. By default it is disabled. You can disable the collection
   * of the logs (it should be disabled in the production unless you wish to trouble shoot something).
   */
  public static void disableLogsCollection() {
    callLogger().setCollectCallLogs(false);;
  }
  
  /**
   * Enable log collections for the thread.
   */
  public static void enableLogsCollection() {
    callLogger().setCollectCallLogs(true);;
  }

  public static final void debug(String msg, Object... args) {
    CallLogger cl = callLogger();
    if (cl.isDebugOn()) {
      log(LocationAwareLogger.DEBUG_INT, msg, args, null);
      cl.debug(msg);
    }
  }

  public static final void debug(String msg, Throwable t, Object... args) {
    CallLogger cl = callLogger();
    if (cl.isDebugOn()) {
      log(LocationAwareLogger.DEBUG_INT, msg, args, t);
      cl.debug(msg);
    }
  }

  public static final void info(String msg, Object... args) {
    CallLogger cl = callLogger();
    if (cl.isInfoOn()) {
      log(LocationAwareLogger.INFO_INT, msg, args, null);
      cl.info(msg);
    }
  }

  public static final void info(String msg, Throwable t, Object... args) {
    CallLogger cl = callLogger();
    if (cl.isInfoOn()) {
      log(LocationAwareLogger.INFO_INT, msg, args, t);
      cl.info(msg);
    }
  }

  public static final void warn(String msg, Object... args) {
    CallLogger cl = callLogger();
    if (cl.isWarningOn()) {
      log(LocationAwareLogger.WARN_INT, msg, args, null);
      cl.warn(msg);
    }
  }

  public static final void warn(String msg, Throwable t, Object... args) {
    CallLogger cl = callLogger();
    if (cl.isWarningOn()) {
      log(LocationAwareLogger.WARN_INT, msg, args, t);
      cl.warn(msg);
    }
  }

  public static final void error(String msg, Object... args) {
    CallLogger cl = callLogger();
    if (cl.isWarningOn()) {
      log(LocationAwareLogger.ERROR_INT, msg, args, null);
      cl.warn(msg);
    }
  }

  public static final void error(String msg, Throwable t, Object... args) {
    CallLogger cl = callLogger();
    if (cl.isWarningOn()) {
      log(LocationAwareLogger.ERROR_INT, msg, args, t);
      cl.warn(msg);
    }
  }

  public static final Result infoR(String msg, Object... args) {
    info(msg, args);
    return Result.getSuccessResult(msg);
  }

  public static final Result infoR(String msg, Throwable t, Object... args) {
    info(msg, t, args);
    return Result.getSuccessResult(msg);
  }

  public static final Result warnR(String msg, Object... args) {
    warn(msg, args);
    return Result.getSuccessResult(msg);
  }

  public static final Result warnR(String msg, Throwable t, Object... args) {
    warn(msg, t, args);
    return Result.getSuccessResult(msg);
  }

  public static final Result errorR(String msg, Object... args) {
    error(msg, args);
    return Result.getFailedResult(msg);
  }

  public static final Result errorR(String msg, Throwable t, Object... args) {
    error(msg, t, args);
    return Result.getFailedResult(msg);
  }
  
  public static final Result errorR(String msg) {
    error(msg);
    return Result.getFailedResult(msg);
  }

  /**
   * Audit log object.
   *
   * @param logObject the log object
   * @return the object
   */
  public static final void auditLog(Map<String, Object> logObject) {
    // no server side logging is done from the local env.
  }
  
  /**
   * Audit log object.
   *
   * @param summary the summary
   * @param args detailed log message (such as description of error message)
   */
  public static final void auditLogInfo(String summary, String ...args) {
    auditLog("info", summary, args);
  }
  
  public static final void auditLogWarning(String summary, String ...args) {
    auditLog("warn", summary, args);
  }
  
  public static final void auditLogWarn(String summary, String ...args) {
    auditLog("warn", summary, args);
  }
  
  public static final void auditLogError(String summary, String ...args) {
    auditLog("error", summary, args);
  }
  
  public static final void auditLogCritical(String summary, String ...args) {
    auditLog("critical", summary, args);
  }
  
  /**
   * Audit log object.
   *
   * @param action the action
   * @param summary the summary
   * @param args detailed log message (such as description of error message)
   */
  public static final void auditInfo(String action, String summary, Object detail, String sourceUid, Object ...args) {
    auditLog2(action, "info", summary, detail, sourceUid, args);
  }
  
  public static final void auditWarn(String action, String summary, Object detail, String sourceUid, Object ...args) {
    auditLog2(action, "warn", summary, detail, sourceUid, args);
  }
  
  public static final void auditError(String action, String summary, Object detail, String sourceUid, Object ...args) {
    auditLog2(action, "error", summary, detail, sourceUid, args);
  }
  
  public static final void auditCritical(String action, String summary, Object detail, String sourceUid, Object ...args) {
    auditLog2(action, "critical", summary, detail, sourceUid, args);
  }
  
  public static final Result auditInfoR(String action, String summary, Object detail, String sourceUid, Object ...args) {
    auditInfo(action, summary, detail, sourceUid, args);
    return Result.getSuccessResult(summary);
  }
  
  public static final Result auditWarnR(String action, String summary, Object detail, String sourceUid, Object ...args) {
    auditWarn(action, summary, detail, sourceUid, args);
    return Result.getSuccessResult(summary);
  }
  
  public static final Result auditErrorR(String action, String summary, Object detail, String sourceUid, Object ...args) {
    auditError(action, summary, detail, sourceUid, args);
    return Result.getFailedResult(summary);
  }
  
  public static final Result auditCriticalR(String action, String summary, Object detail, String sourceUid, Object ...args) {
    auditCritical(action, summary, detail, sourceUid, args);
    return Result.getFailedResult(summary);
  }
  /**
   * Audit log object.
   *
   * @param type (debug, info, warning, error)
   * @param summary the summary
   * @param args detailed log message (such as description of error message)
   */
  public static final void auditLog(String type, String summary, String ...args) {
   String[] sl = {"", "", "", ""};
   for (int i=0; i<args.length && i<sl.length ; i++) {
     sl[i] = args[i];
   }
   for (int i = args.length; i<sl.length; i++) {
     sl[i] = "";
   }
   _auditLog(type, summary, sl[0], sl[1], sl[2], sl[3]);
  }
  
  public static final void auditLog2(String action, String type, String summary, Object detail, String sourceUid, Object ...args) {
    String json = makeJSON(args, true);
    String detailStr = null;
    if (detail instanceof String) {
      detailStr = (String) detail;
    } else if (detail instanceof Map<?, ?>) {
      detailStr = Util.asJSONPrettyString(detail);
    }
    _auditLog(type, summary, detailStr, json, action, sourceUid);
  }
  
  private static final void _auditLog(String type, String summary, String detail, String json, String action, String sourceUid) {
    auditLog(AuditLogUtil.makeAuditLog(type, sourceUid, action, summary, detail, json));
    logToConsole(type, summary);
    /* if (StringUtils.isNotBlank(detail)) {
      logToConsole(type, detail);
    }
    if (StringUtils.isNotBlank(json)) {
      logToConsole(type, json);
    } */
  }
  
  public static void logToConsole(String type, String msg) {
    if ("debug".equals(type)) {
      log(LocationAwareLogger.DEBUG_INT, msg, null, null);
    } else if ("info".equals(type)) {
      log(LocationAwareLogger.INFO_INT, msg, null, null);
    } else if ("warn".equals(type)) {
      log(LocationAwareLogger.WARN_INT, msg, null, null);
    } else if ("error".equals(type)) {
      log(LocationAwareLogger.ERROR_INT, msg, null, null);
    } else if ("critical".equals(type)) {
      log(LocationAwareLogger.ERROR_INT, msg, null, null);
    }
  }
  
  private static void log(int level, String msg, Object[] args, Throwable t) {
   
    String userId = Proxy.getUserId();
    if (StringUtils.isBlank(userId) || "guest".equalsIgnoreCase(userId)) {
      userId = "";
    }
    
    String txId = "";

    String s;

    if (userId.isEmpty() && txId.isEmpty()) {
      s = "";
    } else if (txId.isEmpty()) {
      s = "(" + userId + ") ";
    } else {
      s = "(" + userId + "|" + txId + ") ";
    }

    msg = s + msg;
    
    String json = makeJSON(args, false);
    
    if (json != null) {
      msg += " " + json;
    }

    _log.log(null, _className, level, s + msg, args, t);
  }
  
  @SuppressWarnings("unchecked")
  private static String makeJSON(Object[] args, boolean pretty) {
    if (args == null || args.length == 0) {
      return null;
    }
    
    Map<String, Object> m = new LinkedHashMap<String, Object>();
    
    int i =0;
    int n = args.length;
    
    Object name;
    Object value;
    while (i < n) {
      name = args[i];
      if (name instanceof String) {
        i++;
        if (i < n) {
          value = args[i];
          m.put((String) name, value);
        } else {
          break;
        }
      } else if (name instanceof Map<?, ?>) {
        m.putAll((Map<String, Object>) name);
      }
      i++;
    }
    
    if (m.size() == 0) {
      return null;
    }
    
    if (pretty) {
      return Util.asJSONPrettyString(m);
    } else {
      return Util.asJSONString(m);
    }
  }
  
  
  /* The following methods are for backward compatibility in custom Trillo Functions */
  
  public static final void logInfo(String msg) {
    CallLogger cl = callLogger();
    if (cl.isInfoOn()) {
      log(LocationAwareLogger.INFO_INT, msg, null, null);
      cl.info(msg);
    }
  }

  public static final void logWarn(String msg) {
    CallLogger cl = callLogger();
    if (cl.isWarningOn()) {
      log(LocationAwareLogger.WARN_INT, msg, null, null);
      cl.warn(msg);
    }
  }

  public static final void logError(String msg) {
    CallLogger cl = callLogger();
    if (cl.isErrorOn()) {
      log(LocationAwareLogger.ERROR_INT, msg, null, null);
      cl.error(msg);
    }
  }
}
