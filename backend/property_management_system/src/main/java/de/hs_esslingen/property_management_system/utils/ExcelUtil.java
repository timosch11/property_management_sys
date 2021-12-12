package de.hs_esslingen.property_management_system.utils;

import de.hs_esslingen.property_management_system.entities.OperatingCostStatement;
import de.hs_esslingen.property_management_system.entities.Transaction;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtil {

    static public Workbook loadExcelSheetFromTemplate(String workbookName) throws IOException {
        FileInputStream file = new FileInputStream("src/main/resources/" + workbookName);
        return new XSSFWorkbook(file);
    }

    static public Workbook setAddressHeader(Workbook workbook, String[] headerValues) throws NullPointerException {
        Sheet sheet = workbook.getSheetAt(0);

        Row nameRow = sheet.getRow(1);
        Cell nameCell = nameRow.getCell(0);
        nameCell.setCellValue(headerValues[0] + " " + headerValues[1]);

        Row streetRow = sheet.getRow(2);
        Cell streetCell = streetRow.getCell(0);
        streetCell.setCellValue(headerValues[2] + " "+ headerValues [3]);

        Row postcodeRow = sheet.createRow(3);
        Cell postcodeCell = postcodeRow.createCell(0);
        postcodeCell.setCellValue(headerValues[4] + " " + headerValues[5]);

        return workbook;
    }

    static public Workbook setOperatingCostToExcel(Workbook workbook, List<OperatingCostStatement> operatingCostStatementList) {
        Sheet sheet = workbook.getSheetAt(0);
        sheet.autoSizeColumn(0);

        CreationHelper creationHelper = workbook.getCreationHelper();

        CellStyle cellStyleDate = workbook.createCellStyle();
        cellStyleDate.setDataFormat(creationHelper.createDataFormat().getFormat("dd.mm.yyyy"));

        CellStyle cellStyleMoney = workbook.createCellStyle();
        cellStyleMoney.setDataFormat(creationHelper.createDataFormat().getFormat("#,##0.00\\ €"));

        for (int i = 0; i < operatingCostStatementList.size(); i++) {
            Row row = sheet.createRow(7+i);

            Cell paymentReasonCell = row.createCell(0);
            paymentReasonCell.setCellValue(operatingCostStatementList.get(i).paymentReason);

            Cell totalAmountCell = row.createCell(1);
            totalAmountCell.setCellStyle(cellStyleMoney);
            totalAmountCell.setCellValue(operatingCostStatementList.get(i).totalAmount);

            Cell distributionKeyCell = row.createCell(2);
            switch (operatingCostStatementList.get(i).paymentReason) {
                case "Wasser/Gas":
                case "Strom":
                    distributionKeyCell.setCellValue("Personenanzahl");
                    break;
                case "Müllabfuhrgebühren":
                    distributionKeyCell.setCellValue("Behälter");
                    break;
                case "Straßenreinigungsgebühren":
                case "Grundsteuer":
                case "Kosten der Sach- und Haftpflichtversicherung":
                    distributionKeyCell.setCellValue("Wohnfläche");
                    break;
            }

            Cell transactionAmountCell = row.createCell(3);
            transactionAmountCell.setCellStyle(cellStyleMoney);
            transactionAmountCell.setCellValue(operatingCostStatementList.get(i).splittedAmount);
        }

        return workbook;
    }
}
