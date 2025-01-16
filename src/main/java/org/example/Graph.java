package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
public class Graph {

    private final Map<Integer, List<Node>> graph;

    public Graph() {
        this.graph = new ConcurrentHashMap<>();
    }

    public void addNodeToLayer(int layerIndex, Node node) {
        List<Node> layer = graph.computeIfAbsent(layerIndex, index -> new CopyOnWriteArrayList<>());
        layer.add(node);
    }
}
