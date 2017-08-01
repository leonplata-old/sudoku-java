import java.io.Serializable;

/**
 * La casilla del Sudoku que almacena un valor.
 * @author Guillermo L. Plata Camacho.
 *         J. Gustavo Cossio Crispin,
 * @version 2011.03.30
 */
public class CasillaSudoku implements Serializable {

    private int     valor;
    private boolean bloqueado;

    /**
     * Constructor que define un valor y bloquea la casilla.
     * @param valor El entero a ser insertado en la casilla.
     */
    public CasillaSudoku(int valor)  {
        this.valor     = valor;
        this.bloqueado = true;
    }

    /**
     * @return true si la casilla está bloqueada.
     */
    public boolean getEstado() {
        return bloqueado;
    }

    /**
     * Bloquea o desbloquea la casilla.
     * @param bloqueado true si va a bloquearse, false al contrario.
     */
    public void setEstado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    /**
     * @return Devuelve el entero almacenado en la casilla.
     */
    public int getNumero() {
        return valor;
    }

    /**
     * @param valor El entero a ser insertado en la casilla.
     * @param est Si coincide con el estado de la casilla procede a insertar el entero.
     */
    public void setNumero(int valor, boolean est) {
        if (getEstado() == est)
            this.valor = valor;
    }

    /**
     * @param valor El entero a ser insertado en la casilla.
     */
    public void setNumero(int valor) {
        if (getEstado())
            return;

        this.valor = valor;
    }
}
