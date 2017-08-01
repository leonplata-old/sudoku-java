import java.io.Serializable;

/**
 * Clase principal del Sudoku que almacena a las cajas y el estado del Sudoku, controla
 * el tiempo.<br />
 * <br />
 * El entero <code>pos[]</code> es usado a lo largo del proyecto como
 * representación de la posición de una casilla dentro del sudoku.<br />
 *
 * <ul>
 *  <li><code>pos[0]</code>: La fila de la caja.</li>
 *  <li><code>pos[1]</code>: La columna de la caja.</li>
 *  <li><code>pos[2]</code>: La fila de la casilla dentro de la caja.</li>
 *  <li><code>pos[3]</code>: La columna de la casilla dentro de la caja.</li>
 * </ul>
 *
 * Para examinar en modo consola se recomienda crear una instancia del
 * modelo (<code>ListaCajaSudoku</code>) y otra del controlador
 * (<code>ControladorSudoku</code>) con el constructor
 * <code>ControlSudoku(ListaCajaSudoku modelo)</code> en BlueJ.
 *
 * @author Guillermo L. Plata Camacho.
 *         J. Gustavo Cossio Crispin,
 * @version 2011.03.30
 */
public class ListaCajaSudoku implements Serializable {

    private CajaSudoku[][] matriz;
    private String         jugador;
    private int            limite;
    private int            nivel;
    private int            segundos;
    private int            minutos;
    private int            horas;
    private int            libres;

    /**
     * El constructor de la clase, establece el nombre del jugador y por defecto crea
     * un sudoku de limite 3.
     */
    public ListaCajaSudoku() {
        setJugador ("Jugador1");
        reset      (3, 0);
    }

    /**
     * Reinstancia y reconstruye los objetos de la clase, genera un juego resuelto y
     * lo inserta.
     * @param limite Cantidad de casillas por lado de caja.
     * @param nivel Cantidad de casillas a ser escondidas por caja.
     */
    public void reset(int limite, int nivel) {
        this.limite = limite;
        this.nivel  = nivel;
        libres      = nivel*(limite *limite);
        matriz      = new CajaSudoku[limite][limite];
        segundos    = 0;
        minutos     = 0;
        horas       = 0;

        for (int fil = 0; fil < limite ; fil++) {
            for (int col = 0; col < limite; col++) {
                matriz[fil][col] = new CajaSudoku(limite, nivel);
            }
        }

        generarTablero();
    }

    /**
     * Devuelve el límite
     * @return El límite
     */
    public int getLimite(){
        return limite;
    }

    /**
     * Devuelve la matriz que contiene a las cajas.
     * @return La matriz CajaSudoku[][].
     */
    public CajaSudoku[][] getMatriz() {
        return matriz;
    }

    /**
     * Inserta un número en determinada posición.
     * @param pos Un arreglo de enteros con la notación:
     * {filaCaja, columnaCaja, fila, columna}.
     * @param num el número a ser insertado
     */
    public void setNumero(int[] pos, int num) {
        if (num == 0) {
            if (matriz[pos[0]][pos[1]].getNumero(pos[2], pos[3]) != 0){
                //Se hace la pregunta del getNumero para
                //saber si dicha casilla estaba con algun numero.
                libres++;
            }
        } else {
            libres--;
        }

        matriz[pos[0]][pos[1]].setNumero(pos[2], pos[3], num);

        mostrarEnConsola();
    }

    /**
     * Devuelve el valor de la casilla en una posición dada.
     * @param pos Un arreglo de enteros con la notación:
     * {filaCaja, columnaCaja, fila, columna}.
     * @return El entero de la casilla de la posición dada
     */
    public int getNumero(int pos[]) {
        return matriz[pos[0]][pos[1]].getNumero(pos[2], pos[3]);
    }

    /**
      * Busca numeros repetidos en el Sudoku segun las reglas, en fila, columna y caja.
      * @param pos Un arreglo de enteros con la notación:
     * {filaCaja, columnaCaja, fila, columna}.
      * @param num el número a ser buscado.
      * @return true si el número está repetido.
      */
    public boolean buscarRepetidos(int[] pos, int num) {
        if (num == 0)
            return false;

        if (matriz[pos[0]][pos[1]].verificarCaja(num)) {
            return true;
        }

        boolean res = false;

        for (int cont = 0; cont < limite; cont++) {
            if (matriz[pos[0]][cont].verificarFila(pos[2], num) == true ||
                matriz[cont][pos[1]].verificarColumna(pos[3], num) == true) {
                res = true;
                break;
            }
        }
        return res;
    }

    /** Muestra en consola el estado del Sudoku.*/
    public void mostrarEnConsola() {
        System.out.println(print());
        System.out.println("El tiempo actual es : " + horas + ":" + minutos + ":" + segundos);
    }

    /**
     * Devuelve en forma de cadena de texto del estado del Sudoku.
     * @return Una representación textual (String) del Sudoku.
     */
    public String print() {
        String res = "";
        res += "S U D O K U\n";
        res += "===========\n\n";
        for(int i = 0; i < limite; i++) {
            for(int j = 0; j < limite; j++) {
                String texto = new String();
                for(int k = 0; k < limite; k++) {
                    texto += matriz[i][k].concatFilas(j);
                }
                res += texto + "\n\n";
            }
            res += "\n";
        }
        return res;
    }

    //>>>>>>>>>> METODOS PARA CONTROLAR EL TIEMPO  *

    /**
     * Aumenta en un segundo el tiempo, si el entero 'segundos' llega a 60 se instancia
     * como 0 y aumenta en 1 el entero 'minutos'.
     * Asi mismo con 'minutos' y 'horas' emulando un reloj.
     */
    public void aumentarSegundo() {
        segundos += 1;
        if (segundos == 60) {
            segundos = 0;
            minutos += 1;
        }
        if (minutos == 60) {
            minutos = 0;
            horas += 1;
        }
    }

    /**
     * Devuelve el tiempo.
     * @return un arreglo con la notación {horas, minutos, segundos}.
     */
    public int[] getTiempo() {
        int[] res = {horas, minutos, segundos};
        return res;
    }

    //>>>>>>>>>> METODOS PARA NOMBRAR AL JUGADOR  *

    /**
     * Establece el nombre del jugador.
     * @param jugador El nombre del jugador
     */
    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    /**
     * Devuelve el nombre del jugador.
     * @return El nombre del jugador
     */
    public String getJugador() {
        return jugador;
    }

    //>>>>>>>>>> METODOS PARA GENERAR UN JUEGO SUDOKU  *

    /*
     * Genera los números de un juego Sudoku resuelto basado en una caja de muestra
     * con números concurrentes en orden aleatorio que mediante varios m�todos se lo
     * irá mezclando e insertando en cada una de las cajas del Sudoku.
     */
    private void generarTablero() {
        int[]             num     = Aleatorio.listaAleatoria(limite*limite, limite*limite);
        CasillaSudoku[][] muestra = new CasillaSudoku[limite][limite];
        int               cont    = 0;

        for (int i = 0; i < limite; i++) {
            for (int j = 0; j < limite; j++) {
                muestra[i][j] = new CasillaSudoku(++num[cont]);
                cont++;
            }
        }

        for (int i = 0; i < limite; i++) {
            for ( int j=0 ; j < limite; j++) {
                matriz[i][j].setMatriz(muestra);
                dencolarMatrizFila(muestra);
            }
            dencolarMatrizColumna(muestra);
        }

        for (int i = 0; i < limite; i++) {
            mezclarFilas(i);
        }

        for (int i = 0; i < limite; i++) {
            mezclarColumnas(i);
        }
        mostrarEnConsola();
    }

    /* Decola la primera fila de la matriz de muestra y la encola.*/
    private void dencolarMatrizFila(CasillaSudoku[][] muestra) {
        int             cont = 0;
        CasillaSudoku[] temp = muestra[cont];

        for ( ; (cont + 1) < limite; cont++) {
            muestra[cont] = muestra[cont + 1];
        }
        muestra[cont] = temp;
    }

    /* Decola la primera columna de la matriz de muestra y la encola.*/
    private void dencolarMatrizColumna(CasillaSudoku[][] muestra) {
        int cont;
        CasillaSudoku[] temp = new CasillaSudoku[limite];

        for (int i = 0; i < limite; i++) {
            cont    = 0;
            temp[i] = muestra[i][cont];
            for(  ; (cont + 1) < limite ; cont++){
                 muestra[i][cont] = muestra[i][cont + 1];
            }
            muestra[i][cont] = temp[i];
        }
    }

    /* Mezcla las sub-filas de casillas de la fila de cajas indicada en el parámetro.*/
    private void mezclarFilas(int filaCaja) {
        int[]              num  = Aleatorio.listaAleatoria(limite, limite);
        CasillaSudoku [][] temp = new CasillaSudoku[limite][limite];

        for (int j = 0; j < limite; j++) {
            CasillaSudoku [][] muestra = matriz[filaCaja][j].getMatriz();
            for (int i = 0; i < limite; i++) {
                temp[i] = muestra[num[i]];
            }
            for (int i = 0; i < limite; i++) {
                muestra[i] = temp[i];
            }
        }
    }

    /* Mezcla las sub-columans de casillas de la columna de cajas indicada en el parámetro.*/
    private void mezclarColumnas(int columnaCaja) {
        int[]             num  = Aleatorio.listaAleatoria(limite, limite);
        CasillaSudoku[][] temp = new CasillaSudoku[limite][limite];

        for(int k = 0; k < limite; k++) {
            CasillaSudoku[][] muestra = matriz[k][columnaCaja].getMatriz();
            for (int i = 0; i < limite; i++) {
                for (int j = 0; j < limite; j++) {
                    temp[j][i] = muestra[j][num[i]];
                }
            }
            for (int i = 0; i < limite; i++) {
                for (int j = 0; j < limite; j++) {
                    muestra[j][i] = temp[j][i];
                }
            }
        }
    }

    //>>>>>>>>>> METODOS RELACIONADOS CON LIBRES  *

    /**
     * Verifica si el Sudoku ha sido llenado.
     * @return true si no quedan casillas libres.
     */
    public boolean getOver() {
        return libres == 0;
    }

    /**
     * Verifica la cantidad de casillas libres.
     * @return Las casillas libres.
     */
    public int getFree() {
        return libres;
    }
}