import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Clase complementaria al filechooser para distinguir y solo seleccionar archivos del
 * tipo 'sdku' que serán los únicos compatibles con el programa.
 * @author Guillermo L. Plata Camacho,
 *         J. Gustavo Cossio Crispin,
 *         The Java Tutorials.
 * @version 2011.04.02
 */
public class SudokuFileFilter extends FileFilter {

    /** Extensión de un archivo Sudoku.*/
    public final static String SDKU = "sdku";

    /**
     * Analiza un archivo recibido como parámetro si cumple con los requerimientos
     * (extensión).
     * @param f El archivo en cuestión.
     * @return true si el archivo es una carpeta ó si la extensión es 'sdku'.
     */
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utiles.getExtension(f);
        if (extension != null) {
            if (extension.equals(SDKU)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    /** Muestra la descripción del formato de archivo 'sdku'.*/
    @Override
    public String getDescription() {
        return "Juegos Sudoku";
    }
}
