package com.collager.trillo.util;

import java.util.List;
import com.collager.trillo.pojo.Result;

public class CommandApi extends BaseApi {
  public static Result runOSCmd(List<String> argList) {
    return remoteCallAsResult("CommandApi", "runOSCmd", argList);
  }
}
