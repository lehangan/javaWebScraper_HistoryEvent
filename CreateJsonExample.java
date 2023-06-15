// import com.google.gson.Gson;
// import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CreateJsonExample {
    public static void main(String[] args) {
        // Create a Map to represent the JSON structure
        Map<String, Object> jsonMap = new HashMap<>();
        JSONArray jsonArray = new JSONArray();
        // Add the name and age properties
        jsonMap.put("name", "John");
        jsonMap.put("age", 30);
        Map<String, Object> jsonMap2 = new HashMap<>();
        jsonMap2.put("name", "Johnhn");
        jsonMap2.put("age", 32);
        // Create a List for the cars property
        List<String> carsList = new ArrayList<>();
        carsList.add("Ford");
        carsList.add("BMW");
        carsList.add("Fiat");

        // Add the cars property to the Map
        jsonMap.put("cars", carsList);
        jsonMap2.put("cars" , carsList);
        // Convert the JSON map to a JSON string
        // Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // String json = gson.toJson(jsonMap);

        JSONObject jsonObject1 = new JSONObject(jsonMap);
        JSONObject jsonObject2 = new JSONObject(jsonMap2);
            jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);
        // Write the JSON string to a file
        try (FileWriter writer = new FileWriter("E:/OOP/javaProject/test.json")) {
            writer.write(jsonArray.toString());
            System.out.println("JSON written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
