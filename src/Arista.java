/**
* Subtipo del TAD {@link Lado} que representa los Lados que componen
* al TAD {@link GrafoNoDirigido}.	
*/

public class Arista<E> extends Lado<E> {

	/**
	 * Vertice extremo 1 del Arista
	 */
	private Vertice u;
	/**
	 * Vertice extremo 2 del Arista
	 */
	private Vertice v;
	/**
	 * Identificador que corresponde al número de línea en el archivo original
	 * acorde con la especificación (entre 0 (inclusive) y m (exclusive), con m
	 * el número total de lados). Sirve para identificar al lado en los casos.
	 */
	private int lineId;

	/** Constructor del TAD Arista: 
	* 		@param id 	representa el identificador
	* 		@param dato representa el dato a almacenar
	* 		@param p 	representa el peso del {@link Arista}
	* 		@param vi 	{@link Vertice} extremo 1
	* 		@param vf 	{@link Vertice} extremo 2
	*/
	public Arista(
		String id, E dato, double p, Vertice u, Vertice v, int lineId
	) {
		super(id, dato, p);
		this.u = u;
		this.v = v;
		this.lineId = lineId;
	}

	/** Obtiene el {@link Vertice} del primer extremo del Arista
	* 		@return 	Vertice del primer extremo
	*/
	public Vertice getExtremo1() {
		return this.u;
	}

	/** Obtiene el {@link Vertice} del segundo extermo del Arista
	* 		@return 	Vertice del segundo extremo
	*/
	public Vertice getExtremo2() {
		return this.v;
	}

	public int getLineId() {
		return this.lineId;
	}

	public void setLineId(int newLineId) {
		this.lineId = newLineId;
	}

	/** {@inheritDoc} **/
	@Override
	public String toString() {
		return "Arista \"" + this.id + "\":\n" + 
			"Tipo de dato:	" + this.dato.getClass().getSimpleName() + "\n" +
			"Dato:	" + this.dato + "\n" +
			"Peso:	" + this.peso + "\n" +
			"Vertice 1:	" + this.u.toString() + "\n" +
			"Vertice 2:	" + this.v.toString() + "\n";
	}
}