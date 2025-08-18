package org.example;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class App
{
    public static void main( String[] args ) throws IOException {
        String url = "https://sn.ria.ru/";
        if (!Utilz.isUrl(url)) {
            System.out.println("no URL submitted\nno structure was generated");
            return;
        }
        if (!Utilz.isAccessible(url)) {
            System.out.println("URL is not accessible\nno structure was generated");
            return;
        }
        RefNode root = SiteMapGenerator.generateNode(url);
        root.getChildren().stream()
                .map(Node::getValue)
                .forEach(System.out::println);
    }

}
