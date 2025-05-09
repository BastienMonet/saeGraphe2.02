package org.jgrapht.archetypes;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public class Fonction {


    public static Set<String> getNeighborsOf(Graph<String, DefaultEdge> g, String u){
		Set<String> neighbors= new HashSet<>();
		for(DefaultEdge edge : g.edgesOf(u)){
			String source = g.getEdgeSource(edge);
			String target = g.getEdgeTarget(edge);
			if (!source.equals(u)){
				neighbors.add(source);
			} else {
				neighbors.add(target);
			}
		}
		return neighbors;
	}


    public static Set<String> CollaborateursEnCommuns(Graph<String, DefaultEdge> g , String acteur1, String acteur2){
        Set<String> listAct1 = getNeighborsOf(g, acteur1);
		Set<String> listAct2 = getNeighborsOf(g, acteur2);
        listAct1.retainAll(listAct2);
		return listAct1;
	}
    
}
