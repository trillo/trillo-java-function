/*
 * Copyright (c)  2022 Trillo Inc.
 * All Rights Reserved
 * THIS IS UNPUBLISHED PROPRIETARY CODE OF TRILLO INC.
 * The copyright notice above does not evidence any actual or
 * intended publication of such source code.
 *
 */

import java.util.HashMap;
import java.util.Map;
import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.CacheApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.TrilloFunction;
import com.collager.trillo.util.Util;

/*
  - Everything is private except handle() method
  - return any object and it will become the response payload
  - Make sure that returned object is serializable.
 */
public class RedisPub implements Loggable, TrilloFunction {

  /*
    This is the only entry point into this Trillo function
    - scriptParameter : complete context passed by the application
               including request body map (V).
   */
  public Object handle(ScriptParameter scriptParameter) {

    // do the implementation inside _handle()
    try {
      return _handle(scriptParameter);
    } catch (Exception e) {
      log().error("Failed", e);
      return Result.getFailedResult(e.getMessage());
    }
  }

  private Object _handle(ScriptParameter scriptParameter) {
    //Map<String, Object> functionParameters = (Map<String, Object>)scriptParameter.getV();

    Map<String, Object> testMap = new HashMap<String, Object>();
   
    testMap.put("atttr1", "value1");
    testMap.put("atttr2", "value2");
   
    CacheApi.publishMessage("pyb_sub_channel_name_here", Util.asJSONString(testMap));

    return "Completed successfully";
  }

}
