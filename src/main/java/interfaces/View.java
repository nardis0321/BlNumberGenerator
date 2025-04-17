package interfaces;

import javax.swing.*;
import java.awt.*;

public class View {
    private JPanel mainPanel;

    private final JTextField originField = new JTextField();
    private final JTextField destinationField = new JTextField();
    private final JTextField splitSeqField = new JTextField("1");   // 기본값 1
    private final JButton runButton = new JButton("Run");

    private final JTextArea infoArea = new JTextArea();
    private final JTextArea resultArea = new JTextArea();

    private Controller controller;

    public View() {

        setupGUI();

        // RUN 버튼 이벤트
        runButton.addActionListener(e -> {
            displayInfoMessage("RUN...");

            BlNumberDto.RegisterRequest request = new BlNumberDto.RegisterRequest(
                    originField.getText(),
                    destinationField.getText(),
                    Integer.parseInt(splitSeqField.getText())
            );

            controller.handleGenerateBlNumber(request);
        });
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private void setupGUI() {

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2, 5, 5));


        JLabel prefixLabel = new JLabel("PREFIX");
        JLabel prefixField = new JLabel("RSA");
        prefixLabel.setHorizontalAlignment(0);

        JLabel originLabel = new JLabel("출발지");
        originLabel.setHorizontalAlignment(0);

        JLabel destinationLabel = new JLabel("도착지");
        destinationLabel.setHorizontalAlignment(0);

        JLabel splitSeqLabel = new JLabel("Split into");
        splitSeqLabel.setHorizontalAlignment(0);

        infoArea.setEditable(false);
        resultArea.setEditable(false);

        JLabel blank = new JLabel();


        inputPanel.add(prefixLabel);
        inputPanel.add(prefixField);

        inputPanel.add(originLabel);
        inputPanel.add(originField);
        inputPanel.add(destinationLabel);
        inputPanel.add(destinationField);
        inputPanel.add(splitSeqLabel);
        inputPanel.add(splitSeqField);
        inputPanel.add(blank);
        inputPanel.add(runButton);

        inputPanel.add(infoArea);
        inputPanel.add(resultArea);
        JScrollPane infoPane = new JScrollPane(infoArea);
        infoPane.setPreferredSize(new Dimension(400, 50));
        JScrollPane resultPane = new JScrollPane(resultArea);

        JPanel scrollPanel = new JPanel(new BorderLayout());
        scrollPanel.add(infoPane, BorderLayout.NORTH);
        scrollPanel.add(resultPane, BorderLayout.CENTER);


        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPanel, BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public void displayResult(String blNumber) {
        resultArea.setText(blNumber);
    }

    public void displayInfoMessage(String message) {
        infoArea.setText(message);
    }

}
