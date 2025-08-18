package org.example;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileWriter;
import java.io.IOException;

public class Utilz {

    public static boolean isAccessible(String url){
        boolean isAlive = false;
        try {
            Connection.Response response = Jsoup.connect(url)
                    .ignoreHttpErrors(true) // не падать на 404
                    .timeout(5000)
                    .execute();
            int statusCode = response.statusCode();
            if (statusCode >= 200 && statusCode < 400) {
                isAlive = true;
            } else {
                System.out.println("not accessible URL!");;
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return isAlive;
    }

    public static boolean isUrl(String url){
        boolean isUrl = false;
        String regex = "https?://[^\\s]+";
        if (url.matches(regex)){
            isUrl = true;
        }
        return isUrl;
    }

    public static void getRobotTxt(String url){
        String outputFile = String.join("-",getSuffix(url),"robots.txt");
        try {
            String robot = "robots.txt";
            Document doc = Jsoup.connect(url + robot)
                    .ignoreContentType(true) // важно! иначе Jsoup ожидает HTML
                    .get();
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
