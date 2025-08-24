package org.example;
import com.sun.jndi.toolkit.url.Uri;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
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
        if (raw == null || raw.isEmpty()) return null;
        URI uri = new URI(raw);
        StringBuilder builder = new StringBuilder();
        String scheme = (uri.getScheme() == null ? "https://" : uri.getScheme()).toLowerCase(Locale.ROOT);
        String host = (uri.getHost() == null ? "" : uri.getHost()).toLowerCase(Locale.ROOT);
        String path = (uri.getPath() == null || uri.getPath().isEmpty() ? "/" : uri.getPath()).toLowerCase(Locale.ROOT);
        int port = uri.getPort();
        if (path.length() > 1 && path.endsWith("/")) path = path.substring(0, path.length() - 1);
        builder.append(scheme).append("://").append(host);
        if (port != -1 && port != 80 && port != 443) builder.append(":").append(port);
        builder.append(path);
        return builder.toString();
    }

    public static boolean isHtml(String url){
        String u = url.toLowerCase(Locale.ROOT);
        return u.endsWith(".jpg") || u.endsWith(".jpeg") ||
                u.endsWith(".gif") || u.endsWith(".png") ||
                u.endsWith(".pdf") || u.endsWith("zip") ||
                u.endsWith(".mp3") || u.endsWith(".mp4") ||
                u.endsWith(".avi") || u.endsWith(".mov") ||
                u.endsWith(".rar") || u.endsWith(".webp");
    }

    public static boolean sameHost(String u1, String u2) throws MalformedURLException {
        Uri uri1 = new Uri(u1);
        Uri uri2 = new Uri(u2);
        String h1 = Optional.ofNullable(uri1.getHost()).orElse("");
        String h2 = Optional.ofNullable(uri2.getHost()).orElse("");
        return h1.equalsIgnoreCase(h2);
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
