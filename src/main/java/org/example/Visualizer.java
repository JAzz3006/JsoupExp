package org.example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.*;

public class Visualizer {
    public static final Logger logger = LoggerFactory.getLogger(Visualizer.class);
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
            logger.error("Ошибка: файл не создан " + e.getMessage());
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
            logger.error("Ошибка записи в файл " + e.getMessage());
        }
    }

    public static String getFileName(){
        String fileName = "";
        logger.info("Введите имя файла (для сохранения карты сайта):");
        Scanner scanner = new Scanner(System.in);
        try{
            fileName = scanner.nextLine();
            if (fileName.isBlank()){
                logger.warn("Недопустимое имя файла");
                getFileName();
            }
        } catch (InvalidPathException e) {
            logger.warn("Ошибка: недопустимый символ в имени файла " + e.getMessage());
            getFileName();
        }
        return fileName;
    }

    public static boolean checkDir(Path path){
        if (!Files.exists(path)){
            try {
                Files.createDirectories(Utilz.SAVE_DIR.toPath());
            } catch (IOException e) {
                logger.error("Не удалось создать директорию: " + e.getMessage());
                return false;
            }
        }
        return true;
    }
}
