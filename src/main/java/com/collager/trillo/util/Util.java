package com.collager.trillo.util;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.random;
import static java.lang.Math.round;
import static org.apache.commons.lang3.StringUtils.leftPad;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.json.JSONObject;
import org.json.XML;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Util {
  
  public static final String SNAPSHOT_CLASS = "Snapshot";
  public static final String SHARED_APP_NAME = "shared";
  public static final String COMMON_DS_NAME = "common";
  public static final String KEY_VALUE_CLASS_NAME = "KeyValue";
  public static final String AUDIT_LOG_CLASS_NAME = "AuditLog";
  public static final String AUTH_APP_NAME = "auth";
  public static final String AUTH_DS_NAME = "vault";
  public static final String APP_TO_USER_CLASS = "AppUser";
  public static final String UM_APP_NAME = "UM";
  
  public static final String GCP_SSERVICE_NAME = "gcp";

  public static String asJSONPrettyString(Object obj) {
    try {
      ObjectMapper mapper = new ObjectMapper(new Factory());
      if (obj instanceof String) {
        obj = mapper.readValue((String) obj, Object.class);
      }
      mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
      mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
      return mapper.writeValueAsString(obj);
    } catch (Exception exc) {
      exc.printStackTrace();
      throw new RuntimeException("Failed to stringify object.\n" +
          exc.getLocalizedMessage());
    }
  }

  public static String asJSONString(Object obj) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      return mapper.writeValueAsString(obj);
    } catch (Exception exc) {
      throw new RuntimeException("Failed to stringify object.\n" +
          exc.getLocalizedMessage());
    }
  }

  public static <T> T fromJSONString(String json, Class<T> cls) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      T obj = mapper.readValue(json, cls);
      return obj;
    } catch (Exception exc) {
      throw new RuntimeException("Failed to make object from string.\n" +
          exc.getLocalizedMessage());
    }
  }

  public static <T> T fromJSONString(String json, TypeReference<T> type) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      T obj = mapper.readValue(json, type);
      return obj;
    } catch (Exception exc) {
      throw new RuntimeException("Failed to make object from string.\n" +
          exc.getLocalizedMessage());
    }
  }

  public static String toPrettyJSONString(String json) {
    if (json == null || json.trim().length() == 0) {
      return json;
    }
    try {
      Object obj = fromJSONString(json, Object.class);
      ObjectMapper mapper = new ObjectMapper(new Factory());
      mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
      return mapper.writeValueAsString(obj);
    } catch (Exception exc) {
      throw new RuntimeException("Failed to stringify object.", exc);
    }
  }

  public static <T> T fromJSONSFile(File jsonFile, Class<T> cls) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      T obj = mapper.readValue(jsonFile, cls);
      return obj;
    } catch (Exception exc) {
      throw new RuntimeException("Failed to load JSON file.", exc);
    }
  }

  /* Example of type references:
   * new TypeReference<HashMap<String, Object>>() {}
   *  TypeReference<List<LinkedHashMap<String, Object>>>(){}
   */
  public static <T> T fromJSONSFile(File jsonFile, TypeReference<T> type) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      T obj = mapper.readValue(jsonFile, type);
      return obj;
    } catch (Exception exc) {
      throw new RuntimeException("Failed to load JSON file.", exc);
    }
  }
  
  public static Map<String, Object> fromJSONStringAsMap(String json) {
    return fromJSONString(json,  new TypeReference<HashMap<String, Object>>() {});
  }
  
  public static List<Map<String, Object>> fromJSONStringAsListOfMap(String json) {
    return fromJSONString(json,  new TypeReference<ArrayList<Map<String, Object>>>() {});
  }

  public static void saveAsJSONString(File file, Object obj) {
    String s = asJSONPrettyString(obj);
    FileUtil.writeFile(file, s);
  }

  public static Properties loadProperyFile(String propertyFileName) {
    Properties prop = new Properties();
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    InputStream in = loader.getResourceAsStream(propertyFileName);
    try {
      prop.load(in);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        in.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return prop;
  }

  private static class PrettyPrinter extends DefaultPrettyPrinter {
    private static final long serialVersionUID = 1L;
    public static final PrettyPrinter instance = new PrettyPrinter();

    public PrettyPrinter() {
      _arrayIndenter = DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;
    }
  }

  private static class Factory extends JsonFactory {
    private static final long serialVersionUID = 1L;

    @Override
    protected JsonGenerator _createGenerator(Writer out, IOContext ctxt) throws IOException {
      return super._createGenerator(out, ctxt).setPrettyPrinter(PrettyPrinter.instance);
    }
  }

  public static String uidToClassName(String uid) {
    if (uid == null) {
      return null;
    }
    int idx = uid.indexOf(".");
    if (idx > 0) {
      return uid.substring(0, idx);
    }
    return null;
  }
  
  public static long uidToId(String uid) {
    if (uid == null) {
      return -1;
    }
    int idx = uid.lastIndexOf(".");
    if (idx > 0) {
      return Long.parseLong(uid.substring(idx+1));
    }
    return -1;
  }

  public static String uidToIdStr(String uid) {
    if (uid == null) {
      return null;
    }
    int idx = uid.lastIndexOf(".");
    if (idx > 0) {
      return uid.substring(idx+1);
    }
    return null;
  }

  public static void validateJson(String json) throws RuntimeException {
      ObjectMapper mapper = new ObjectMapper();
      try {
        mapper.readTree(json);
      } catch (Exception e) {
        String str = e.getLocalizedMessage();
        int idx1 = str.indexOf("[Source:");
        int idx2 = str.lastIndexOf("; line:");
        if (idx1 >=0 && idx2 > 0 && idx2 > idx1) {
          str = str.substring(0, idx1) + str.substring(idx2+1);
        }
        throw new RuntimeException(str);
      }
  }

  

  public static Map<String, ?> toMap(Object obj) {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.convertValue(obj, new TypeReference<Map<String, Object>>() {});
  }

  public static boolean containsWhiteSpace(final String testCode){
    if(testCode != null){
        for(int i = 0; i < testCode.length(); i++){
            if(Character.isWhitespace(testCode.charAt(i))){
                return true;
            }
        }
    }
    return false;
  }

  public static String getKeyForKVStore(String org, String app, String serviceName) {
    return "/" + org + "/" + app + "/service/" + serviceName + "/url";
  }

  public static String genRandomAlphaNum(int length) {
    StringBuilder sb = new StringBuilder();
    for (int i = length; i > 0; i -= 12) {
      int n = min(12, abs(i));
      sb.append(leftPad(Long.toString(round(random() * pow(36, n)), 36), n, '0'));
    }
    return sb.toString();
  }

  // Converts a generic map to an object of given class.
  // If conversion fails then it throws RuntimeException by wrapping the original exception.
  public static <T> T fromMap(Map<String, Object>inputObjectAsMap, Class<T> toValueType) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      T obj = mapper.convertValue(inputObjectAsMap, toValueType);
      return obj;
    } catch (Exception exc) {
      throw new RuntimeException(
          "Failed to make object from input map.\n" + exc.getLocalizedMessage(), exc);
    }
  }

  // Converts an object to a generic map.
  // If conversion fails then it throws RuntimeException by wrapping the original exception.
  public static Map<String, Object> covertObjectToMap(Object obj) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      Map<String, Object> map =
          mapper.convertValue(obj, new TypeReference<HashMap<String, Object>>() {
          });
      return map;
    } catch (Exception exc) {
      throw new RuntimeException(
          "Failed to make object from input map.\n" + exc.getLocalizedMessage(), exc);
    }
  }
 
  public static String asUid(String className, Object id) {
    return className + "." + id;
  }

  public static String asUid(String className) {
    return className + ".";
  }

  public static String getCurrentLanguage() {
    return "en";
  }
  
  public static String removeLeadingChars(String s, char c) {
    StringBuilder sb = new StringBuilder(s);
    while (sb.length() > 0 && sb.charAt(0) == c) {
        sb.deleteCharAt(0);
    }
    return sb.toString();
  }
  
  public static String removeLastCharIfMatches(String s, char c) {
    StringBuilder sb = new StringBuilder(s);
    while (sb.length() > 0 && sb.charAt(sb.length() - 1) == c) {
        sb.deleteCharAt(sb.length() - 1);
    }
    return sb.toString();
  }
  
  public static Map<String, Object> xmlToMap(String xml) {
    JSONObject xmlJSONObj = XML.toJSONObject(xml);
    String jsonPrettyPrintString = xmlJSONObj.toString();
    return Util.fromJSONString(jsonPrettyPrintString, new TypeReference<Map<String, Object>>() {});
  }
  
  /* public static Map<String, Object> xmlToMap(String xml) {
    XmlMapper xmlMapper = new XmlMapper();
    try {
      return xmlMapper.readValue(xml, new TypeReference<Map<String, Object>>() {});
    } catch (Exception e) {
      throw new RuntimeException(e.toString(), e);
    }
  } */
  
  public static double roundDouble(double value, int places) {
    if (places < 0)
      throw new IllegalArgumentException();

    BigDecimal bd = BigDecimal.valueOf(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }
  
  public static String urlEncode(String str) {
    try {
      return URLEncoder.encode(str, "UTF-8");
    } catch (Exception exc) {
      return str;
    }
  }

  public static String urlDecode(String str) {
    try {
      return URLDecoder.decode(str, "UTF-8");
    } catch (Exception exc) {
      return str;
    }
  }
}
