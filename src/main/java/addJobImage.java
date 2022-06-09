import java.util.HashMap;
import java.util.Map;
import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.DSApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.TrilloFunction;

/*
  - Everything is private except handle() method
  - return any object and it will become the response payload
  - Make sure that returned object is serializable.
 */
public class addJobImage implements Loggable, TrilloFunction {

    /*
      This is the only entry point into this Trillo function
      - scriptParameter : complete context passed by the application
                 including request body map (V).
     */
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

        functionParameters.put("idOfUser", scriptParameter.getIdOfUser());

        // by conventions we know that _file key of functionParameters gives the file object
        // also additionalParameter gives the parameter passed as functionStr in the upload call
        Map<String, Object> fileMap;
        Map<String, Object> additionalParams;
        if (!(functionParameters.get("_file") instanceof Map<?, ?>)) {
          return Result.getFailedResult("_file aparameter (for the file object) is not valid");
        }
        if (!(functionParameters.get("_file") instanceof Map<?, ?>)) {
          return Result.getFailedResult("_file aparameter (for the file object) is not valid");
        }
        fileMap = (Map<String, Object>) functionParameters.get("_file");
        additionalParams = (Map<String, Object>) functionParameters.get("additionalParams");
        Object result = DSApi.get("Job", "" + additionalParams.get("jobId"));
        if (!(result instanceof Map<?, ?>)) {
            return result;
        }
        Map<String, Object> image = new HashMap<String, Object>();
        image.put("idOfUser", scriptParameter.getIdOfUser());
        image.put("fileName", fileMap.get("fileName"));
        image.put("fileId", fileMap.get("id"));
        image.put("jobId", additionalParams.get("jobId"));
        image.put("folderName", fileMap.get("folderName"));
        image.put("bucketName", fileMap.get("bucketName"));
        image.put("_uniqueness_condition_", "jobid = " + additionalParams.get("jobId") + 
            " and fileName = '" + fileMap.get("fileName") + "'");
        if (additionalParams.get("sequenceNumber") != null) {
          try {
            image.put("sequenceNumber", Integer.valueOf("" + additionalParams.get("sequenceNumber")));
          } catch (Exception exc) {
            image.put("sequenceNumber", 0);
          }
        } else {
          image.put("sequenceNumber", 0);
        }
        result = DSApi.save("JobImage", image);
        //log().info(BaseApi.asJSONPrettyString(result));
        return result;
    }
}
