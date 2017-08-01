import javax.swing.*;
import java.awt.*;

/**
 * @author Guillermo L. Plata Camacho.
 *         J. Gustavo Cossio Crispin,
 * @version 2011.04.02
 */
public class SplashScreen {
    //Componente en donde colocamos la Imagen Splash
    private JLabel splashLabel;
    //Ventana del SPlash
    private JWindow splashScreen;
    private JProgressBar progreso;

    public SplashScreen() {
        crearSplashScreen();
        try {
            splashScreen.setVisible(true);
            //pausa(1000);
            hiloProgreso hilo=new hiloProgreso();
            hilo.start();
        } catch (Exception e) {}
    }

    public void crearSplashScreen() {
        //dimension del escritorio
        //Dimension screenRect = Toolkit.getDefaultToolkit().getScreenSize();

        //Inicializa "splashLabel" con la ruta de la Imagen Splash
        splashLabel = new JLabel(Utiles.createImageIcon("images/SudokuSplash.png"));
        progreso    = new JProgressBar();

        progreso.setBorderPainted (true);
        progreso.setForeground    (new Color(0, 0, 51));

        // Mostrar el valor de avance
        progreso.setStringPainted(true);
        splashScreen = new JWindow();
        splashScreen.setBackground(new java.awt.Color(204, 204, 204));
        splashScreen.setLayout   (new BorderLayout());
        splashScreen.add         (splashLabel, BorderLayout.CENTER);
        splashScreen.add         (progreso, BorderLayout.SOUTH);
        splashScreen.pack        ();
        splashScreen.setLocationRelativeTo (null);
        splashScreen.setAlwaysOnTop(true);
    }

    public void pausa(int mlSeg) {
        try{
            // pausa para el splash
            Thread.sleep(mlSeg);
        } catch(Exception e) {}
    }

    //Hilo del Progreso
    class hiloProgreso extends Thread {
        public hiloProgreso() {
           super();
        }

        public void run() {
            for(int i = 1; i <= 100; i++) {
                progreso.setValue(i);
                pausa(25);
            }
            splashScreen.setVisible(false);
            //liberar Splash de memoria
            splashScreen = null;
        }
    }
}