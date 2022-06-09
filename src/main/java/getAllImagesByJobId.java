import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.DSApi;
import com.collager.trillo.util.TrilloFunction;
import java.util.Map;

/*
  - Everything is private except handle() method
  - return any object and it will become the response payload
  - Make sure that returned object is serializable.
 */
public class getAllImagesByJobId implements Loggable, TrilloFunction {

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

  @SuppressWarnings("unchecked")
  private Object _handle(ScriptParameter scriptParameter) {
        Map<String, Object> functionParameters = (Map<String, Object>)scriptParameter.getV();
        String jobId = "" + functionParameters.get("jobId");
        String sqlQuery = "select * from JobImage_tbl where jobId=" + 
        jobId + " and deleted != true order by fileName";
        Object imageResult = DSApi.queryMany(sqlQuery);
        return imageResult;
    }

}
