package org.jgrapht.archetypes;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.nio.AttributeType;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.csv.CSVFormat;
import org.jgrapht.nio.csv.CSVImporter;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.util.SupplierUtil;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Hello JGraphT!
 */
public class App extends Application {

	private Stage stage;


	@Override
	public void start(Stage stage){

		BorderPane root = new BorderPane();

		Scene scene = new Scene(root);

		View vue = new View();

		stage.setScene(vue.ViewGraph(this));
		stage.setTitle("test jgrapht");
		stage.show();

	}

	public static void loadData(Graph<String, DefaultEdge> graph){
		try {
			Scanner scanner = new Scanner(new File("datamicro.txt"));
	
			String res = "";
			int cpt = 1;

			while (scanner.hasNextLine()) {
				res = scanner.nextLine();
				
				Gson gson = new Gson();

				JsonObject jsonObject = gson.fromJson(res, JsonObject.class);

				JsonArray jsonArray = jsonObject.getAsJsonArray("cast");

				List<String> caster = new ArrayList<>(); 
				
				for (JsonElement element : jsonArray) {
                	caster.add(element.getAsString());
            	}


				// System.out.println(cpt);
				// System.out.println(nettoyer(caster));

				// cpt++;
				

				Set<String> castPropre = nettoyer(caster);
				ajouterAuGraph(graph,castPropre);
			}
			
	
			scanner.close();
			} catch (FileNotFoundException e) {
			e.printStackTrace();
			}


		// System.out.println(graph.vertexSet().size());

	}

	
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
			if (! (g.containsVertex(v)))
				g.addVertex(v);
		}

		for (String v1 : set) {
			for (String v2 : set){
				if(! (g.containsEdge(v1, v2) || g.containsEdge(v2, v1) ) && v1 != v2)
					g.addEdge(v1, v2);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {

		Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

		loadData(graph);

		System.out.println(Fonction.DistanceMoyen(graph, "i"));



		// Application.launch(args);
		

		// DOTExporter<String, DefaultEdge> exporter = new DOTExporter<String, DefaultEdge>();
		// exporter.setVertexAttributeProvider((x) -> Map.of("label", new DefaultAttribute<>(x, AttributeType.STRING)));
		// exporter.exportGraph(graph, new FileWriter("graph.dot"));
	}

		
	
}
