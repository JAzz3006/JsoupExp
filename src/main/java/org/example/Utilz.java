package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileWriter;
import java.io.IOException;

public class Utilz {

    public static void getRobotTxt(String url){
        String outputFile = String.join("-",getSuffix(url),"robots.txt");
        try {
            // Загружаем robots.txt
            String robot = "robots.txt";
            Document doc = Jsoup.connect(url + robot)
                    .ignoreContentType(true) // важно! иначе Jsoup ожидает HTML
                    .get();

            // Сохраняем как текст
            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write(doc.text());
            }

            System.out.println("Файл robots.txt сохранён как " + outputFile + " в " + getWorkingDir());
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }



    public static String getSuffix(String url){
        String regex = "https?://";
        String cutUrl = url.replaceAll(regex,"");
        int endIndex = cutUrl.indexOf("/");
        return cutUrl.substring(0,endIndex);
    }
    public static String  getWorkingDir() {
        return System.getProperty("user.dir");
    }
}
