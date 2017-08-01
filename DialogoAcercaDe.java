import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Muestra la ventana 'Acerca de' en la cual se puede ver una presentación
 * con los nombres de los programadores.
 * @author Guillermo L. Plata Camacho.
 *         J. Gustavo Cossio Crispin,
 * @version 2011.04.02
 */
public class DialogoAcercaDe extends JWindow implements ActionListener {

    private JButton splash;

    /** Construye la ventana de diálogo.*/
    public DialogoAcercaDe() {
        splash = new  JButton(Utiles.createImageIcon("images/SudokuAcercaDe.png"));
        splash.setMargin            (null);
        splash.setBorder            (null);
        splash.setContentAreaFilled (false);
        splash.addActionListener    (this);

        add                   (splash);
        pack                  ();
        setLocationRelativeTo (null);
        setVisible            (true);
        setAlwaysOnTop        (true);
    }

    /** Cierra la ventana de diálogo.*/
    @Override
    public void actionPerformed(ActionEvent evt) {
        setVisible (false);
        dispose    ();
    }
}
