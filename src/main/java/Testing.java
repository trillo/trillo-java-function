import java.util.List;

import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.BaseApi;
import com.collager.trillo.util.DSApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.TrilloFunction;

/*
  - Everything is private except handle() method
  - return any object and it will become the response payload
  - Make sure that returned object is serializable.
 */
public class Testing implements Loggable, TrilloFunction {

    /*
      This is the only entry point into this Trillo function
      - params : complete context passed by the application
                 including request body map (V).
     */
    public Object handle(ScriptParameter params) {

        // do the implementation inside _handle()
        try {
            log().info("function working");
            return _handle(params);
        } catch (Exception e) {
            log().error("Failed", e);
            return Result.getFailedResult(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private Object _handle(ScriptParameter params) {
        Object result = DSApi.queryMany("auth.vault.User", "");
        //apis.shared.common.ComputerInfo
        if (result instanceof List<?>) {
            List<Object> l = (List<Object>) result;
            log().info("Number of users: " + l.size());
            log().info("Params: " + BaseApi.asJSONPrettyString(l));
            return l;
        } else {
            return result;
        }
    }

}
