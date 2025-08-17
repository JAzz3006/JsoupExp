package org.example;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws IOException {
        String url = "https://sn.ria.ru/";
        Document doc = Jsoup.connect(url).get();
        String cssQuery1 = "a[href]";
        Elements links = doc.select(cssQuery1);
        //System.out.println(Utilz.getSuffix(url));
        //Utilz.getRobotTxt(url);
        links.stream()
                .map(el -> el.attr("abs:href"))
                .forEach(System.out::println);



    }

}
