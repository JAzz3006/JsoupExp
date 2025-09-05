package org.example;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RefNode {
    private final String value;
    private Set<RefNode> children = new HashSet<>();

    public RefNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Set<RefNode> getChildren() {
        return children;
    }

    public void addChild(RefNode child){
        children.add(child);
    }

    public void setChildren(Set<RefNode> nodes){
        children = nodes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RefNode refNode = (RefNode) o;
        return Objects.equals(value, refNode.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
