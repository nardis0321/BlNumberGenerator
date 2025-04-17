import interfaces.Controller;
import interfaces.View;

import javax.swing.*;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
        View view = new View();
        Controller controller = new Controller(view);
        view.setController(controller);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("BL Number Generator for 시윤♡");

            URL iconUrl = Main.class.getResource("/icon.png");
            if (iconUrl != null) {
                ImageIcon icon = new ImageIcon(iconUrl);
                frame.setIconImage(icon.getImage());
            }

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setContentPane(view.getPanel()); // 조립
            frame.setVisible(true);
        });
    }
}
