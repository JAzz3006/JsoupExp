package org.example;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ForkJoinPool;

public class App
{
    public static final String MAIN_URL = "https://ria.ru/";

    public static void main( String[] args ) throws IOException, URISyntaxException {
        RefNode root = new ForkJoinPool().invoke(new SiteMapGenerator(MAIN_URL));
        Visualizer.simpleDottedPrintSiteMap(root,"");
    }

    public static void oldMain(){
        RefNode root = new ForkJoinPool().invoke(new SiteMapGenerator(MAIN_URL));
        Visualizer.simpleDottedPrintSiteMap(root,"");
    }
}
