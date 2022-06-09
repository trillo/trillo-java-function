import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.collager.trillo.model.CSVWriter;
import com.collager.trillo.model.DataIterator;
import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.BaseApi;
import com.collager.trillo.util.DSApi;
import com.collager.trillo.util.FileUtil;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.StorageApi;
import com.collager.trillo.util.TrilloFunction;

/*
  - Everything is private except handle() method
  - return any object and it will become the response payload
  - Make sure that returned object is serializable.
 */
public class DataIteratorSample implements Loggable, TrilloFunction {

  /*
    This is the only entry point into this Trillo function
    - params : complete context passed by the application
               including request body map (V).
   */
  public Object handle(ScriptParameter params) {

    // do the implementation inside _handle()
    try {
      return _handle(params);
    } catch (Exception e) {
      log().error("Failed", e);
      return Result.getFailedResult(e.getMessage());
    }
  }

  @SuppressWarnings("unchecked")
  private Object _handle(ScriptParameter params) {
    DataIterator dataIterator = DSApi.getDataIterator(1, 5, "select * from cloud_vault.user_tbl");
    Object result;
    int n = 0;
    
    // get a uniue temporary file name inside /tmp directory
    String fileName = FileUtil.getUniqueFileName("/tmp", "Test", "csv");
    // list of CSV headers (columns)
    List<String> columnNames = new ArrayList<String>();
    columnNames.add("userId");
    columnNames.add("firstName");
    columnNames.add("lastName");
    
    CSVWriter csvWriter = new CSVWriter("/tmp/" + fileName, columnNames);
    while (dataIterator.hasNext()) {
      result = dataIterator.getNext();
      System.out.println("Row : " + n);
      System.out.println(BaseApi.asJSONPrettyString(result));
      System.out.println("--------------------------------");
      csvWriter.addRow((Map<String, Object>) result);
      n++;
    }
    csvWriter.close();
    Result r = StorageApi.copyFileToBucket("/tmp/" + fileName, "/groups/tmp/" + fileName);
    if (r.isFailed()) {
      log().error("Failed to copy file to bucket, error: " + r.getMessage());
    }
    new File("/tmp/" + fileName).delete();
    return Result.getSuccessResult("Number of rows: " + n);
  }

}

