package org.jgrapht.archetypes;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.util.SupplierUtil;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void rigorousTest()
    {
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        
      try {
        Scanner scanner = new Scanner(new File("datamicro.txt"));
    
        String res = "";
        int cpt = 1;

          while (scanner.hasNextLine()) {
            res = scanner.nextLine();
            
            Gson gson = new Gson();

            // Parse the string into a JsonObject
            JsonObject jsonObject = gson.fromJson(res, JsonObject.class);

            // Get the array associated with the "key"
            JsonArray jsonArray = jsonObject.getAsJsonArray("cast");

            List<String> caster = new ArrayList<>(); 
            
            for (JsonElement element : jsonArray) {
                      caster.add(element.getAsString());
                  }


            System.out.println(cpt);
            System.out.println(App.nettoyer(caster));

            cpt++;
            

            Set<String> castPropre = App.nettoyer(caster);
            App.ajouterAuGraph(graph,castPropre);
          }

      } catch (FileNotFoundException e) {
			e.printStackTrace();
			}




      

    }

}
