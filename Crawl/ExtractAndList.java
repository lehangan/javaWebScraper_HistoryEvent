package Crawl;

// import org.jsoup.*;
import java.io.IOException;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Database.Database;

public class ExtractAndList extends ExtractData {
    public static int numberUrlUsed = 0;
    public static List<String> listUrlNew = new ArrayList<>();

    public void writeListUrl(List<String> listUrl, String filepathString) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filepathString)) {
            for (String link : listUrl) {
                fileWriter.write(link + "\n");
                numberUrlUsed++;
            }
            System.out.println(numberUrlUsed + " Links have been stored in the file");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createListUrl(String url) throws IOException {
        writeData(url);
    }

    public void writeListUrlNew(String url) throws IOException {
        try {
            Document doc = Jsoup.connect(url).get();
            Element div = doc.select("div#mw-content-text").first();
            // System.out.println(div);
            Elements href = div.select("a");
            for (Element i : href) {
                String linkUrl = i.attr("abs:href");
                LinkEvent linkEvent = new LinkEvent(linkUrl);
                if (linkEvent.isEvent() && !listUrl.contains(linkUrl) && !listUrlNew.contains(linkUrl)) {
                    listUrlNew.add(linkUrl);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDataNew(String url) throws IOException {
        LinkEvent linkEvent = new LinkEvent(url);
        Map<String, Object> entry = new HashMap<>();
        Document doc = Jsoup.connect(url).get();
        Element div = doc.select("div#mw-content-text").first();
        Element infobox = div.select(".infobox.vevent").first();
        Element trElement = infobox.select("tr").first();
        String sukien = "";
        String thoigian = "";
        if (trElement != null) {
            Element thElement = trElement.select("th.summary").first();
            if (thElement != null) {
                sukien = thElement.text();
                thoigian = linkEvent.contentInfoBox("Thời gian");
            }
        }
        // entry.put("Link" , url);
        entry.put("Sự kiện", sukien);
        entry.put("id",sukien);
        entry.put("Thời gian", thoigian);
        infobox(linkEvent, entry);
        JSONObject jsonObject = new JSONObject(entry);
        jsonArray.add(jsonObject);
        size++;
        System.out.println(size);
    }

    public void wrtieDataAll() throws IOException {
        for (String i : listUrlNew) {
            writeDataNew(i);
        }
    }

    public static void main(String[] args) throws IOException {
        ExtractAndList searchMoreLink = new ExtractAndList();
        searchMoreLink.createListUrl(
                "https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam");
        searchMoreLink.writeListUrl(listUrl, "E:/OOP/javaProject/Crawl/eventLink.txt");
        searchMoreLink.writeListUrlNew(
                "https://vi.wikipedia.org/wiki/Danh_s%C3%A1ch_tr%E1%BA%ADn_%C4%91%C3%A1nh_trong_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam#Nh%C3%A0_Ti%E1%BB%81n_L%C3%BD_(544_-_603)");
        searchMoreLink.writeListUrlNew(
                "https://vi.wikipedia.org/wiki/Th%E1%BB%83_lo%E1%BA%A1i:Chi%E1%BA%BFn_tranh_Vi%E1%BB%87t%E2%80%93Chi%C3%AAm");
        searchMoreLink.writeListUrlNew(
                "https://vi.wikipedia.org/wiki/Th%E1%BB%83_lo%E1%BA%A1i:Chi%E1%BA%BFn_tranh_li%C3%AAn_quan_t%E1%BB%9Bi_Vi%E1%BB%87t_Nam");
        searchMoreLink.writeListUrlNew(
                "https://vi.wikipedia.org/wiki/Th%E1%BB%83_lo%E1%BA%A1i:Tr%E1%BA%ADn_%C4%91%C3%A1nh_v%C3%A0_chi%E1%BA%BFn_d%E1%BB%8Bch_trong_Chi%E1%BA%BFn_tranh_%C4%90%C3%B4ng_D%C6%B0%C6%A1ng");
        searchMoreLink.writeListUrlNew(
                "https://vi.wikipedia.org/wiki/Th%E1%BB%83_lo%E1%BA%A1i:Phong_tr%C3%A0o_%C4%91%E1%BB%99c_l%E1%BA%ADp_Vi%E1%BB%87t_Nam");
        searchMoreLink.writeListUrlNew(
                "https://vi.wikipedia.org/wiki/Th%E1%BB%83_lo%E1%BA%A1i:Chi%E1%BA%BFn_tranh_%C4%90%C3%B4ng_D%C6%B0%C6%A1ng_th%E1%BB%A9_ba");
        searchMoreLink.writeListUrlNew(
                "https://vi.wikipedia.org/wiki/Th%E1%BB%83_lo%E1%BA%A1i:L%E1%BB%8Bch_s%E1%BB%AD_mi%E1%BB%81n_Nam_Vi%E1%BB%87t_Nam");
        searchMoreLink.writeListUrlNew(
                "https://vi.wikipedia.org/wiki/Th%E1%BB%83_lo%E1%BA%A1i:Chi%E1%BA%BFn_tranh_%C4%90%C3%B4ng_D%C6%B0%C6%A1ng_th%E1%BB%A9_ba");
        listUrlNew.remove("https://vi.wikipedia.org/wiki/1947");
        listUrlNew.remove("https://vi.wikipedia.org/wiki/1950");
        listUrlNew.remove("https://vi.wikipedia.org/wiki/1954");

        searchMoreLink.writeListUrl(listUrlNew,
                "E:/OOP/javaProject/Crawl/eventLinkNew.txt");
        searchMoreLink.wrtieDataAll();
        for( int i = 0 ; i<=6 ; i++)
            jsonArray.remove(i);
        Database database = new Database(jsonArray);
        //searchMoreLink.writeDataToFile();

        System.out.println(size);
    }
}
