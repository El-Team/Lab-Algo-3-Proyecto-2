/**
 * Biblioteca de métodos de uso común.
 */
import java.io.File;
import java.util.List;
import java.lang.Integer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.io.IOException;
import java.lang.NumberFormatException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Utilidades {

	/**
	 * Verifica que el archivo exista
	 */
	public static boolean isValidPath(String filename) {
		File tmpFile = new File(filename);
		if (tmpFile.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * Verifica que el archivo tenga el formato correcto
	 */
	public static boolean documentHasValidFormat(String filename, String typeOfFile) {
		List<String> lines = null;
		String fileContent = "";
		Integer n = 0;
		Integer m = 0;

		try {
			lines = Files.readAllLines(
				Paths.get(filename),
				Charset.defaultCharset()
			);
			if (typeOfFile.equals("graph")) {
				n = Integer.parseInt(lines.get(0));
				m = Integer.parseInt(lines.get(1));
			}
			else {
				n = Integer.parseInt(lines.get(1));
				m = Integer.parseInt(lines.get(2));
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(NumberFormatException e) {
			System.out.println("Número de vértices o lados inválido");
		}

		for (String line : lines) {
			fileContent += line;
			fileContent += ",";
		}

		String regexStr = typeOfFile.equals("graph")
			? "[0-9]+,[0-9]+," +
			  "([A-Z]+[0-9]*\\s[0-9]+\\s[0-9]+,){" + n.toString() + "}" +
			  "(([A-Z]+[0-9]*)\\s([A-Z]+[0-9]*)\\s[0-9]+\\s[0-9]+(.[0-9]+){0,1},){" +
			  m.toString() + "}"
			: "\\S+,[0-9]+,[0-9]+," +
			  "([A-Z]+[0-9]*(\\s(\\+|-)[0-9])*,){" + n.toString() + "}" +
			  "([0-9]+,){" + m.toString() + "}";

		Pattern regexPattern = Pattern.compile(regexStr);
		Matcher match = regexPattern.matcher(fileContent);

		return match.lookingAt();
	}
}