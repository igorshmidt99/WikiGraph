package org.example;

public record Node(Node parent, String link) {

    @Override
    public String toString() {
        return "Node{" +
                "parent=" + parent +
                ", link='" + link + '\'' +
                '}';
    }
}
