package org.example;

import java.sql.Ref;

public class Visualizer {
    public static void simpleDottedPrintSiteMap(RefNode root, String indent){
        System.out.println(indent + root.getValue());
        for (RefNode node : root.getChildren()){
            simpleDottedPrintSiteMap(node, indent + ".");
        }
    }
}
