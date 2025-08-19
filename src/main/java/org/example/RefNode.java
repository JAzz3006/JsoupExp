package org.example;
import java.util.HashSet;
import java.util.Set;

public class RefNode implements Node{
    private final String value;
    private Set<Node> children = new HashSet<>();

    public RefNode(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Set<Node> getChildren() {
        return children;
    }

    public void setChildren(Set<Node> nodes){
        children = nodes;
    }
}
