package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Visualizer {
    public static void simpleDottedPrintSiteMap(RefNode root, String indent){
        System.out.println(indent + root.getValue());
        for (RefNode node : root.getChildren()){
            simpleDottedPrintSiteMap(node, indent + ".");
        }
    }

    public static void saveSiteMapIntoFile(){
        System.out.println("Введите имя файла: ");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        if (fileName.isBlank()){
            System.out.println("Недопустимое имя файла");
            return;
        }
        if (!Utilz.SAVE_DIR.exists()){
            try {
                Files.createDirectories(Utilz.SAVE_DIR.toPath());
            } catch (IOException e) {
                System.out.println("Не удалось создать директорию: " + e.getMessage());
                return;
            }
        }
        Path pathToSave = Utilz.SAVE_DIR.toPath().resolve(fileName);

    }

    

}
