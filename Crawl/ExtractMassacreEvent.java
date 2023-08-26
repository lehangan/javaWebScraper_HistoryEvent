package Crawl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class ExtractMassacreEvent {
    public Document doc;

    public ExtractMassacreEvent(String url) throws IOException{
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void crawlData(String filepath){

    }
}
