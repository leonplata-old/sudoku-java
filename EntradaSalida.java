import java.io.*;

/**
 * Clase (Static Factory) que contiene métodos para abrir y guardar objetos en archivos.
 * @author Guillermo L. Plata Camacho,
 *         J. Gustavo Cossio Crispin,
 *         The Java Tutorials.
 * @version 2011.04.02
 */
public abstract class EntradaSalida {
    /** Guarda un objeto en una dirección especificada.*/
    public static void guardar(Object obj, String path)
    throws IOException {
        FileOutputStream   fos = new FileOutputStream(path);
        ObjectOutputStream out = new ObjectOutputStream(fos);

        out.writeObject(obj);
    }

    /** Carga un objeto desde un archivo.*/
    public static Object cargar(String direccion)
    throws IOException,
           ClassNotFoundException {
        Object objeto = null;

        FileInputStream   fis = new FileInputStream(direccion);
        ObjectInputStream in  = new ObjectInputStream(fis);

        objeto = in.readObject();

        return objeto;
    }

    /** Guarda una cadena de caráceters en un formato legible.*/
    public static void imprimir(String texto, String direccion)
    throws IOException {
        String[] c = texto.split("\n");

        BufferedReader flujoEntrada = null;
        PrintWriter flujoSalida = null;

        try {
            flujoSalida =
                new PrintWriter(new FileWriter(direccion));

            for (String l : c) {
                flujoSalida.println(l);
            }
        }
        finally {
            if (flujoEntrada != null) {
                flujoEntrada.close();
            }
            if (flujoSalida != null) {
                flujoSalida.close();
            }
        }
    }
}
