package org.example;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class HtmlConnect {

    public static final String USER_AGENT_1 = "Mozilla/5.0 (compatible, SiteMapBot/1.0";

    public static Connection.Response getResponse(
            String userAgent,
            int timeOut,
            boolean ignoreHttpErr,
            boolean followRedirects
    ) throws IOException {
        return Jsoup.connect(App.MAIN_URL)
                .userAgent(userAgent)
                .timeout(timeOut)
                .ignoreHttpErrors(ignoreHttpErr)
                .followRedirects(followRedirects)
                .execute();
    }
    public static Document getDoc( int timeOut, boolean ignoreHttpErr, boolean followRedirects ) throws IOException {
        org.jsoup.nodes.Document doc = null;
        Connection.Response resp = getResponse(USER_AGENT_1, timeOut, ignoreHttpErr, followRedirects);
        if (resp.statusCode() < 400 && resp.statusCode() >= 100){
            doc = resp.parse();
        }else System.out.println("Something has gone wrong");
        return doc;
    }
}
