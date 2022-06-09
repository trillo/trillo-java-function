import com.collager.trillo.pojo.Result;
import com.collager.trillo.pojo.ScriptParameter;
import com.collager.trillo.util.EmailApi;
import com.collager.trillo.util.Loggable;
import com.collager.trillo.util.TrilloFunction;

/*
  - Everything is private except handle() method
  - return any object and it will become the response payload
  - Make sure that returned object is serializable.
 */
public class SendEmail implements Loggable, TrilloFunction {

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

        String mailTo= "afsatti@gmail.com";
        String body = "This email is send through EmailApi using the local setup , BR : Ahmed Fayyaz";
        String subject = "Testing EmailAPi";
        return EmailApi.sendEmailMarkDownContent(mailTo,body, subject);


    }


}


