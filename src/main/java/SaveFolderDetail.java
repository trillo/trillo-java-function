import java.util.Map;
import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.DSApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.TrilloFunction;


public class SaveFolderDetail implements Loggable, TrilloFunction {

  
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
      return DSApi.save("FolderDetail", functionParameters);
    } else {
      String folderId = "" + functionParameters.get("folderId");
      Object res = DSApi.queryOne("FolderDetail", "folderId = " + folderId);
      if (res instanceof Map<?, ?>) {
        Map<String, Object> m = (Map<String, Object>) res;
        m.putAll(functionParameters); // merge give object with the object in DS
        // save merged object
        return DSApi.save("FolderDetail", m);
      }
      return DSApi.save("FolderDetail", functionParameters);
    }
  }

}
