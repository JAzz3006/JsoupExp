package org.example;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;

public class SiteMapGenerator extends RecursiveAction {
    private final String url;

    public SiteMapGenerator(String url) {
        this.url = url;
    }

    @Override
    protected void compute() {
        RefNode node = new RefNode(url);
        List<SiteMapGenerator> actionList = new ArrayList<>();
        try {
            node.setChildren(generateChildren(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Node child : node.getChildren()){
            SiteMapGenerator action = new SiteMapGenerator(child.getValue());
            action.fork();
            actionList.add(action);
        }
        for (SiteMapGenerator action : actionList){
            action.join();
        }
    }

    public static Set<Node> generateChildren(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        String cssQuery1 = "a[href]";
        Elements links = doc.select(cssQuery1);
        Set <Node> children = links.stream()
                .map(el -> el.attr("abs:href"))
                .distinct()
                .filter(el -> el.contains(url))
                .filter(el -> !el.equals(url))
                .map(RefNode::new)
                .collect(Collectors.toSet());
        return children;
    }

}
