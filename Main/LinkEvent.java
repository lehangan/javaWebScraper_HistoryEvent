package Main;

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

    public String contentInfoBox( String element) throws IOException {
        String returnString = "";
        Element content = doc.getElementsByClass("infobox vevent").first();
        Element contentSpecial = content.getElementsMatchingOwnText(element).first();

        if (contentSpecial != null && contentSpecial.nextElementSibling() != null) {
            returnString = returnString + contentSpecial.nextElementSibling().text();
        }
        return returnString;
    }

    public String contentInfoBox( String element, int type) throws IOException {
        List<String> listString = new ArrayList<>();
        String returnString = "";

        Element content = doc.getElementsByClass("infobox vevent").first();
        Element contentSpecial = content.select("tr:has(th:containsOwn(" + element + "))").first();
        if (contentSpecial != null && contentSpecial.nextElementSibling() != null) {
            Elements contentArray = contentSpecial.nextElementSibling().select("a[href]");
            for (Element i : contentArray)
                listString.add(i.text());
            returnString = String.join(", ", listString);
        }
        return returnString;

    }

    public String contentInfobox( String element, int type1, int type2) throws IOException {
        String returnString = "";

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

    public String contentInfobox() throws IOException {
        String returnString = "";
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

        return returnString;
    }

}
