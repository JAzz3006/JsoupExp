package org.example;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

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
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new SiteMapGenerator(url));
    }
}
