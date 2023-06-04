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
    SearchLinkEvent searchLink = new SearchLinkEvent();

    // public static List<String> trieudai = new ArrayList<>();
    // public static List<String> nhanvat = new ArrayList<>();

    public String contentInfoBox(String url, String element) throws IOException {
        String returnString = "";
        try {
            Document doc = Jsoup.connect(url).get();
            Element content = doc.getElementsByClass("infobox vevent").first();
            Element contentSpecial = content.getElementsMatchingOwnText(element).first();

            if (contentSpecial != null && contentSpecial.nextElementSibling() != null) {
                returnString = returnString + contentSpecial.nextElementSibling().text();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;

    }

    public String contentInfoBox(String url, String element, int type) throws IOException {
        List<String> listString = new ArrayList<>();
        String returnString = "";
        try {
            Document doc = Jsoup.connect(url).get();
            Element content = doc.getElementsByClass("infobox vevent").first();
            Element contentSpecial = content.select("tr:has(th:containsOwn(" + element + "))").first();
            if (contentSpecial != null && contentSpecial.nextElementSibling() != null) {
                Elements contentArray = contentSpecial.nextElementSibling().select("a[href]");
                for (Element i : contentArray)
                    listString.add(i.text());
                returnString = String.join(", ", listString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;

    }

    public String contentInfobox(String url, String element, int type1, int type2) throws IOException {
        String returnString = "";
        try {
            Document doc = Jsoup.connect(url).get();
            Element content = doc.getElementsByClass("infobox vevent").first();
            Element contentSpecial = content.select("tr:has(th:containsOwn(" + element + "))").first();
            if (contentSpecial != null && contentSpecial.nextElementSibling() != null) {
                Element contentArray = contentSpecial.nextElementSibling();
                Element first = contentArray.select("td").first();
                Element second = contentArray.select("td").last();
                returnString = "Bên 1 " + first.text() + " " + "Bên 2 " + second.text();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public String contentInfobox(String url) throws IOException {
        String returnString = "";
        try {
            Document doc = Jsoup.connect(url).get();
            Element div = doc.select("div#mw-normal-catlinks").first();
            if (div != null) {
                Element elementul = div.select("ul").first();
                if (elementul != null) {
                    Elements elementli = elementul.select("li");
                    List<String> theloai = new ArrayList<>();
                    for (Element e : elementli)
                        theloai.add(e.text());
                    returnString = String.join(", ", theloai);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public void infobox(String url, Map<String, String> object) {
        try {
            // instance = new HashMap<String, String>(10000);
            String result = contentInfoBox(url, "Kết quả");
            String destination = contentInfoBox(url, "Địa điểm");
            String tdLienQuan = contentInfoBox(url, "Tham chiến", 0);
            String nvLienQuan = contentInfoBox(url, "Chỉ huy", 0);
            String lucluong = contentInfobox(url, "Lực lượng", 0, 0);
            String thuongvong = contentInfobox(url, "Thương vong", 0, 0);
            String theloai = contentInfobox(url);

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
                        if (SearchLinkEvent.isEvent(linkChildUrl) && !listUrl.contains(linkChildUrl)) {
                            listUrl.add(linkChildUrl);
                            infobox(linkChildUrl, entry);
                        }
                    }
                    data.add(entry);
                    size++;
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
                    if (SearchLinkEvent.isEvent(linkChildUrl) && !listUrl.contains(linkChildUrl)) {
                        listUrl.add(linkChildUrl);
                        infobox(linkChildUrl, entry);
                    } else {
                        entry.put("Thực thể liên quan", j.text());
                        entry.put("Thể loại", contentInfobox(linkChildUrl));
                    }
                }
                data.add(entry);
                size++;
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

    public static void main(String[] args) throws IOException {
        ExtractData extractData = new ExtractData();
        extractData.writeData(
                "https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam");
        System.out.println(size);
    }
}
