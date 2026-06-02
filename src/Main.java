import javax.swing.SwingUtilities;
public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            StudentFrame frame = new StudentFrame();
            frame.setVisible(true);
        });
    }
}
