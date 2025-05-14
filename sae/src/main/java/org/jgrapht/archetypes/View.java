package org.jgrapht.archetypes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class View {


    public Scene ViewGraph(App app){

        Label welcome = new Label("Bienvenue dans l'appli jgrapht selectionner l'action de votre choix (certain choix nessesite de rentrer le nom d'acteur à étudier)");

        HBox hb1 = new HBox(20);

        TextField cast1 = new TextField();

        TextField cast2 = new TextField();

        hb1.getChildren().addAll(cast1, cast2);
        hb1.setAlignment(Pos.CENTER);

        HBox hb2 = new HBox(20);

        Button btn1 = new Button("test");

        hb2.getChildren().addAll(btn1);
        hb2.setAlignment(Pos.CENTER);

        Label result = new Label();

        TextArea setofcast = new TextArea();


        VBox vb = new VBox(30);

        vb.getChildren().addAll(welcome, hb1, hb2, result, setofcast);
        vb.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();

        root.setCenter(vb);

		Scene scene = new Scene(root, 500, 500);

        return scene;

        
    }
}
