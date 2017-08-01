import javax.swing.Timer;
import javax.swing.JFileChooser;
import java.awt.event.*;
import java.io.File;

/**
 * El control del Sudoku, define la forma de jugar, los Listeners y atrapa las
 * Excepciones.
 * Puede trabajar con interface gr�fica o en consola seg�n el constructor que se use.
 * @author Guillermo L. Plata Camacho.
 *         J. Gustavo Cossio Crispin,
 * @version 2011.04.02
 */
public class ControlSudoku {

    /** Define en milisegundos la duraci�n de un segundo*/
    public final static int SEGUNDO = 1000;

    // El modelo y la vista.
    private ListaCajaSudoku modelo;
    private SudokuGUI       vista;

    // Clases utiles y necesarias.
    private Timer         cronometro;
    private JFileChooser  fileChooser;
    private Reloj         relojGUI;

    /**
     * Constructor que arranca el juego sin interfaz gr�fica.
     * @param modelo La estructura de datos del Sudoku.
     */
    public ControlSudoku(ListaCajaSudoku modelo) {
        this.modelo = modelo;
        cronometro  = new Timer(SEGUNDO, new TiempoConsolaListener());
    }

    /**
     * Constructor que arranca el juego con interfaz gr�fica.
     * @param modelo La estructura de datos del Sudoku.
     * @param vista La interface que representa al modelo.
     */
    public ControlSudoku(ListaCajaSudoku modelo, SudokuGUI vista) {
        this.modelo = modelo;
        this.vista  = vista;

        iniciarComponentes();

        vista.addNuevoJuegoListener (new NuevoJuegoListener());
        vista.addSalirListener      (new SalirListener());
        vista.addCasillaListener    (new CasillaListener());
        vista.addAbrirListener      (new AbrirListener());
        vista.addGuardarListener    (new GuardarListener());
        vista.addVerRelojListener   (new VerRelojListener());
        vista.addAcercaDeListener   (new AcercaDeListener());

        vista.setVisible(true);
    }

    private void iniciarComponentes() {
        try {
            new SplashScreen();
            Thread.sleep(3000);
        } catch (Exception excp) { }

        cronometro  = new Timer        (SEGUNDO, new TiempoListener());
        fileChooser = new JFileChooser ();
        relojGUI    = new Reloj        (vista);

        fileChooser.addChoosableFileFilter     (new SudokuFileFilter());
        fileChooser.setAcceptAllFileFilterUsed (false);
    }

    private void resetearReloj() {
        relojGUI.reset   ();
        relojGUI.detener (false);
        relojGUI.setNick (modelo.getJugador());
    }

    /**
     * Inicia un nuevo juego setenado el nombre del jugador, el numero de casillas y
     * el nivel.
     * Cada vez que se inicia un nuevo juego se genera un archivo de texto que contiene
     * el juego a resolver en la misma ruta que se encuentra el proyecto � el binario.
     * @param jugador El nombre del jugador.
     * @param limite El n�mero de casillas por lado de caja.
     * @param nivel la cantidad de casillas a ser escondidas por caja.
     */
    public void nuevoJuego(String jugador, int limite, int nivel) {
        modelo.setJugador       (jugador);
        modelo.reset            (limite, nivel);
        cronometro.restart      ();
        modelo.mostrarEnConsola ();
        try {
            EntradaSalida.imprimir(modelo.print(), "NuevoSudoku" + Utiles.getFecha() + ".txt");
        }
        catch (java.io.IOException excp) {
            System.out.println("Ha ocurrido un error: IO");
        }
    }

    /**
     * Abre un archivo que contiene un juego Sudoku.
     * @param path La ruta o direcci�n del archivo.
     */
    public void abrirJuego(String path) {
        ListaCajaSudoku nuevoModelo = null;

        try {
            nuevoModelo = (ListaCajaSudoku) EntradaSalida.cargar(path);
        }
        catch (java.io.IOException excp) {
            System.out.println("Ha ocurrido un error: IO");
        }
        catch (ClassNotFoundException excp) {
            System.out.println("Ha ocurrido un error: ClassNotFound");
        }

        if (nuevoModelo == null) {
            vista.MensajeError("El archivo no es un juego sudoku");
            return;
        }

        System.out.println("\n*** SE HA CARGADO CON EXITO ***");
        System.out.println(path + "\n");

        modelo = nuevoModelo;

        cronometro.restart ();
    }

    /**
     * Guarda un jugo Sudoku a un archivo.
     * @param path La ruta o direcci�n del archivo.
     */
    public void guardarJuego(String path) {
        try {
            EntradaSalida.guardar(modelo, path);

            System.out.println("\n*** SE HA GUARDADO CON EXITO ***");
            System.out.println(path + "\n");
        }
        catch (java.io.IOException ioe) {
            System.out.println("Ha ocurrido un error: IO");
        }
    }

    /** Verfica que un n�mero dado sea el �nico en la fila, columna o caja del Sudoku,
     * siendo as� este se sit�a en la posici�n dada.
     * @param pos Un arreglo de enteros con la notaci�n:
     * {filaCaja, columnaCaja, fila, columna}.
     * @param num El n�mero a ser introducido
     * @return true si la operaci�n fu� satisfactoria.
     */
    private boolean esNumeroConcurrente(int[] pos, int num) {
        boolean res = false;

        if (modelo.buscarRepetidos(pos, num) == false) {
            modelo.setNumero(pos, num);
            System.out.println("winner: "+ modelo.getFree());
            res = true;
        }

        return res;
    }

    /**
     * Verifica si ganaste, de ser cierto se detiene el reloj.
     * @return true si se verifica en el modelo que no existen casillas libres.
     */
    private boolean ganaste() {
        boolean res = false;

        if (modelo.getOver() == true) {
            cronometro.stop();
            res = true;
        }

        return res;
    }

    /**
     * Se usa para insert�r un n�mero en una posici�n dada sin intervenci�n de
     * una interfaz gr�fica para mostrar los resultados.
     * @param pos un arreglo que representa la posici�n con la notaci�n
     * {filaCaja, columnaCaja, fila, columna}.
     * @param num el n�mero a ser insertado.
     */
    public void insertarNumeroConsola(int[] pos, int num) {
        if (esNumeroConcurrente(pos, num)) {
            System.out.print("Se h� insertado el n�mero '" + num + "' en: ");
            System.out.println(pos[0] + " " + pos[1] + " " + pos[2] + " " + pos[3]);
        }
        else {
            System.out.println("El n�mero est� repetido");
        }

        if (ganaste())
            System.out.println("���GANASTE!!!");
    }

    //>>>>>>>>>> LISTENERS  *

    /** Listener para iniciar un nuevo juego y actualizarlo en la vista.*/
    class NuevoJuegoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] formulario = {"","","",""};

            new DialogoDificultad(vista, formulario);

            if (formulario[3] == DialogoDificultad.CANCELAR)
                return;

            int limite;
            int nivel;

            try {
                limite = new Integer(formulario[0]);
                nivel  = (limite*limite) / new Integer(formulario[1]);
            }
            catch(NumberFormatException nfe) {
                vista.MensajeError("Par�metro incorrecto: '" + formulario[0] + "', Se esperaba un n�mero");
                return;
            }

            try {
                nuevoJuego(formulario[2], limite, nivel);

                vista.reset   ();
                vista.iniciar ();
            }
            catch (ArrayIndexOutOfBoundsException excp) {
                vista.MensajeError("Par�metro incorrecto: N�mero cero");
                return;
            }
            catch (NegativeArraySizeException excp) {
                vista.MensajeError("Par�metro incorrecto: '" + limite + "', N�mero negativo");
                return;
            }
            catch (OutOfMemoryError excp) {
                System.exit(0);
            }

            resetearReloj();
        }
    }

    /**
     * Listener para abrir un juego guardado con un selector de archivo (FileChooser)
     * y actualizarlo en la vista.
     */
    class AbrirListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int returnVal = fileChooser.showOpenDialog(vista);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                System.out.println("Opening: " + file.getPath());
                abrirJuego(file.getPath());
            }
            else
                return;

            vista.cambiarModelo (modelo);
            vista.reset         ();
            vista.iniciar       ();
            resetearReloj       ();
        }
    }

    /** Listener para guardar un juego mediante un selector de archivo (FileChooser).*/
    class GuardarListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int returnVal = fileChooser.showSaveDialog(vista);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                System.out.println("Saving: " + file.getPath() + ".sdku");
                guardarJuego(file.getPath() + "." + SudokuFileFilter.SDKU);
            }
        }
    }

    /** Listener para insertar o borrar un n�mero.*/
    class CasillaListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent evt) {
            vista.activarCasilla(evt);
        }

        @Override
        public void mouseExited(MouseEvent evt) {
            vista.desactivarCasilla();
        }

        @Override
        public void mousePressed(MouseEvent evt) {
            if (modelo.getOver())
                return;

            int[] num = {0};
            int[] pos = vista.getPosCasillaActiva();

            if (evt.getButton() == MouseEvent.BUTTON1)
                new SelectNumero(vista, evt.getComponent(), modelo.getLimite(), num);

            if (esNumeroConcurrente(pos, num[0])) {
                vista.setNumeroCasillaActiva (modelo.getNumero(pos));
            }
            else {
                vista.MensajeError("El n�mero est� repetido");
            }

            if(ganaste()) {
                relojGUI.detener  (true);
                vista.finalizar   ();
                new  SplashWinner ();
            }
        }
    }

    /**
     * Listener para aumentar en un segundo el tiempo del modelo y actualizarlo en la
     * interface gr�fica del reloj.
     */
    class TiempoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            modelo.aumentarSegundo  ();
            relojGUI.insertarTiempo (modelo.getTiempo());
        }
    }

    /** Listener para aumentar en un segundo el tiempo del modelo.*/
    class TiempoConsolaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            modelo.aumentarSegundo();
        }
    }

    /** Listener para ver el reloj en caso de cerrarlo.*/
    class VerRelojListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            relojGUI.setVisible(true);
        }
    }

    /** Listener para ver la ventana de dialogo Acerca De.*/
    class AcercaDeListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new DialogoAcercaDe();
        }
    }

    /** Listener para salir.*/
    class SalirListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}