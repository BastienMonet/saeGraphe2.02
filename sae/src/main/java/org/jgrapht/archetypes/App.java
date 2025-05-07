package org.jgrapht.archetypes;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.util.SupplierUtil;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Hello JGraphT!
 */
public class App {


	public static Set<String> nettoyer(List<String> lst){
		/*
		 * @param : la liste de nom à nettoyer
		 * @return : la liste nettoyer (sans [] ou " supplementaire)
		 */
		Set<String> cp = new HashSet<>();
		for(String s : lst){
			cp.add(s.replaceAll("[\\[\\]\"]",""));
		}
		return cp;
	}


	public static void ajouterAuGraph(Graph<String, DefaultEdge> g, Set<String> set) {
		for (String v : set) {
			g.addVertex(v);
		}

		for (String v1 : set) {
			for (String v2 : set){
				g.addEdge(v1, v2);
			}
		}
	}
	
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
		

		try {
			Scanner scanner = new Scanner(new File("datamini.txt"));
	
			String res = "";
			int cpt = 1;

			while (scanner.hasNextLine()) {
				res = scanner.nextLine();
				
				Gson gson = new Gson();

				// Parse the string into a JsonObject
				JsonObject jsonObject = gson.fromJson(res, JsonObject.class);

				// Get the array associated with the "key"
				JsonArray jsonArray = jsonObject.getAsJsonArray("cast");

				List<String> caster = new ArrayList<>(); 
				
				for (JsonElement element : jsonArray) {
                	caster.add(element.getAsString());
            	}

				cpt++;

				System.out.println(nettoyer(caster));
				System.out.println(cpt);

				Set<String> castPropre = nettoyer(caster);
				ajouterAuGraph(graph,castPropre);
			}
			
	
			scanner.close();
			} catch (FileNotFoundException e) {
			e.printStackTrace();
			}
	}
	
}
