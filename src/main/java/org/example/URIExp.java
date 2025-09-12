package org.example;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class URIExp {
    public static final String MAIN_URL = "https://ria.ru/";

    public static void main(String[] args) throws URISyntaxException, IOException {
//        List<Pattern> forbidden = Utilz.getForbidden(App.MAIN_URL);
        String oneMoreString = "http://www.defenceimagery.mod.uk/fotoweb/archives/5046-all news - stock/purged/archpurged/raf/2018/june/135eaw-official-20180427-0008-0331.jpg";

        String randomRef = "https://gu.spb.ru/634197/#:~:text=%D0%95%D0%B6%D0%B5%D0%B3%D0%BE%D0%B4%D0%BD%D0%B0%D1%8F%20%D0%BA%D0%BE%D0%BC%D0%BF%D0%B5%D0%BD%D1%81%D0%B0%D1%86%D0%B8%D0%BE%D0%BD%D0%BD%D0%B0%D1%8F%20%D0%B2%D1%8B%D0%BF%D0%BB%D0%B0%D1%82%D0%B0%20%D0%BD%D0%B0%20%D0%B4%D0%B5%D1%82%D0%B5%D0%B9%20%D0%";
//        String test = "/whatf{{or";
//        String test1 = "/rul%7B%7B123";
//        String rule = "*%7B%7B*";
//        String decoded = URLDecoder.decode(rule, StandardCharsets.UTF_8);
//        System.out.println(decoded + " - это декодированное правило");
//        String regDecoded = Pattern.quote(decoded);
//        System.out.println(regDecoded + " - это результат по quote()");
//        regDecoded = regDecoded.replace("*", ".*");
//        Pattern p = Pattern.compile(regDecoded);
//        System.out.println(p + " - это готовый паттерн");
//        System.out.println(p.matcher(test1).matches() + " - это паттерн р");
//        Pattern newP = Pattern.compile(".*%7B%7B.*");
//        System.out.println(newP.matcher(test1).matches() + " - это паттерн newP");
//generalExpos(randomRef);
        System.out.println(oneMoreString.replaceAll(" ", "%20"));


}

    public static String normalize(String raw) throws URISyntaxException {
        String result ="";
        URI uri = new URI(raw);
        String query = uri.getQuery();
        String frag = uri.getFragment();

        if (frag != null){
            result = raw.replace("#" + frag, "");
        } else if (query != null) {
            result = result.replace("?" + query, "");
        }
        return result;
    }

    public static String normalize1(String raw) throws URISyntaxException {
        String result ="";

        URI uri = new URI(raw);
        String syntax1 = "://";
        String scheme = uri.getScheme();
        String host = uri.getHost();
        String path = uri.getPath();
        String query = uri.getQuery();
        String frag = uri.getFragment();


        return String.join("", scheme, syntax1, host, path);

    }


    public static void generalExpos(String url) throws URISyntaxException {
        System.out.println(url + " - с этим работаем");

        URI uri = new URI(url);
        System.out.println(uri.getHost() + " - это хост");
        System.out.println(uri.getQuery() + " - это квери");
        System.out.println(uri.getPath()+ " - это патх");
        System.out.println(uri.getScheme()+ " - это схема");
        System.out.println(uri.getRawSchemeSpecificPart() + " - это raw-схема");
        System.out.println(uri.getRawFragment()+ " - это РауФрагмент");
        System.out.println(uri.normalize() + " - normalize");
        System.out.println(uri.getAuthority() + " - athoritah");
        System.out.println(uri.getSchemeSpecificPart() + " - scheme specific part");
        System.out.println(uri.getUserInfo() + " - user info");
        String result = "start value";
        if (uri.getFragment() != null){
            result = url.replace("#" + uri.getFragment(), "");
        }
        System.out.println();

        System.out.println(result);
    }
}
