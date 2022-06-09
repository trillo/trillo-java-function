import java.util.List;
import java.util.Map;
import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.BaseApi;
import com.collager.trillo.util.DSApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.TrilloFunction;


public class ExtractTextResult implements Loggable, TrilloFunction {

  
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
   
    res = DSApi.get("JobImage", imageId);
    Map<String, Object> resultMap;
    if (!(res instanceof Map<?, ?>)) {
      return Result.getFailedResult("Failed to find job result");
    } 
    resultMap = (Map<String, Object>) res;
    try {
      String fileId = "" + resultMap.get("fileId");
      Map<String, Object> textRecord;
      res = DSApi.queryOne("Text", "fileId = " + fileId);
      
      if (res instanceof Map<?, ?>) {
        textRecord = (Map<String, Object>) res;
        return textRecord.get("content");
      } else {
        return res;
      }
    } catch (Exception exc) {
      return Result.getFailedResult("Failed to get text, error: " + exc.getMessage());
    }
    
  }

}
