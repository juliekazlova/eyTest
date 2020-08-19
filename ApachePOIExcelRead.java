package ds.kontrolnaya.db;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class ApachePOIExcelRead {

    private static final String FILE__NAME = "data.xls";
    private static Driver driver=new Driver();

    public static void main(String[]args) {
        readExcel();
    }

    public static void readExcel(){
        try {

            FileInputStream excelFile = new FileInputStream(new File(FILE__NAME));
            Workbook workbook = new HSSFWorkbook(excelFile);
            for (int classNumber=1; classNumber<10; classNumber++){
                Sheet datatypeSheet = workbook.getSheetAt(classNumber);
                Iterator<Row> iterator = datatypeSheet.iterator();

                String bank="-1";
                String activeOpeningBalance="-1";
                String passiveOpeningBalance="-1";
                String debitTurnover="-1";
                String creditTurnover="-1";
                String activeOutgoingBalance="-1";
                String passiveOutgoingBalance="-1";

                while (iterator.hasNext()) {//ходим по строкам

                    Row currentRow = iterator.next();
                    Iterator<Cell> cellIterator = currentRow.iterator();
                    int cellCounter = 0;
                    while (cellIterator.hasNext()) {//ходим по клеткам строки
                        Cell currentCell = cellIterator.next();
                        switch (cellCounter % 7) {
                            case 0:
                                if (currentCell.getCellTypeEnum() == CellType.STRING) {
                                    bank = currentCell.getStringCellValue();
                                    //System.out.print("bank:"+bank+"--");
                                } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                                    bank = NumberToTextConverter.toText(currentCell.getNumericCellValue());
                                    //System.out.print("bank:"+bank+"--");
                                }
                                break;

                            case 1:
                                activeOpeningBalance = NumberToTextConverter.toText(currentCell.getNumericCellValue());
                                //System.out.print("act.op.bal:"+activeOpeningBalance+"--");
                                break;
                            case 2:
                                passiveOpeningBalance = NumberToTextConverter.toText(currentCell.getNumericCellValue());
                                //System.out.print("pas.op.bal:"+passiveOpeningBalance+"--");
                                driver.writeOpeningBalance(bank, classNumber, activeOpeningBalance, passiveOpeningBalance);
                                break;
                            case 3:
                                debitTurnover = NumberToTextConverter.toText(currentCell.getNumericCellValue());
                                //System.out.print("deb.turnover:"+debitTurnover+"--");
                                break;
                            case 4:
                                creditTurnover = NumberToTextConverter.toText(currentCell.getNumericCellValue());
                                //System.out.print("kred.turnover:"+creditTurnover+"--");
                                driver.writeTurnover(bank, classNumber, debitTurnover, creditTurnover);
                                break;
                            case 5:
                                activeOutgoingBalance = NumberToTextConverter.toText(currentCell.getNumericCellValue());
                                //System.out.print("act.out.bal:"+activeOutgoingBalance+"--");
                                break;
                            case 6:
                                passiveOutgoingBalance = NumberToTextConverter.toText(currentCell.getNumericCellValue());
                                //System.out.print("pas.out.bal:"+passiveOutgoingBalance);
                                driver.writeOutgoingBalance(bank, classNumber, activeOutgoingBalance, passiveOutgoingBalance);
                                break;

                        }
                        cellCounter++;

                    }

                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
