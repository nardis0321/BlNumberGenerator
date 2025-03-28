import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

public class View {

    private final Controller controller;
    private final ExceptionMessage exceptionMessage;

    public View() {
        this.exceptionMessage = ExceptionMessage.of();
        this.controller = Controller.getInstance();
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("BL Number Generator for 시윤♡");

        URL iconUrl = getClass().getResource("/icon.png");
        if (iconUrl != null) {
            ImageIcon icon = new ImageIcon(iconUrl);
            frame.setIconImage(icon.getImage());
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 450);
        frame.setLayout(new GridLayout(8, 2, 5, 5));

        JLabel prefixLabel = new JLabel("PREFIX");
        JTextField prefixField = new JTextField("RSA");
        prefixField.setEditable(false);
        prefixLabel.setHorizontalAlignment(0);

        JLabel originLabel = new JLabel("출발지");
        JTextField originField = new JTextField();
        originLabel.setHorizontalAlignment(0);

        JLabel destinationLabel = new JLabel("도착지");
        JTextField destinationField = new JTextField();
        destinationLabel.setHorizontalAlignment(0);

        JLabel splitSeqLabel = new JLabel("Split into");
        JTextField splitSeqField = new JTextField("1"); // 기본값 1
        splitSeqLabel.setHorizontalAlignment(0);

        JButton runButton = new JButton("RUN");
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);

        JLabel infoLabel = new JLabel();
        infoLabel.setHorizontalAlignment(0);
        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);

        // RUN 버튼 이벤트 (Controller에 요청)
        runButton.addActionListener(e -> {
            infoArea.setText("BL 번호 생성 시작...");

            String origin = originField.getText().toUpperCase();
            String destination = destinationField.getText().toUpperCase();
            int splitSeq = Integer.parseInt(splitSeqField.getText());

            var initBlNumber = new BlNumber(origin, destination, splitSeq);
            File file = initBlNumber.getEXCEL_FILE();

            String result = controller.generateBlNumber(origin, destination, splitSeq, file);
            resultArea.setText(result);

            infoArea.setText(exceptionMessage.getMessage());
            ExceptionMessage.setMessage("");
        });

        frame.add(prefixLabel);
        frame.add(prefixField);

        frame.add(originLabel);
        frame.add(originField);
        frame.add(destinationLabel);
        frame.add(destinationField);
        frame.add(splitSeqLabel);
        frame.add(splitSeqField);
        frame.add(runButton);
        frame.add(resultArea);

        frame.add(infoLabel);
        frame.add(infoArea);

        frame.setVisible(true);
    }

}
