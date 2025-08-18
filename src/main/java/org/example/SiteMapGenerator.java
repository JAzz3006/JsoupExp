package org.example;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class SiteMapGenerator {

    public static RefNode generateNode(String url) throws IOException {
        RefNode node = new RefNode(url);
        Document doc = Jsoup.connect(url).get();
        String cssQuery1 = "a[href]";
        Elements links = doc.select(cssQuery1);
        Set<Node>children = links.stream()
                .map(el -> el.attr("abs:href"))
                .distinct()
                .filter(el -> el.contains(url))
                .filter(el -> !el.equals(url))
                .map(RefNode::new)
                .collect(Collectors.toSet());
        node.setChildren(children);
        if (!children.isEmpty()){
            children.forEach(child -> {
                try {
                    generateNode(child.getValue());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return node;
    }





}
