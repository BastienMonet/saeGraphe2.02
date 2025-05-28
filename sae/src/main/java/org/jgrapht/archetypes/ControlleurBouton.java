package org.jgrapht.archetypes;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ControlleurBouton implements EventHandler<ActionEvent> {


    private App app;
    Graph<String, DefaultEdge> g;


    public ControlleurBouton(App app, Graph<String, DefaultEdge> graph) {
        this.app = app;
        this.g = graph;
    }

    @Override
    public void handle(ActionEvent arg0) {
        Button btn = (Button) arg0.getSource();
        String nom = btn.getText();
        switch (nom) {
            case ("f°1"):
                String cast = app.getCast1();
                System.out.println(cast);
                if (cast != "" && g.vertexSet().contains(cast)){
                    app.result.setText(Fonction.getNeighborsOf(g, cast).toString());
                }
                break;

        }
    }
    
}
