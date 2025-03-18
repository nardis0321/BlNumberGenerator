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

        JButton fileButton = new JButton("파일 선택");
        JLabel fileLabel = new JLabel("선택된 파일 없음");
        File[] selectedFile = {null}; // 선택한 파일을 저장할 배열

        JButton runButton = new JButton("RUN");
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);

        JLabel infoLabel = new JLabel();
        infoLabel.setHorizontalAlignment(0);
        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);

        // 파일 선택 버튼 이벤트
        fileButton.addActionListener(e -> {
            File file = chooseFile();
            if (file != null) {
                selectedFile[0] = file;
                fileLabel.setText("선택된 파일: " + file.getName());
            } else {
                fileLabel.setText("파일을 선택하세요.");
            }
        });

        // RUN 버튼 이벤트 (Controller에 요청)
        runButton.addActionListener(e -> {
            infoArea.setText("BL 번호 생성 시작...");

            String origin = originField.getText().toUpperCase();
            String destination = destinationField.getText().toUpperCase();
            int splitSeq = Integer.parseInt(splitSeqField.getText());
            File file = selectedFile[0];

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
        frame.add(fileButton);
        frame.add(fileLabel);
        frame.add(runButton);
        frame.add(resultArea);

        frame.add(infoLabel);
        frame.add(infoArea);

        frame.setVisible(true);
    }

    // GUI를 사용하여 파일 선택하는 메서드
    public static File chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("엑셀 파일 선택"); // 창 제목 설정
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false); // 단일 파일 선택

        // .xlsx 파일만 선택하도록 필터 추가
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File file) {
                // 디렉토리 또는 .xlsx 파일만 허용
                return file.isDirectory() || file.getName().endsWith(".xlsx");
            }

            @Override
            public String getDescription() {
                return "엑셀 파일 (*.xlsx)";
            }
        });

        int result = fileChooser.showOpenDialog(null); // 파일 선택 창 열기
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile(); // 선택한 파일 반환
        }
        return null;
    }
}
