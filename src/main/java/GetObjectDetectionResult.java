import java.util.List;
import java.util.Map;
import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.BaseApi;
import com.collager.trillo.util.DSApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.TrilloFunction;


public class GetObjectDetectionResult implements Loggable, TrilloFunction {

  
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
    String imageId = "" + functionParameters.get("imageId");
    //Object res = DSApi.get("Job", jobId);
    Object res = DSApi.queryOne("select * from Job_tbl where id = " + jobId);
    if (!(res instanceof Map<?, ?>)) {
      return res;
    }
   
    res = DSApi.queryOne("JobResult", "jobId = " + jobId + " and " + "type = 'ObjectDetection'");
    Map<String, Object> resultMap;
    if (!(res instanceof Map<?, ?>)) {
      return Result.getFailedResult("Failed to find job result");
    } 
    resultMap = (Map<String, Object>) res;
    if (resultMap.get("value") instanceof String) {
      try {
        List<Map<String,Object>> l = BaseApi.fromJSONStringAsArray("" + resultMap.get("value"));
        for (Map<String, Object> m : l) {
          if (imageId.equals("" + m.get("imageId"))) {
            return m;
          }
        }
      } catch (Exception exc) {
        
      }
    }
    return Result.getFailedResult("No objects found for the give image id");
  }

}
