import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.TrilloFunction;
import com.collager.trillo.util.BaseApi;
import com.collager.trillo.util.DSApi;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/*
  This is an example of DSApi.queryMany()
 */
public class QueryMany2 implements Loggable, TrilloFunction {

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

    private Object _handle(ScriptParameter params) {
        Object result = DSApi.queryMany("auth.vault.User", "");
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
