package domain;

import javax.swing.*;
import java.io.File;

public class BlNumber {

    private final String PREFIX = "RSA";
    private final String EXCEL_FILE_PATH = "BL NO LIST.xlsx";
    private final String ORIGIN;
    private final String DESTINATION;
    private int random4Digit;
    private final int SPLIT_SEQUENCE;
    private final File EXCEL_FILE;

    public BlNumber(String origin, String destination, int splitSequence) {
        this.ORIGIN = origin.toUpperCase();
        this.DESTINATION = destination.toUpperCase();
        random4Digit = (int) (Math.random() * 10000);
        this.SPLIT_SEQUENCE = splitSequence - 1;
        if (splitSequence > 100) {
            int option = JOptionPane.showConfirmDialog(
                    null,
                    "Split이 100을 넘으면, 101번째 스플릿부터는 기록되지 않습니다.\n계속 진행하시겠습니까?",
                    "확인",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (option == JOptionPane.NO_OPTION) {
                throw new RuntimeException();
            }
        }

        EXCEL_FILE = new File(EXCEL_FILE_PATH);
        if (!EXCEL_FILE.exists()) {
            JOptionPane.showMessageDialog(null,
                    "파일이 존재하지 않습니다: " + EXCEL_FILE.getAbsolutePath()
                    , "오류", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException();
        }
    }

    public void reGenerate() {
        random4Digit = (int) (Math.random() * 10000);
    }

    public int getRandom4Digit() {
        return random4Digit;
    }

    public String getRandom4DigitFormatted() {
        return String.format("%04d", random4Digit);
    }

    public String getORIGIN() {
        return ORIGIN;
    }

    public String getDESTINATION() {
        return DESTINATION;
    }

    public String getBlNumber(){
        return String.format("%s%s%s%04d%02d", PREFIX, ORIGIN, DESTINATION, random4Digit, SPLIT_SEQUENCE);
    }

    public File getEXCEL_FILE(){
        return EXCEL_FILE;
    }
}