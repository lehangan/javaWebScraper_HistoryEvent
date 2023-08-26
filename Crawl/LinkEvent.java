package Crawl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class LinkEvent {
    public String url;
    public static Document doc;

    LinkEvent(String url) throws IOException {
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isEvent() {

        // Check if the desired class exists in the parsed document
        boolean hasInfoboxVevent = doc.select(".infobox.vevent").size() > 0;
        // Print the result
        if (hasInfoboxVevent) {
            return true;
        } else {
            return false;
        }
    }

    public String contentInfoBox(String element) throws IOException {
        String returnString = "";
        Element content = doc.getElementsByClass("infobox vevent").first();
        if (content != null) {
            Element contentSpecial = content.getElementsMatchingOwnText(element).first();

            if (contentSpecial != null && contentSpecial.nextElementSibling() != null) {
                returnString = returnString + contentSpecial.nextElementSibling().text();
            }
        }
        return returnString;
    }

    public List<String> contentInfoBoxList(String element, int type) throws IOException {
        List<String> listString = new ArrayList<>();

        Element content = doc.getElementsByClass("infobox vevent").first();
        Element contentSpecial = content.select("tr:has(th:containsOwn(" + element + "))").first();
        if (contentSpecial != null && contentSpecial.nextElementSibling() != null) {
            Elements contentArray = contentSpecial.nextElementSibling().select("a[href]");
            for (Element i : contentArray)
                listString.add(i.text());
        }
        return listString;
    }

    public String contentInfobox(String element, int type1, int type2) throws IOException {
        String returnString = "";
        List<String> listString = new ArrayList<>();
        Element content = doc.getElementsByClass("infobox vevent").first();
        Element contentSpecial = content.select("tr:has(th:containsOwn(" + element + "))").first();
        if (contentSpecial != null && contentSpecial.nextElementSibling() != null) {
            Element contentArray = contentSpecial.nextElementSibling();
            Element first = contentArray.select("td").first();
            Element second = contentArray.select("td").last();
            returnString = "Bên 1 " + first.text() + " " + "Bên 2 " + second.text();
        }

        return returnString;
    }

    public List<String> contentInfoboxList(String element, int type1, int type2) throws IOException {
        String returnString = "";
        List<String> listString = new ArrayList<>();
        Element content = doc.getElementsByClass("infobox vevent").first();
        Element contentSpecial = content.select("tr:has(th:containsOwn(" + element + "))").first();
        if (contentSpecial != null && contentSpecial.nextElementSibling() != null) {
            Element contentArray = contentSpecial.nextElementSibling();
            Element first = contentArray.select("td").first();
            Element second = contentArray.select("td").last();
            returnString = "Bên 1 " + first.text() + " " + "Bên 2 " + second.text();
            listString.add(first.text());
            listString.add(second.text());
        }
        return listString;
    }

    // Theloai 
    public List<String> contentInfoboxList() throws IOException {
        List<String> theloai = new ArrayList<>();
        Element div = doc.select("div#mw-normal-catlinks").first();
        if (div != null) {
            Element elementul = div.select("ul").first();
            if (elementul != null) {
                Elements elementli = elementul.select("li");
                for (Element e : elementli)
                    theloai.add(e.text());
                // returnString = String.join(", ", theloai);
            }
        }

        return theloai;
    }

    public String description() throws IOException {
        String returnString = "";
        Element div = doc.select("div.mw-parser-output").first();
        if (div != null) {
            Element des = div.select("p").first();
            if (des != null)
                returnString = des.text();
        }
        return returnString;
    }

    public String image() throws IOException{
        String imageUrl ="";
        try {
            // instance = new HashMap<String, String>(10000);
            Document docChild = Jsoup.connect(url).get();
            Element content = docChild.getElementsByClass("infobox vevent").first();
            Element image = content.selectFirst("img");
            imageUrl = image.attr("abs:src");
        }catch (IOException e) {
            e.printStackTrace();
        }
        return imageUrl;
    }
}
