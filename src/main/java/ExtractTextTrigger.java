import java.util.HashMap;
import java.util.Map;
import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.DSApi;
import com.collager.trillo.util.FuncApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.TrilloFunction;


public class ExtractTextTrigger implements Loggable, TrilloFunction {

  
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
    Object res = DSApi.queryOne("select * from Job_tbl where id = " + jobId);
    if (!(res instanceof Map<?, ?>)) {
      return res;
    }
    
    
    String taskName = ((Map<String, Object>)res).get("jobName") +  "(id=" + jobId + ")";
    String taskType = "ExtractText";
    String sourceUid = "Job." + jobId;
    String functionName = "ExtractText";
    res = FuncApi.createTaskBySourceUid(taskName, taskType, sourceUid, functionName, functionParameters);
    
    if (!(res instanceof Result)) {
      return Result.getFailedResult("createTaskBySourceUid() returned unexpected type");
    }
    
    Result result = (Result)res;
    
    if (result.isFailed()) {
      return result;
    }
    
    Map<String, Object> m = (Map<String, Object>) result.getData();
    String taskExecId = "" + m.get("taskExecId");
    
    Map<String, Object> m2 = new HashMap<String, Object>();
    m2.put("jobId", jobId);
    m2.put("taskExecId", taskExecId);
    
    return Result.getSuccessResultWithData(m2);
  }

}
