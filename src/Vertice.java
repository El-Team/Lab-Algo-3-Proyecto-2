/**
* Clase <code>Vertice</code> representa un tipo de dato que posee
* el {@link Grafo}. Se identifica con {@link id} y su informacion
* principal se guarda en {@link dato}. Tambien tienen un {@link peso}
* asociado a ellos
*/

public class Vertice<E> {

	/**
	 * Identificador asociado al vertice
	 */
	private String id;
	/**
	 * Dato que se quiere almacenar en el vertice. Representa el número de
	 * personas que puden ir al baño en este edificio.
	 */
	private E dato;
	/**
	 * Peso asociado al vertice. Representa el número de pisos que hay que subir
	 * para encontrar un baño.
	 */
	private int peso;
	/**
	 * Indica si el edificio o baño representado por el vértice tiene agua.
	 */
	private boolean hasWater;

	/**
	 * Indica si el vértice representa un baño. En caso de ser falso, dicho
	 * vértice representa un edificio (más específicamente su PB).
	 */
	private boolean isBathroom;

	/**
	 * Indica la distancia mínima encontrada a partir del vértice de inicio.
	 */
	private Double shortestDist;
	/**
	 * Id del vértice previo al vértice actual en la cadena que representa el
	 * camino más corto encontrado hasta el momento.
	 */
	private String prevVertexInShortestPath;

	public Vertice(String id, E dato, int p) {
		this.id = id;
		this.dato = dato;
		this.peso = p;
		this.hasWater = false;
		this.isBathroom = false;
		this.shortestDist = Double.MAX_VALUE;
		this.prevVertexInShortestPath = null;
	}

	public Vertice(String id, E dato, int p, boolean hasWater, boolean isBathroom) {
		this.id = id;
		this.dato = dato;
		this.peso = p;
		this.hasWater = hasWater;
		this.isBathroom = isBathroom;
		this.shortestDist = Double.MAX_VALUE;
		this.prevVertexInShortestPath = null;
	}

	/** Funcion para obtener el peso del vertice
	* 		@return 	Peso asociado al vertice
	*/
	public int getPeso() {
		return this.peso;
	}

	public void setPeso(int newPeso) {
		this.peso = newPeso;
	}

	/** Funcion para obtener el identificador del vertice
	* 		@return 	Identificador del vertice
	*/
	public String getId() {
		return this.id;
	}

	/** Funcion para obtener el dato del vertice
	* 		@return 	dato almacenado en el vertice
	*/
	public E getDato() {
		return this.dato;
	}

	public boolean getHasWater() {
		return this.hasWater;
	}

	public boolean getIsBathroom() {
		return this.isBathroom;
	}

	public Double getShortestDist() {
		return this.shortestDist;
	}

	public String getPrevVertexInShortestPath() {
		return this.prevVertexInShortestPath;
	}

	public void setHasWater(boolean newHasWater) {
		this.hasWater = newHasWater;
	}

	public void setIsBathroom(boolean newIsBathroom) {
		this.isBathroom = newIsBathroom;
	}

	public void getShortestDist(Double newShortestDist) {
		this.shortestDist = newShortestDist;
	}

	public void getPrevVertexInShortestPath(String newPrevVertexInShortestPath) {
		this.prevVertexInShortestPath = newPrevVertexInShortestPath;
	}

	/** Funcion para obtener toda la informacion del vertice
	* 		@return 	<code>String</code> con toda la informacion del vertice
	*/
	public String toString() {
		return "Vertice \"" + this.id + "\":\n" +
			"	Tipo de dato:		" + this.dato.getClass().getSimpleName() + "\n" +
			"	Dato:		" + this.dato + "\n" +
			"	Peso:		" + this.peso + "\n";
	}
}