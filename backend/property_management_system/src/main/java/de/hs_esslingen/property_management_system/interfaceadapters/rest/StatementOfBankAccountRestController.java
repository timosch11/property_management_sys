package de.hs_esslingen.property_management_system.interfaceadapters.rest;

import de.hs_esslingen.property_management_system.entities.StatementOfBankAccount;
import de.hs_esslingen.property_management_system.usecases.StatementOfBankAccountUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statement-of-bank-account")
public class StatementOfBankAccountRestController {

    @Autowired
    private StatementOfBankAccountUseCase statementOfBankAccountUseCase;

    @GetMapping
    public Map<String, List<StatementOfBankAccount>> getAllStatementsOfBankAccount() {
        List<StatementOfBankAccount> statementOfBankAccountList = statementOfBankAccountUseCase.findAllStatementsOfBankAccount();
        return addKeyForStatementOfBankAccountList(statementOfBankAccountList);
    }

    @PostMapping("/upload")
    public Map<String, String> uploadStatementOfBankAccount(@RequestParam("file") MultipartFile multipartFile) {
        String responseMessage = statementOfBankAccountUseCase.insertStatementOfBankAccountFromCSV(multipartFile);
        Map<String, String> response = new HashMap<>();
        response.put("message", responseMessage);
        return response;
    }

    private Map<String, List<StatementOfBankAccount>> addKeyForStatementOfBankAccountList(List<StatementOfBankAccount> statementOfBankAccountList) {
        Map<String, List<StatementOfBankAccount>> statementOfBankAccountMap = new HashMap<>();
        statementOfBankAccountMap.put("statementsOfBankAccount", statementOfBankAccountList);
        return statementOfBankAccountMap;
    }
}
