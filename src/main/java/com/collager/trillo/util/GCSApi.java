/*
 * Copyright (c)  2020 Trillo Inc.
 * All Rights Reserved
 * THIS IS UNPUBLISHED PROPRIETARY CODE OF TRILLO INC.
 * The copyright notice above does not evidence any actual or
 * intended publication of such source code.
 *
 */

package com.collager.trillo.util;

public class GCSApi extends BaseApi {
  
  public static String getTrilloGCSBucket() {
    return remoteCallAsString("GCSApi", "getTrilloGCSBucket");
  }
  
  public static String getTrilloGCSBucketURI() {
    return remoteCallAsString("GCSApi", "getTrilloGCSBucketUR");
  }

  public static String getGCSFileURI(String pathToFile) {
    return remoteCallAsString("GCSApi", "getGCSFileUR", pathToFile);
  }
  
  public static String getGCSFileURI(String path, String fileName) {
    return remoteCallAsString("GCSApi", "getGCSFileURI", path, fileName);
  }

  
  public static String getLocalPath (String bucketPath) {
    return remoteCallAsString("GCSApi", "getLocalPath", bucketPath);
  }

  public static String getGCSPath(String appDataPath) {
    return remoteCallAsString("GCSApi", "getGCSPath", appDataPath);
  }

  public static String getGCSTempPath() {
    return remoteCallAsString("GCSApi", "getGCSTempPath");
  }
  
  public static String getDefaultRootFolder() {
    return remoteCallAsString("GCSApi", "getDefaultRootFolder");
  }
}


