import java.util.ArrayList;
import java.util.List;
import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.DSApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.TrilloFunction;

public class StoredProcedureWithParemterDemo implements Loggable, TrilloFunction {
  /*
  This function assumes that the following stored procedure is created
  in the database.
  
  DELIMITER //

  CREATE PROCEDURE UpdateAllUsersWithParameters(IN dept VARCHAR(64))
  BEGIN
    update user_tbl set deptName=dept;
  END //

  DELIMITER ;

  The function demonstrates how to invoke the procdure using SDK.
  
  */

  /*
    This is the entry point.
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

  private Object _handle(ScriptParameter scriptParameter) {
    List<Object> params = new ArrayList<Object>();
    params.add(1);
    params.add(2);
    params.add(100);
    return DSApi.executePrepareStatement("shared.postgres",
            "CALL transfer(?,?,?)", params , null);
  }

}