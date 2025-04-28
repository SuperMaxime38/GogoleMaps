package fr.poutrecosmique.gps.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.util.Vector;

public class Node {
	
	public double x,y,z;
	public List<Node> neightbors;
	
	public Node(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.neightbors = new ArrayList<Node>();
	}
	
	public void setCoordinates(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void addNeightbor(Node neightbor) {
		this.neightbors.add(neightbor);
	}
	
	public void doubleAddNeightbor(Node neightbor) {
		this.neightbors.add(neightbor);
		neightbor.neightbors.add(this);
	}
	
	public void removeNeightbor(Node neightbor) {

		this.neightbors.remove(neightbor);
	}
	
	public void clearNeightbors() {
		this.neightbors = new ArrayList<Node>();
	}
	
	public double distanceOf(Node node) {
		if(this.neightbors.contains(node)) {
			Vector v = getVectorDirector(node);
			
			return Math.sqrt(Math.pow(v.getX(), 2) + Math.pow(v.getY(), 2) + Math.pow(v.getZ(), 2));
		}
		return Double.POSITIVE_INFINITY;
	}
	
	public Vector getVectorDirector(Node node) {

		double deltaX = Math.abs((this.x - node.x)/2);
		double deltaY = Math.abs((this.y - node.y)/2);
		double deltaZ = Math.abs((this.z - node.z)/2);
		
		return new Vector(deltaX, deltaY, deltaZ);
	}
	
	@Override
	public String toString() {
		return "Node [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
}
