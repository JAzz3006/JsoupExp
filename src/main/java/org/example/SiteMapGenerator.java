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

        return node;
    }

}
