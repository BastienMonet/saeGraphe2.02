package org.jgrapht.archetypes;

import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public class Fonction {


    public static Set<String> getNeighborsOf(Graph<String, DefaultEdge> g, String u){
		/*
		 * @param : le graph
		 * @param : un sommet u
		 * @return : les voisins de u
		 * complexité : O(n) avec n = nbre d'arrête
		 */
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

	public static Set<String> getNeighborsNonMemoire(Graph<String, DefaultEdge> g, String u, Set<String> memoire){
		/*
		 * @param : le graph
		 * @param : un sommet u
		 * @param : memoire
		 * @return : les voisins de u privé de memoire
		 * complexité : O(n) avec n = nbre d'arrête
		 */
		Set<String> neighbors= new HashSet<>();
		for(DefaultEdge edge : g.edgesOf(u)){
			String source = g.getEdgeSource(edge);
			String target = g.getEdgeTarget(edge);
			if (!source.equals(u) && !(memoire.contains(source))){ // access direct 
				neighbors.add(source);
			} else if (source.equals(u) && !(memoire.contains(target))) {
				neighbors.add(target);
			}
		}
		return neighbors;
	}

    public static Set<String> CollaborateursEnCommuns(Graph<String, DefaultEdge> g , String acteur1, String acteur2){
		/*/
		@param : le graph ; 2 acteurs du graph
		@return : intersection entre les voisins des 2 acteurs
		complexite : O(N²)
		*/
        Set<String> listAct1 = getNeighborsOf(g, acteur1);
		Set<String> listAct2 = getNeighborsOf(g, acteur2);
        listAct1.retainAll(listAct2);
		return listAct1;
	}


	public static Set<String> CollaborateursProches(Graph<String, DefaultEdge> g , String acteur, int k){
		/*
		 * @param : le graph
		 * @param : un sommet
		 * @param : la distance souhaiter
		 * @return : les voisin situer à au plus k
		 */
		Set<String> prev = new HashSet<>();
		prev.add(acteur);
		Set<String> new_ = new HashSet<>();
		Set<String> memoire = new HashSet<>();
		int i = 0;
		while (i<k) {
			i++;
			new_.clear();
			for (String u : prev){
				memoire.addAll(prev);
				new_.addAll(getNeighborsNonMemoire(g, u, memoire));
			}
			prev = new HashSet<>(new_);
			
		}
		return new_;
	}

	public static Set<String> CollaborateursProchesRecursif(Graph<String, DefaultEdge> g , Set<String> prev){
		Set<String> new_ = new HashSet<>();
		new_.clear();
		for (String u : prev){
			new_.addAll(getNeighborsOf(g, u));
		}	
		return new_;
	}


	public static boolean EstADistanceK(Graph<String, DefaultEdge> g , String acteur1, String acteur2, int k){
		List<String> lst = new ArrayList<>(CollaborateursProches(g, acteur1, k));
		return lst.contains(acteur2);
	}

	public static int DistanceEntreActeurs(Graph<String, DefaultEdge> g , String acteur1, String acteur2){
		/*
		 * complexité : O(N²)
		 */
		if (!(g.containsVertex(acteur1)) || !(g.containsVertex(acteur2)))
			return -1;
		Set<String> prev = new HashSet<>();
		prev.add(acteur1);
		boolean fini = false;
		int k = 0;
		while (!(fini)){
			k++;
			Set<String> new_ = CollaborateursProchesRecursif(g, prev);
			if (new_.contains(acteur2)){
				fini = true;
			} else if (prev.equals(new_)){
				return -1;
			}
			System.out.println(prev);
			System.out.println(new_);
			prev = new HashSet<>(new_);

		}
		return k;
	}



	public static Set<String> ActeurPlusEloigne(Graph<String, DefaultEdge> g, String acteur){
		if (!(g.containsVertex(acteur)))
			return null;
		Set<String> memoire = new HashSet<>();
		Set<String> prev = new HashSet<>();
		prev.add(acteur);
		Set<String> new_ = new HashSet<>();
		while (true){
			new_ = CollaborateursProchesRecursif(g, prev);
			if (prev.equals(new_)){
				prev.removeAll(memoire);
				return prev;
			}
			memoire.addAll(prev);
			prev = new HashSet<>(new_);
		}
	}


	public static int DistanceMax(Graph<String, DefaultEdge> g, String acteur){
		if (!(g.containsVertex(acteur)))
			return -1;
		Set<String> prev = new HashSet<>();
		prev.add(acteur);
		Set<String> new_ = new HashSet<>();
		Set<String> memoire = new HashSet<>();
		int k = -2; 
		while (true){
			k++;
			memoire.addAll(prev);
			new_ = CollaborateursProchesRecursif(g, prev);
			new_.removeAll(memoire);
			if (prev.equals(new_)){
				return k;
			}
			prev = new HashSet<>(new_);
		}
	}
	
	public static Set<String> ActeurAuCentre(Graph<String, DefaultEdge> g){
		/*
		 *  ! ne marche que pour un graphe connexe
		 */
		Set<String> lesActeurs = g.vertexSet();
		Integer centralMin = null;
		Set<String> acteurCentralMin = new HashSet<>();
		int cpt = 0;
		for (String cast : lesActeurs){
			cpt++;
			System.out.println(cpt);
			int centralite = DistanceMax(g, cast);
			if (centralMin == null || centralite < centralMin){
				centralMin = centralite;
				acteurCentralMin.clear();
				acteurCentralMin.add(cast);
			} else if (centralite == centralMin){
				acteurCentralMin.add(cast);
			}

		}  
		return acteurCentralMin;

	}

	public static Set<String> BFS(Graph<String, DefaultEdge> g , String acteur){
		/*
		 * BFS : parcours en largeur
		 * complexite : O(N²)
		 */ 
		Deque<String> d = new ArrayDeque<>();
		d.addLast(acteur);

		Set<String> res = new HashSet<>(); 

		while (! (d.isEmpty())){
			String u = d.removeFirst();
			res.add(u);
			System.out.println(d);
			for (String v : getNeighborsOf(g, u)){
				if (!(res.contains(v)))
						d.addLast(v);
						
				}
			}
			
		return res;
	}
    
}
