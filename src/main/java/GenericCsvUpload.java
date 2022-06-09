import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.collager.trillo.model.CSVIterator;
import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.CSVApi;
import com.collager.trillo.util.DSApi;
import com.collager.trillo.util.LogApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.StorageApi;
import com.collager.trillo.util.TrilloFunction;

public class GenericCsvUpload implements Loggable, TrilloFunction {

  
  private int PAGE_SIZE = 1000;

  
  /* This is the entry point for the function. */
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
  private Object _handle(ScriptParameter scriptParameter) throws IOException {
   
    Map<String, Object> functionParameters = (Map<String, Object>)scriptParameter.getV();
    // by conventions we know that _file key of functionParameters gives the file object
    // also additionalParameter gives the parameter passed as functionParam in the upload call
    Map<String, Object> fileMap;
    Map<String, Object> additionalParams;
    if (!(functionParameters.get("_file") instanceof Map<?, ?>)) {
      return Result.getFailedResult("_file aparameter (for the file object) is not valid");
    }
    if (!(functionParameters.get("additionalParams") instanceof Map<?, ?>)) {
      return Result.getFailedResult("function parameters are not valid");
    }
    fileMap = (Map<String, Object>) functionParameters.get("_file");
    additionalParams = (Map<String, Object>) functionParameters.get("additionalParams");
    
    if (!additionalParams.containsKey("className")) {
      return Result.getFailedResult("Missing className parameter in the funcitonParam");
    }
    
    String className =  "" + additionalParams.get("className");
    
    LogApi.auditLogInfo("Saving CSV file in the table for the class: " + className);
    
    // file name on the bucket
    String sourceFilePath = fileMap.get("folderName") + "/" + fileMap.get("fileName");
    
    // create a temp file on the current machine's disk
    File tempFile = File.createTempFile("" + fileMap.get("fileName"), 
        ".json", new File("/tmp"));
    
    try {
      
      // Copy bucket file as the temporary file
      Result result = StorageApi.copyFileFromBucket(sourceFilePath, tempFile.getPath());     
      if (result.isFailed()) {
        return result;
      }
      
      // process the CSV file copied on the local file system
      return processCSVFile(tempFile.getPath(), className);
    
    } finally {
      if (tempFile.exists()) {
        // delete the temporary file created in the local file system
        tempFile.delete();
      }
    }
  }

 
  /*
   * Process one CSV file.
   * It paginates through the CSV file (PAGE_SIZE records at a time, PAGE_SIZE is defined above).
   * It maps rows and saves in the table represented by the className.
   */
  @SuppressWarnings("unchecked")
  private Result processCSVFile(String localFile, String className) {

    int n = 0;
    int successful = 0;
    int failed = 0;
    String failedMessage = "";
    
    // creates CSV iterator to iterate thru each row at a time or get a page of the row.
    
    CSVIterator csvIter = CSVApi.getCSVIterator(localFile, 0, PAGE_SIZE);

    Object res;

    try {

      List<Map<String, Object>> rows;
      List<Object> mappedRows;
      if (!csvIter.hasNextPage()) {
        return Result.getSuccessResult("No rows present in the file");
      }
      do {
        res = csvIter.getPage();
        if (res instanceof List<?>) {
          rows = (List<Map<String, Object>>) res;
          n += rows.size();
          mappedRows = mapRows(rows);
          // LogApi.auditLogInfo("Number of rows: " + n);
          res = DSApi.saveMany(className, mappedRows);
          if (res instanceof Result && ((Result) res).isFailed()) {
            failed += rows.size();

          } else {
            successful += rows.size();
          }
        } else {
          return res instanceof Result ? (Result) res
              : new Result("Unknown result type: " + res.getClass().getName());
        }
      } while (csvIter.hasNextPage());
      if (failed > 0) {
        LogApi.auditLogInfo("Total records processed: " + n + ", Succeeded records: " + successful
            + ", Failed records: " + failed + "<br/>" + failedMessage);
        return Result.getFailedResult("Total records processed: " + n + ", Succeeded records: "
            + successful + ", Failed records: " + failed + "<br/>" + failedMessage);
      } else {
        return Result.getSuccessResult("Total: " + n + ", Succeeded: " + successful);
      }

    } finally {
      csvIter.close();
    }
  }

  
  /*
   * Maps CSV row in the row that is required by the GMC API call.
   */
  private List<Object> mapRows(List<Map<String, Object>> rows) {
    List<Object> mappedRows = new ArrayList<Object>();
    Map<String, Object> row;
    
    for (Map<String, Object> m : rows) {
      row = mapOneRow(m);
      mappedRows.add(row);
    }

    return mappedRows;
  }

  /* implement function to map one row in application specifc way. */
  private Map<String, Object> mapOneRow(Map<String, Object> m) {
    // For example if CSV column name is "Purchase Order".
    // You create database table with the column name "purchaseOrder"
    // You will use the following code to map.
    //Map<String, Object> newMap = new LinkedHashMap<String, Object>();
    //newMap.put("purchaseOrderNumber", m.get("Purchase Order Number"));
    
    // newMap.put("_uniqueness_condition_", "purchaseOrderNumber=' + m.get("Purchase Order Number") + "'");
    
    // return newMap;
    return m;
  }

}
