package org.jgrapht.archetypes;

import java.util.Comparator;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public class ComparatorDegree implements Comparator<String> {

    private Graph<String, DefaultEdge> g;

    public ComparatorDegree(Graph<String, DefaultEdge> g){
        this.g = g;
    }

    @Override
    public int compare(String arg0, String arg1) {
        return Integer.compare(g.degreeOf(arg1), g.degreeOf(arg0));
    }
    
}
