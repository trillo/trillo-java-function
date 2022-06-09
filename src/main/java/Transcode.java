import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.TrilloFunction;
import java.util.Map;

/*
  - Everything is private except handle() method
  - return any object and it will become the response payload
  - Make sure that returned object is serializable.
 */
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.BaseApi;
import com.collager.trillo.util.GCPRestApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.StorageApi;
import com.collager.trillo.util.TaskApi;
import com.collager.trillo.util.TrilloFunction;



public class Transcode implements Loggable, TrilloFunction {

  
  public Object handle(ScriptParameter scriptParameter) {

    // do the implementation inside _handle()
    try {
      return _handle(scriptParameter);
    } catch (Exception e) {
      log().error("Failed", e);
      return Result.getFailedResult(e.getMessage());
    }
  }

  private Object _handle(ScriptParameter scriptParameter) {
       
    Map<String, Object> message = new HashMap<String, Object>();
    
    /*
    {
      "input-bucket-file-path": "gs://transcode-test-files/inputFiles/longTestFile.mp4",
      "output-file-format": "flac",
      "output-bucket-folder": "gs://transcode-test-files/outputFiles/",
      "taskId": "<unique identifier string>",
      "statusUpdateUrl": "<url>",
      "accessToken": "token",
      "commandName": "transcoding"
    }
     */
    
    Map<String, Object> invokerContext = new LinkedHashMap<String, Object>();
    invokerContext.put("successCallback", "Transcribe");
    
    message.put("input-bucket-file-path", "gs://" + StorageApi.getBucketName() + 
        "/" + "inputFiles/" + "CV-01.mp4");
    message.put("output-file-format", "flac");
    message.put("output-bucket-folder", "gs://" + StorageApi.getBucketName() + "/" + "outputFiles/");
    message.put("invokerContext", invokerContext);
    message.put("statusUpdateUrl", BaseApi.getStatusInformUrl());
    message.put("accessToken", BaseApi.getAccessToken());
    message.put("commandName", "transcoding");
    
    Object res = GCPRestApi.publish("test-conversion-topic", message);
    
    if (res instanceof Result) {
      Result result = (Result) res;
      if (result.isFailed()) {
        message.put("error", result.getMessage());
      } else {
        message.put("response", result.getData());
      }
    } else {
      message.put("response", res);
    }
    
    return Result.getSuccessResultWithData(message);
  }

}
