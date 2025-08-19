package org.example;
import java.util.Set;

public interface Node {
    String getValue();
    Set<Node> getChildren();
}
