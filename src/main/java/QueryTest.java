import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.TrilloFunction;
import com.collager.trillo.util.DSApi;
import com.collager.trillo.util.LogApi;
import java.util.Map;
import java.util.HashMap;

public class QueryTest implements Loggable, TrilloFunction
{
    public Object handle(ScriptParameter scriptParameter)
    {
        try {
            return _handle(scriptParameter);
        } catch (Exception e) {
            log().error("Failed", e);
            return Result.getFailedResult(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private Object _handle(ScriptParameter scriptParameter)
    {
        Map<String, Object> functionParameters = (Map<String, Object>)scriptParameter.getV();
        Map<String, Object> params = null;
        params = new HashMap<String, Object>();
        params.put("start", 1);
        params.put("size", 3);
        Object topStores = DSApi.executeNamedQuery("shared.common","Q2", params);


        Map<String,Object> resultant = new HashMap<>();
        resultant.put("topStores",topStores);
//        resultant.put("Item Description",ItemDescription);
        return resultant;
    }

}