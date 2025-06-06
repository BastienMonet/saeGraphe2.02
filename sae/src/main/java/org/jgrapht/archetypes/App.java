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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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

import javax.tools.Tool;

/**
 * Hello JGraphT!
 */
public class App extends Application {

	TextField cast1;
	TextField cast2;
	Label result;
	Graph<String, DefaultEdge> graph;


	@Override
	public void init(){
		graph = new SimpleGraph<>(DefaultEdge.class);
		loadData(graph);
		cast1 = new TextField();
        cast2 = new TextField();
		result = new Label();

	}


	@Override
	public void start(Stage stage){

		Text welcome = new Text("Bienvenue dans l'appli jgrapht selectionner l'action de votre choix (certain choix nessesite de rentrer le nom des acteur à étudier)");

		welcome.setStyle("-fx-font-size: 18px; -fx-font-family: cursive;");
		welcome.setTextAlignment(TextAlignment.CENTER);	

		welcome.setWrappingWidth(250);

        HBox hb1 = new HBox(20);

        hb1.getChildren().addAll(this.cast1, this.cast2);
        hb1.setAlignment(Pos.CENTER);

        VBox hb2 = new VBox(20);

		Tooltip t1 = new Tooltip("compléter le 1er champs de texte avec l'acteur a étudier");
		Tooltip t2 = new Tooltip("compléter les 2 champs de texte avec les acteurs étudiés");

        Button btn1 = new Button("les voisins");
		Button btn2 = new Button("collaborateur commun");
		Button btn3 = new Button("distance entre acteurs");
		Button btn4 = new Button("acteur le plus éloigné");
		Button btn5 = new Button("acteur au centre");
		Button btn6 = new Button("distance moyen");

		Tooltip.install(btn1, t1);
		Tooltip.install(btn2, t2);
		Tooltip.install(btn3, t2);
		Tooltip.install(btn4, t1);
		Tooltip.install(btn6, t1);

		ControlleurBouton control = new ControlleurBouton(this, graph);
		btn1.setOnAction(control);
		btn2.setOnAction(control);
		btn3.setOnAction(control);
		btn4.setOnAction(control);
		btn5.setOnAction(control);
		btn6.setOnAction(control);

        hb2.getChildren().addAll(btn1, btn2, btn3, btn4, btn5, btn6);
        hb2.setAlignment(Pos.CENTER);

        TextArea setofcast = new TextArea();
        setofcast.setWrapText(true);

        setofcast.setText(graph.vertexSet().toString());


        VBox vb = new VBox(30);

        vb.getChildren().addAll(welcome, hb1, hb2, this.result, setofcast);
        vb.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();

        root.setCenter(vb);

		Scene scene = new Scene(root, 700, 700);
		
		
		stage.setScene(scene); 
		stage.setTitle("test jgrapht");
		stage.show();

	}


	public String getCast1() {
		return cast1.getText();
	}

	public String getCast2() {
		return cast2.getText();
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

		// Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

		// loadData(graph);


		Application.launch(args);

		// System.out.println(Fonction.DistanceMoyen(graph, "Harrison Ford"));
		

		// DOTExporter<String, DefaultEdge> exporter = new DOTExporter<String, DefaultEdge>();
		// exporter.setVertexAttributeProvider((x) -> Map.of("label", new DefaultAttribute<>(x, AttributeType.STRING)));
		// exporter.exportGraph(graph, new FileWriter("graph.dot"));
	}

		
	
}
