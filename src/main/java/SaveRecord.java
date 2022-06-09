import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.TrilloFunction;
import com.collager.trillo.util.DSApi;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

/*
 This is an example of DSApi.saveMany()
 It creates many entities and saves in the database.
 Normall, the source of entities will be the body of this funcitons API call,
 or loaded from a file (JSON, CSV, XML). In this example, we create dummy entities.

 We are using Product class.
 */
public class SaveRecord implements Loggable, TrilloFunction {

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
        return DSApi.saveMany("mydb", entities);
    }

    /*
      In an actual application this may be load from CSV.
    */
    private List<Object> makeDummyList()

    {
        int numberOfEntities = 5;
        List<Object> entities = new ArrayList<Object>(numberOfEntities);
        Map<String, Object> oneEntity;
        for (int i = 0; i < numberOfEntities; i++) {
            oneEntity = new HashMap<String, Object>();
            oneEntity.put("test_id", 1 + i);
            oneEntity.put("first_name", "Suleman" + i);
            oneEntity.put("last_name", "Shah" + i);
            oneEntity.put("age", "26" + i);
            oneEntity.put("nic", "654321" + i);
            entities.add(oneEntity);
        }
        log().info("Data Added Successfully");
        return entities;
    }
}

