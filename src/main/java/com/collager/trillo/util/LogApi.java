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
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;
import com.collager.trillo.model.AuditLogUtil;
import com.collager.trillo.pojo.Result;

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
   * Disable audit log, by default it is enabled.
   * Audit log is logging to Trillo DB. It is used for a coarse business events and background tasks to
   * isolate errors per task. The level of audit logging depends on the setLogLevel call above. 
   * During some operations, you may choose to disable logging independent of log level (to console)
   */
  public static void disableAuditLog() {
    callLogger().setAuditLogEnabled(false);;
  }
  
  /**
   * Enabled audit log. See above for the description of audit log.
   */
  public static void enableAuditLog() {
    callLogger().setAuditLogEnabled(false);;
  }
  
  /**
   * Disable log collections for the thread. Logs are collected for a call and sent back the client
   * in the result. This is useful for debugging. By default it is disabled. You can disable the colleciton
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
  
  public static final void debug(String msg) {
    debug(msg, (Object[])null);
  }
 
  public static final void debug(String msg, Object... parameters) {
    CallLogger cl = callLogger();
    if (cl.isDebugOn()) {
      log(LocationAwareLogger.DEBUG_INT, msg, parameters, null);
      cl.debug(msg);
    }
  }
  
  public static final void debug(String msg, Throwable t) {
    CallLogger cl = callLogger();
    if (cl.isDebugOn()) {
      log(LocationAwareLogger.DEBUG_INT, msg, null, t);
      cl.debug(msg);
    }
  }
  
  public static final void info(String msg) {
    info(msg, (Object[])null);
  }
  
  public static final void info(String msg, Object... parameters) {
    CallLogger cl = callLogger();
    if (cl.isInfoOn()) {
      log(LocationAwareLogger.INFO_INT, msg, parameters, null);
      cl.info(msg);
    }
  }
  
  public static final void info(String msg, Throwable t) {
    CallLogger cl = callLogger();
    if (cl.isInfoOn()) {
      log(LocationAwareLogger.INFO_INT, msg, null, t);
      cl.info(msg);
    }
  }
  
  public static final void warn(String msg) {
    warn(msg, (Object[])null);
  }
  
  public static final void warn(String msg, Object... parameters) {
   CallLogger cl = callLogger();
   if (cl.isWarningOn()) {
     log(LocationAwareLogger.WARN_INT, msg, parameters, null);
     cl.warn(msg);
   }
  }
  
  public static final void warn(String msg, Throwable t) {
    CallLogger cl = callLogger();
    if (cl.isWarningOn()) {
      log(LocationAwareLogger.WARN_INT, msg, null, t);
      cl.warn(msg);
    }
  }

  public static final void error(String msg) {
    error(msg, (Object[])null);
  }
  
  public static final void error(String msg, Object... parameters) {
    CallLogger cl = callLogger();
    if (cl.isWarningOn()) {
      log(LocationAwareLogger.ERROR_INT, msg, parameters, null);
      cl.warn(msg);
    }
   }
   
   public static final void error(String msg, Throwable t) {
     CallLogger cl = callLogger();
     if (cl.isWarningOn()) {
       log(LocationAwareLogger.ERROR_INT, msg, null, t);
       cl.warn(msg);
     }
   }
  
  public static final Result infoR(String msg) {
    info(msg);
    return Result.getSuccessResult(msg);
  }
  
  public static final Result warnR(String msg) {
    warn(msg);
    return Result.getSuccessResult(msg);
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
   * @param detail detailed log message (such as description of error message)
   * @param json any associated json object (such as the object being saved that failed)
   * @param action the action
   * @param sourceUid
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
   * @param detail detailed log message (such as description of error message)
   * @param json any associated json object (such as the object being saved that failed)
   * @param sourceUid
   */
  public static final void auditLogInfo2(String action, String summary, String ...args) {
    auditLog2(action, "info", summary, args);
  }
  
  public static final void auditLogWarn2(String action, String summary, String ...args) {
    auditLog2(action, "warn", summary, args);
  }
  
  public static final void auditLogError2(String action, String summary, String ...args) {
    auditLog2(action, "error", summary, args);
  }
  
  public static final void auditLogCritical2(String action, String summary, String ...args) {
    auditLog2(action, "critical", summary, args);
  }
  
  public static final Result auditLogInfo2R(String action, String summary, String ...args) {
    auditLogInfo2(action, summary, args);
    return Result.getSuccessResult(summary);
  }
  
  public static final Result auditLogWarn2R(String action, String summary, String ...args) {
    auditLogWarn2(action, summary, args);
    return Result.getSuccessResult(summary);
  }
  
  public static final Result auditLogError2R(String action, String summary, String ...args) {
    auditLogError2(action, summary, args);
    return Result.getFailedResult(summary);
  }
  
  public static final Result auditLogCritical2R(String action, String summary, String ...args) {
    auditLogCritical2(action, summary, args);
    return Result.getFailedResult(summary);
  }
  /**
   * Audit log object.
   *
   * @param type (debug, info, warning, error)
   * @param summary the summary
   * @param detail detailed log message (such as description of error message)
   * @param json any associated json object (such as the object being saved that failed)
   * @param action the action
   * @param parentUid
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
  
  public static final void auditLog2(String action, String type, String summary, String ...args) {
    String[] sl = {"", "", ""};
    for (int i=0; i<args.length && i<sl.length; i++) {
      sl[i] = args[i];
    }
    for (int i = args.length; i<sl.length; i++) {
      sl[i] = "";
    }
    _auditLog(type, summary, sl[0], sl[1], action, sl[2]);
   }
  
  private static final void _auditLog(String type, String summary, String detail, String json, String action, String sourceUid) {
    if (canAuditLog(type)) {
      auditLog(AuditLogUtil.makeAuditLog(type, sourceUid, action, summary, detail, json));
    }
    logToConsole(type, (StringUtils.isNotBlank(action) ? action + ", " : "") + summary);
    if (StringUtils.isNotBlank(detail)) {
      logToConsole(type, detail);
    }
    if (StringUtils.isNotBlank(json)) {
      logToConsole(type, json);
    }
  }
  
  private static boolean canAuditLog(String type) {
    CallLogger cl = callLogger();
    return cl.isAuditLogEnabled();
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
  
  private static void log(int level, String msg, Object[] parameters, Throwable t) {
    _log.log(null, _className, level, msg, parameters, t);
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
