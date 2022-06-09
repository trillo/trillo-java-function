import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.DSApi;
import com.collager.trillo.util.GCPRestApi;
import com.collager.trillo.util.LogApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.ServerlessFunction;
import com.collager.trillo.util.TrilloFunction;
import com.collager.trillo.util.Util;


public class ObjectDetection extends ServerlessFunction implements TrilloFunction, Loggable {
  
  private static final String serviceUrl = "https://vision.googleapis.com/v1/images:annotate";
  
  public Object handle(ScriptParameter params) {

    // do the implementation inside _handle()
    try {
      return _handle(params);
    } catch (Exception e) {
      log().error("Failed", e);
      return Result.getFailedResult(e.getMessage());
    } finally {
    }
  }
  
  @SuppressWarnings("unchecked")
  private Object _handle(ScriptParameter scriptParameter) {
    Map<String, Object> functionParameters = (Map<String, Object>)scriptParameter.getV();
    // jobId is passed as parameter
    String jobId = "" + functionParameters.get("jobId");
    Object res = DSApi.queryOne("select * from Job_tbl where id = " + jobId);
    if (!(res instanceof Map<?, ?>)) {
      return res;
    }
    Map<String, Object> job = (Map<String, Object>) res;
    // fetch a list of images for the given job
    String sqlQuery = "select * from JobImage_tbl where jobId="+ 
        jobId + " order by sequenceNumber";
    Object imageResult = DSApi.queryMany(sqlQuery);
    
    if (!(imageResult instanceof List<?>)) {
      // if it is not a list then it is Result which 
      // gives the details of the error, return back
      return imageResult;  
    }

    List<Map<String, Object>> images = (List<Map<String, Object>>) imageResult;
   
    
    if (images.size() == 0) {
      return Result.getFailedResult("There are no images available in the job, please add a few photos and then try");
    }
    
    
    Map<String, Object> image = images.get(0);
    
    // from the first image in the list get the bucketName,
    // and folderName
    String bucketName = (String) image.get("bucketName");
    String folderName = (String) image.get("folderName");
   
    Map<String, Object> body;
    Result result;
    body = getRequestBody(bucketName, folderName, images);
    LogApi.auditLogInfo("Request body", Util.asJSONPrettyString(body));
    res = GCPRestApi.post(serviceUrl, body);
    
    if (res instanceof Result) {
      result = (Result) res;
      if (result.isFailed()) {
        LogApi.auditLogError("Failed to process request", result.getMessage());
        job.put("status", "failed");
        DSApi.save("Job", job);
        return result;
      } else {
        String resultJson = processResult(result, images);
        Object r2 = DSApi.queryOne("JobResult", "jobId = " + jobId + " and " + "type = 'ObjectDetection'");
        Map<String, Object> resultMap;
        if (r2 instanceof Map<?, ?>) {
          resultMap = (Map<String, Object>) r2;
        } else {
          resultMap = new HashMap<String, Object>();
          resultMap.put("jobId", jobId);
          resultMap.put("type", "ObjectDetection");
        }
        LogApi.auditLogInfo("Object detection completed", resultJson);
        resultMap.put("value", resultJson);
        DSApi.save("JobResult", resultMap);
        job.put("status", "completed");
        DSApi.save("Job", job);
      }
    }
    return Result.getSuccessResult();
  }

  @SuppressWarnings("unchecked")
  private String processResult(Result result, List<Map<String, Object>> images) {
    if (!(result.getData() instanceof Map<?, ?>)) {
      LogApi.auditLogError("Unexpected result type");
    }
    
    Map<String, Object> data = (Map<String, Object>) result.getData();
    
    List<Map<String, Object>> responses = (List<Map<String, Object>>) data.get("responses");
    
    //LogApi.auditLogInfo("Response", Util.asJSONPrettyString(responses));
    
    JSONObject response;
    JSONObject image;
    JSONArray annotations;
    JSONObject annotation;
    JSONArray imageResults = new JSONArray();
    JSONObject imageResult;
    JSONArray objects;
    JSONObject object;
    JSONArray normalizedVertices;
    for (int i=0; i<responses.size(); i++) {
      response = new JSONObject(responses.get(i));
      image = new JSONObject(images.get(i));
      
      if (response.has("error")) {
        LogApi.auditLogError("Failed to process image: " + image.getString("fileName"), 
            response.getJSONObject("error").getString("message"));
      } else if (response.has("localizedObjectAnnotations")) {
        annotations = response.getJSONArray("localizedObjectAnnotations");
        imageResult = new JSONObject();
        imageResults.put(imageResult);
        imageResult.put("imageId", image.get("id"));
        imageResult.put("imageName", image.get("fileName"));
        
        objects = new JSONArray();
        imageResult.put("objects", objects);
        
        for (int j=0; j<annotations.length(); j++) {
          annotation = annotations.getJSONObject(j);
          try {
            object = new JSONObject();
            objects.put(object);
            object.put("mid", annotation.get("mid"));
            object.put("name", annotation.get("name"));
            object.put("score", annotation.get("score"));
            normalizedVertices = annotation.getJSONObject("boundingPoly").getJSONArray("normalizedVertices");
            try {
              object.put("x1", normalizedVertices.getJSONObject(0).getDouble("x") * 100);
            } catch (Exception e) {
              object.put("x1", 0);
            }
            try {
              object.put("y1", normalizedVertices.getJSONObject(0).getDouble("y") * 100);
            } catch (Exception e) {
              object.put("y1", 0);
            }
            try {
              object.put("x2", normalizedVertices.getJSONObject(2).getDouble("x") * 100);
            } catch (Exception e) {
              object.put("x2", 0);
            }
            try {
              object.put("y2", normalizedVertices.getJSONObject(2).getDouble("y") * 100);
            } catch (Exception e) {
              object.put("x2", 0);
            }
          } catch (Exception exc) {
            LogApi.auditLogInfo("Failed to process reponse : " + j + ", ignoring it.", annotation.toString(2));
          }
        }
      }
    }
    
    return imageResults.toString(2);
  }

  private Map<String, Object> getRequestBody(String bucketName, String folderName, 
      List<Map<String, Object>> images) {
    Map<String, Object> body = new LinkedHashMap<String, Object>();
    List<Map<String, Object>> requests = new ArrayList<Map<String, Object>>();
    String fileName;
    String fileUrl;
    Map<String, Object> table = new HashMap<String, Object>();
    int n = 0;
    for (Map<String, Object> img : images) {
      fileName = (String)img.get("fileName");
      fileUrl = "gs://" + bucketName + "/" + folderName + "/" + fileName;
      if (table.containsKey(fileUrl)) {
        LogApi.auditLogInfo("Deuplicate file", fileUrl);
        continue;
      }
      table.put(fileUrl, fileUrl);
      LogApi.auditLogInfo(n + ": URL: " + fileUrl);
      n++;
      requests.add(getOneRequestBody(fileUrl));
      if (n == 16) {
        break;
      }
    }
    body.put("requests", requests);
    return body;
  }
  
  private Map<String, Object> getOneRequestBody(String fileUrl) {
    Map<String, Object> request = new LinkedHashMap<String, Object>();
    Map<String, Object> image = new LinkedHashMap<String, Object>();
    Map<String, Object> source = new LinkedHashMap<String, Object>();
    request.put("image", image);
    image.put("source", source);
    source.put("imageUri", fileUrl);
    List<Map<String, Object>> features = new ArrayList<Map<String, Object>>();
    Map<String, Object> oneFeature = new LinkedHashMap<String, Object>();
    oneFeature.put("maxResults", 10);
    oneFeature.put("type", "OBJECT_LOCALIZATION");
    features.add(oneFeature);
    request.put("features", features);
    return request;
  }
}
