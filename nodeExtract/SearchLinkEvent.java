package nodeExtract;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

import java.io.FileWriter;

public class SearchLinkEvent {
	public static List<String> listUrl = new ArrayList<>();
	public static int size = 0;

	public static boolean isEvent(String url) {
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

	public void listUrl(String url) {
		try {
			// Fetch the HTML content from the URL
			Document doc = Jsoup.connect(url).get();

			Element div = doc.selectFirst("div.mw-content-container");
			div.select("dl").tagName("p");
			// Select all anchor tags (links) from the parsed document
			Elements p = div.select("p");
			//Elements links = p.select("a[href^=/wiki/]");

			// Extract and print the href attribute of each link
			for (Element link : p) {
				Elements linkhref = link.select("a[href]");
				for (Element i : linkhref) {
					// System.out.println(i);
					
					String href = i.attr("abs:href");
					if (isEvent(href) && !listUrl.contains(href)) {
						size++;
						listUrl.add(href);
					}
				}
			}

		} catch (IOException e) {
			System.out.println("Error: Failed to fetch the URL.");
			e.printStackTrace();
		}


	}

	public void writeListUrl(List<String> listUrl) throws IOException{
		try (FileWriter fileWriter = new FileWriter("E:/OOP/javaProject/nodeExtract/eventLink.txt")) {
			for (String link : listUrl) {
				fileWriter.write(link + "\n");
			}
			System.out.println(size + " Links have been stored in the file");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		SearchLinkEvent linkEvent = new SearchLinkEvent();
		linkEvent.listUrl("https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam");
		linkEvent.writeListUrl(listUrl);
	}
}
