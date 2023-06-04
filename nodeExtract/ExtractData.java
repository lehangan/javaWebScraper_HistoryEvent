package nodeExtract;

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
    List<Map<String, String>> data = new ArrayList<>();
    JSONArray jsonArray = new JSONArray();
    public static List<String> listUrl = new ArrayList<>();

    public static int size = 0;
    //LinkEventV2 searchLink = new LinkEventV2();

    // public static List<String> trieudai = new ArrayList<>();
    // public static List<String> nhanvat = new ArrayList<>();


    public void infobox(LinkEvent linkEvent, Map<String, String> object) {
        try {
            // instance = new HashMap<String, String>(10000);
            String result = linkEvent.contentInfoBox("Kết quả");
            String destination = linkEvent.contentInfoBox( "Địa điểm");
            String tdLienQuan = linkEvent.contentInfoBox( "Tham chiến", 0);
            String nvLienQuan = linkEvent.contentInfoBox( "Chỉ huy", 0);
            String lucluong = linkEvent.contentInfobox( "Lực lượng", 0, 0);
            String thuongvong = linkEvent.contentInfobox("Thương vong", 0, 0);
            String theloai = linkEvent.contentInfobox();

            object.put("Kết quả ", result);
            object.put("Địa điểm liên quan", destination);
            object.put("Triều đại liên quan (Tham chiến)", tdLienQuan);
            object.put("Nhân vật liên quan (Chỉ huy)", nvLienQuan);
            object.put("Lực lượng", lucluong);
            object.put("Thương vong", thuongvong);
            object.put("Thể loại", theloai);

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

            Element nextLink = link.nextElementSibling();
            if (nextLink != null && nextLink.tagName().equalsIgnoreCase("dl")) {
                Elements elements_dd = nextLink.select("dd");
                for (Element i : elements_dd) {
                    Map<String, String> entry = new HashMap<>();
                    String thoigiancuthe = i.select("b").text() + " " + thoigian;
                    i.child(0).remove();
                    entry.put("Sự kiện", i.text());
                    entry.put("Thời gian", thoigiancuthe);
                    Elements linkChild = i.select("a");
                    for (Element j : linkChild) {
                        String linkChildUrl = j.attr("abs:href");
                        LinkEvent linkEvent = new LinkEvent(linkChildUrl);
                        if (LinkEvent.isEvent() && !listUrl.contains(linkChildUrl)) {
                            listUrl.add(linkChildUrl);
                            infobox(linkEvent, entry);
                        }
                    }
                    data.add(entry);
                    size++;
                    //System.out.println(size);
                }
            } else {
                Map<String, String> entry = new HashMap<>();
                link.child(0).remove();
                String sukien = link.text();
                entry.put("Sự kiện", sukien);
                entry.put("Thời gian", thoigian);
                Elements linkChild = link.select("a");
                for (Element j : linkChild) {
                    String linkChildUrl = j.attr("abs:href");
                    LinkEvent linkEvent = new LinkEvent(linkChildUrl);
                    if (LinkEvent.isEvent() && !listUrl.contains(linkChildUrl)) {
                        listUrl.add(linkChildUrl);
                        infobox(linkEvent, entry);
                    } else {
                        entry.put("Thực thể liên quan", j.text());
                        entry.put("Thể loại", linkEvent.contentInfobox());
                    }
                }
                data.add(entry);
                size++;
                //System.out.println(size);
            }
        }

        for (Map<String, String> d : data) {
            JSONObject jsonObject1 = new JSONObject(d);
            jsonArray.add(jsonObject1);
        }

        try (
                FileWriter fileWriter = new FileWriter("E:/OOP/javaProject/nodeExtract/extractData.json")) {
            String modifiedJsonString = unescapeUnicode(jsonArray.toString());
            fileWriter.write(modifiedJsonString);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String unescapeUnicode(String input) {
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
