import java.io.File;

public class BlNumber {

    private final String PREFIX = "RSA";
    private final String EXCEL_FILE_PATH = "./BL NO. LIST.xlsx";
    private final String ORIGIN;
    private final String DESTINATION;
    private int number;
    private final int SPLIT_SEQUENCE;
    private final File EXCEL_FILE;

    public BlNumber(String origin, String DESTINATION, int SPLIT_SEQUENCE) {
        this.ORIGIN = origin;
        this.DESTINATION = DESTINATION;
        number = (int) (Math.random() * 10000);
        this.SPLIT_SEQUENCE = SPLIT_SEQUENCE - 1;
        EXCEL_FILE = new File(EXCEL_FILE_PATH);
        try {
            if (!EXCEL_FILE.exists()) {
                throw new Exception("파일이 존재하지 않습니다: " + EXCEL_FILE.getAbsolutePath());
            }
            System.out.println("파일 확인 성공: " + EXCEL_FILE.getAbsolutePath());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void reGenerate(){
        number = (int) (Math.random() * 10000);
    }

    public int getNumber() {
        return number;
    }

    public String getStringNumber() {
        return String.format("%04d", number);
    }

    public String getOrigin() {
        return ORIGIN;
    }

    public String getDESTINATION() {
        return DESTINATION;
    }

    public String getBlNumber(){
        return String.format("%s%s%s%04d%02d", PREFIX, ORIGIN, DESTINATION, number, SPLIT_SEQUENCE);
    }

    public File getEXCEL_FILE(){
        return EXCEL_FILE;
    }
}