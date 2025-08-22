package org.example;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class App
{
    public static final String MAIN_URL = "https://ria.ru/";

    public static void main( String[] args ) throws IOException, URISyntaxException {

        String url = "https://ria.ru/video/";
        Set<RefNode> children = SiteMapGenerator.generateChildren(url);
        saveChildren(children);

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
