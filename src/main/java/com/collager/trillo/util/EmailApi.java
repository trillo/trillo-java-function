package com.collager.trillo.util;

import java.util.Map;

import com.collager.trillo.pojo.Result;

public class EmailApi extends BaseApi {
 
  public static Object sendEmail(String appName, String email, String subject, String content, String template,
                                 String fromAlias, Map<String, Object> templateParams) {
    
    return remoteCall("EmailApi", "sendEmail", appName, email, subject, content, template, fromAlias, templateParams);
  }

  public static Result sendEmail(
      final String toEmail,
      final String subject,
      String content) {
    return remoteCallAsResult("EmailApi", "sendEmail", toEmail, subject, content);
  }

  
  public static Result sendEmailMarkDownContent(String mailTo, String subject, String content) {
    return remoteCallAsResult("EmailApi", "sendEmailMarkDownContent", mailTo, subject, content);
  }

  
}


