/**
 * Programa que, dado una lista de casos de un campus, un edificio de partida, y
 * un número de personas en ese edificio, le asigne el baño a todas el baño más
 * cercano disponible en todos los casos.
 */
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EncontrarAgua {

	/**
	 * Grafo inicial tal cual fue importado sin tomar en cuenta las
	 * modificaciones introducidas por cada caso.
	 */
	GrafoNoDirigido<Integer, Integer> baseGraph;
	/**
	 * Diccionario donde cada clave corresponde al identificador de un caso y
	 * cada valor corresponde a un grafo modificado acorde al caso con el
	 * identificador de la clave.
	 */
	LinkedHashMap<String, GrafoNoDirigido<Integer, Integer>> caseBasedGraphs;
	/**
	 * Lista de casos importados.
	 */
	ArrayList<Case> cases = new ArrayList();
	/**
	 * Id del vértice de partida.
	 */
	String origin;
	/**
	 * Cantidad de personas a las que se les debe asignar baño.
	 */
	int numOfPeople;


	/**
	 * Rutina principal
	 */
	public static void main(String[] args) {

		System.out.println("Sup?");
	}
}
