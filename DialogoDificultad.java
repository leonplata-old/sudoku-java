import javax.swing.*;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

/**
 * La ventana de diálogo DialogoDificultad permite establecer las caractrísticas que
 * tendrá el Sudoku a ser generado.
 * @author Guillermo L. Plata Camacho.
 *         J. Gustavo Cossio Crispin,
 * @version 2011.04.02
 */
public class DialogoDificultad extends JDialog {

    /** Los limites predefinidos.*/
    public static String[] LIMITES_DISP = {"2 X 2", "3 X 3", "4 X 4", "5 X 5"};
    /** Constante para identificar a la acción de Aceptar.*/
    public static String   ACEPTAR      = "1";
    /** Constante para identificar a la acción de Cancelar.*/
    public static String   CANCELAR     = "0";

    // Componentes de la ventana de diálogo.
    private ButtonGroup  GrupoDificultad;
    private JButton      buttonAceptar;
    private JButton      buttonCancelar;
    private JComboBox    cbLimite;
    private JCheckBox    cbPersonalizado;
    private JSeparator   separador;
    private JLabel       labelLimite;
    private JLabel       labelNombre;
    private JLabel       labeldificultad;
    private JRadioButton rbDificil;
    private JRadioButton rbFacil;
    private JRadioButton rbMedio;
    private JTextField   tfLimite;
    private JTextField   tfNombre;

    private String[]  formulario;

    /**
     * Crea la ventana de diálogo con su respectivo poseedor(owner) y formulario
     * a ser llenado.
     * @param owner El frame que poseerá al dialogo.
     * @param formulario La referencias a ser instanciadas, con la notación:
     * {limite, dificultad, nombre de jugador, estado(Aceptar/Cancelar)}.
     */
    public DialogoDificultad(Frame owner, String[] formulario) {
        super(owner, true);

        this.formulario = formulario;
        formulario[3]   = CANCELAR;

        iniciarComponentes    ();
        alinearComponentes    ();
        setResizable          (false);
        pack                  ();
        setLocationRelativeTo (owner);
        setVisible            (true);
    }

    /**
     * Instancia y dota de carácteristicas a los componentes de la ventana de diálogo.
     */
    private void iniciarComponentes() {
        GrupoDificultad = new ButtonGroup();
        labelNombre     = new JLabel();
        labeldificultad = new JLabel();
        labelLimite     = new JLabel();
        tfNombre        = new JTextField();
        tfLimite        = new JTextField();
        rbFacil         = new JRadioButton();
        rbMedio         = new JRadioButton();
        rbDificil       = new JRadioButton();
        cbLimite        = new JComboBox();
        cbPersonalizado = new JCheckBox();
        separador       = new JSeparator();
        buttonAceptar   = new JButton();
        buttonCancelar  = new JButton();

        setDefaultCloseOperation            (DISPOSE_ON_CLOSE);
        setTitle                            ("Nuevo Juego");
        this.getContentPane().setBackground (SudokuGUI.COLOR_PREFERIDO);

        labelNombre     .setText ("Nombre:");
        labeldificultad .setText ("Dificultad:");
        labelLimite     .setText ("Tipo Sudoku:");

        tfNombre.setText("Jugador1");

        tfLimite.setColumns  (3);
        tfLimite.setEditable (false);
        tfLimite.setText     ("3");


        rbFacil.setText          ("FACIL");
        rbFacil.setActionCommand ("4");
        rbFacil.setBackground    (SudokuGUI.COLOR_PREFERIDO);

        rbMedio.setSelected      (true);
        rbMedio.setText          ("MEDIO");
        rbMedio.setActionCommand ("3");
        rbMedio.setBackground    (SudokuGUI.COLOR_PREFERIDO);

        rbDificil.setText          ("DIFICIL");
        rbDificil.setActionCommand ("2");
        rbDificil.setBackground    (SudokuGUI.COLOR_PREFERIDO);

        GrupoDificultad.add (rbFacil);
        GrupoDificultad.add (rbMedio);
        GrupoDificultad.add (rbDificil);

        cbLimite.setModel          (new DefaultComboBoxModel(LIMITES_DISP));
        cbLimite.setSelectedIndex  (1);
        cbLimite.setBackground     (Color.white);
        cbLimite.addActionListener (new LimiteComboBoxListener());

        cbPersonalizado.setText         ("Personalizado (!)");
        cbPersonalizado.addItemListener (new PersonalizadoCheckBoxListener());
        cbPersonalizado.setBackground   (SudokuGUI.COLOR_PREFERIDO);

        buttonAceptar.setText           ("Aceptar");
        buttonAceptar.addActionListener (new LlenarFormularioListener());

        buttonCancelar.setText           ("Cancelar");
        buttonCancelar.addActionListener (new CancelarListener());
    }

    /**
     * Alineación generada con NetBeans.
     */
    private void alinearComponentes() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 207, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(separador, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelLimite, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelNombre)
                            .addComponent(labeldificultad))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(cbPersonalizado, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfLimite, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                            .addComponent(rbDificil, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                            .addComponent(rbMedio, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                            .addComponent(rbFacil, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                            .addComponent(tfNombre, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                            .addComponent(cbLimite, 0, 107, Short.MAX_VALUE)))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(buttonAceptar)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNombre))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(rbFacil)
                    .addComponent(labeldificultad))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbMedio)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbDificil)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLimite)
                    .addComponent(cbLimite, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfLimite, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbPersonalizado)
                .addGap(18, 18, 18)
                .addComponent(separador, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCancelar)
                    .addComponent(buttonAceptar))
                .addContainerGap())
        );
    }

    /**
     * Cierra la ventana de diálogo.
     */
    private void cerrarDialogo() {
        setVisible (false);
        dispose    ();
    }

    /**
     * Permite elejír entre los límites predefinidos.
     */
    class LimiteComboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            String dato = (String) cbLimite.getSelectedItem();

            tfLimite.setText(dato.substring(0, 1));
        }
    }

    /**
     * Desactiva los límites predefinidos y activar el modo personalizado.
     */
    class PersonalizadoCheckBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent evt) {
            switch(evt.getStateChange()) {
                case ItemEvent.SELECTED:   cbLimite.setEnabled(false);
                                           tfLimite.setEditable(true);
                                           tfLimite.setText("3");
                                           break;

                case ItemEvent.DESELECTED: cbLimite.setEnabled(true);
                                           tfLimite.setEditable(false);
                                           tfLimite.setText("");
                                           break;
            }
        }
    }

    /**
     * Llena el formulario, introduce la constante Aceptar y cierra la ventana de diálogo.
     */
    class LlenarFormularioListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            formulario[0] = tfLimite.getText();
            formulario[1] = GrupoDificultad.getSelection().getActionCommand();
            formulario[2] = tfNombre.getText();
            formulario[3] = ACEPTAR;

            cerrarDialogo    ();
        }
    }

    /**
     * Accede al método para cerrar la ventana de diálogo.
     */
    class CancelarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            cerrarDialogo();
        }
    }
}