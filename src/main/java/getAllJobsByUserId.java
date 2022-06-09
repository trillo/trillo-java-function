import java.util.List;
import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.BaseApi;
import com.collager.trillo.util.DSApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.TrilloFunction;

/*
  - Everything is private except handle() method
  - return any object and it will become the response payload
  - Make sure that returned object is serializable.
 */
public class getAllJobsByUserId implements Loggable, TrilloFunction {

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
  private Object _handle(ScriptParameter scriptParameter) {
    String sqlQuery = "idOfUser=" + scriptParameter.getIdOfUser();
    Object result = DSApi.queryMany("Job", sqlQuery);
    if (result instanceof List<?>) {
      List<Object> l = (List<Object>) result;
      if (l.size() > 0){
        log().info("Jobs: " + BaseApi.asJSONPrettyString(l));
        return l;
      }
    }

    return result;

  }

}
