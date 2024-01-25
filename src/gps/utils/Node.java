package gps.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Node {
	
	String name;
	
	Coordinate coord;
	
	HashMap<Node, Double> links = new HashMap<Node, Double>();
	
	List<Node> nodes = new ArrayList<>(); //Liste de tous les nodes
	
	public Node(String name, Coordinate coord) { //Créer un nouvel objet Node
		this.name = name;
		this.coord = coord;
		nodes.add(this);
	}
	
	public void remove() {
		this.name = null;
		this.coord = null;
		nodes.remove(this);
		System.gc();
	}
	
	public void addLink(Node node, double distance) {
		links.put(node, distance);
	}
	
	public void removeLink(Node node) {
		if(links.containsKey(node)) {
			links.remove(node);
		}
	}
	
	public String getName() {
		return name;
	}
	
	public Coordinate getCoordinates() {
		return coord;
	}
	
	public HashMap<Node, Double> getLinks() {
		return links;
	}
	
	public List<Node> getLinksAsList() {
		List<Node> nodes = new ArrayList<>();
		for(Node n : links.keySet()) {
			nodes.add(n);
		}
		return nodes;
	}
	
	public Node getNearestNode() {
		Node nearest = null;
		Double nearestDist = Double.MAX_VALUE;
		
		for(Node node : links.keySet()) {
			if(links.get(node) < nearestDist) {
				nearest = node;
				nearestDist = links.get(node);
			}
		}
		return nearest;
		
	}
	
	public void getPath(Node end) { //From the current node to the node "end"
		

		List<Node> sorted = new ArrayList<>();
		sorted.add(this);
		Node current = this;
		

		List<Pair> distances = new ArrayList<>();
		
		//Juste pr le 1er point
		for(Node n : current.getLinks().keySet()) {
			if(!sorted.contains(n)) {
				distances.add(new Pair(links.get(n), n));
			}
		}
		
		int smallest = 0;
		for(int i = 1; i < distances.size(); i++) {
			if(Double.valueOf(distances.get(i).getKey().toString()) < Double.valueOf(distances.get(smallest).getKey().toString())) smallest = i;
		}
		Node nearest = (Node) distances.get(smallest).getValue();
		sorted.add(nearest);
		
		List<Pair> truc = distances;
		for(Pair p : truc) {
			if(p.getValue().equals(nearest)) {
				distances.remove(p);
			}
		}
		
		current = nearest;
		
		System.out.println("NEW CURRENT : " + current.getName());
		for(Pair p : distances) {
			System.out.println("A DISTANCE: " + p.getKey().toString());
		}
		
	}
	
	public double distanceOf(Node node) {
		Coordinate c = node.getCoordinates();
		double x = this.getCoordinates().getX();
		double y = this.getCoordinates().getY();
		double z = this.getCoordinates().getZ();
	    return Math.sqrt(Math.pow(x - c.getX(), 2) + Math.pow(y - c.getY(), 2) + Math.pow(z - c.getZ(), 2));
	}
	
}
