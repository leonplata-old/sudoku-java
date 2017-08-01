import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;

/**
 * La representación gráfica de una casilla de Sudoku.
 * @author Guillermo L. Plata Camacho.
 *         J. Gustavo Cossio Crispin,
 * @version 2011.04.02
 */
public class CasillaGUI extends JTextField {
    /** El tipo de letra por defecto.*/
    public final static Font TIPO_LETRA = new Font("Elephant", 0, 18);
    /** Las dimensiones de la casilla por defecto.*/
    public final static Dimension DIMENSION_CASILLA = new Dimension(30, 30);
    /** El color de una casilla editable.*/
    public final static Color COLOR_NORMAL = new Color(240, 240, 240);
    /** El color de una casilla activada.*/
    public final static Color COLOR_ACTIVADO = new Color(213, 128, 128);
    /** El color de una casilla bloqueada.*/
    public final static Color COLOR_BLOQUEADO = new Color(178, 228, 242);

    private int[]   posicion = new int[4];
    private boolean bloqueado;

    /**
     * Contruye La casilla dotandole de una posición.
     * @param posicion Establece la posición en la cuál se encuentra la casilla
     * dentro del Sudoku.
     */
    public CasillaGUI(int[] posicion) {
        this.posicion = posicion;

        setEditable            (false);
        setFont                (TIPO_LETRA);
        setHorizontalAlignment (JTextField.CENTER);
        setPreferredSize       (DIMENSION_CASILLA);

        this.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        bloquear();
    }

    /**
     * Inicia la casilla estableciendo las características de la casilla del modelo,
     * si la casilla es jugable (está desbloqueada) se agrega un MouseListener para
     * interactuar con esta.
     * @param c la casilla del modelo en la cual basar el estado y valor.
     * @param casillaListener el MouseListener para la casilla.
     */
    public void iniciar(CasillaSudoku c, MouseListener casillaListener) {
        desbloquear();
        setNumero(c.getNumero());

        if (c.getEstado()) {
            bloquear();
            return;
        }

        addMouseListener(casillaListener);
    }

    /**
     * @return true si la casilla está bloqueada, false si la casilla no está
     * bloqueada.
     */
    public boolean estaBloqueado() {
        return bloqueado;
    }

    /** Bloquea la casilla y toma el color de bloqueado.*/
    public void bloquear() {
        bloqueado = true;
        setBackground(COLOR_BLOQUEADO);
    }

    /** Desbloquea la casilla y vuelve al color normal.*/
    public void desbloquear() {
        bloqueado = false;
        setBackground(COLOR_NORMAL);
    }

    /**
     * @return true si la casilla está vacia, false si la casilla está llena.
     */
    public boolean estaVacia() {
        return getText() == "";
    }

    /**
     * Muestra un número en la casilla, si el numero es 0 la casilla no muestra nada.
     * @param numero el número a ser mostrado.
     */
    public void setNumero(int numero) {
        if (numero == 0) {
            setText("");
            return;
        }

        setText("" + numero);
    }

    /**
     * Devuelve el valor de la casilla, si la casilla está vacía devuelve 0.
     * @return El valor de la casilla, 0 si la casilla está vacía.
     */
    public int getNumero() {
        int numero = 0;

        if (!estaVacia())
            numero = new Integer(getText());

        return numero;
    }

    /**
     * Devuelve la posición de la casilla en el Sudoku.
     * @return La posición con la notación: {filaCaja, columnaCaja, fila, columna}.
     */
    public int[] getPosicion() {
        return posicion;
    }

    /** La casilla se resalta con el color activado.*/
    public void iluminarOn() {
        if (bloqueado)
            return;

        setBackground(COLOR_ACTIVADO);
    }

    /** La casilla vuelve al color normal.*/
    public void iluminarOff() {
        if (bloqueado)
            return;

        setBackground(COLOR_NORMAL);
    }
}
