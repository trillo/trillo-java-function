import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.BaseApi;
import com.collager.trillo.util.DSApi;
import com.collager.trillo.util.GCPRestApi;
import com.collager.trillo.util.LogApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.StorageApi;
import com.collager.trillo.util.TrilloFunction;

public class ExtractEntity implements Loggable, TrilloFunction {

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
    LogApi.auditLogInfo("Input parameter ", BaseApi.asJSONPrettyString(functionParameters));
    if (!functionParameters.containsKey("folderName")) {
      return Result.getBadRequestError("Missing folder name");
    }
    if (!functionParameters.containsKey("fileName")) {
      return Result.getBadRequestError("Missing file name");
    }
    
    String folderName = "" + functionParameters.get("folderName");
    String fileName = "" + functionParameters.get("fileName");
    
    String sourceFilePath = folderName + "/" + fileName;
    Result r = StorageApi.readFromBucket(sourceFilePath);
    if (r.isFailed()) {
      return r;
    }
    String content = new String((byte[])r.getData());
    LogApi.auditLogInfo("Input text", content);
    Object res = extractEntity(content);
    
    Map<String, Object> m;
    if (res instanceof Map<?, ?>) {
      m = (Map<String, Object>) res;
    } else if (res instanceof Result){
      Result result = (Result) res;
      if (result.isSuccess() && result.getData() instanceof Map<?, ?>) {
        m = (Map<String, Object>) result.getData();
      } else {
        return res;
      }
    } else {
      return res;
    }
    LogApi.auditLogInfo("Saving file: " + folderName + "/" + fileName);
    String entities = "";
    if (m.get("entities") instanceof List<?>) {
      List<Object> l = (List<Object>) m.get("entities");
      entities = BaseApi.asJSONPrettyString(l);
    }
    Map<String, Object> tobj;
    Object tempRes = DSApi.queryOne("EntityExtraction", "folderName = '" + folderName + 
        "' and fileName = '" + fileName + "'");
    if (tempRes instanceof Map<?, ?>) {
      tobj = (Map<String, Object>) tempRes;
    } else {
      tobj = new HashMap<String, Object>();
      tobj.put("fileName", fileName);
      tobj.put("folderName", folderName);
    }
    tobj.put("originalText", content);
    tobj.put("entities", entities);
    LogApi.auditLogInfo("Saving entities", entities);
    return DSApi.save("EntityExtraction", tobj);
  }
  
  private Object extractEntity(String content) {
    Map<String, Object> params = new HashMap<String, Object>();
    Map<String, Object> document = new HashMap<String, Object>();
    params.put("encodingType", "UTF8");
    params.put("document", document);
    document.put("type", "PLAIN_TEXT");
    document.put("content", content);
    Object res = extractEntity(params);
    LogApi.auditLogInfo("Entity extracton results", BaseApi.asJSONPrettyString(res));
    return res;
  }
 
  private Object extractEntity(Map<String, Object> parameters) {
    String url = "https://language.googleapis.com/v1/documents:analyzeEntities";
    return GCPRestApi.post(url, parameters);
  }
}