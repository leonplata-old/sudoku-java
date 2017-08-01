import java.io.Serializable;

/**
 * Representa la caja que contiene las casillas con los números concurrentes definidos
 * por el límite y realiza operaciones dirigidas solo a estos.
 * @author Guillermo L. Plata Camacho.
 *         J. Gustavo Cossio Crispin,
 * @version 2011.03.30
 */
public class CajaSudoku implements Serializable {

    private CasillaSudoku[][] matriz;
    private int               limite;
    private int               nivel;

    /**
     * Construye una caja con un limite y nivel dados.
     * @param limite Cantidad de casillas por lado a ser creadas e instanciadas.
     * @param nivel Cantidad de casillas a ser escondidas.
     * @throws java.lang.OutOfMemoryError Si el limite es muy elevado.
     */
    public CajaSudoku(int limite, int nivel) {
        this.limite = limite;
        this.nivel  = nivel;
        matriz      = new CasillaSudoku [limite][limite];

        for (int fil = 0; fil < limite ; fil++) {
           for (int col = 0; col < limite; col++) {
               matriz[fil][col] = new CasillaSudoku(0);
           }
        }

        esconder();
    }

    /**
     * Devuelve la matriz de casillas.
     * @return la matriz de casillas.
     */
    public CasillaSudoku[][] getMatriz() {
        return matriz;
    }

    /**
     * Introduce una matriz de casillas y hace una copia por valor de ella.
     * Para ello usa los métodos de 'CasillaSudoku'.
     * @param casilla
     */
    public void setMatriz(CasillaSudoku[][] casilla) {
        for (int fil = 0; fil < limite; fil++) {
            for (int col = 0; col < limite; col++) {
                matriz[fil][col].setNumero(casilla[fil][col].getNumero(), true);
            }
        }
    }

    /**
     * Setea los numero de la caja sudoku a través de la fila y columna en la que se
     * encuetra.
     * @param fil Fila del numero.
     * @param col Columna del numero.
     * @param num Numero a cambiar.
     */
    public void setNumero(int fil, int col, int num) {
        if (control(fil, col)) {
            matriz[fil][col].setNumero(num);
        }
    }

    /**
     * Devuelve un numero de la caja Sudoku a través de la fila y columna en la que se
     * encuentra.
     * @param fil La fila.
     * @param col La columna.
     * @return El número en la fila y columna dada, si la fila o columna no existen
     * devuelve -1.
     */
    public int getNumero(int fil, int col) {
        int num = -1;

        if (control(fil, col)) {
            num = matriz[fil][col].getNumero();
        }
        return num;
    }

    /**
     * Controla que la fila y la columna estén dentro del rango de la matriz.
     * @param fil La fila.
     * @param col La columna.
     */
    private boolean control(int fil, int col) {
        boolean res = false;

        if (fil > -1 && fil < limite) {
           if (col > -1  && col < limite) {
               res = true;
           }
        }
        return res;
    }

    /**
     * Verifica si existe el número en determinada fila.
     * @param pos La posicion de la fila.
     * @param num El numero a ser buscado.
     * @return true si el número es encontrado.
     */
    public boolean verificarFila(int pos, int num) {
        boolean res = false;

        for (int i = 0; i < limite; i++) {
            if (matriz[pos][i].getNumero() == num) {
                res = true; //Encontre el numero en alguna fila
                break;      // y alguna columna
            }
        }
        return res;
    }

    /**
     * Verifica si existe el número en determinada columna.
     * @param pos La posicion de la columna.
     * @param num El numero a ser buscado.
     * @return true si el número es encontrado.
     */
    public boolean verificarColumna(int pos, int num) {
        boolean res = false;

        for (int i = 0; i < limite; i++) {
            if (matriz[i][pos].getNumero() == num ) {
                res = true; //Encontre el numero en alguna fila
                break;      // y alguna columna
            }
        }
        return res;
    }

    /**
     * Verifica si existe el número en la caja.
     * @param num El numero a ser buscado.
     * @return true si el número es encontrado.
     */
    public boolean verificarCaja(int num) {
        boolean res = false;

        for (int i = 0; i < limite; i++) {
            for (int j = 0; j < limite; j++) {
                if (matriz[i][j].getNumero() == num) {
                    res = true; //Encontre el numero en alguna fila
                    break;      // y alguna columna
                }
            }
        }
        return res;
    }

    /**
     * Muestra las casillas de una fila en forma textual.
     * @param fil La fila.
     * @return Una cadena de texto con los numeros de determinada fila.
     */
    public String concatFilas(int fil) {
        String res = "";
        int num;
        int dim;

        for (int col = 0; col < limite; col++) {
            dim = limite*limite;
            num = getNumero(fil, col);

            if (num == 0) {
                while (num != dim) {
                    res += 0;
                    dim /= 10;
                }
                res += " ";
            }
            else {
                while (num != 0) {
                    num /= 10;
                    dim /= 10;
                }
                while (num != dim) {
                    res += 0;
                    dim /= 10;
                }
                res += getNumero(fil, col) +  " ";
            }
        }
        res += "      ";

        return res;
    }

    /*
     * Establece el estado de las casillas a false (desbloqueado), al generarse un
     * Sudoku e insertar los números, estos solo se situarán en las casillas bloqueadas
     * ignorando a las desbloqueadas.
     */
    private void esconder() {
        int   dimension = limite * limite;
        int[] num       = Aleatorio.listaAleatoria(dimension, dimension);

        if (nivel <= 0 || nivel > dimension)
            return;

        for (int cont = 0; cont < nivel ; cont++) {
            int fil = num[cont] / limite;
            int col = num[cont] % limite;

            matriz[fil][col].setEstado(false);
        }
    }
}

