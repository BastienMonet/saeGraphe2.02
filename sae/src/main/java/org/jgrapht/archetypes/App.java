package org.jgrapht.archetypes;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.util.SupplierUtil;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Hello JGraphT!
 */
public class App {
	
	public static void main(String[] args) {
		
		Graph<String, DefaultEdge> graph = GraphTypeBuilder
				.directed()
				.allowingMultipleEdges(true)
				.allowingSelfLoops(true)
				.vertexSupplier(SupplierUtil.createStringSupplier())
				.edgeSupplier(SupplierUtil.createDefaultEdgeSupplier())
				.buildGraph();

		String v0 = graph.addVertex();
		String v1 = graph.addVertex();
		String v2 = graph.addVertex();

		graph.addEdge(v0, v1);
		graph.addEdge(v1, v2);
		graph.addEdge(v0, v2);

		for (String v : graph.vertexSet()) {
			System.out.println("vertex: " + v);
		}

		for (DefaultEdge e : graph.edgeSet()) {
			System.out.println("edge: " + graph.getEdgeSource(e));
		}
		
		String res = "";
		try {
			Scanner scanner = new Scanner(new File("datamini.txt"));
	
			while (scanner.hasNextLine()) {
				res += scanner.nextLine();
			}
	
			scanner.close();
			} catch (FileNotFoundException e) {
			e.printStackTrace();
			}
	}
	
}
