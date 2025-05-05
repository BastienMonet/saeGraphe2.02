package org.jgrapht.archetypes;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.util.SupplierUtil;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello JGraphT!
 */
public class App {


	public static List<String> listerLesAuteurs(String line){
		/*
		 * @param : la ligne étudier 
		 * @return : la liste des auteurs du film
		 * 
		 */

		List<String> rep = new ArrayList<>(); 

		int i=0;
		while (line.charAt(i) != 'c'|| line.charAt(i+1) != 'a'|| line.charAt(i+2) != 's' || line.charAt(i+3) != 't' || line.charAt(i+5) != ':')
		{
			i++;
		}

		String acteur = "";
		int j = i+9;
		while (line.charAt(j) != 'd'|| line.charAt(j+1) != 'i'|| line.charAt(j+2) != 'r' || line.charAt(j+3) != 'e')
		{
			if (line.charAt(j) == ','){
				rep.add(acteur);
				acteur = "";
			} else {
				acteur += line.charAt(j);
			}
			j++;
		}
		return rep;
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

			while (scanner.hasNextLine()) {
				res = scanner.nextLine();
			}

			System.out.println(listerLesAuteurs(res));
	
			scanner.close();
			} catch (FileNotFoundException e) {
			e.printStackTrace();
			}
	}
	
}
