//package jsoup.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import java.io.IOException;

public class web_crawl {
    public static void main(String[] args) throws IOException{
        Document doc = Jsoup.connect("https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam").get();
        String title = doc.title();
        System.out.println("niên biểu lịch sử việt nam");
    }

}
