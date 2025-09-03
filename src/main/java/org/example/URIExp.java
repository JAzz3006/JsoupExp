package org.example;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
//        forbidden.forEach(System.out::println);
        String test = "/whatf{{or";
        String regex = ".*\\{\\{.*";
        Pattern p = Pattern.compile(regex);
        System.out.println(p.matcher(test).matches());
//generalExpos("https://ria.ru/?watever");



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
