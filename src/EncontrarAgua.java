/**
 * Programa que, dado una lista de casos de un campus, un edificio de partida, y
 * un número de personas en ese edificio, le asigne el baño a todas el baño más
 * cercano disponible en todos los casos.
 */
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.lang.NumberFormatException;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.io.IOException;
import java.util.Stack;

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
	private static LinkedHashMap<String, GrafoNoDirigido<Integer, Integer>> 
		caseBasedGraphs = new LinkedHashMap();
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
		try {
			if (!Utilidades.isValidPath(filename)) {
				throw new FileNotFoundException();
			}
			else if (!Utilidades.documentHasValidFormat(filename, "cases")) {
				throw new ParseException("", 0);
			}
		}
		catch(FileNotFoundException e) {
			System.out.println("No fue posible importar el archivo, verifique que el nombre es el correcto");
		}
		catch(ParseException e) {
			System.out.println("El documento que contiene a los CASOS no tiene el formato correcto");
		}


		// Importar archivo
		List<String> lines = null;
		try {
			lines = Files.readAllLines(
				Paths.get(filename),
				Charset.defaultCharset()
			);
		}
		catch(IOException e) {
			e.printStackTrace();
		}


		// Calcular número de casos
		int numOfCases = 0;
		String prevLine;
		for (int i = 0; i < lines.size(); i++) {
			if (i > 0) {
				prevLine = lines.get(i-1);
			}
			if (lines.get(i).trim().equals("")) {
				if (lines.get(i-1).trim().equals("")) {
					break;
				}
				else {
					numOfCases++;
				}
			}
		}


		int offset = 0;
		for (int j = 0; j < numOfCases; j++) {

			int e  = Integer.parseInt(lines.get(offset + 1)); // num de vertexWeightUpdates
			int a  = Integer.parseInt(lines.get(offset + 2)); // num de unavailableEdges
			int v1 = offset + 3;
			int ve = offset + 2 + e;
			int l1 = offset + 2 + e + 1;
			int la = offset + 2 + e + a;


			String id = lines.get(offset);
			ArrayList<String> verticesWithWater = new ArrayList();
			LinkedHashMap<String, Integer> vertexWeightUpdates = new LinkedHashMap<String, Integer>();
			ArrayList<String> unavailableEdges = new ArrayList();

			for (int v = v1; v <= ve; v++) {
				String[] vData = lines.get(v).split("\\s");
				verticesWithWater.add(vData[0]);
				if (vData.length == 2) {
					vertexWeightUpdates.put(vData[0], Integer.parseInt(vData[1]));
				}
			}

			for (int l = l1; l <= la; l++) {
				unavailableEdges.add(lines.get(l));
			}

			Case newCase = new Case(
				id,
				verticesWithWater,
				vertexWeightUpdates,
				unavailableEdges
			);
			cases.add(newCase);

			offset = offset + 2 + e + a + 2;
		}
	}
	
	/**
	 * Clona baseGraph, modifica los datos del grafo acorde con el caso
	 * planteado y lo agrega a caseBasedGraphs. Es de notar que uno de los
	 * cambios es agregar nuevas aristas que representan el camino de la planta
	 * baja de cada edificio al baño de dicho edificio.
	 */
	private static GrafoNoDirigido<Integer, Integer>
	createGraphAccordingToCase(Case _case) {

		GrafoNoDirigido<Integer, Integer> caseGraph =
			(GrafoNoDirigido<Integer, Integer>)baseGraph.clone(baseGraph);
		caseGraph.deleteUnavailableEdges(_case);
		caseGraph.updateVertexWeights(_case);
		caseGraph.addBathrooms();
		caseGraph.updateWaterAvailability(_case);

		return caseGraph;
	}
	
	/**
	 * Crea un grafo modificado por cada caso introducido y lo almacena en
	 * caseBasedGraphs.
	 */
	private static void createGraphsAccordingToCases() {
		for (Case c : cases) {
			GrafoNoDirigido<Integer, Integer> caseGraph = 
				createGraphAccordingToCase(c);
			caseBasedGraphs.put(c.getId(), caseGraph);
		}
	}
	
	/**
	 * Aplica el algoritmo de Bellman-Ford al grafo usando como vértice de
	 * partida a origin.
	 */
	private static void applyBellmanFordTo(
		GrafoNoDirigido<Integer, Integer> graph,
		String originV
	) {

	}

	/**
	 * Construye un ShortestPath que tiene como vértice de inicio a origin y
	 * como vértice final a v.
	 */
	private static ShortestPath getShortestPathTo(
		Vertice v,
		GrafoNoDirigido<Integer, Integer> graph
	) {

		Vertice currentVertex = v;
		Stack vertexIdsInReverseOrder = new Stack();

		// Construir atributo path
		ArrayList<Vertice> path = new ArrayList();
		while (currentVertex.getPrevVertexInShortestPath() != null) {
			vertexIdsInReverseOrder.push(
				currentVertex.getPrevVertexInShortestPath()
			);
		}
		while (!vertexIdsInReverseOrder.empty()) {
			path.add(
				graph.obtenerVertice(graph, (String)vertexIdsInReverseOrder.pop())
			);
		}

		return new ShortestPath(path, path.get(path.size()-1).getShortestDist());
	}
	
	/**
	 * Construye una lista con los caminos más cortos hallados luego de aplicar
	 * el algoritmo de Bellman-Ford al grafo usando como vértice de partida a
	 * origin y filtra de dicha lista a los caminos cuyo vértice final no sea un
	 * baño con agua.
	 */
	private static ArrayList<ShortestPath> getShortestPathsToBathroomsFor(
		GrafoNoDirigido<Integer, Integer> graph,
		String originV
	) {

		ArrayList<ShortestPath> pathsToBathrooms = new ArrayList();
		applyBellmanFordTo(graph, originV);
		
		ShortestPath currentPath;
		for (Vertice v : graph.vertices(graph)) {
			currentPath = getShortestPathTo(v, graph);
			if (currentPath.isPathToBathroom()) {
				pathsToBathrooms.add(currentPath);
			}
		}

		return pathsToBathrooms;
	}

	/**
	 * Retorna el camino más corto de la lista suministrada.
	 */
	private static ShortestPath getShortestFrom(ArrayList<ShortestPath> shortestPaths) {
		ShortestPath shortest = shortestPaths.get(0);
		for (int i = 1; i < shortestPaths.size(); i++) {
			if (shortestPaths.get(i).getDistance() < shortest.getDistance()) {
				shortest = shortestPaths.get(i);
			}
		}
		return shortest;
	}

	/**
	 * Obtiene el mayor número de personas que se pueden enviar a través del
	 * camino suministrado (determinado por el mínimo de las capacidades de
	 * las aristas), asigna este valor al atributo peopleSent del camino y
	 * actualiza los datos necesarios, además de notificar al usuario de los
	 * cambios efectuados.
	 */
	private static void sendPeopleTo(ShortestPath shortestPath) {
		/*
		Ver cuál es el número de personas puedan pasar por la arista con menor capacidad en el camino (asignar ese valor a shortestPath.peopleSent)
		====== printResultsFor(shortestPath)  ======
		Restar shortestPath.peopleSent de la capacidad de cada uno de los lados en shortestPath y en caso de que esta resta de 0 eliminar la arista en el grafo
		Restar shortestPath.peopleSent de la capacidad del edificio y del baño representados por el penúltimo y último vértice del shortestPath [probablemente no es necesario porque al final si !isBathroom , getShortestPathsToBathroomsFor() no lo va a considerar]
		*/
	}

	/**
	 * Imprime una línea de la forma “N personas a Z Ruta: X - Y - Z (M m)”
	 */
	private static void printResultsFor(ShortestPath path) {
		String pathStr = "";
		for (Vertice v : path.getPath()) {
			pathStr += v.getId() + " - ";
		}
		pathStr = pathStr.substring(0, pathStr.length() - 3);

		System.out.println(
			"\t" + path.getPeopleSent() + " personas a " +
			path.getPath().get(path.getPath().size() - 1).getId() + "\n" +
			"\t\tRuta: " + pathStr + " (" + path.getDistance() + " m)"
		);
	}
	
	/**
	 * Distribuye a las personas en vertexId a los baños más cercanos.
	 */
	private static void distributePeopleFrom(String vertexId) {
		for (String caseId : caseBasedGraphs.keySet()) {
			GrafoNoDirigido<Integer, Integer>
				caseGraph = caseBasedGraphs.get(caseId);
			ArrayList<ShortestPath>
				shortestPaths = getShortestPathsToBathroomsFor(caseGraph, origin);
			int numOfAvailablePaths = shortestPaths.size();
			int remainingPeople = numOfPeople;

			System.out.println(caseId + "\n");

			while (numOfAvailablePaths > 0 && remainingPeople > 0) {
				ShortestPath shortestPath = getShortestFrom(shortestPaths);
				sendPeopleTo(shortestPath); // Actualiza caseGraph
				printResultsFor(shortestPath);

				remainingPeople = remainingPeople - shortestPath.getPeopleSent();
				shortestPaths = getShortestPathsToBathroomsFor(caseGraph, origin);
				numOfAvailablePaths = shortestPaths.size();	
			}

			System.out.println("\n\t" + remainingPeople + " personas sin asignar");
		}
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
		//distributePeopleFrom(args[2]);
	}
}
