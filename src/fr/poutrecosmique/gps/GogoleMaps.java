package fr.poutrecosmique.gps;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import fr.poutrecosmique.gps.utils.Graph;
import fr.poutrecosmique.gps.utils.Node;
import fr.poutrecosmique.gps.utils.Pair;

public class GogoleMaps extends JavaPlugin{
	
	@Override
	public void onEnable() {
		System.out.println("GogoleMaps started");
		
		Graph graph = new Graph();
		Node A = new Node(0, 0, 0);
		Node B = new Node(0, 5, 0);
		Node C = new Node(-5, 5, 0);
		Node D = new Node(-1, 8, 0);
		Node E = new Node(3, 8, 0);
		Node F = new Node(3, 5, 0);
		Node G = new Node(2, 3, 0);
		Node H = new Node(4, 4, 0);
		Node I = new Node(5, 7, 0);
		Node J = new Node(10, 7, 0);
		Node K = new Node(6, 0, 0);
		
		graph.setNodes(Arrays.asList(A, B, C, D, E, F, G, H, I, J, K));

		graph.setNeightbors(A, Arrays.asList(B, K));
		graph.setNeightbors(B, Arrays.asList(A, C));
		graph.setNeightbors(C, Arrays.asList(B, D));
		graph.setNeightbors(D, Arrays.asList(C, E));
		graph.setNeightbors(E, Arrays.asList(D, F));
		graph.setNeightbors(F, Arrays.asList(E, G));
		graph.setNeightbors(G, Arrays.asList(F, H));
		graph.setNeightbors(H, Arrays.asList(G, I));
		graph.setNeightbors(I, Arrays.asList(H, J));
		graph.setNeightbors(J, Arrays.asList(I, K));
		graph.setNeightbors(K, Arrays.asList(J, A));
		
		Pair result = Dijkstra.createPath(graph, A);
		@SuppressWarnings("unchecked")
		List<Node> path = Dijkstra.rebuildPath((HashMap<Node, Node>) result.b, H);
		Collections.reverse(path); // So list starts from start to end
		
		for(Node node : path) {
			System.out.println(node);
		}
		
	}
	
	@Override
	public void onDisable() {
		System.out.println("GogoleMaps stopped");
	}
}
