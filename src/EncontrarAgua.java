/**
 * Programa que, dado una lista de casos de un campus, un edificio de partida, y
 * un número de personas en ese edificio, le asigne el baño a todas el baño más
 * cercano disponible en todos los casos.
 */
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.lang.NumberFormatException;

public class EncontrarAgua {

	/**
	 * Grafo inicial tal cual fue importado sin tomar en cuenta las
	 * modificaciones introducidas por cada caso.
	 */
	private static GrafoNoDirigido<Integer, Integer> baseGraph = 
		new GrafoNoDirigido<Integer, Integer>();
	/**
	 * Diccionario donde cada clave corresponde al identificador de un caso y
	 * cada valor corresponde a un grafo modificado acorde al caso con el
	 * identificador de la clave.
	 */
	private static LinkedHashMap<String, GrafoNoDirigido<Integer, Integer>> caseBasedGraphs;
	/**
	 * Lista de casos importados.
	 */
	private static ArrayList<Case> cases = new ArrayList();
	/**
	 * Id del vértice de partida.
	 */
	private static String origin;
	/**
	 * Cantidad de personas a las que se les debe asignar baño.
	 */
	private static int numOfPeople;



	/**
	 * Importa el grafo suministrado inicializando a todos los vértices con el
	 * atributo hasWater en false e inicializando las distancias de cada lado
	 * con las distancias entre los edificios sin considerar la distancia extra
	 * necesaria para llegar a los baños disponibles en dichos edificios.
	 */
	private static void importGraphFrom(String filename) {
		baseGraph.cargarGrafo(baseGraph, filename);
	}
	
	/**
	 * Importa todos los casos planteados en el documento suministrado (crea una
	 * nueva instancia de Case por cada caso y la almacena en la propiedad cases).
	 */
	private static void importCasesFrom(String filename) {

	}
	
	/**
	 * Elimina las aristas afectadas por las lluvias.
	 */
	private static void deleteAffectedEdges() {

	}
	
	/**
	 * Actualiza los valores del peso en los vértices del grafo conforme a las
	 * modificaciones planteadas en el caso.
	 */
	private static void updateVertexWeights() {

	}
	
	/**
	 * Actualiza las distancias en las aristas para reflejar la distancia exacta
	 * entre los baños disponibles en los edificios.
	 */
	private static void updateEdgeWeights() {

	}
	
	/**
	 * Actualiza el valor hasWater en los vértices del grafo conforme a lo
	 * planteado en el caso.
	 */
	private static void updateWaterAvailability() {

	}
	
	/**
	 * Modifica los datos del grafo importado acorde con el caso planteado.
	 */
	private static void updateGraphAccordingToCase(Case c) {
		/*
		deleteAffectedEdges()
		updateVertexWeights()
		updateEdgeWeights()
		updateWaterAvailability()
		*/
	}
	
	/**
	 * Crea un grafo modificado por cada caso introducido y lo almacena en
	 * caseBasedGraphs.
	 */
	private static void createGraphsAccordingToCases() {
		/*
		Crear un grafo por cada caso y almacenar en caseBasedGraphs
		Por cada nuevo grafo en caseBasedGraphs:
			updateGraphAccordingToCase(Case case)
		*/
	}
	
	/**
	 * Aplica el algoritmo de Bellman-Ford al grafo usando como vértice de
	 * partida a origin.
	 */
	private static void applyBellmanFordTo(
		GrafoNoDirigido<Integer, Integer> graph,
		String origin
	) {

	}
	
	/**
	 * Retorna una lista con los caminos más cortos hallados luego de aplicar
	 * el algoritmo de Bellman-Ford al grafo usando como vértice de partida a
	 * origin.
	 */
	private static ArrayList<ArrayList<String>> getShortestPathsFor(
		GrafoNoDirigido<Integer, Integer> graph,
		String origin
	) {
		return new ArrayList();
	}
	
	/**
	 * Distribuye a las personas en vertexId a los baños más cercanos.
	 */
	private static void distributePeopleFrom(String vertexId) {
		/*
		Por cada Grafo en caseBasedGraphs:
		====== Imprimir el caseId ======
		caseId ← la clave del grafo basado en caso que se está evaluando
		shortestPaths ← getShortestPathsFor(graph, origin)
		availablePaths ← |shortestPaths|
		remainingPeople ← toda la gente del principio
		Mientras availablePaths > 0 y remainingPeople > 0
		Escoger el camino más corto y enviar tantas personas como número de personas puedan pasar por la arista con menor capacidad en el camino (modificar el atributo peopleSent de este canino)
		====== printResultsFor(<el ShortestPath en cuestión>)  ======
		updateCaseGraph()
		Restar peopleSent de la capacidad de cada uno de los lados del ShortestPath en cuestión y en caso de que esta resta de 0 eliminar la arista en el grafo
		Restar peopleSent de la capacidad del edificio representado por el último vértice del ShortestPath en cuestión
		shortestPaths ← getShortestPathsFor(graph, origin)  ─basándose en el grafo actualizado─
		availablePaths ← |shortestPaths|
		Restar el número de personas asignadas de remainingPeople
		====== Imprimir remainingPeople ======
		printResultsFor(ShortestPath path) - Imprime una línea de la forma “N personas a Z Ruta: X - Y - Z (M m)”
		*/
	}

	/**
	 * Imprime una línea de la forma “N personas a Z Ruta: X - Y - Z (M m)”
	 */
	public static void printResultsFor(ShortestPath path) {

	}

	/**
	 * Rutina principal
	 */
	public static void main(String[] args) {

		if (args.length != 4) {
			System.out.println(
				"Debe introducir un comando de la forma:\n" +
				"	EncontrarAgua <grafo.txt> <casos.txt> <edif> <numDePersonas>"
			);
			System.exit(0);
		}

		try {
			numOfPeople = Integer.parseInt(args[3]);
		}
		catch(NumberFormatException e) {
			System.out.println("Número de personas incorrecto");
			System.exit(0);
		}

		importGraphFrom(args[0]);
		importCasesFrom(args[1]);
		createGraphsAccordingToCases();
		distributePeopleFrom(args[2]);
	}
}
