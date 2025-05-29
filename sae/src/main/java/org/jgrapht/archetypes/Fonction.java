package org.jgrapht.archetypes;

import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public class Fonction {

	private Fonction() {};

	public static boolean isNumber(String str) {
    if (str == null || str.isEmpty()) {
        return false;
    }
    try {
        Double.parseDouble(str);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
}

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
		 * complexite : O(k * n) avec k la distance mise en paramètre et n le nombre d'arrète dans le graphe 
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
		/*
		 * @param : le graph
		 * @param : un ensemble de sommet
		 * @return : les voisins situer à au plus k
		 * * complexite : O(n) avec n le nombre d'arrète dans le graphe 
		 */
		Set<String> new_ = new HashSet<>();
		new_.clear();
		for (String u : prev){
			new_.addAll(getNeighborsOf(g, u));
		}	
		return new_;
	}


	public static boolean EstADistanceK(Graph<String, DefaultEdge> g , String acteur1, String acteur2, int k){
		/*
		 * @param : le graph
		 * @param : un acteur 1
		 * @param : un acteur 2
		 * @return : les voisin situer à au plus k
		 * * complexite : celle de collaborateur proche soit O(n²)
		 */
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
			// System.out.println(prev);
			// System.out.println(new_);
			prev = new HashSet<>(new_);

		}
		return k;
	}



	public static Set<String> ActeurPlusEloigne(Graph<String, DefaultEdge> g, String acteur){
		// * * complexite : O(n²)
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
		// * * complexite : O(n²)
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
			new_.clear();
			for (String u : prev){
				new_.addAll(getNeighborsNonMemoire(g, u, memoire));
			}	
			if (prev.equals(new_)){
				return k;
			}
			prev = new HashSet<>(new_);
		}
	}
	
	public static Set<String> ActeurAuCentre(Graph<String, DefaultEdge> g){
		/*
		 *  ! ne marche que pour un graphe connexe
		 * 		     complexite : O(n^3)
		 */
		List<String> lesActeurs = TrieParDegree(g);
		if (lesActeurs.size() > 1000){
			lesActeurs.subList(0, 10);
		}
			// .stream()
			// .sorted((v1, v2) -> Integer.compare(g.degreeOf(v2), g.degreeOf(v1)))
			// .collect(Collectors.toList());
		Integer centralMin = null;
		Set<String> acteurCentralMin = new HashSet<>();
		int cpt = 0;
		for (String cast : lesActeurs){
			cpt++;
			// System.out.println(cpt);
			int centralite = DistanceMax(g, cast);
			if (centralMin == null || centralite < centralMin){
				centralMin = centralite;
				acteurCentralMin.clear();
				acteurCentralMin.add(cast);
				// System.out.println(acteurCentralMin);
			} else if (centralite == centralMin){
				acteurCentralMin.add(cast);
			}

		}  
		return acteurCentralMin;
	}


	public static Integer PetiteFamille(Graph<String, DefaultEdge> g){
		/*
		 *  ! ne marche que pour un graphe connexe
		 * =======================================
		 *        oui le max est bien a 9
		 * =======================================
		 * 			complexite : O(n^3)
		 */
		List<String> lesActeurs = TrieParDegree(g);
		if (lesActeurs.size() > 1000){
			lesActeurs.subList(0, 10);
		}
		Integer centralMax = null;
		int cpt = 0;
		for (String cast : lesActeurs){
			cpt++;
			System.out.println(cpt);
			int centralite = DistanceMax(g, cast);
			if (centralMax == null || centralite > centralMax){
				centralMax = centralite;
				// System.out.println(acteurCentralMin);
			}
		}  
		return centralMax;
	}


	public static double DistanceMoyen(Graph<String, DefaultEdge> g, String acteur){
		// complexite : O(n^3)
		double centraliteTot = 0.0;
		Set<String> lstActeur = new HashSet<>(g.vertexSet());
		lstActeur.remove(acteur);
		for (String cast : lstActeur){
			// System.out.println(DistanceEntreActeurs(g, cast, acteur));
			 centraliteTot += DistanceEntreActeurs(g, cast, acteur);
		} 
		if (centraliteTot != 0){
			return centraliteTot / (g.vertexSet().size()-1);
		} else {
			return 0;
		}
	}

	public static List<String> TrieParDegree(Graph<String, DefaultEdge> g){
		Set<String> set = g.vertexSet();
		List<String> nodes = new ArrayList<>(set);
		Collections.sort(nodes, new ComparatorDegree(g));
		return nodes;

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
