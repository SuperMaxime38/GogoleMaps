package fr.poutrecosmique.gps.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Graph {
	
	HashMap<Node, List<Node>> graph;
	
	public Graph() {
		graph = new HashMap<Node, List<Node>>();
	}
	
	public void addNode(Node node) {
		graph.put(node, node.neightbors);
	}
	
	public void setNodes(List<Node> nodes) {
		for(Node node:nodes) {
			graph.put(node, node.neightbors);
		}
	}
	
	public void removeNode(Node node) {
		graph.remove(node);
		for(Node n : node.neightbors) {
			n.removeNeightbor(node);
		}
	}
	
	public void addNeightbor(Node node, Node neightbor) {
		graph.get(node).add(neightbor);
		node.addNeightbor(neightbor);
	}
	
	public void setNeightbors(Node node, List<Node> neightbors) {
		graph.get(node).clear();
		node.clearNeightbors();
		for(Node n : neightbors) {
			addNeightbor(node, n);
		}
	}
	
	public void removeNeightbor(Node node, Node neightbor) {
		graph.get(node).remove(neightbor);
		node.removeNeightbor(neightbor);
	}
	
	
	public Set<Node> getNodes() {
		return graph.keySet();
	}
}
