package Database;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.parser.Element;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Crawl.ExtractData;

public class Database implements IDatabase<JSONObject> {
    ExtractData extractData = new ExtractData();
    JSONArray jsonArray = new JSONArray();
    List<JSONObject> listObject = new ArrayList<>();

    public Database(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        store(this.listObject);
    }

    /* Store an object into database */
    public void store(List<JSONObject> listObject) {
        for (Object i : jsonArray) {
            listObject.add((JSONObject) i);
        }
        try (
            FileWriter fileWriter = new FileWriter("E:/OOP/javaProject/Crawl/extractData.json")) {
            String modifiedJsonString = extractData.unescapeUnicode(jsonArray.toString());
            fileWriter.write(modifiedJsonString);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Load a list of objects with given index range[startIndex, endIndex)
     * - startIndex is inclusive
     * - endIndex is exclusive
     */
    public List<JSONObject> load(int startIndex, int endIndex) {
        List<JSONObject> cloneList = new ArrayList<>(this.listObject.subList(startIndex, endIndex));
        return cloneList;
    };

    /* Load and return all objects in the database */
    public List<JSONObject> load() {
        return this.listObject;
    }

    /* return the number of objects in the database */
    public int size() {
        return this.jsonArray.size();
    }

    /* close the database: cleaning environment if neccessary */
    public void close() {
    }
}
