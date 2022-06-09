import java.util.Map;

import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.BaseApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.TrilloFunction;

/*
 * This function is called with a parameters. It simply prints the pretty
 * JSON of the passed parameter.
 */

/*
  - Everything is private except handle() method
  - return any object and it will become the response payload
  - Make sure that returned object is serializable.
 */
public class ParameterSample implements Loggable, TrilloFunction {

  /*
    This is the only entry point into this Trillo function
    - params : complete context passed by the application
               including request body map (V).
   */
  public Object handle(ScriptParameter params) {

    // do the implementation inside _handle()
    try {
      return _handle(params);
    } catch (Exception e) {
      log().error("Failed", e);
      return Result.getFailedResult(e.getMessage());
    }
  }

  @SuppressWarnings("unchecked")
  private Object _handle(ScriptParameter params) {
    Map<String, Object> functionParams = (Map<String, Object>) params.getV();
    log().info("Params: " + BaseApi.asJSONPrettyString(functionParams));
    return params.getV();
  }

}

