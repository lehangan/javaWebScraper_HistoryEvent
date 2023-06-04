package nodeExtract;

// import org.jsoup.*;
import java.io.IOException;

import java.io.FileWriter;

import java.util.List;

public class LinkEvent {
    ExtractData extractData = new ExtractData();
    public static int size = 0;

    LinkEvent() throws IOException {
        try {
            extractData.writeData(
                    "https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeListUrl(List<String> listUrl) throws IOException {
        try (FileWriter fileWriter = new FileWriter("E:/OOP/javaProject/nodeExtract/eventLink.txt")) {
            for (String link : listUrl) {
                fileWriter.write(link + "\n");
                size++;
            }
            System.out.println(size + " Links have been stored in the file");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        LinkEvent linkEvent = new LinkEvent();
        List<String> listUrl = ExtractData.listUrl;
        linkEvent.writeListUrl(listUrl);
    }
}
