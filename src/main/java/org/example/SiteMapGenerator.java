package org.example;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class SiteMapGenerator extends RecursiveTask<RefNode> {
    private final String url;

    public SiteMapGenerator(String url) {
        this.url = url;
    }

    @Override
    protected RefNode compute() {
        RefNode node = new RefNode(url);
        List<SiteMapGenerator> taskList = new ArrayList<>();
        Set<RefNode>children = new HashSet<>();
        try {
             children = generateChildren(url);
        } catch (IOException | URISyntaxException e) {
            return node;
        }
        for (RefNode child : children){
            SiteMapGenerator task = new SiteMapGenerator(child.getValue());
            task.fork();
            taskList.add(task);
        }
        for (SiteMapGenerator task : taskList){
            RefNode childNode = task.join();
            node.addChild(childNode);
        }
        return node;
    }

    public static Set<RefNode> generateChildren(String url) throws IOException, URISyntaxException {
        URI uri = new URI(url);
        Document doc = Jsoup.connect(url).get();
        String cssQuery1 = "a[href]";
        Elements links = doc.select(cssQuery1);

        Set <RefNode> children = links.stream()
                .map(el -> el.attr("abs:href"))
                .distinct()
                .filter(el -> {
                    try {
                        return new URI(el).getHost() != null;
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(el -> {
                    try {
                        return new URI(el).getHost().equals((uri.getHost()));
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(el -> !el.equals(url))
                .map(RefNode::new)
                .collect(Collectors.toSet());
        return children;
    }

}
