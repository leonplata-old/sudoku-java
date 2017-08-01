/**
 * @author Guillermo L. Plata Camacho.
 *         J. Gustavo Cossio Crispin,
 * @version 2011.04.02
 */
public class Sudoku {
    public static void main(String[] arg) {
        try {
            ListaCajaSudoku modelo      = new ListaCajaSudoku ();
            SudokuGUI       vista       = new SudokuGUI       (modelo);
            ControlSudoku   controlador = new ControlSudoku   (modelo, vista);
        }
        catch (Exception e) {}
    }
}
