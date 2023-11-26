package sjcc;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class JSONFile {


  public static JSONArray readArray(String resourcePath) {

    JSONArray data = null;

    try (InputStream inputStream = JSONFile.class.getResourceAsStream(resourcePath)) {
      if (inputStream == null) {
        throw new IOException("Resource not found: " + resourcePath);
      }

      String text = new Scanner(inputStream, StandardCharsets.UTF_8.name()).useDelimiter("\\A").next();
      data = new JSONArray(text);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return data;
  }
}
