import com.github.pjfanning.xlsx.exceptions.MissingSheetException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Writer {

    public static void writeBlNumberToExcel(BlNumber blNumber) {
        File file = blNumber.getEXCEL_FILE();

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            Workbook workbook = new XSSFWorkbook(fileInputStream);

            // All에 넣기
            Sheet sheetAll = workbook.getSheet("ALL");
            if(sheetAll != null){
                Row appendRow = sheetAll.createRow(sheetAll.getLastRowNum()+1);
                Cell writeCell = appendRow.createCell(0);
                writeCell.setCellValue(blNumber.getBlNumber());
            } else {
                sheetAll = workbook.createSheet("ALL");
                sheetAll.createRow(0).createCell(0).setCellValue(blNumber.getBlNumber());
            }

            // 개별 시트 로직

            String sheetName = blNumber.getOrigin() + ">" + blNumber.getDESTINATION();
            Sheet sheet = workbook.getSheet(sheetName);

            if(sheet != null) {

                // 새로운 행을 시트의 마지막에 추가
                int lastRowNum = sheet.getLastRowNum();
                Row newRow = sheet.createRow(lastRowNum + 1);

                // 새 행의 첫 번째 셀에 blNumber 값을 넣기
                Cell newCell = newRow.createCell(0);
                newCell.setCellValue(blNumber.getStringNumber());

                // 엑셀 파일에 변경 사항 저장
                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                    workbook.write(outputStream);
                }

                ExceptionMessage.appendMessage("엑셀 파일에 번호가 입력되었습니다.");
            } else {
                sheet = workbook.createSheet(sheetName);

                // 첫 번째 행 생성
                Row row = sheet.createRow(0);
                Cell cell = row.createCell(0);
                cell.setCellValue(blNumber.getStringNumber());  // BlNumber의 값을 셀에 작성

                // 엑셀 파일에 변경 사항 저장
                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                    workbook.write(outputStream);
                }

                ExceptionMessage.appendMessage("엑셀 파일에 번호가 입력되었습니다.");
            }
        } catch (IOException e) {
            ExceptionMessage.setMessage("엑셀 파일에 쓰기 작업 도중 오류가 발생했습니다." + e.getMessage());
        } catch (MissingSheetException e) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                Workbook workbook = new XSSFWorkbook(fileInputStream);

                String sheetName = blNumber.getOrigin() + ">" + blNumber.getDESTINATION();
                Sheet sheet = workbook.createSheet(sheetName);

                // 첫 번째 행 생성
                Row row = sheet.createRow(0);
                Cell cell = row.createCell(0);
                cell.setCellValue(blNumber.getNumber());  // BlNumber의 값을 셀에 작성

                // 엑셀 파일에 변경 사항 저장
                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                    workbook.write(outputStream);
                }

                ExceptionMessage.appendMessage("엑셀 파일에 번호가 입력되었습니다.");
            } catch (IOException ex) {
                ExceptionMessage.setMessage("엑셀 파일에 쓰기 작업 도중 오류가 발생했습니다." + e.getMessage());
            }
        }
    }

}
