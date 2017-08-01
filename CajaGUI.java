import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseListener;

/**
 * @author Guillermo L. Plata Camacho.
 *         J. Gustavo Cossio Crispin,
 * @version 2011.04.02
 */
public class CajaGUI extends JPanel {

    private CasillaGUI[][] casillas;
    private int            limite;

    /**
     * Constructor que define el limite, recice el numero de fila y columna de la caja
     * dentro de SudokuGUI.
     * @param limite Cantidad de casillas por lado.
     * @param idFil Fila de la caja.
     * @param idCol Columna de la caja.
     */
    public CajaGUI(int limite, int idFil, int idCol) {
        this.limite = limite;
        casillas    = new CasillaGUI[limite][limite];

        GridLayout  thisLayout = new GridLayout(limite, limite);
        thisLayout.setVgap    (3);
        thisLayout.setHgap    (3);
        thisLayout.setRows    (limite);
        thisLayout.setColumns (limite);

        this.setLayout(thisLayout);
        this.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setBackground (new Color(45, 73, 103));

        for(int fil = 0 ; fil < limite ; fil++) {
            for(int col = 0; col < limite ; col++) {
                int[] pos = {idFil, idCol, fil, col};
                casillas[fil][col] = new CasillaGUI(pos);
                this.add(casillas[fil][col]);
             }
        }
    }

    /**
     * Inicia las casillas pasando por parámetro la casilla en el modelo que le
     * corresponde y agrega el respectivo listener para interactuar con la casilla.
     * @param casillasModelo la casilla del modelo.
     * @param casillaListener Un Mouselistener.
     */
    public void iniciar(CasillaSudoku[][] casillasModelo, MouseListener casillaListener) {
        for (int fil = 0; fil < limite; fil++) {
            for (int col = 0; col < limite; col++) {
                casillas[fil][col].iniciar(casillasModelo[fil][col], casillaListener);
            }
        }
    }

    /** Bloquea todas las casillas.*/
    public void finalizar() {
        for (int fil = 0; fil < limite; fil++) {
            for (int col = 0; col < limite; col++) {
                casillas[fil][col].bloquear();
            }
        }
    }
}
