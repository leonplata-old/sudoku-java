import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * La ventana de di�logo SelectNumero permite elegir entre los n�meros disponibles
 * seg�n la longitud del Sudoku.
 * @author Guillermo L. Plata Camacho.
 *         J. Gustavo Cossio Crispin,
 * @version 2011.04.02
 */
public class SelectNumero extends JDialog implements ActionListener {

    /** Establece como 0 todos los m�rgenes del componente. */
    public static Insets SIN_MARGEN = new Insets(0, 0, 0, 0);

    private int[] formulario;

    /**
     * Crea un dialogo sin las caracter�sticas de una ventana con su respectivo
     * poseedor(owner), posici�n, cantidad de botones y el formulario a ser llenado.
     * @param owner El frame que poseer� al dialogo.
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
     * Establece caracter�sticas y adiere el ActionListener a un bot�n.
     * @param boton El JButton a ser tratado.
     * @param num el n�mero a establecer como ActionCommand en el JButton.
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
     * Cierra la ventana de di�logo.
     */
    private void cerrarDialogo() {
        setVisible (false);
        dispose    ();
    }

    /**
     * Actualiza el formulario con el ActionCommand del bot�n presionado
     * y finaliza la ventana de di�logo.
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        formulario[0] = new Integer(evt.getActionCommand());

        cerrarDialogo();
    }
}
