package org.jgrapht.archetypes;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.util.SupplierUtil;
import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */

    public static void getGraph(Graph<String, DefaultEdge> graph){
        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");
        graph.addVertex("d");
        graph.addVertex("e");
        graph.addVertex("f");
        graph.addVertex("g");
        graph.addVertex("h");
        graph.addVertex("i");

        graph.addEdge("a", "b");
        graph.addEdge("a", "c");
        graph.addEdge("b", "c");
        graph.addEdge("b", "d");
        graph.addEdge("b", "e");
        graph.addEdge("d", "e");
        graph.addEdge("b", "f");
        graph.addEdge("c", "f");
        graph.addEdge("g", "h");
        graph.addEdge("g", "i");
        graph.addEdge("h", "i");
        graph.addEdge("d", "f");
        graph.addEdge("d", "g");
        graph.addEdge("f", "g");
    }


    @Test
    public void TestNeighbors()
    {
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        getGraph(graph);

        Set<String> res1 = Set.of("b", "c");
        Set<String> res2 = Set.of("a", "d", "c", "e","f");
        Set<String> res3 = Set.of("h", "g");

        assertEquals(Fonction.getNeighborsOf(graph, "a"),res1);
        assertEquals(Fonction.getNeighborsOf(graph, "b"),res2);
        assertEquals(Fonction.getNeighborsOf(graph, "i"),res3);

        
        
      




      

    }

}
