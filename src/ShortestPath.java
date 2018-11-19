import java.util.ArrayList;

/**
 * Representa a un camino de costo mínimo hallado
 */
public class ShortestPath {

	/**
	 * Lista de Vertices que hacen este camino.
	 */
	private ArrayList<Vertice> path;
	/**
	 * Lista de los ids de las Aristas que hacen este camino.
	 */
	private ArrayList<String> pathEdges;
	/**
	 * Distancia total recorrida en este camino.
	 */
	private Double distance;
	/**
	 * Número de personas enviadas al último edificio del camino usando esta ruta.
	 */
	private int peopleSent;

	public ShortestPath(
		ArrayList<Vertice> path,
		ArrayList<String> pathEdges,
		Double distance
	) {
		this.path = path;
		this.pathEdges = pathEdges;
		this.distance = distance;
		this.peopleSent = 0;
	}

	public ArrayList<Vertice> getPath() {
		return this.path;
	}

	public ArrayList<String> getPathEdges() {
		return this.pathEdges;
	}

	public Double getDistance() {
		return this.distance;
	}

	public int getPeopleSent() {
		return this.peopleSent;
	}

	public void setPath(ArrayList<Vertice> newPath) {
		this.path = newPath;
	}

	public void setPathEdges(ArrayList<String> newPathEdges) {
		this.pathEdges = newPathEdges;
	}

	public void setDistance(Double newDistance) {
		this.distance = newDistance;
	}

	public void setPeopleSent(int newPeopleSent) {
		this.peopleSent = newPeopleSent;
	}

	public boolean isPathToBathroom() {
		return this.path.get(this.path.size() - 1).getIsBathroom();
	}

	public boolean endsInAVertexWithWater() {
		return this.path.get(this.path.size() - 1).getHasWater();
	}
}