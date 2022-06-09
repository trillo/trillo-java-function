import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.TrilloFunction;
import com.collager.trillo.util.DSApi;
import java.util.List;
import java.util.ArrayList;

/*
 This is an example of DSApi.saveMany()
 It creates many entities and saves in the database.
 Normall, the source of entities will be the body of this funcitons API call,
 or loaded from a file (JSON, CSV, XML). In this example, we create dummy entities.

 We are using Product class.
 */
public class UpdateManyRecord implements Loggable, TrilloFunction {

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

    private Object _handle(ScriptParameter params)
    {
        List<String> ids = new ArrayList<String>();
        ids.add("4");
        ids.add("6");

        log().info("Ids updated" + ids);
        return DSApi.updateMany("mydb", ids, "first_name","Ahmed");

    }

}
