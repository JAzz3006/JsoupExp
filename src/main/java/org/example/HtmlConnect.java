package org.example;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class HtmlConnect {
    private static final Logger logger = LoggerFactory.getLogger(HtmlConnect.class);

    public static final String USER_AGENT_1 = "Mozilla/5.0 (compatible; SiteMapBot/1.0; +mailto:imathing78@mail.ru)";

    public static Connection.Response getResponse(
            String url,
            String userAgent,
            int timeOut,
            boolean ignoreHttpErr,
            boolean followRedirects
    ) throws IOException {
        return Jsoup.connect(url)
                .userAgent(userAgent)
                .timeout(timeOut)
                .ignoreHttpErrors(ignoreHttpErr)
                .followRedirects(followRedirects)
                .execute();
    }
    public static Document getDoc( String url, int timeOut, boolean ignoreHttpErr, boolean followRedirects ) throws IOException {
        org.jsoup.nodes.Document doc = null;
        Connection.Response resp = getResponse(url, USER_AGENT_1, timeOut, ignoreHttpErr, followRedirects);
        if (resp.statusCode() < 400 && resp.statusCode() >= 100){
            doc = resp.parse();
        }else logger.warn("Something has gone wrong at " + url);
        return doc;
    }
}
