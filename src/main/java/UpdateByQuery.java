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
public class UpdateByQuery implements Loggable, TrilloFunction {

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
        Map<String, Object> oneEntity;
        oneEntity = new HashMap<String, Object>();
        oneEntity.put("maker","AsusSTRIX");
        oneEntity.put("cpu_name", "Ryzen 3600x");
        oneEntity.put("gpu_name", "RX 6900XT");
        oneEntity.put("ram_info", "Corsair vengence 32gb RGB");
        oneEntity.put("os_name", "Kali linux");
        String query = "UPDATE Customers\n" +
                "SET gpu_name=\'RTX 3090\'\n" +
                "WHERE cpu_name=\'Ryzen 3700x\';";

        return DSApi.updateByQuery("ComputerInfo", "",oneEntity,"updated using query");


    }


}


