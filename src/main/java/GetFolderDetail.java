import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.DSApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.TrilloFunction;
import java.util.HashMap;
import java.util.Map;


public class GetFolderDetail implements Loggable, TrilloFunction {

  
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
    if (!functionParameters.containsKey("id") || !functionParameters.containsKey("folderId")) {
      return Result.getBadRequestError("id or folderId is required");
    }
    
    if (functionParameters.containsKey("id")) {
      return DSApi.get("FolderDetail", "" + functionParameters.containsKey("id"));
    } else {
      String folderId = "" + functionParameters.get("folderId");
      Object res = DSApi.queryOne("FolderDetail", "folderId = " + folderId);
      if (res instanceof Map<?, ?>) {
        return res;
      }
      
      Map<String, Object> fd = new HashMap<String, Object>();
      fd.put("folderId", folderId);
      return DSApi.save("FolderDetail", fd);
    }
  }

}
