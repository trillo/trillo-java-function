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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.collager.trillo.model.AuditLogUtil;

/** Log Api. */

public class LogApi {
  
  static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  
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
  public void setLogLevel(String logLevel) {
    callLogger().setLogLevel(logLevel);
  }
  
  /**
   * Disable audit log, by default it is enabled.
   * Audit log is logging to Trillo DB. It is used for a coarse business events and background tasks to
   * isolate errors per task. The level of audit logging depends on the setLogLevel call above. 
   * During some operations, you may choose to disable logging independent of log level (to console)
   */
  public void disableAuditLog() {
    callLogger().setAuditLogEnabled(false);;
  }
  
  /**
   * Enabled audit log. See above for the description of audit log.
   */
  public void enableAuditLog() {
    callLogger().setAuditLogEnabled(false);;
  }
  
  /**
   * Disable log collections for the thread. Logs are collected for a call and sent back the client
   * in the result. This is useful for debugging. By default it is disabled. You can disable the colleciton
   * of the logs (it should be disabled in the production unless you wish to trouble shoot something).
   */
  public void disableLogsCollection() {
    callLogger().setCollectCallLogs(false);;
  }
  
  /**
   * Enable log collections for the thread.
   */
  public void enableLogsCollection() {
    callLogger().setCollectCallLogs(true);;
  }
  
  /**
   * logging
   *
   *
   * @param msg
   */
  public static final void logDebug(String msg) {
    CallLogger cl = callLogger();
    if (cl.isDebugOn()) {
      LOG.debug(msg);
      cl.debug(msg);
    }
  }
  
  /**
   * login info
   *
   *
   * @param msg
   */
  public static final void logInfo(String msg) {
    CallLogger cl = callLogger();
    if (cl.isInfoOn()) {
      LOG.info(msg);
      cl.info(msg);
    }
  }
  
  /**
  *
  * logging
  *
  * @param msg
  */
  public static final void logWarn(String msg) {
   CallLogger cl = callLogger();
   if (cl.isWarningOn()) {
     LOG.warn(msg);
     cl.warning(msg);
   }
  }


  /**
   *
   * logging
   *
   * @param msg
   */
  public static final void logError(String msg) {
    CallLogger cl = callLogger();
    if (cl.isErrorOn()) {
      LOG.error(msg);
      cl.error(msg);
    }
  }
  
  public static boolean isLogLevelOn(String type) {
    if ("debug".equals(type)) {
      return true;
    } else if ("info".equals(type)) {
      return true;
    } else if ("warning".equals(type)) {
      return true;
    } else if ("error".equals(type)) {
      return true;
    }
    return false;
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
   * @param parentUid
   */
  public static final void auditLogInfo(String summary, String ...args) {
    auditLog("info", summary, args);
  }
  
  /**
   * Audit log object.
   *
   * @param summary the summary
   * @param detail detailed log message (such as description of error message)
   * @param json any associated json object (such as the object being saved that failed)
   * @param action the action
   * @param parentUid
   */
  public static final void auditLogError(String summary, String ...args) {
    auditLog("error", summary, args);
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
   for (int i=0; i<args.length; i++) {
     sl[i] = args[i];
   }
   for (int i = args.length; i<sl.length; i++) {
     sl[i] = "";
   }
   _auditLog(type, summary, sl[0], sl[1], sl[2], sl[3]);
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
    return isLogLevelOn(type) && cl.isAuditLogEnabled();
  }
  
  public static void logToConsole(String type, String msg) {
    if ("debug".equals(type)) {
      LOG.debug(msg);
    } else if ("info".equals(type)) {
      LOG.info(msg);
    } else if ("warning".equals(type)) {
      LOG.warn(msg);
    } else if ("error".equals(type)) {
      LOG.error(msg);
    }
  }

}
