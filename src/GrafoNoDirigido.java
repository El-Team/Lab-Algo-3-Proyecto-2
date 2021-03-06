/**
 * Este TAD es un subtipo de la inerfaz {@link Grafo}. Como su nombre lo indica,
 * representa a un grafo no dirigido.
 */
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.io.IOException;
import java.lang.Integer;
import java.util.NoSuchElementException;
import java.lang.Math;

public class GrafoNoDirigido<V, L> implements Grafo<V, L> {

	/**
	 * Número de vértices
	 */
	private int vertexCount;
	/**
	 * Número de aristas
	 */
	private int edgeCount;
	/**
	 * Diccionario que contiene a los vértices
	 */
	private LinkedHashMap<String, Vertice<V>> vertices;
	/**
	 * Diccionario que contiene a las aristas
	 */
	private LinkedHashMap<String, Lado<L>> edges;

	/**
	 * Crea un nuevo GrafoNoDirigido
	 */
	public <V,L>GrafoNoDirigido() {
		this.vertexCount = 0;
		this.edgeCount = 0;
		this.vertices = new LinkedHashMap();
		this.edges = new LinkedHashMap();
	}

	/**
	 * Getters y setters
	 */
	public int getVertexCount() {
		return this.vertexCount;
	}
	public int getEdgeCount() {
		return this.edgeCount;
	}
	public LinkedHashMap<String, Vertice<V>> getVertices() {
		return this.vertices;
	}
	public LinkedHashMap<String, Lado<L>> getEdges() {
		return this.edges;
	}
	public void setVertexCount(int n) {
		this.vertexCount = n;
	}
	public void setEdgeCount(int m) {
		this.edgeCount = m;
	}
	public void setVertices(LinkedHashMap<String, Vertice<V>> vertices) {
		this.vertices = vertices;
	}
	public void setEdges(LinkedHashMap<String, Lado<L>> edges) {
		this.edges = edges;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean cargarGrafo(Grafo<V,L> g, String archivo) {

		try {
			if (!Utilidades.isValidPath(archivo)) {
				throw new FileNotFoundException();
			}
			else if (!Utilidades.documentHasValidFormat(archivo, "graph")) {
				throw new ParseException("", 0);
			}
		}
		catch(FileNotFoundException e) {
			System.out.println("No fue posible importar el archivo, verifique que el nombre es el correcto");
			return false;
		}
		catch(ParseException e) {
			System.out.println("El documento que contiene al GRAFO no tiene el formato correcto");
			return false;
		}


		// Abrir archivo de texto

		List<String> lines = null;
		try {
			lines = Files.readAllLines(
				Paths.get(archivo),
				Charset.defaultCharset()
			);
		}
		catch(IOException e) {
			e.printStackTrace();
			return false;
		}


		// Cargar datos

		this.setVertexCount(Integer.parseInt(lines.get(0)));
		this.setEdgeCount(Integer.parseInt(lines.get(1)));

		LinkedHashMap<String, Vertice<V>> importedVertices =
			new LinkedHashMap<String, Vertice<V>>();

		for (int i = 2; i < 2 + this.getVertexCount(); i++) {
			String[] vertexData = lines.get(i).split("\\s");
			Vertice<V> v = new Vertice<V>(
				vertexData[0],
				(V)vertexData[1],
				Integer.parseInt(vertexData[2])
			);
			importedVertices.put(vertexData[0], v);
		}
		this.setVertices(importedVertices);


		LinkedHashMap<String, Lado<L>> importedEdges =
			new LinkedHashMap<String, Lado<L>>();
		for (int i = 2 + this.getVertexCount(); i < lines.size(); i++) {

			String[] edgeData = lines.get(i).split("\\s");
			Vertice<V> vi = this.obtenerVertice(this, edgeData[0]);
			Vertice<V> vf = this.obtenerVertice(this, edgeData[1]);
			Integer lineId = i - 2 - this.getVertexCount();

			// El id de la arista no se pasa directamente por tratarse de un
			// multigrafo
			String edgeId = edgeData[0] + "-" + edgeData[1];
			int suffix = 1;
			while (this.estaLado(edgeId)) {
				edgeId = suffix == 1
					? edgeId + suffix 
					: edgeId.substring(0, edgeId.length() - 1) + suffix;
			}

			Arista<L> e = new Arista<L>(
				edgeId,
				(L)edgeData[2],
				Double.parseDouble(edgeData[3]),
				vi,
				vf,
				lineId
			);

			importedEdges.put(edgeId, e);
		}
		this.setEdges(importedEdges);


		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public int numeroDeVertices(Grafo<V,L> g) {
		GrafoNoDirigido<V,L> castedGraph = (GrafoNoDirigido<V,L>)g;
		return castedGraph.getVertexCount();
	}

	/**
	 * {@inheritDoc}
	 */
	public int numeroDeLados(Grafo<V,L> g) {
		GrafoNoDirigido<V,L> castedGraph = (GrafoNoDirigido<V,L>)g;
		return castedGraph.getEdgeCount();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean agregarVertice(Grafo<V,L> g, Vertice<V> v) {
		GrafoNoDirigido<V,L> castedGraph = (GrafoNoDirigido<V,L>)g;
		try {
			castedGraph.getVertices().put(v.getId(), v);
		}
		catch(Error e) {
			return false;
		}
		castedGraph.setVertexCount(castedGraph.getVertexCount() + 1);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean agregarVertice(Grafo<V,L> g, String id, V dato, int p) {
		GrafoNoDirigido<V,L> castedGraph = (GrafoNoDirigido<V,L>)g;
		if (castedGraph.estaVertice(this, id)) {
			return false;
		}
		try {
			Vertice<V> v = new Vertice<V>(id, dato, p);
			castedGraph.getVertices().put(id, v);
		}
		catch(Error e) {
			return false;
		}
		castedGraph.setVertexCount(castedGraph.getVertexCount() + 1);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public Vertice<V> obtenerVertice(Grafo<V,L> g, String id) throws NoSuchElementException {
		GrafoNoDirigido<V,L> castedGraph = (GrafoNoDirigido<V,L>)g;
		Vertice<V> v = castedGraph.getVertices().get(id);
		if (v == null) {
			throw new NoSuchElementException();
		}
		return v;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean estaVertice(Grafo<V,L> g, String id) {
		GrafoNoDirigido<V,L> castedGraph = (GrafoNoDirigido<V,L>)g;
		return castedGraph.getVertices().containsKey(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean estaLado(Grafo<V,L> g, String u, String v) {
		GrafoNoDirigido<V,L> castedGraph = (GrafoNoDirigido<V,L>)g;
		for (String edgeId : castedGraph.getEdges().keySet()) {
			Arista<L> castedEdge = (Arista<L>)castedGraph.getEdges().get(edgeId);
			if (
				(
					castedEdge.getExtremo1().getId().equals(u) &&
					castedEdge.getExtremo2().getId().equals(v)
				) ||
				(
					castedEdge.getExtremo1().getId().equals(v) &&
					castedEdge.getExtremo2().getId().equals(u)
				)
				
			) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Verifica la existencia de una arista en base a un id suministrado.
	 */
	public boolean estaLado(String edgeId) {
		for (String eId : this.getEdges().keySet()) {
			if (eId.equals(edgeId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean eliminarVertice(Grafo<V,L> g, String id) {
		GrafoNoDirigido<V,L> castedGraph = (GrafoNoDirigido<V,L>)g;
		if (castedGraph.getVertices().remove(id) != null) {
			castedGraph.setVertexCount(castedGraph.getVertexCount() - 1);
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<Vertice<V>> vertices(Grafo<V,L> g) {
		GrafoNoDirigido<V,L> castedGraph = (GrafoNoDirigido<V,L>)g;
		ArrayList<Vertice<V>> vertices = new ArrayList();
		for (String vertexId : castedGraph.getVertices().keySet()) {
			vertices.add((Vertice<V>)(castedGraph.getVertices().get(vertexId)));
		}
		return vertices;
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<Lado<L>> lados(Grafo<V,L> g) {
		GrafoNoDirigido<V,L> castedGraph = (GrafoNoDirigido<V,L>)g;
		ArrayList<Lado<L>> lados = new ArrayList();
		for (String edgeId : castedGraph.getEdges().keySet()) {
			lados.add((Lado<L>)(castedGraph.getEdges().get(edgeId)));
		}
		return lados;
	}

	/**
	 * {@inheritDoc}
	 */
	public int grado(Grafo<V,L> g, String id) throws NoSuchElementException {
		GrafoNoDirigido<V,L> castedGraph = (GrafoNoDirigido<V,L>)g;
		int grado = 0;
		if (!g.estaVertice(this, id)) {
			throw new NoSuchElementException();
		}
		for (String edgeId : castedGraph.getEdges().keySet()) {
			Arista<L> castedEdge = (Arista<L>)castedGraph.getEdges().get(edgeId);
			if (
				castedEdge.getExtremo1().getId().equals(id) ||
				castedEdge.getExtremo2().getId().equals(id)
			) {
				grado++;
			}
			// Caso bucles
			if (
				castedEdge.getExtremo1().getId().equals(id) &&
				castedEdge.getExtremo2().getId().equals(id)
			) {
				grado++;
			}
		}
		return grado;
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<Vertice<V>> adyacentes(Grafo<V,L> g, String id) throws NoSuchElementException {
		GrafoNoDirigido<V,L> castedGraph = (GrafoNoDirigido<V,L>)g;
		ArrayList<Vertice<V>> adyacentes = new ArrayList();
		if (!g.estaVertice(this, id)) {
			throw new NoSuchElementException();
		}
		for (String edgeId : castedGraph.getEdges().keySet()) {
			Arista<L> castedEdge = (Arista<L>)castedGraph.getEdges().get(edgeId);
			if (castedEdge.getExtremo1().getId().equals(id)) {
				adyacentes.add(castedEdge.getExtremo2());
			}
			if (castedEdge.getExtremo2().getId().equals(id)) {
				adyacentes.add(castedEdge.getExtremo1());
			}
		}
		return adyacentes;
	}

	/**
	 * {@inheritDoc}
	 */
	public ArrayList<Lado<L>> incidentes(Grafo<V,L> g, String id) throws NoSuchElementException {
		GrafoNoDirigido<V,L> castedGraph = (GrafoNoDirigido<V,L>)g;
		ArrayList<Lado<L>> incidentes = new ArrayList();
		if (!g.estaVertice(this, id)) {
			throw new NoSuchElementException();
		}
		for (String edgeId : castedGraph.getEdges().keySet()) {
			Arista<L> castedEdge = (Arista<L>)castedGraph.getEdges().get(edgeId);
			if (
				castedEdge.getExtremo1().getId().equals(id) ||
				castedEdge.getExtremo2().getId().equals(id)
			) {
				incidentes.add(castedEdge);
			}
		}
		return incidentes;
	}

	/**
	 * {@inheritDoc}
	 */
	public Grafo clone(Grafo<V,L> g) {

		// Se preparan los datos de entrada

		GrafoNoDirigido<V,L> castedGraph = (GrafoNoDirigido<V,L>)g;

		ArrayList<Vertice<V>> vertices = castedGraph.vertices(this);
		LinkedHashMap<String, Vertice<V>> importedVertices =
			new LinkedHashMap<String, Vertice<V>>();
		for (Vertice<V> v : vertices) {
			importedVertices.put(v.getId(), v);
		}

		ArrayList<Lado<L>> lados = castedGraph.lados(this);
		LinkedHashMap<String, Lado<L>> importedEdges =
			new LinkedHashMap<String, Lado<L>>();
		for (Lado<L> l : lados) {
			importedEdges.put(l.getId(), l);
		}


		// Se asignan los datos al clon

		GrafoNoDirigido<V,L> newGraph = new GrafoNoDirigido<V,L>();
		newGraph.setVertexCount(castedGraph.numeroDeVertices(this));
		newGraph.setEdgeCount(castedGraph.numeroDeVertices(this));
		newGraph.setVertices(importedVertices);
		newGraph.setEdges(importedEdges);


		return newGraph;
	};

	/**
	 * {@inheritDoc}
	 */
	public String toString(Grafo<V,L> g) {
		GrafoNoDirigido<V,L> castedGraph = (GrafoNoDirigido<V,L>)g;
		String graphStr =
			"Número de vértices: " + castedGraph.numeroDeVertices(this) + "\n" +
			"Número de lados: " + castedGraph.numeroDeLados(this) + "\n" +
			"Vertices:\n" + castedGraph.vertices(this).toString() + "\n" +
			"Lados:\n" + castedGraph.lados(this).toString();

		return graphStr;
	}

	/**
	 * Agrega una nueva arista al grafo si el identificador de la arista no lo posee ninguna arista en el grafo.
	 * Retorna true en caso en que la inserción se lleve a cabo, false en contrario.
	 */
	public boolean agregarArista(Grafo<V,L> g, Arista<L> a) {
		GrafoNoDirigido<V,L> castedGraph = (GrafoNoDirigido<V,L>)g;
		for (Lado<L> l : castedGraph.lados(this)) {
			Arista<L> castedEdge = (Arista<L>)l;
			if (castedEdge.getId() == a.getId()) {
				return false;
			}
		}
		try {
			castedGraph.getEdges().put(a.getId(), a);
		}
		catch(Error e) {
			return false;
		}
		castedGraph.setEdgeCount(castedGraph.getEdgeCount() + 1);
		return true;
	}

	/**
	 * Si el identificador id no lo posee ninguna arista en el grafo, crea una nueva arista y la agrega en el
	 * grafo. Retorna true en caso en que la inserción se lleve a cabo, false en contrario.
	 */
	public boolean agregarArista(
			Grafo<V,L> g,
			String id,
			L dato,
			double p,
			String u,
			String v
		) {

		GrafoNoDirigido<V,L> castedGraph = (GrafoNoDirigido<V,L>)g;
		try {
			if (
				!g.estaVertice(this, u) ||
				!g.estaVertice(this, v)
			) {
				throw new NoSuchElementException();
			}
			Vertice<V> vi = g.obtenerVertice(this, u);
			Vertice<V> vf = g.obtenerVertice(this, v);
			Arista<L> a = new Arista(id, dato, p, vi, vf, null);
			castedGraph.getEdges().put(a.getId(), a);
		}
		catch(Error e) {
			return false;
		}
		castedGraph.setEdgeCount(castedGraph.getEdgeCount() + 1);
		return true;
	}

	/**
	 * Elimina la arista en el grafo que esté identificada con id. Se retorna true en caso que se haya
	 * eliminado la arista del grafo y false en caso de que no exista una arista con ese identificador en el
	 * grafo.
	 */
	public boolean eliminarArista(Grafo<V,L> g, String id) {
		GrafoNoDirigido<V,L> castedGraph = (GrafoNoDirigido<V,L>)g;
		if (castedGraph.getEdges().remove(id) != null) {
			castedGraph.setEdgeCount(castedGraph.getEdgeCount() - 1);
			return true;
		}
		return false;
	}

	/**
	 * Devuelve la arista que tiene como identificador id. En caso de que no exista ninguna arista con ese
	 * identificador, se lanza la excepción NoSuchElementException.
	 */
	public Arista obtenerArista(Grafo<V,L> g, String id) throws NoSuchElementException {
		GrafoNoDirigido<V,L> castedGraph = (GrafoNoDirigido<V,L>)g;
		Arista<L> a = (Arista<L>)castedGraph.getEdges().get(id);
		if (a == null) {
			throw new NoSuchElementException();
		}
		return a;
	}


	/**
	 * Elimina las aristas afectadas por las lluvias.
	 */
	public void deleteUnavailableEdges(Case _case) {
		for (String edgeId : _case.getUnavailableEdges()) {
			this.eliminarArista(this, edgeId);
		}
	}
	
	/**
	 * Actualiza los valores del peso en los vértices del grafo conforme a las
	 * modificaciones planteadas en el caso.
	 */
	public void updateVertexWeights(Case _case) {
		int newPeso;
		for (String vertexId : _case.getVertexWeightUpdates().keySet()) {
			newPeso = 
				this.obtenerVertice(this, vertexId).getPeso() +
				_case.getVertexWeightUpdates().get(vertexId);
			this.obtenerVertice(this, vertexId).setPeso(newPeso);
		}
	}
	
	/**
	 * Agrega un nuevo vértice por cada baño disponible, además de agregar una
	 * arista que va de la planta baja del edificio al baño.
	 */
	public void addBathrooms() {

		ArrayList<String>initialVertices =
			new ArrayList<String>(this.getVertices().keySet());

		for (String vertexId : initialVertices) {

			Vertice v = this.obtenerVertice(this, vertexId);

			Vertice<V> vBathroom = new Vertice(
				v.getId() + "_bathroom",
				v.getDato(),
				0,
				false,
				true
			);
			this.agregarVertice(this, vBathroom);

			Arista<L> stairs = new Arista(
				v.getId() + "-" + v.getId() + "_bathroom",
				v.getDato(),
				(double)25 * Math.abs(v.getPeso()),
				v,
				vBathroom,
				Integer.MAX_VALUE
			);
			this.agregarArista(this, stairs);
		}
	}
	
	/**
	 * Actualiza el valor hasWater en los vértices del grafo conforme a lo
	 * planteado en el caso.
	 */
	public void updateWaterAvailability(Case _case) {
		for (String vertexId : _case.getVerticesWithWater()) {
			this.obtenerVertice(this, vertexId).setHasWater(true);
			this.obtenerVertice(this, vertexId + "_bathroom").setHasWater(true);
		}
	}

	/**
	 * Retorna la arista de peso mínimo que une a los vértice con id v1 y v2.
	 */
	public Arista<L> getShortestAristaFor(String v1, String v2) throws NoSuchElementException {

		Arista<L> shortestArista = null;

		//System.out.println(v1);
		//System.out.println(v2);

		for (Lado l : this.lados(this)) {
			Arista<L> e = (Arista<L>)l;
			if (
				(
					e.getExtremo1().getId().equals(v1) &&
					e.getExtremo2().getId().equals(v2)
				) ||
				(
					e.getExtremo1().getId().equals(v2) &&
					e.getExtremo2().getId().equals(v1)
				)
			) {
				if (shortestArista == null) {
					shortestArista = e;
				}
				else if (e.getPeso() < shortestArista.getPeso()) {
					shortestArista = e;
				}
			}
		}

		if (shortestArista == null) {
			throw new NoSuchElementException();
		}

		return shortestArista;
	}
}
