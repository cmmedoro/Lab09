package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private BordersDAO dao;
	private List<Country> allCountries;
	private List<Border> countrypairs;
	private Graph<Country, DefaultEdge> grafo;
	private Map<Integer, Country> countryIdMap;

	public Model() {
		this.dao = new BordersDAO();
		this.countrypairs = new ArrayList<Border>();
	}

	public List<Country> getAllCountries(){
		return this.allCountries;
	}
	
	//grafo da costruire a partire da un certo anno
	public void creaGrafo(int anno) {
		this.countryIdMap = new HashMap<Integer, Country>();
		this.allCountries = this.dao.loadAllCountries(countryIdMap);
		
		this.countrypairs = this.dao.getCountryPairs(anno, countryIdMap);
		//creo struttura dati del grafo: non orientato, non pesato
		this.grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		//carico gli stati come vertici del grafo: solo quelli fra cui esiste un confine nell'arco di tempo
		for(Border b : countrypairs) {
			this.grafo.addVertex(b.getS1());
			this.grafo.addVertex(b.getS2());
			this.grafo.addEdge(b.getS1(), b.getS2());
		}
	}
	
	//grado dei vertici del grafo
	public Map<Country, Integer> getVertexGrade() {
		if(this.grafo == null) {
			throw new RuntimeException("Grafo non esistente");
		}
		Map<Country, Integer> gradiVertici = new HashMap<>();
		for(Country cc : this.grafo.vertexSet()) {
			gradiVertici.put(cc, this.grafo.degreeOf(cc));
		}
		return gradiVertici;
	}
	
	//numero componenti connesse del grafo
	public int getNumberConnectedComponents() {
		if(this.grafo == null) {
			throw new RuntimeException("Grafo non esistente");
		}
		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<Country, DefaultEdge>(this.grafo);
		return ci.connectedSets().size();
	}
	
	//visita del grafo
	public List<Country> getStatiRaggiungibili(Country partenza){
		List<Country> reachable = new ArrayList<>();
		if(!this.grafo.vertexSet().contains(partenza)) {
			throw new RuntimeException("La nazione selezionata non è presente nel grafo!");
		}
		reachable = this.visitaJGraphT(partenza);
		System.out.println("Stati raggiungibili: "+reachable.size());
		reachable = this.visitaIterativa(partenza);
		System.out.println("Stati raggiungibili(versione iterativa): "+reachable.size());
		reachable = this.visitaRecursiva(partenza);
		System.out.println("Stati raggiungibili(versione recursiva): "+reachable.size());
		return reachable;
	}
	
	//visita del grafo con JGraphT
	private List<Country> visitaJGraphT(Country partenza){
		List<Country> visitati = new ArrayList<Country>();
		//uso di BreadthFirstIterator
		/*GraphIterator<Country, DefaultEdge> visita = new BreadthFirstIterator<>(this.grafo, partenza);
		while(visita.hasNext()) {
			visitati.add(visita.next());
		}*/
		//uso di DepthFirstIterator
		GraphIterator<Country, DefaultEdge> visitaD = new DepthFirstIterator<>(this.grafo, partenza);
		while(visitaD.hasNext()) {
			visitati.add(visitaD.next());
		}
		return visitati;
	}
	
	//algoritmo ricorsivo per la visita in profondità
	private List<Country> visitaRecursiva(Country partenza){
		List<Country> sol = new ArrayList<>();
		ricorsione(partenza, sol);
		return sol;
	}
	private void ricorsione(Country partenza, List<Country> parziale) {
		parziale.add(partenza);
		for(Country cc : Graphs.neighborListOf(this.grafo, partenza)) {
			if(!parziale.contains(cc)) {
				//Se non lo contiene aggiungo e mando avanti la ricorsione a partire da questo nodo
				//parziale.add(cc); ---> NON AGGIUNGERE perchè lo fai all'inizio della ricorsione
				ricorsione(cc, parziale);
			}
		}
		
	}
	
	//algoritmo iterativo
	private List<Country> visitaIterativa(Country partenza){
		List<Country> visitati = new ArrayList<>();
		List<Country> daVisitare = new ArrayList<>();
		//inserisco lo stato scelto nella lista di quelli da visitare
		daVisitare.add(partenza);
		
		//ad ogni passo si estrae nodo da daVisitare e si inseriscono tutti i nodi vicini (non visitati) in daVisitare
		//nodo estratto in Visitati
		while(!daVisitare.isEmpty()) {
			//partiamo dal vertice in testa alla coda
			Country visitato = daVisitare.remove(0);
			//Aggiungilo fra i visitati
			visitati.add(visitato);
			List<Country> vicini = Graphs.neighborListOf(this.grafo, visitato);
			for(Country v : vicini) {
				if(!daVisitare.contains(v) && !visitati.contains(v)) {
					daVisitare.add(v);
				}
			}
		}
		return visitati;
	}
}
