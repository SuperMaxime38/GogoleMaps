package fr.poutrecosmique.gps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.poutrecosmique.gps.utils.Graph;
import fr.poutrecosmique.gps.utils.Node;
import fr.poutrecosmique.gps.utils.Pair;
import fr.poutrecosmique.gps.utils.TasMin;

public class Dijkstra {
	
//	public void createPath(List<Node> nodes, Node startPoint) {
//		HashMap<Node, Pair> path = new HashMap<Node, Pair>();
//		path.put(startPoint, new Pair(0, null));
//		
//		HashMap<Node, Pair> distances = new HashMap<Node, Pair>();
//		distances.put(startPoint, new Pair(0, null));
//		
//		List<Node> done = new ArrayList<Node>();
//		
//		Node current = startPoint;
//		while(done.size() != nodes.size()) {
//			
//			for(Node node : nodes) {
//				
//				double distance = current.distanceOf(node);
//				
//				if(current.neightbors.contains(node)) {
//					//double dist = distances.get(current).a + 
//				}
//				
//				if(distances.containsKey(current)) {
//					if(distance < (double) distances.get(current).a) {
//						distances.put(current, new Pair(distance, current));
//					}
//				} else {
//					distances.put(current, new Pair(distance, current));
//				}
//			}
//			
//			
//		}
//	}
	
	public static Pair createPath(Graph graph, Node startPoint) {
		HashMap<Node, Double> distances = new HashMap<Node, Double>();
		HashMap<Node, Node> predecesors = new HashMap<Node, Node>();
		
		for(Node node: graph.getNodes()) {
			distances.put(node, Double.MAX_VALUE);
			predecesors.put(node, null);
		}
		
		TasMin priorityQueue = new TasMin();
		
		distances.put(startPoint, 0.0);
		priorityQueue.add(new Pair(0d, startPoint));
		
		while(priorityQueue.tas.size() > 0) {
			Pair priotity = priorityQueue.pop();
			double distance = (double) priotity.a;
			Node node = (Node) priotity.b;
			
			if(distance > distances.get(node)) {
				continue;
			}
			
			for(Node neighbor : node.neightbors) {
				double neighborDistance = distances.get(node) + node.distanceOf(neighbor);
				if(neighborDistance < distances.get(neighbor)) {
					distances.put(neighbor, neighborDistance);
					priorityQueue.add(new Pair(neighborDistance, neighbor));
					predecesors.put(neighbor, node);
				}
			}
		}
		
		return new Pair(distances, predecesors);
	}
	
	public static List<Node> rebuildPath(HashMap<Node, Node> predecesors, Node endPoint) {
		Node current = endPoint;
		List<Node> path = new ArrayList<Node>();
		path.add(current);
		
		while(predecesors.get(current) != null) {
			current = predecesors.get(current);
			path.add(current);
		}
		
		return path;
	}
}
