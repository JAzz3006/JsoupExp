package org.example;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ForkJoinPool;

public class App
{
    public static final String MAIN_URL = "https://ria.ru/";

    public static void main( String[] args ) throws IOException, URISyntaxException {
        ForkJoinPool pool = new ForkJoinPool();
        SiteMapGenerator task = new SiteMapGenerator(MAIN_URL);
        RefNode root = pool.invoke(task);
        Visualizer.saveSiteMapIntoFile(root);
//        System.out.println(cleanUrl("https://www.consultant.ru/document/cons_doc_LAW_19559/468b6853ba75e3bc6ac57b0cc35981d7ba1027dc/#:~:text=%D0%98%D1%81%D0%BF%D0%BE%D0%BB%D0%BD%D0%B5"));
    }

    public static void oldMain(){
        RefNode root = new ForkJoinPool().invoke(new SiteMapGenerator(MAIN_URL));
        Visualizer.simpleDottedPrintSiteMap(root,"");
    }

    public static String cleanUrl(String url){
        String rubbish = "#:~:text=";
        if (url.contains(rubbish)){
            int index = url.indexOf(rubbish);
            return url.substring(0, index);

        }
        return url;
    }

}
