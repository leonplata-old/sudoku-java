import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * La ventana de diálogo SelectNumero permite elegir entre los números disponibles
 * según la longitud del Sudoku.
 * @author Guillermo L. Plata Camacho.
 *         J. Gustavo Cossio Crispin,
 * @version 2011.04.02
 */
public class SelectNumero extends JDialog implements ActionListener {

    /** Establece como 0 todos los márgenes del componente. */
    public static Insets SIN_MARGEN = new Insets(0, 0, 0, 0);

    private int[] formulario;

    /**
     * Crea un dialogo sin las características de una ventana con su respectivo
     * poseedor(owner), posición, cantidad de botones y el formulario a ser llenado.
     * @param owner El frame que poseerá al dialogo.
     * @param activo El componente al cual alinearse en el centro.
     * @param limite La cantidad de botones en base al calculo de su cuadrado.
     * @param formulario La referencia del entero a ser instanciado.
     */
    public SelectNumero(Frame owner, Component activo, int limite, int[] formulario) {
        super(owner, true);

        this.formulario  = formulario;

        setSize   (limite*35, limite*35);
        setLayout (new GridLayout(limite, limite));

        for (int num = 1; num <= limite*limite; num++) {
            JButton boton = new JButton("" + num);

            iniciarBoton (boton, num);
            add          (boton);
        }

        setLocationRelativeTo    (activo);
        setResizable             (false);
        setUndecorated           (true);
        setVisible               (true);
        setDefaultCloseOperation (DISPOSE_ON_CLOSE);
    }

    /**
     * Establece características y adiere el ActionListener a un botón.
     * @param boton El JButton a ser tratado.
     * @param num el número a establecer como ActionCommand en el JButton.
     */
    private void iniciarBoton(JButton boton, int num) {
        boton.setActionCommand  ("" + num);
        boton.setBackground     (new Color(213, 128, 128));
        boton.setForeground     (Color.white);
        boton.setMargin         (SIN_MARGEN);
        boton.setFont           (CasillaGUI.TIPO_LETRA);
        boton.addActionListener (this);
    }

    /**
     * Cierra la ventana de diálogo.
     */
    private void cerrarDialogo() {
        setVisible (false);
        dispose    ();
    }

    /**
     * Actualiza el formulario con el ActionCommand del botón presionado
     * y finaliza la ventana de diálogo.
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        formulario[0] = new Integer(evt.getActionCommand());

        cerrarDialogo();
    }
}
