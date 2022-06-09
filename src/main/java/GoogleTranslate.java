import java.util.Map;
import com.collager.trillo.util.Api;
import com.collager.trillo.util.GCPRestApi;
import com.collager.trillo.util.ServerlessFunction;

/* The following is a recipe for Google Translate service.
   Each method can be invoked via client tool kit or remote API.
*/
public class GoogleTranslate extends ServerlessFunction {
  
  @Api(httpMethod="post")
  public Object translateText(Map<String, Object> parameters) {
    String url = "https://translate.googleapis.com/v3/projects/{projectId}:translateText";
    return GCPRestApi.post(url, parameters);
  }
}