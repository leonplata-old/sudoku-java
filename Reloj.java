import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.*;

/**
 * @author Guillermo L. Plata Camacho.
 *         J. Gustavo Cossio Crispin,
 * @version 2011.04.02
 */
public class Reloj extends JDialog {
    public static String JUGADOR = "Jugador: ";

    private JLabel     jugadorLabel;
    private JTextField tiempoTF;
    private boolean detener;
    public Reloj(Frame parent) {
        super(parent, false);

        iniciarComponentes();

        setTitle                 ("Reloj");
        setVisible               (true);
        pack                     ();
        setDefaultCloseOperation (HIDE_ON_CLOSE);
        setAlwaysOnTop           (true);
    }

    private void iniciarComponentes() {
        this.getContentPane().setBackground(Color.black);

        tiempoTF     = new JTextField();
        jugadorLabel = new JLabel(Utiles.createImageIcon("images/Jugador.png"));

        tiempoTF.setEditable            (false);
        tiempoTF.setBackground          (Color.black);
        tiempoTF.setColumns             (8);
        tiempoTF.setFont                (new Font("Courier New", 1, 30));
        tiempoTF.setForeground          (Color.white);
        tiempoTF.setText                ("00:00:00");
        tiempoTF.setHorizontalAlignment (SwingConstants.CENTER);

        jugadorLabel.setForeground          (Color.white);
        jugadorLabel.setFont                (new Font("Lucida Console", 1, 16));
        jugadorLabel.setText                (JUGADOR);
        jugadorLabel.setHorizontalAlignment (SwingConstants.CENTER);

        setLayout(new GridLayout(2, 1));
        detener = false;
        this.add(jugadorLabel);
        this.add(tiempoTF);
    }

    public void setNick(String usuario) {
        jugadorLabel.setText(JUGADOR + usuario);
        pack();
    }

    public void detener(boolean det) {
        detener = det;
    }

    public void insertarTiempo(int[] n) {
        String total = "";
        if(n[0] < 10)
            total += "0";
        total += n[0];
        total += ":";
        if(n[1] < 10)
            total += "0";
        total += n[1];
        total += ":";
        if(n[2] < 10)
            total += "0";
        total += n[2];
        if(detener == false){
            tiempoTF.setText(total); }

    }

    public void reset() {
        tiempoTF.setText("00:00:00");
    }
}
