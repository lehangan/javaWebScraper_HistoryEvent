
// import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import java.io.IOException;

import java.util.HashMap;

import org.json.simple.*;
import java.io.FileWriter;

// import javax.json.Json;
// import javax.json.JsonArray;
// import javax.json.JsonObject;
// import javax.json.JsonWriter;
// import java.io.FileWriter;
import java.io.IOException;
//import java.net.URL;

public class test {
    
    public static boolean checkOneLevelUrl(String url) {
        try {
            // Fetch the HTML content from the URL
            Document doc = Jsoup.connect(url).get();

            // Check if the desired class exists in the parsed document
            boolean hasInfoboxVevent = doc.select(".infobox.vevent").size() > 0;

            // Print the result
            if (hasInfoboxVevent) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

    public static void infobox(String url) throws IOException{
        try { 
            Document docChild = Jsoup.connect(url).get();
        Elements content = docChild.getElementsByClass("infobox vevent");
        System.out.println(content.text());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        JSONArray jsonArray = new JSONArray();

        Document doc = Jsoup
                .connect(
                        "https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam")
                .get();

        String title = doc.title();
        System.out.println("title: " + title);
        doc.select("dl").tagName("p");
        Elements links = doc.select("p:has(b)");
        System.out.println(links.size());

        for (Element link : links) {
            JSONObject jsonObject = new JSONObject();

            Elements time = link.select("b");
            Elements href = link.select("a[href^=/wiki/]");

            for (Element linkChild : href) {
                String hrefLink = linkChild.attr("abs:href");
                if (checkOneLevelUrl(hrefLink)) {
                    Document docEvent = Jsoup.connect(hrefLink).get();

                    jsonObject.put("link", hrefLink);
                    jsonObject.put("title link", linkChild.text());
                }
            }

            String thoigian = time.text();
    
            jsonObject.put("time", thoigian);

            String event = link.text();
            jsonObject.put("event", event);

            Elements connectCharElements = link.select("a[href]");

            String connect = connectCharElements.text();
            jsonObject.put("reference", connect);

            jsonArray.add(jsonObject);
        }
        try (
                FileWriter fileWriter = new FileWriter("E:/OOP/javaProject/test.json")) {
            String modifiedJsonString = unescapeUnicode(jsonArray.toString());
            fileWriter.write(modifiedJsonString);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String unescapeUnicode(String input) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        int length = input.length();

        while (i < length) {
            char currentChar = input.charAt(i);
            if (currentChar == '\\' && i < length - 1 && input.charAt(i + 1) == 'u') {
                // Unescape "\u2013" to "-"
                builder.append("-");
                i += 6; // Skip the "\u2013" characters
            } else {
                builder.append(currentChar);
                i++;
            }
        }

        return builder.toString();
    }
}
