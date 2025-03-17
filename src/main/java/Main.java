import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(View::new);
        } catch (Exception e) {
            ExceptionMessage.appendMessage("오류 발생" + e.getMessage());
        }
    }

}
