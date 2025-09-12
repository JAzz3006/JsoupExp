package org.example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ForkJoinPool;

public class App
{
    public static final String MAIN_URL = "https://ria.ru/";

    public static void main( String[] args ) throws IOException, URISyntaxException {
        Logger logger = LoggerFactory.getLogger(App.class);
        ForkJoinPool pool = new ForkJoinPool();
        SiteMapGenerator task = new SiteMapGenerator(MAIN_URL);
        RefNode root = pool.invoke(task);
        Visualizer.saveSiteMapIntoFile(root);
    }

}
