import java.util.ArrayList;
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

public class TranslateFile implements Loggable, TrilloFunction {

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
    Object res = translateToSpanish(content);
    
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
    String translations = "";
    if (m.get("translations") instanceof List<?>) {
      List<Object> l = (List<Object>) m.get("translations");
      for (Object tempMap : l) {
        if (tempMap instanceof Map<?, ?>) {
          translations += (translations.length() > 0 ? "\n" : "") + ((Map<?, ?>) tempMap).get("translatedText");
        }
      }
    }
    Map<String, Object> tobj;
    Object tempRes = DSApi.queryOne("Translation", "folderName = '" + folderName + 
        "' and fileName = '" + fileName + "'");
    if (tempRes instanceof Map<?, ?>) {
      tobj = (Map<String, Object>) tempRes;
    } else {
      tobj = new HashMap<String, Object>();
      tobj.put("fileName", fileName);
      tobj.put("folderName", folderName);
    }
    tobj.put("originalText", content);
    tobj.put("translatedText", translations);
    LogApi.auditLogInfo("Saving translation", translations);
    return DSApi.save("Translation", tobj);
  }
  
  private Object translateToSpanish(String content) {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("sourceLanguageCode", "en");
    params.put("targetLanguageCode", "es");
    List<String> cl = new ArrayList<String>();
    cl.add(content);
    params.put("contents", cl);
    Object res = translateText(params);
    LogApi.auditLogInfo("Translation result", BaseApi.asJSONPrettyString(res));
    return res;
  }
  
  private Object translateText(Map<String, Object> parameters) {
    String url = "https://translate.googleapis.com/v3/projects/{projectId}:translateText";
    return GCPRestApi.post(url, parameters);
  }
}