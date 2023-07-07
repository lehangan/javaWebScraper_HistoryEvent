import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Crawl.LinkEvent;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import java.io.FileWriter;

public class Main {
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

    public static void infobox(String url) throws IOException {
        // Map<String, String> instance = null;
        try {
            // instance = new HashMap<String, String>(10000);
            Document docChild = Jsoup.connect(url).get();
            Element content = docChild.getElementsByClass("infobox vevent").first();

            Element des = content.getElementsMatchingOwnText("Địa điểm").first();
            String destination = des.nextElementSibling().text();

            Element re = content.getElementsMatchingOwnText("Kết quả").first();
            String result = re.nextElementSibling().text();

            Element trElement = content.select("tr:has(th:containsOwn(Tham chiến))").first();
            Element army = trElement.nextElementSibling();
            Elements thamchien = army.select("a[href]");

            List<String> trieudai = new ArrayList<>();
            for (Element i : thamchien)
                trieudai.add(i.text());
            String tdLienQuan = String.join(", ", trieudai);

            Element trElement2 = content.select("tr:has(th:containsOwn(Chỉ huy))").first();
            Element leader = trElement2.nextElementSibling();
            Elements chihuy = leader.select("a[href]");

            List<String> nhanvat = new ArrayList<>();
            for (Element i : chihuy)
                nhanvat.add(i.text());
            String nvLienQuan = String.join(", ", nhanvat);

            Element trElement3 = content.select("tr:has(th:containsOwn(Lực lượng))").first();
            Element number = trElement3.nextElementSibling();

            Element firstNB = number.select("td").first();
            Element secondNB = number.select("td").last();

            String lucluong1 = "Bên 1 " + firstNB.text();
            String lucluong2 = "Bên 2 " + secondNB.text();

            Element trElement4 = content.select("tr:has(th:containsOwn(Thương vong))").first();
            Element death = trElement4.nextElementSibling();

            Element firstD = death.select("td").first();
            Element secondD = death.select("td").last();

            String thuongvong1 = "Bên 1 " + firstD.text();
            String thuongvong2 = "Bên 2 " + secondD.text();

            System.out.println("Kết quả: " + result);
            System.out.println("Địa điểm liên quan: " + destination);
            System.out.println("Triều đại liên quan (Tham chiến): " + tdLienQuan);
            System.out.println("Nhân vật liên quan (Chỉ huy): " + nvLienQuan);
            System.out.println("Lực lượng: " + lucluong1 + ", " + lucluong2);
            System.out.println("Thương vong: " + thuongvong1 + ", " + thuongvong2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String theloai(String url) throws IOException {
        String returnString = "";
        try {
            Document doc = Jsoup.connect(url).get();
            Element div = doc.select("div#mw-normal-catlinks").first();
            Element elementul = div.select("ul").first();
            Elements elementli = elementul.select("li");
            List<String> theloai = new ArrayList<>();
            for (Element e : elementli)
                theloai.add(e.text());
            returnString = String.join(", ", theloai);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    public static String contentInfoBox(String element) throws IOException {
        String returnString = "";
        Document doc = Jsoup.connect(
                "https://vi.wikipedia.org/wiki/Chi%E1%BA%BFn_tranh_H%C3%A1n%E2%80%93Vi%E1%BB%87t_(42%E2%80%9343)")
                .get();
        Element content = doc.getElementsByClass("infobox vevent").first();
        Element contentSpecial = content.getElementsMatchingOwnText(element).first();

        if (contentSpecial != null && contentSpecial.nextElementSibling() != null) {
            returnString = returnString + contentSpecial.nextElementSibling().text();
        }
        return returnString;
    }

    public static void mota(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element div = doc.select("div.mw-parser-output").first();
        Elements paragraphs = div.select("p");
        Element pElement = paragraphs.get(0);
        System.out.println(pElement);
    }

    public static void list(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element divElements = doc.select("div[style=\"padding:0em 0.25em\"]").first();
        Elements href = divElements.select("a");
        for (Element i : href) {
            String linkUrl = i.attr("abs:href");
            //System.out.println(linkUrl);
            if (checkOneLevelUrl(linkUrl)) {
                System.out.println(linkUrl);
                System.out.println(i.text());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        // System.out
        //         .println(theloai("https://vi.wikipedia.org/wiki/Chi%E1%BA%BFn_tranh_T%E1%BA%A7n%E2%80%93Vi%E1%BB%87t"));
        //System.out.println(contentInfoBox("Thời gian"));
        //list("https://vi.wikipedia.org/wiki/Hi%E1%BB%87p_%C4%91%E1%BB%8Bnh_Paris_1973");
        System.out.println("Tiếng việt");
    }
}
