import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.lang.Integer;

/**
 * representa las condiciones particulares de un día
 */
public class Case {

	/**
	 * Identificador del caso.
	 */
	private String id;
	/**
	 * Lista de edificios con agua.
	 */
	private ArrayList<String> verticesWithWater;
	/**
	 * Diccionario donde las claves corresponden a los vértices cuyo peso debe
	 * ser modificado y los valores corresponden al cambio.
	 */
	private LinkedHashMap<String, Integer> vertexWeightUpdates;
	/**
	 * Lista de aristas no disponibles.
	 */
	private ArrayList<String> unavailableEdges;

	public Case(
		String id,
		ArrayList<String> verticesWithWater,
		LinkedHashMap<String, Integer> vertexWeightUpdates,
		ArrayList<String> unavailableEdges
	) {
		this.id = id;
		this.verticesWithWater = verticesWithWater;
		this. vertexWeightUpdates = vertexWeightUpdates;
		this.unavailableEdges = unavailableEdges;
	}

	private String getId() {
		return this.id;
	}

	private ArrayList<String> getVerticesWithWater() {
		return this.verticesWithWater;
	}

	private LinkedHashMap<String, Integer> getVertexWeightUpdates() {
		return this.vertexWeightUpdates;
	}

	private ArrayList<String> getUnavailableEdges() {
		return this.unavailableEdges;
	}

	private void setId(String newId) {
		this.id = newId;
	}

	private void setVerticesWithWater(ArrayList<String> newVerticesWithWater) {
		this.verticesWithWater = newVerticesWithWater;
	}

	private void setVertexWeightUpdates(
		LinkedHashMap<String, Integer> newVertexWeightUpdates
	) {
		this.vertexWeightUpdates = newVertexWeightUpdates;
	}

	private void setUnavailableEdges(ArrayList<String> newUnavailableEdges) {
		this.unavailableEdges = newUnavailableEdges;
	}
}