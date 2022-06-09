import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class SaveMany implements Loggable, TrilloFunction {

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
        List<Object> entities = makeDummyList();
        log().info(String.valueOf(entities));
        return DSApi.saveMany("ComputerInfo", entities);
    }
    private List<Object> makeDummyList() {
        int numberOfEntities = 2;
        List<Integer> ids = new ArrayList<Integer>();
        List<Object> entities = new ArrayList<Object>(numberOfEntities);
        Map<String, Object> oneEntity;
        for (int i=0; i<numberOfEntities; i++) {
            oneEntity = new HashMap<String, Object>();
            oneEntity.put("maker","AsusTUF");
            oneEntity.put("cpu_name", "Ryzen 3700x");
            oneEntity.put("manufacturer", "Mfr-" + i);
            oneEntity.put("gpu_name", "RX 6800XT");
            oneEntity.put("ram_info", "Corsair vengence 32gb");
            oneEntity.put("os_name", "Windows 10 pro");
            log().info(String.valueOf(oneEntity));
            entities.add(oneEntity);
        }
        return entities;
    }

}


