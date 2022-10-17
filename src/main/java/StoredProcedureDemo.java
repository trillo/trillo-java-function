import java.util.List;
import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.DSApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.TrilloFunction;

public class StoredProcedureDemo implements Loggable, TrilloFunction {
  /*
  This function assumes that the following stored procedure is created
  in the database.
  
  DELIMITER //

  CREATE PROCEDURE UpdateAllUsers()
  BEGIN
    update user_tbl set deptName='Dept1';
  END //

  DELIMITER ;

  The function demonstrates how to invoke the procedure using SDK.
  
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
    return DSApi.executePrepareStatement("auth.vault",
      "CALL UpdateAllUsers()", (List<Object>)null);
  }

}
