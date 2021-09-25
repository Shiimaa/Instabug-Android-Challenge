package apps.instabugandroidchallenge.operations.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class NetworkOperations {
    public static boolean isNetworkError = false;

    public static String getWebSiteContent(String url) {
        String body = "";
        try {
            Document document = Jsoup.connect(url).get();
            Element element = document.body();
            body = element.text();
        } catch (IOException e) {
            isNetworkError = true;
            e.printStackTrace();
        }
        return body;
    }
}
