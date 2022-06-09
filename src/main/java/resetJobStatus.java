import java.util.Map;
import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.JobApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.TrilloFunction;


public class resetJobStatus implements Loggable, TrilloFunction {

  
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
    return JobApi.updateStatus(jobId, "");
  }

}
