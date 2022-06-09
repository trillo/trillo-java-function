import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.DSApi;
import com.collager.trillo.util.LogApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.StorageApi;
import com.collager.trillo.util.TrilloFunction;
import com.collager.trillo.util.UMApi;

@SuppressWarnings({"unchecked", "rawtypes"})
public class CopyJobs implements Loggable, TrilloFunction {

  public Object handle(ScriptParameter scriptParameter) {
    try {
      return _handle(scriptParameter);
    } catch (Exception e) {
      log().error("Failed", e);
      return Result.getFailedResult(e.getMessage());
    }
  }

  private Object _handle(ScriptParameter scriptParameter) {
    Map<String, Object> functionParameters = (Map<String, Object>)scriptParameter.getV();
    String sourceIdOfUser = "" + functionParameters.get("sourceIdOfUser");
    LogApi.auditLogInfo("Source user: " + sourceIdOfUser, "");
    Object res;
    UMApi.switchToPrivilegedMode();
    int n = 0;
    try {
      res = DSApi.queryMany("BatchIOJob", "idOfUser = " + sourceIdOfUser);
      if (!(res instanceof List)) {
        LogApi.auditLogError("Failed to get jobs for the given if of user", ((Result)res).getMessage());
        return res;
      }
      List<Map> l = (List<Map>) res;
      LogApi.auditLogInfo("Total number of jobs: " + l.size());
      for (Map m : l) {
        LogApi.auditLogInfo("Copying job number: " + n);
        n++;
        copyOneJob(m, scriptParameter.getIdOfUser());
      }
      
    } finally {
      UMApi.resetPrivilegedMode();
    }
    return Result.getSuccessResult("Number of jobs copied: " + n);
  }

  private void copyOneJob(Map m, long targetIdOfUser) {
    String sourceJob = "" + m.get("id");
    String sourceIdOfUser = "" + m.get("idOfUser");
    Map m2 = new HashMap(m);
    m2.remove("id");
    m2.put("idOfUser", targetIdOfUser);
    Object res = DSApi.save("BatchIOJob", m2);
    if (!(res instanceof Map)) {
      LogApi.auditLogError("Failed to save job: " + sourceJob, ((Result)res).getMessage());
      return;
    }
    LogApi.auditLogInfo("Copied job: " + sourceJob);
    m2 = (Map) res;
    String targetJob = "" + m2.get("id");
    copyImages(sourceJob, sourceIdOfUser, targetJob, "" + targetIdOfUser);
  }

  private void copyImages(String sourceJob, String sourceIdOfUser, String targetJob, String targetIdOfUser) {
    Object res = DSApi.queryMany("BatchIOImage", "jobId = " + sourceJob);
    if (!(res instanceof List)) {
      LogApi.auditLogError("Failed to get images for the given job: " + sourceJob, ((Result)res).getMessage());
      return;
    }
    List<Map> l = (List<Map>) res;
    LogApi.auditLogInfo("Copying images: " + l.size());
    for (Map m : l) {
      copyOneImage(sourceJob, sourceIdOfUser, targetJob, targetIdOfUser, m);
    }
  }

  private void copyOneImage(String sourceJob, String sourceIdOfUser, String targetJob,
    String targetIdOfUser, Map m) {
    String fileName = "" + m.get("fileName");
    String folderName = "" + m.get("folderName");
    folderName = StringUtils.stripEnd(folderName, "/");
    String targetFolderName;
    Result r;
    //LogApi.auditLogInfo("Coping image: " + fileName);
    int idx = folderName.lastIndexOf("/" + sourceJob);
    if (idx > 0) {
      targetFolderName = folderName.substring(0, idx) + "/" + targetJob;
      r = StorageApi.copyFileWithinBucket(folderName + "/" + fileName, targetFolderName + "/" + fileName, true);
      if (r.isFailed()) {
        LogApi.auditLogError("Failed top copy file: " + fileName, r.getMessage());
      }
    } else {
      targetFolderName = folderName;
    }
    
    Map m2 = new HashMap(m);
    m2.remove("id");
    m2.put("idOfUser", targetIdOfUser);
    m2.put("jobId", targetJob);
    m2.put("folderName", targetFolderName);
    Object res = DSApi.save("BatchIOImage", m2);
    if (!(res instanceof Map)) {
      LogApi.auditLogError("Failed to save image for the job: " + sourceJob + ", image: " + fileName, 
          ((Result)res).getMessage());
    } else {
      //LogApi.auditLogInfo("Copied image: " + fileName);
    }
  }

}