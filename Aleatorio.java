/**
 * Clase (Static Factory) que contiene métodos que generan número aleatorios.
 * @author Guillermo L. Plata Camacho.
 *         J. Gustavo Cossio Crispin,
 *
 * @version 2011.04.02
 */
public abstract class Aleatorio {
    /**
     * Genera una lista de numeros del 0 al limite, si el limite sobrepasa a la
     * cantidad se crea una redundancia ciclica.
     * @param cantidad Cantidad de numeros para generar.
     * @param limite Límite del numero que puede generarse.
     * @return
     */
    public static int[] listaAleatoria(int cantidad, int limite) {
        int[] res          = new int[cantidad];
        int   cont         = 0;
        int   numAleatorio = 0;

        do {
            numAleatorio = (int) (Math.random() * limite); //Genero un numero aleatorio.
            if(numeroRepetido(numAleatorio, cont, res))
                continue;
            res[cont] = numAleatorio;
            cont++;
        } while (cont != cantidad);

        return res;
    }

    /**
     * Busca un número en una lista especifa a travez de la cantidad de números que
     * ya fueron agregados a la lista.
     * @param num Numero a buscar.
     * @param conta Cantidad de numeros agregados.
     * @param arreglo Lista en la que se busca el numero.
     * @return true si el número está repetido.
     */
    private static boolean numeroRepetido(int num, int cont, int[] arreglo) {
        boolean res = false; //Numero no encontrado.

        for (int i = 0; i < cont; i++) {
            if (arreglo[i] == num) {
               res = true;
               break;
            }
        }

        return res;
    }
}
