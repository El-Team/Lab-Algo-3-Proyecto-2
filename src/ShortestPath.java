import java.util.ArrayList;

/**
 * Representa a un camino de costo mínimo hallado
 */
public class ShortestPath {

	/**
	 * Lista de Aristas que hacen este camino.
	 */
	private ArrayList<Arista> path;
	/**
	 * Distancia total recorrida en este camino.
	 */
	private Double distance;
	/**
	 * Número de personas enviadas al último edificio del camino usando esta ruta.
	 */
	private int peopleSent;

	public ShortestPath(ArrayList<Arista> path, Double distance, int peopleSent) {
		this.path = path;
		this.distance = distance;
		this.peopleSent = 0;
	}

	public ArrayList<Arista> getPath() {
		return this.path;
	}

	public Double getDistance() {
		return this.distance;
	}

	public int getPeopleSent() {
		return this.peopleSent;
	}

	public void setPath(ArrayList<Arista> newPath) {
		this.path = newPath;
	}

	public void setDistance(Double newDistance) {
		this.distance = newDistance;
	}

	public void setPeopleSent(int newPeopleSent) {
		this.peopleSent = newPeopleSent;
	}
}