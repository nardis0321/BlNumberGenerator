package domain;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Writer {

    public static void writeToExcel(BlNumber blNumber) {
        File file = blNumber.getEXCEL_FILE();

        try (
                FileInputStream fileInputStream = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(fileInputStream)
        ) {
            // 1. All 시트에 bl번호 넣기
            Sheet sheetNameAll = workbook.getSheet("ALL");
            if(sheetNameAll == null) sheetNameAll = workbook.createSheet("ALL");
            writeBlNumbers(sheetNameAll, blNumber);

            // 2. 개별 시트에 난수 넣기
            String sheetName = blNumber.getORIGIN() + ">" + blNumber.getDESTINATION();
            Sheet sheet = workbook.getSheet(sheetName);
            if(sheet == null) sheet = workbook.createSheet(sheetName);
            writeRandom4Digit(sheet, blNumber);

            // 3. 엑셀 파일에 변경 사항 저장
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "쓰기 작업 도중 오류가 발생했습니다. \n" + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException();
        }
    }

    private static void writeBlNumbers(Sheet sheetNameAll, BlNumber blNumber) {
        blNumber.getBlNumbers().forEach(bl -> {
            Row row = sheetNameAll.createRow(getNextRowNum(sheetNameAll));
            Cell cell = row.createCell(0);
            cell.setCellValue(bl);
        });
    }

    private static void writeRandom4Digit(Sheet sheet, BlNumber blNumber){
        Row row = sheet.createRow(getNextRowNum(sheet));
        Cell cell = row.createCell(0);
        cell.setCellValue(blNumber.getRandom4DigitFormatted());
    }

    private static int getNextRowNum(Sheet sheet){
        if(sheet.getPhysicalNumberOfRows() == 0) return 0;

        return sheet.getLastRowNum()+1;
    }

}
