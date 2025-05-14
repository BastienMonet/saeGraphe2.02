package org.jgrapht.archetypes;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class View {


    public Scene ViewGraph(App app){


        VBox vb = new VBox();

        BorderPane root = new BorderPane();

		Scene scene = new Scene(root, 500, 500);

        return scene;

        
    }
}
