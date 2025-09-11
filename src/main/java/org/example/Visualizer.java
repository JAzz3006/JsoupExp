package org.example;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.*;

public class Visualizer {
    public static void simpleDottedPrintSiteMap(RefNode root, String indent){
        System.out.println(indent + root.getValue());
        for (RefNode node : root.getChildren()){
            simpleDottedPrintSiteMap(node, indent + ".");
        }
    }

    public static void saveSiteMapIntoFile(RefNode root){
        if (!checkDir(Utilz.SAVE_DIR.toPath())){
            return;
        }
        Path path = Utilz.SAVE_DIR.toPath().resolve(getFileName());
        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            recursiveWrite(root, writer, "", true);
        }catch (IOException e){
            System.out.println("Ошибка: файл не создан " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void recursiveWrite(RefNode node, BufferedWriter writer, String indent, boolean isLast){
        try{
            writer.write(indent + (isLast ? "└──" : "├──") + node.getValue());
            writer.newLine();
            Iterator<RefNode> iterator = node.getChildren().iterator();
            while (iterator.hasNext()){
                RefNode currentChild = iterator.next();
                boolean last = !iterator.hasNext();
                recursiveWrite(currentChild, writer, indent + (isLast ? "    " : "│   "), last);
            }

        } catch (IOException e) {
            System.out.println("Ошибка записи в файл " + e.getMessage());
        }
    }

    public static String getFileName(){
        String fileName = "";
        System.out.println("Введите имя файла:");
        Scanner scanner = new Scanner(System.in);
        try{
            fileName = scanner.nextLine();
            if (fileName.isBlank()){
                System.out.println("Недопустимое имя файла");
                getFileName();
            }
        } catch (InvalidPathException e) {
            System.out.println("Ошибка: недопустимый символ в имени файла " + e.getMessage());
            getFileName();
        }
        return fileName;
    }

    public static boolean checkDir(Path path){
        if (!Files.exists(path)){
            try {
                Files.createDirectories(Utilz.SAVE_DIR.toPath());
            } catch (IOException e) {
                System.out.println("Не удалось создать директорию: " + e.getMessage());
                return false;
            }
        }
        return true;
    }

    

}
