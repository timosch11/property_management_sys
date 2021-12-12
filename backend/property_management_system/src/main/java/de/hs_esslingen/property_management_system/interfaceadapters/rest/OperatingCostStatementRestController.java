package de.hs_esslingen.property_management_system.interfaceadapters.rest;

import de.hs_esslingen.property_management_system.entities.OperatingCostStatementRenter;
import de.hs_esslingen.property_management_system.usecases.OperatingCostStatementUseCase;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/operating-cost-statements")
public class OperatingCostStatementRestController {

    @Autowired
    private OperatingCostStatementUseCase operatingCostStatementUseCase;

    @GetMapping
    public Map<String, List<OperatingCostStatementRenter>> getAllRenterWithTransactions() {
        List<OperatingCostStatementRenter> operatingCostStatementRenterList = operatingCostStatementUseCase.getAllRentersWithTransactions();
        Map<String, List<OperatingCostStatementRenter>> operatingCostStatementRenterMap = new HashMap<>();
        operatingCostStatementRenterMap.put("operatingCostStatementRenters", operatingCostStatementRenterList);
        return operatingCostStatementRenterMap;
    }

    @GetMapping("/q")
    public ResponseEntity getOperatingCostStatementByRenterId (@RequestParam("renterId") Integer renterId, @RequestParam("year") Year year) {
        Workbook workbook = operatingCostStatementUseCase.getOperatingCostStatementExcelForRenterByIdAndYear(renterId, year);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] byteArray = outputStream.toByteArray();

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"operating_cost_statement_" + renterId.toString() + "_" + year.toString() + ".xlsx\"").body(byteArray);
    }
}
