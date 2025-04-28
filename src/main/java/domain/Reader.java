package domain;

import com.github.pjfanning.xlsx.StreamingReader;
import com.github.pjfanning.xlsx.exceptions.MissingSheetException;
import org.apache.poi.ss.usermodel.*;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Reader {

    public static boolean isValidNumber(BlNumber blNumber) {
        File file = blNumber.getEXCEL_FILE();
        String origin = blNumber.getORIGIN();
        String destination = blNumber.getDESTINATION();
        int number = blNumber.getRandom4Digit();

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
                    if (number == Integer.parseInt(blNumberCell.getStringCellValue())){
                        return false;
                    }
                } else if(blNumberCell.getCellType() == CellType.NUMERIC){
                    if (number == blNumberCell.getNumericCellValue()) {
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "파일을 읽을 수 없습니다. 프로그램이 종료됩니다. \n" + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (MissingSheetException e) {
            JOptionPane.showMessageDialog(null, "시트에 없는 새로운 출발지>도착지입니다.", "오류", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            throw new RuntimeException("검증 작업에서 알 수 없는 오류가 발생했습니다. \n" + e.getMessage());
        }

        return true;
    }

}
