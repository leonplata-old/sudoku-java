import java.io.File;
import javax.swing.ImageIcon;
import java.util.Calendar;

/**
 * Clase (Static Factory) que contiene métodos de utilidad variada.
 * @author Guillermo L. Plata Camacho,
 *         J. Gustavo Cossio Crispin,
 *         The Java Tutorials.
 * @version 2011.04.02
 */
public abstract class Utiles {
    /**
     * Identifica la extensión de un archivo.
     * @param f El archivo a obtener su extensión.
     * @return La extensión del archivo
     */
    public static String getExtension(File f) {
        String ext = null;
        String s   = f.getName();
        int    i   = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }

        return ext;
    }

    /**
     * Crea un objeto de imágen.
     * @param path La dirección de la imagen.
     * @return La imagen en objeto.
     */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = SudokuGUI.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("No se encontró el archivo: " + path);
            return null;
        }
    }

    /**
     * Devuelve la fecha actual en el computador en el que corre el programa.
     * @return La fecha representada por un entero.
     */
    public static String getFecha() {
        String res = "";
        Calendar c = Calendar.getInstance();
        res += c.get(Calendar.YEAR);
        res += c.get(Calendar.MONTH);
        res += c.get(Calendar.DAY_OF_MONTH);
        res += c.get(Calendar.HOUR);
        res += c.get(Calendar.MINUTE);
        res += c.get(Calendar.SECOND);
        return res;
    }
}