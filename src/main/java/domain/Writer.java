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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Writer {

    public static void writeToExcel(BlNumber blNumber) {
        File file = blNumber.getEXCEL_FILE();

        File tempFile = new File(file.getParentFile(), file.getName() + System.currentTimeMillis());
        if (file.exists()) {
            try {
                Files.copy(file.toPath(), tempFile.toPath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        "임시 파일 작업 도중 오류가 발생했습니다. \n" + e.getMessage(),
                        "오류", JOptionPane.ERROR_MESSAGE);
            }
        }

        try (
                FileInputStream fileInputStream = new FileInputStream(tempFile);
                Workbook workbook = new XSSFWorkbook(fileInputStream);
                FileOutputStream outputStream = new FileOutputStream(tempFile)
        ) {
            // 1. All 시트에 bl번호 넣기
            Sheet sheetNameAll = workbook.getSheet("ALL");
            if(sheetNameAll == null) {
                int sheetIndex = workbook.getSheetIndex(sheetNameAll);
                if(sheetIndex == -1){
                    sheetNameAll = workbook.createSheet("ALL");
                } else {
                    sheetNameAll = workbook.getSheetAt(sheetIndex);
                }
            }
            writeBlNumbers(sheetNameAll, blNumber);

            // 2. 개별 시트에 난수 넣기
            String sheetName = blNumber.getORIGIN() + ">" + blNumber.getDESTINATION();
            Sheet sheet = workbook.getSheet(sheetName);
            if(sheet == null) sheet = workbook.createSheet(sheetName);
            writeRandom4Digit(sheet, blNumber);

            // 3. 엑셀 임시 파일에 변경 사항 저장
            workbook.write(outputStream);
            outputStream.flush();

            // 4. 원본으로 저장
            if (file.exists()) {
                file.delete();
            }
            if (!tempFile.renameTo(file)) {
                Files.copy(tempFile.toPath(), file.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
                tempFile.delete();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "쓰기 작업 도중 입출력 오류가 발생했습니다. \n" + e.getMessage(),
                    "오류", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            tempFile.delete();
            JOptionPane.showMessageDialog(null,
                    "쓰기 작업 도중 오류가 발생했습니다. \n" + e.getMessage(),
                    "오류", JOptionPane.ERROR_MESSAGE);
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
