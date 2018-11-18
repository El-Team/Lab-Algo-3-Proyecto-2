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
	 * Distancia total recorrida en este camino.
	 */
	private Double distance;
	/**
	 * Número de personas enviadas al último edificio del camino usando esta ruta.
	 */
	private int peopleSent;

	public ShortestPath(ArrayList<Vertice> path, Double distance) {
		this.path = path;
		this.distance = distance;
		this.peopleSent = 0;
	}

	public ArrayList<Vertice> getPath() {
		return this.path;
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

	public void setDistance(Double newDistance) {
		this.distance = newDistance;
	}

	public void setPeopleSent(int newPeopleSent) {
		this.peopleSent = newPeopleSent;
	}

	public boolean isPathToBathroom() {
		return this.path.get(this.path.size() - 1).getIsBathroom();
	}
}