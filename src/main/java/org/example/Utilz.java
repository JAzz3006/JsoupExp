package org.example;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Utilz {
    public static final File SAVE_DIR = new File(System.getProperty("user.dir") + "/src/output/");

    public static List<Pattern> getForbidden(String url) throws IOException {
        List<Pattern> forbidden = new ArrayList<>();

        String fileNameRobotsTxt = Utilz.getFileName(URIExp.MAIN_URL);
        Path path = Paths.get(Utilz.SAVE_DIR.toString(), fileNameRobotsTxt);
        File robotsTxt = new File(path.toString());
        if (robotsTxt.exists()) {
            List<String> lines = Files.readAllLines(robotsTxt.toPath());
            boolean inApplicableSection = false;
            for (String line : lines) {
                if (line.isEmpty() || line.startsWith("#")) continue;

                if (line.toLowerCase().contains("user-agent")) {
                    String uaType = line.split(":", 2)[1].trim();
                    inApplicableSection = uaType.equals("*");
                }
                if (inApplicableSection && line.toLowerCase().contains("disallow")) {
                    String forbiddenUrl = line.split(":",2)[1].trim();
                    String regex = forbiddenUrl.replace("*",".*");
                    forbidden.add(Pattern.compile(regex));
                }
                if (line.toLowerCase().startsWith("user-agent:") &&
                        !line.split(":",2)[1].trim().equals("*")){
                    inApplicableSection = false;
                }

            }
        }else System.out.println("File 'robots.txt' not found!");
        return forbidden;
    }

    public static String normalize(String raw) throws URISyntaxException {
        URI uri = new URI(raw);
        String result = raw;
        String query = uri.getQuery();
        if (query != null){
            result = raw.replace(query,"");
        }
        uri.normalize();
        return result;
    }

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

    public static File saveRobotsTxt(String url){
        String outputFile = getFileName(url);
        if (!SAVE_DIR.exists()) {
            SAVE_DIR.mkdirs();
        }
        File targetFile = new File(SAVE_DIR, outputFile);
        try {
            String robot = "robots.txt";
            String body = Jsoup.connect(url + robot)
                    .ignoreContentType(true)
                    .execute()
                    .body();
            try (FileWriter writer = new FileWriter(targetFile)) {
                writer.write(body);
            }
            System.out.println("Файл robots.txt сохранён как " + targetFile + " в " + getWorkingDir());
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        return targetFile;
    }

    public static File getRobots(String fileName){
        File file = new File(SAVE_DIR + fileName);
        if (!file.exists()){
            System.out.println("file not found");
            return null;
        }
        return file;
    }

    public static String getFileName(String url){
        String suffix = "robots.txt";
        String regex = "https?://";
        String cutUrl = url.replaceAll(regex,"");
        int endIndex = cutUrl.indexOf("/");
        return String.join("-",cutUrl.substring(0,endIndex), suffix);
    }
    public static String  getWorkingDir() {
        return System.getProperty("user.dir");
    }
}
