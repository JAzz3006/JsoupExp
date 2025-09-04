package org.example;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class SiteMapGenerator extends RecursiveTask<RefNode> {
    private final String url;
    private AtomicInteger budget = new AtomicInteger(5000);
    private Set<RefNode> visited= new HashSet<>();
    private Semaphore throttle;

    public SiteMapGenerator(String url) {
        this.url = url;
    }

    @Override
    protected RefNode compute() {
        RefNode node = new RefNode(url);
        if (budget.get() <= 0) return node;
        Document doc = null;
        try {
            throttle.acquire();
            doc = HtmlConnect.getDoc(8000, true, true);
//            Connection.Response resp = Jsoup.connect(url)
//                    .userAgent("Mozilla/5.0 (compatible, SiteMapBot/1.0")
//                    .timeout(8000)
//                    .followRedirects(true)
//                    .ignoreHttpErrors(true)
//                    .execute();
//
//            if (resp.statusCode() >= 200
//                    && resp.statusCode() < 400
//                    && Optional.ofNullable(resp.contentType()).orElse("").toLowerCase(Locale.ROOT).contains("text/html")){
//                doc = resp.parse();
//            }else return node;
        } catch (IOException e) {
            Thread.currentThread().interrupt();
            return node;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return node;
    }

}
