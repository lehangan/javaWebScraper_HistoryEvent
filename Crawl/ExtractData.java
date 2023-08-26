package Crawl;

// import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.jsoup.Jsoup;
import java.io.IOException;

import org.json.simple.*;
import java.io.FileWriter;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractData {
    //List<Map<String, String >> data = new ArrayList<>();

    public static JSONArray jsonArray = new JSONArray();
    public static List<String> listUrl = new ArrayList<>();

    public static int size = 0;

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

    public void infobox(LinkEvent linkEvent, Map<String, Object> object) {
        try {
            // instance = new HashMap<String, String>(10000);
            String result = linkEvent.contentInfoBox("Kết quả");
            String destination = linkEvent.contentInfoBox("Địa điểm");
            List<String> tdLienQuan = linkEvent.contentInfoBoxList("Tham chiến", 0);
            List<String> nvLienQuan = linkEvent.contentInfoBoxList("Chỉ huy", 0);
            //String lucluong = linkEvent.contentInfobox("Lực lượng", 0, 0);
            //String thuongvong = linkEvent.contentInfobox("Thương vong", 0, 0);
            List<String> theloai = linkEvent.contentInfoboxList();
            String mota = linkEvent.description();
            object.put("Result", result);
            object.put("Location", destination);
            object.put("Dynasty related", tdLienQuan);
            object.put("Character related", nvLienQuan);
            //object.put("Lực lượng", lucluong);
            //object.put("Thương vong", thuongvong);
            object.put("Related to", theloai);
            object.put("Description" , mota);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeData(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element div = doc.selectFirst("div.mw-content-container");
        Elements links = div.select("p:has(b)");

        for (Element link : links) {
            Elements time = link.select("b");
            String thoigian = time.text();
            String thoidai = "";
            Element previousH3Element = link.previousElementSibling();
            while (previousH3Element != null && !previousH3Element.tagName().equalsIgnoreCase("h3")) {
                previousH3Element = previousH3Element.previousElementSibling();
            }
            if (previousH3Element != null) {
                Element spanE = previousH3Element.select("span.mw-headline").first();
                thoidai = spanE.text();
            }

            Element nextLink = link.nextElementSibling();
            if (nextLink != null && nextLink.tagName().equalsIgnoreCase("dl")) {
                Elements elements_dd = nextLink.select("dd");
                for (Element i : elements_dd) {
                    Map<String, Object> entry = new HashMap<>();    
                    String thoigiancuthe = i.select("b").text() + " " + thoigian;
                    i.child(0).remove();
                    //entry.put("Sự kiện", i.text());
                    entry.put("id" , i.text());
                    entry.put("Time", thoigiancuthe);
                    entry.put("Dynasty related", thoidai);
                    Elements linkChild = i.select("a");
                    for (Element j : linkChild) {
                        String linkChildUrl = j.attr("abs:href");
                        LinkEvent linkEvent = new LinkEvent(linkChildUrl);
                        if (LinkEvent.isEvent() && !listUrl.contains(linkChildUrl)) {
                            listUrl.add(linkChildUrl);
                            infobox(linkEvent, entry);
                            //entry.put("Link" , linkChildUrl.replace("\"", ""));
                        }
                    }
                    size++;
                    JSONObject jsonObject = new JSONObject(entry);
                    jsonArray.add(jsonObject);
                    System.out.println(size);
                }
            } else {
                //Map<String, String> entry = new HashMap<>();
                Map<String, Object> entry = new HashMap<>();
                link.child(0).remove();
                String sukien = link.text();
                //entry.put("Sự kiện", sukien);
                entry.put("id" , sukien);
                entry.put("Time", thoigian);
                entry.put("Dynasty related", thoidai);
                Elements linkChild = link.select("a");
                for (Element j : linkChild) {
                    String linkChildUrl = j.attr("abs:href");
                    LinkEvent linkEvent = new LinkEvent(linkChildUrl);
                    if (LinkEvent.isEvent() && !listUrl.contains(linkChildUrl)) {
                        listUrl.add(linkChildUrl);
                        infobox(linkEvent, entry);
                        //entry.put("Link" , linkChildUrl.replace("\"", ""));
                    } else {
                        entry.put("Related to", linkEvent.contentInfoboxList());
                    }
                }
                size++;
                JSONObject jsonObject = new JSONObject(entry);
                jsonArray.add(jsonObject);
                System.out.println(size);
            }

        }
        //writeDataToFile();

    }

    public void writeDataToFile() throws IOException{
        try (
                FileWriter fileWriter = new FileWriter("E:/OOP/javaProject/Crawl/extractData.json")) {
            String modifiedJsonString = unescapeUnicode(jsonArray.toString());
            fileWriter.write(modifiedJsonString);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
