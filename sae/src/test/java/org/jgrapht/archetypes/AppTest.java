package org.jgrapht.archetypes;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
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

    @Test
    public void TesCollaborateurEnCommuns(){
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        getGraph(graph);

        Set<String> res1 = Set.of("c");
        Set<String> res2 = Set.of("c", "d");
        Set<String> res3 = Set.of();

        assertEquals(Fonction.CollaborateursEnCommuns(graph, "a", "b"),res1);
        assertEquals(Fonction.CollaborateursEnCommuns(graph, "b", "f"),res2);
        assertEquals(Fonction.CollaborateursEnCommuns(graph, "i", "e"),res3);
    }

    @Test
    public void CollaborateursProches(){
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        getGraph(graph);

        Set<String> res1 = Set.of("b", "c");
        Set<String> res2 = Set.of("f", "d");
        Set<String> res3 = Set.of("h", "i");

        assertEquals(Fonction.CollaborateursProches(graph, "a", 1),res1);
        assertEquals(Fonction.CollaborateursProches(graph, "i", 2),res2);
        assertEquals(Fonction.CollaborateursProches(graph, "b", 3),res3);
    }

    @Test
    public void TestDistanceEntreActeurs(){
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        getGraph(graph);

        assertEquals(Fonction.DistanceEntreActeurs(graph, "a", "b"),1);
        assertEquals(Fonction.DistanceEntreActeurs(graph, "a", "d"),2);
        assertEquals(Fonction.DistanceEntreActeurs(graph, "a", "i"),4);
    }

    @Test
    public void TestActeurPlusEloigne(){
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        getGraph(graph);

        Set<String> res1 = Set.of("i", "h");
        Set<String> res2 = Set.of("a");
        Set<String> res3 = Set.of("i", "h");

        assertEquals(Fonction.ActeurPlusEloigne(graph, "a"),res1);
        assertEquals(Fonction.ActeurPlusEloigne(graph, "i"),res2);
        assertEquals(Fonction.ActeurPlusEloigne(graph, "e"),res3);
    }


    @Test
    public void TestDistanceMax(){
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        getGraph(graph);

        assertEquals(Fonction.DistanceMax(graph, "c"),3);
        assertEquals(Fonction.DistanceMax(graph, "a"),4);
        assertEquals(Fonction.DistanceMax(graph, "i"),4);
    }

    @Test
    public void TestActeurAuCentre(){
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        getGraph(graph);

        Set<String> res1 = Set.of("d", "f");

        assertEquals(Fonction.ActeurAuCentre(graph),res1);
    }

    @Test
    public void TestPetiteFamille(){
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        getGraph(graph);

        int res = Fonction.PetiteFamille(graph);

        assertEquals(res,4);
    }

    @Test
    public void TestDistanceMoyen(){
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        getGraph(graph);

        double res = Fonction.DistanceMoyen(graph, "i");

        assertEquals(res, 2.375, 0.1);
    }

    @Test
    public void TestTrieParDegree(){
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        getGraph(graph);

        List<String> res1 = Arrays.asList("b", "d", "f", "g", "c", "a", "e", "h", "i");

        assertEquals(Fonction.TrieParDegree(graph),res1);
    }


}
