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
        String cast1 = app.getCast1();
        String cast2 = app.getCast2();
        switch (nom) {
            case ("les voisins"):
                if (g.vertexSet().contains(cast1)){
                    app.result.setText(Fonction.getNeighborsOf(g, cast1).toString());
                }
                break;
            case ("collaborateur commun"):
                if (g.vertexSet().contains(cast1) && g.vertexSet().contains(cast2)){
                    app.result.setText(Fonction.CollaborateursEnCommuns(g, cast1, cast2).toString());
                }
                break;
            case ("distance entre acteurs"):
                if (g.vertexSet().contains(cast1) && g.vertexSet().contains(cast2)){
                    Integer res1 = Fonction.DistanceEntreActeurs(g, cast1, cast2);
                    app.result.setText(res1.toString());
                }
                break;
            case ("acteur le plus éloigné"):
                if (g.vertexSet().contains(cast1)){
                    app.result.setText(Fonction.ActeurPlusEloigne(g, cast1).toString());
                }
                break;
            case ("acteur au centre"):
                app.result.setText(Fonction.ActeurAuCentre(g).toString());
                break;

            case ("distance moyen"):
                if (g.vertexSet().contains(cast1)){
                    Double res2 = Fonction.DistanceMoyen(g, cast1);
                    app.result.setText(res2.toString());
                }
                break;
            

        }
    }
    
}
