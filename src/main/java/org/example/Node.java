package org.example;

public record Node(Node parent, String link) {

    public Node getNode() {
        return this;
    }

    @Override
    public String toString() {
        return "Node{" +
                "parent=" + parent +
                ", link='" + link + '\'' +
                '}';
    }
}
