package org.example;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SiteMapGenerator extends RecursiveTask<RefNode> {
    private static final Set<String> visited = ConcurrentHashMap.newKeySet();
    private static final Semaphore throttle = new Semaphore(2);
    private static  final AtomicInteger budget = new AtomicInteger(600);
    private static final List<Pattern> forbidden;
    static {
        try {
            forbidden = Utilz.getForbidden(App.MAIN_URL);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private final String url;

    public SiteMapGenerator(String url){
        this.url = url;
    }

    @Override
    protected RefNode compute() {
        RefNode node = new RefNode(url);
        if (budget.getAndDecrement() < 0){
            System.out.println("Out of budget");
            return node;
        }
        List<SiteMapGenerator> tasks = new ArrayList<>();
        Document doc = null;
        try {
            throttle.acquire();
            doc = HtmlConnect.getDoc(url,8000, true, true);
            if (doc == null){
                System.out.println("Что-то пошло не так при получении кода страницы");
                return node;
            }
            visited.add(Utilz.normalize(url));

            String cssQuery1 = "a[href]";
            List<String> refList = doc.select(cssQuery1).stream()
                    .map(e -> e.attr("abs:href"))
                    .map(Utilz::normalize)
                    .filter(Utilz::sameHost)
                    .filter(Utilz::isHtml)
                    .filter(ref -> Utilz.isForbidden(forbidden, ref))
                    .distinct()
                    .filter(visited::add)
                    .collect(Collectors.toList());

            for (String ref : refList){
                SiteMapGenerator task = new SiteMapGenerator(ref);
                task.fork();
                tasks.add(task);
            }

            Thread.sleep(300);

        } catch (IOException e) {
            return node;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return node;
        }finally {
            throttle.release();
        }
        for (SiteMapGenerator task : tasks){
            RefNode child = task.join();
            node.addChild(child);
        }
        return node;
    }
}
