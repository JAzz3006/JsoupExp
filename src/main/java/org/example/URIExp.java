package org.example;

import java.net.URI;
import java.net.URISyntaxException;

public class URIExp {
    public static void main(String[] args) throws URISyntaxException {

        String uriString = "https://ria.ru/20250715/qwer.html#hua";
        System.out.println(uriString + " - с этим работаем");

        URI uri = new URI(uriString);
        System.out.println(uri.getHost() + " - это хост");
        System.out.println(uri.getQuery() + " - это квери");
        System.out.println(uri.getPath()+ " - это патх");
        System.out.println(uri.getScheme()+ " - это схема");
        System.out.println(uri.getRawFragment()+ " - это РауФрагмент");
        System.out.println(uri.normalize() + " - normalize");
        System.out.println(uri.getAuthority() + " - athoritah");
        System.out.println(uri.getSchemeSpecificPart() + " - scheme specific part");
        System.out.println(uri.getUserInfo() + " - user info");
        String result = "start value";
        if (uri.getFragment() != null){
            result = uriString.replace("#" + uri.getFragment(), "");
        }
        System.out.println();

        System.out.println(result);
    }
}
