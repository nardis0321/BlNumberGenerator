import com.github.pjfanning.xlsx.StreamingReader;
import com.github.pjfanning.xlsx.exceptions.MissingSheetException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Reader {

    public static boolean isValidBl(File file, String origin, String destination, int blNumber) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            // 스트리밍 리더 사용, 행 캐시와 버퍼 크기 설정
            Workbook workbook = StreamingReader.builder()
                    .rowCacheSize(1000)  // 한 번에 메모리에 올릴 행 수
                    .bufferSize(8192)   // 버퍼 크기 설정
                    .open(fileInputStream);

            String sheetName = origin + ">" + destination;
            Sheet sheet = workbook.getSheet(sheetName);

            for (Row row : sheet) { // 모든 행 순회
                Cell blNumberCell = row.getCell(0);

                if (blNumberCell.getCellType() == CellType.STRING){
                    if (blNumber == Integer.parseInt(blNumberCell.getStringCellValue())){
                        return false;
                    }
                } else if(blNumberCell.getCellType() == CellType.NUMERIC){
                    if (blNumber == blNumberCell.getNumericCellValue()) {
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            ExceptionMessage.setMessage("파일을 읽을 수 없습니다. : " + e.getMessage());
        } catch (MissingSheetException e) {
            ExceptionMessage.setMessage("시트에 없는 새로운 출발지>도착지입니다.");
        } catch (Exception e) {
            ExceptionMessage.setMessage("검증 작업에서 오류가 발생했습니다. : " + e.getMessage());
        }

        return true;
    }

}
