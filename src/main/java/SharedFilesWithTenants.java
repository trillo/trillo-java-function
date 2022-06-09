import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.StorageApi;
import com.collager.trillo.util.TrilloFunction;
import java.util.Map;

public class SharedFilesWithTenants implements Loggable, TrilloFunction {

  public Object handle(ScriptParameter scriptParameter) {

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
    Object res = StorageApi.shareWithTenants(functionParameters);
    return res;
  }

}
