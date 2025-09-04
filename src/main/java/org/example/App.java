package org.example;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class App
{
    public static final String MAIN_URL = "https://ria.ru/";

    public static void main( String[] args ) throws IOException, URISyntaxException {

        Document doc = HtmlConnect.getDoc(8000, true,true);
        if (doc == null){
            System.out.println("Что-то пошло не так");
            return;
        }
        TreeSet<String> visited = new TreeSet<>();
        visited.add(MAIN_URL);

        String cssQuery1 = "a[href]";
        List<Pattern> forbidden = Utilz.getForbidden(App.MAIN_URL);

        ArrayList<String> refStrings = doc.select(cssQuery1).stream()
                .map(e -> e.attr("abs:href"))
                .map(Utilz::normalize)
                .filter(Utilz::sameHost)
                .filter(Utilz::isHtml)
                .filter(ref -> Utilz.isForbidden(forbidden, ref))
                .distinct()
                .filter(visited::add)
                .collect(Collectors.toCollection(ArrayList::new));



//        Utilz.printForbidden(forbidden);
        Utilz.saveFile(refStrings);

    }
    public static void saveChildren(Set<RefNode> children) throws IOException {
        System.out.println("имя файла: ");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        List<String> listOfUrl = children.stream()
                .map(RefNode::getValue)
                .collect(Collectors.toList());
        Path dirPath = Paths.get("src/output").normalize();
        if (!Files.exists(dirPath)){
            Files.createDirectories(dirPath);
        }
        Path filePath = dirPath.resolve(fileName);

        Files.write(filePath, listOfUrl);


    }

}
