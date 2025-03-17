import java.io.File;

public class Controller {

    private static final Controller INSTANCE = new Controller();

    private Controller(){}

    public static Controller getInstance() {
        return INSTANCE;
    }

    public String generateBlNumber(String origin, String destination, int splitSeq, File file) {
        BlNumber blNumber = new BlNumber(origin, destination, splitSeq);
        if (file != null) {
            while(!Reader.isValidBl(file, origin, destination, blNumber.getNumber())){
                blNumber.reGenerate();
            }
        } else {
            ExceptionMessage.setMessage("파일이 비어 있습니다.");
            return " ";
        }

        Writer.writeBlNumberToExcel(file, blNumber);
        return blNumber.getBlNumber();
    }
}
