import java.awt.*;
import javax.swing.WindowConstants;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * @author Guillermo L. Plata Camacho.
 *         J. Gustavo Cossio Crispin,
 * @version 2011.04.02
 */
public class SudokuGUI extends JFrame {

    /** Color de la barra de men�s y otros componentes.*/
    public static Color COLOR_PREFERIDO = new Color(186, 217, 241);

    // Variables para la Barra de Menus
    private JMenuBar  barraMenu;
    private JMenu     archivoMenu;
    private JMenu     ayudaMenu;
    private JMenuItem nuevoJuego;
    private JMenuItem abrir;
    private JMenuItem guardar;
    private JMenuItem salir;
    private JMenuItem acercaDe;
    private JMenuItem verReloj;

    // Variables de la clase
    private ListaCajaSudoku modelo;
    private CajaGUI[][]     cajas;
    private CasillaGUI      casillaActiva;
    private int             limite;
    private int             nivel;
    private MouseListener   casillaListener;

    /**
     * Constructor que define el modelo.
     * @param modelo
     */
    public SudokuGUI(ListaCajaSudoku modelo) {
        this.modelo = modelo;

        iniciarBarraMenu ();
        reset            ();

        setTitle                 ("Sudoku");
        setIconImage             (Utiles.createImageIcon("images/LogoSudoku.png").getImage());
        setDefaultCloseOperation (EXIT_ON_CLOSE);
    }

    /**
     * Permite instanciar un nuevo modelo, este m�todo se usa cuando se desea abrir
     * un modelo desde archivo.
     * @param modelo El modelo nuevo.
     */
    public void cambiarModelo(ListaCajaSudoku modelo) {
        this.modelo = modelo;
    }

    /** Reinstancia y reconstruye los componentes.*/
    public void reset() {
        setContentPane(new Container());

        limite        = modelo.getLimite();
        cajas         = new CajaGUI[limite][limite];
        casillaActiva = null;

        GridLayout thisLayout = new GridLayout(limite, limite);
        thisLayout.setVgap(3);
        thisLayout.setHgap(3);
        thisLayout.setRows(limite);
        thisLayout.setColumns(limite);
        setLayout(thisLayout);

        for (int fil = 0; fil < limite; fil++) {
            for (int col = 0; col < limite; col++) {
                cajas[fil][col] = new CajaGUI(limite, fil, col);
                add(cajas[fil][col]);
            }
        }

        JScrollPane scroll = new JScrollPane(getContentPane());

        setContentPane        (scroll);
        pack                  ();
        setLocationRelativeTo (null);
    }

    /**
     * Actualiza la interface gr�fica con el modelo y habilita la edici�n del modelo
     * mediante la interface gr�fica.
     * Inserta las casillas del modelo en la interface gr�fica, habilita el bot�n 'Guardar'
     * de la barra de men�s.
     */
    public void iniciar() {
        if (!guardar.isEnabled())
            guardar.setEnabled(true);

        for (int i = 0; i < limite; i++) {
            for (int j = 0; j < limite; j++) {
                cajas[i][j].iniciar(modelo.getMatriz()[i][j].getMatriz(), casillaListener);
            }
        }
    }

    /**
     * Lanza una ventana de di�logo con un mensaje de error.
     * @param mensaje El mensaje de error.
     */
    public void MensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Mensaje de Sudoku",
        JOptionPane.PLAIN_MESSAGE, Utiles.createImageIcon("images/LogoSudokuError.png"));
    }

    /**
     * Instancia un MouseListener para ser pasado como referencia en todas las
     * casillas de la interface gr�fica.
     * @param evt El MouseListener en cuesti�n.
     */
    public void addCasillaListener(MouseListener evt) {
        casillaListener = evt;
    }

    /**
     * Finaliza la actualizaci�n del modelo, esto se da cuando se gana el juego.
     * Bloqu�a todas las casillas de la interface gr�fica y desabilita el bot�n guardar.
     */
    public void finalizar() {
        guardar.setEnabled(false);

        for (int i = 0; i < limite; i++) {
            for (int j = 0; j < limite; j++) {
                cajas[i][j].finalizar();
            }
        }
    }

    //>>>>>>>>>> METODOS PARA CONSTRUIR LA BARRA DE MENU  *

    /* Instancia los componentes de la barra de men�s.*/
    private void iniciarBarraMenu() {
        barraMenu   = new JMenuBar  ();
        archivoMenu = new JMenu     ("Archivo");
        ayudaMenu   = new JMenu     ("Ayuda");
        nuevoJuego  = new JMenuItem ("Nuevo Juego", Utiles.createImageIcon("images/New16.gif"));
        abrir       = new JMenuItem ("Abrir", Utiles.createImageIcon("images/Open16.gif"));
        guardar     = new JMenuItem ("Guardar", Utiles.createImageIcon("images/Save16.gif"));
        salir       = new JMenuItem ("Salir", Utiles.createImageIcon("images/Close16.gif"));
        acercaDe    = new JMenuItem ("Acerca de...");
        verReloj    = new JMenuItem ("Ver Reloj");

        guardar.setEnabled(false);

        archivoMenu .add(nuevoJuego);
        archivoMenu .add(abrir);
        archivoMenu .add(guardar);
        archivoMenu .add(new JSeparator());
        archivoMenu .add(verReloj);
        archivoMenu .add(new JSeparator());
        archivoMenu .add(salir);
        ayudaMenu   .add(acercaDe);
        barraMenu   .add(archivoMenu);
        barraMenu   .add(ayudaMenu);

        barraMenu   .setBackground(COLOR_PREFERIDO);

        setJMenuBar(barraMenu);
    }

    /**
     * Agrega un ActionListener al item "Nuevo Juego" del men� "Archivo".
     * @param evt El ActionListener a ser agregado
     */
    public void addNuevoJuegoListener(ActionListener evt) {
        nuevoJuego.addActionListener(evt);
    }

    /**
     * Agrega un ActionListener al item "Salir" del men� "Archivo".
     * @param evt El ActionListener a ser agregado
     */
    public void addSalirListener(ActionListener evt) {
        salir.addActionListener(evt);
    }

    /**
     * Agrega un ActionListener al item "Abrir" del men� "Archivo".
     * @param evt El ActionListener a ser agregado
     */
    public void addAbrirListener(ActionListener evt) {
        abrir.addActionListener(evt);
    }

    /**
     * Agrega un ActionListener al item "Guardar" del men� "Archivo".
     * @param evt El ActionListener a ser agregado
     */
    public void addGuardarListener(ActionListener evt) {
        guardar.addActionListener(evt);
    }

    /**
     * Agrega un ActionListener al item "Ver Reloj" del men� "Archivo".
     * @param evt El ActionListener a ser agregado
     */
    public void addVerRelojListener(ActionListener evt) {
        verReloj.addActionListener(evt);
    }

    /**
     * Agrega un ActionListener al item "Acerca De" del men� "Ayuda".
     * @param evt El ActionListener a ser agregado
     */
    public void addAcercaDeListener (ActionListener evt) {
        acercaDe.addActionListener(evt);
    }

    //>>>>>>>>>> METODOS RELACIONADOS CON CASILLAACTIVA  *

    /**
     * Permite sustituir la referencia de casillaActiva por otra
     * @param nuevaCasilla Una CasillaGUI
     */
    public void setCasillaActiva(CasillaGUI nuevaCasilla) {
        this.casillaActiva = nuevaCasilla;
    }

    /**
     * Devuelve la posici�n de la casilla activada en forma de un arreglo de
     * enteros.
     * @return un int[] con la notaci�n {filaCaja, columnaCaja, fila, columna}
     */
    public int[] getPosCasillaActiva() {
        return casillaActiva.getPosicion();
    }

    /**
     * Activa la casilla.
     * @param evt Trae embedido la referencia a la casilla que se desea activar.
     */
    public void activarCasilla(MouseEvent evt) {
        setCasillaActiva((CasillaGUI) evt.getComponent());
        casillaActiva.iluminarOn();
    }

    /** Se desactiva la casilla.*/
    public void desactivarCasilla() {
        casillaActiva.iluminarOff();
        setCasillaActiva(null);
    }

    /**
     * Inserta un n�mero en la casilla activada.
     * @param num N�mero a insertar en la casilla activada.
     */
    public void setNumeroCasillaActiva(int num) {
        casillaActiva.setNumero(num);
    }
}