import java.util.HashMap;
import java.util.Map;
import com.collager.trillo.util.Api;
import com.collager.trillo.util.DSApi;
import com.collager.trillo.util.MetaApi;
import com.collager.trillo.util.ServerlessFunction;

@SuppressWarnings("unchecked")
public class FolderTask extends ServerlessFunction {
  
  @Api(httpMethod="post")
  public Object saveFolderTask(Map<String, Object> parameters) {
    Object res = getFolderTask("" + parameters.get("folderId"));
    
    Map<String, Object> m;
    if (res instanceof Map<?, ?>) {
      m = (Map<String, Object>) res;
    } else {
      m = new HashMap<String, Object>();
      m.put("folderId", parameters.get("folderId"));
    }
    m.put("taskId", parameters.get("taskId"));
    return DSApi.save("FolderToTask", m);
  }
  
  @Api(httpMethod="get")
  public Object getFolderTask(Map<String, Object> parameters) {
    Object res = getFolderTask("" + parameters.get("folderId"));
    Map<String, Object> m;
    if (res instanceof Map<?, ?>) {
      m = (Map<String, Object>) res;
      Object res2 = DSApi.get("TrilloMD", "" + m.get("taskId"));
      if (res2 instanceof Map) {
        m.put("taskName", ((Map<String, Object>) res2).get("name"));
      }
      return m;
    }
    return res;
  }
  
  public Object getFolderTask(String folderId) {
    folderId = folderId.trim();
    return DSApi.queryOne("FolderToTask", "folderId = " + folderId);
  }
}