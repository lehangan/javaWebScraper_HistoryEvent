//package jsoup.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import java.io.IOException;


public class web_crawl {

    public static void main(String[] args) throws IOException{	
        
        Document doc = Jsoup.connect("https://vi.wikipedia.org/wiki/C%C3%A1c_cu%E1%BB%99c_chi%E1%BA%BFn_tranh_Vi%E1%BB%87t_Nam_tham_gia").get();
        //Document doc = Jsoup.parse(new URL("https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam").openStream(), UNICODE, "https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam");
        String title = doc.title();
        System.out.println(title);

        //Elements d = doc.select("table");
        Elements links = doc.select("table.wikitable");
        System.out.println(links.size());
        
        for (int i = 0; i < links.size(); i++) {
            // Get the i-th link in the list
            //Elements link = links.get(i);
        
            // Get the link's URL
            //String url = links.get(i).attr("href");
            Elements link = links.get(i).select("td");
            System.out.println(link.text());
            System.out.println();
            // Do something with the link URL, such as print it to the console
            //System.out.println(url);
        }
        
    }

}
