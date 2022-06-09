import java.util.List;
import java.util.Map;
import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.BaseApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.StorageApi;
import com.collager.trillo.util.TrilloFunction;

/*
  - Everything is private except handle() method
  - return any object and it will become the response payload
  - Make sure that returned object is serializable.
 */
public class StorageAPISample implements Loggable, TrilloFunction {

  /*
   * This is the only entry point into this Trillo function - params : complete context passed by
   * the application including request body map (V).
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

  private Object _handle(ScriptParameter params) {
    List<Map<String, Object>> list = StorageApi.listFiles("users/anil", false);
    log().info("Files: \n" + BaseApi.asJSONPrettyString(list));
    return Result.getSuccessResult();
  }

}

