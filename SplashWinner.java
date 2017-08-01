import javax.swing.*;
import java.awt.*;

/**
 * @author Guillermo L. Plata Camacho.
 *         J. Gustavo Cossio Crispin,
 * @version 2011.04.02
 */
public class SplashWinner {

    private JLabel splashLabel;
    private JWindow splashScreen;

    public SplashWinner() {
        crearSplashScreen();
        try {
            splashScreen.setVisible(true);
            hiloProgreso hilo=new hiloProgreso();
            hilo.start();
            // pausa(1000);
        } catch (Exception e) {}
    }

    public void crearSplashScreen() {
        splashLabel = new JLabel(Utiles.createImageIcon("images/trofeo.png"));

        splashScreen = new JWindow();

        splashScreen.setLayout   (new BorderLayout());
        splashScreen.add         (splashLabel, BorderLayout.CENTER);

        splashScreen.pack                  ();
        splashScreen.setLocationRelativeTo (null);
        splashScreen.setAlwaysOnTop        (true);
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
            for(int i = 1; i <= 50; i++) {
                pausa(25);
            }
            splashScreen.setVisible(false);
            // Liberar Splash de memoria
            splashScreen = null;
        }
    }
}
