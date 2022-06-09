import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.DSApi;
import com.collager.trillo.util.HttpApi;
import com.collager.trillo.util.LogApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.StorageApi;
import com.collager.trillo.util.TrilloFunction;

public class ExtractText implements Loggable, TrilloFunction {

  private static final String TIKA_SERVER_URL = "<url of tika server>";
  private String serviceUrl;
 
  @SuppressWarnings("unchecked")
  public Object handle(ScriptParameter scriptParameter)  {

    serviceUrl = TIKA_SERVER_URL; //System.getenv("TRILLO_ADDON_SERVICE");

    
    Map<String, Object> functionParameters = (Map<String, Object>)scriptParameter.getV();
    // jobId is passed as parameter
    String jobId = "" + functionParameters.get("jobId");
    Object res = DSApi.queryOne("select * from Job_tbl where id = " + jobId);
    if (!(res instanceof Map<?, ?>)) {
      return res;
    }
    //Map<String, Object> job = (Map<String, Object>) res;
    // fetch a list of images for the given job
    String sqlQuery = "select * from JobImage_tbl where jobId="+
        jobId + " and !deleted order by sequenceNumber";
    Object imageResult = DSApi.queryMany(sqlQuery);

    if (!(imageResult instanceof List<?>)) {
      // if it is not a list then it is Result which
      // gives the details of the error, return back
      return imageResult;
    }

    List<Map<String, Object>> images = (List<Map<String, Object>>) imageResult;
    
    for (Map<String, Object> im : images) {
      res = DSApi.get("File", "" + im.get("fileId"));
      if (res instanceof Map<?, ?>) {
        extractText((Map<String, Object>) res);
      }
    }
    
    return Result.getSuccessResult();
  }

  private void updateStatus(String fileName, String folderName, String status, String message) {
    DSApi.saveFileOp(fileName, folderName, "textExtraction", "", status, message);
  }

  
  @SuppressWarnings("unchecked")
  private Object extractText(Map<String, Object> fileMap) {
    
   
    String fileName;
    String folderName;
    String fileId = "" + fileMap.get("id");
    Object res;
    
    fileName = "" + fileMap.get("fileName");
    folderName = StorageApi.getFolderPath("" + fileMap.get("folderId"));
    
    String fullFilePath = folderName + "/" + fileName;
    
    Result result = StorageApi.readFromBucket(fullFilePath);
    
    if (result.isFailed()) {
      return result;
    }
    
   
    
    String content;
    
    if (fileName.endsWith(".txt")) {
      content = new String((byte[])result.getData());
      LogApi.auditLogInfo("File content", content);
    } else {
    
      byte[] fileContent;
      if (result.getData() instanceof byte[]) {
        fileContent = (byte[]) result.getData();
      } else {
        try {
          fileContent = Base64.getDecoder().decode(new String((String)result.getData()).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
           return Result.getFailedResult("Filaed to decode returned result: " + e.getMessage());
        }
      }
  
      
      LogApi.auditLogInfo("File length: " + fileContent.length);
      
      Map<String, String> headers = new HashMap<String, String>();
      headers.put("Accept", "text/plain");
      
      result = HttpApi.writeFileBytes(serviceUrl, fileName, fileContent, "upload", headers);
      
      if (result.isFailed()) {
        LogApi.auditLogInfo("Failed to extract text for, file: " + fileName);
        LogApi.auditLogInfo("Error message: " + result.getMessage());
        updateStatus(fileName, folderName, "failed", result.getMessage());
        return Result.getFailedResult("Failed to extract text for, file: " + fileName);
      }
      
      content = "" + result.getData();
    }
    
    updateStatus(fileName, folderName, "completed", "success");
  
    Map<String, Object> textRecord;
    res = DSApi.queryOne("Text", "fileId = " + fileId);
    
    if (res instanceof Map<?, ?>) {
      textRecord = (Map<String, Object>) res;
    } else {
      textRecord = new HashMap<>();
    }
    
    
    
    LogApi.auditLogInfo("Extracted content length: " + content.length());
    
    textRecord.put("fileId", fileId);
    textRecord.put("content", content);
    textRecord.put("contentType", FilenameUtils.getExtension(fileName));

    res = DSApi.save("Text", textRecord);
    DSApi.commitTx();
    return res;
  }
}