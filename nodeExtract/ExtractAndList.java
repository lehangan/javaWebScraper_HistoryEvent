package nodeExtract;

// import org.jsoup.*;
import java.io.IOException;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class ExtractAndList {
    ExtractData extractDataV2 = new ExtractData();
    public static int size = 0;
    public static List<String> listUrl = new ArrayList<>();

    ExtractAndList() throws IOException {
        try {
            extractDataV2.writeData(
                    "https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam");
        } catch (IOException e) {
            e.printStackTrace();
        }
        listUrl = ExtractData.listUrl;
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
        ExtractAndList linkEvent = new ExtractAndList();
        linkEvent.writeListUrl(listUrl);
    }
}
