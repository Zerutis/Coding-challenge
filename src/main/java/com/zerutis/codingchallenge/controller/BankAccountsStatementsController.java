package com.zerutis.codingchallenge.controller;

import com.zerutis.codingchallenge.helper.CSVHelper;
import com.zerutis.codingchallenge.service.BankAccountsStatementsService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/bank-accounts-statements")
public class BankAccountsStatementsController {

    final BankAccountsStatementsService service;

    public BankAccountsStatementsController(BankAccountsStatementsService service) {
        this.service = service;
    }

    @GetMapping("/csv")
    public ResponseEntity<Object> downloadBankAccountsStatements(
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to
    ) {
        String filename = "bank-accounts-statements.csv";
        InputStreamResource file;

        if(from != null && to != null) {
            file = new InputStreamResource(service.loadBankAccountsStatements(from, to));
        }
        else {
            file = new InputStreamResource(service.loadBankAccountsStatements());

        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @PostMapping("/csv")
    public ResponseEntity<String> uploadBankAccountsStatements(@RequestParam("file") MultipartFile file) {
        if (file != null && CSVHelper.hasCSVFormat(file)) {
            try {
                service.saveBankAccountsStatements(file);

                return ResponseEntity.status(HttpStatus.OK).body("");

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload CSV file");
    }

    @GetMapping("/account/{accountNumber}/balance")
    public ResponseEntity<BigDecimal> queryBalance(
            @PathVariable String accountNumber,
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to
    ) {
        BigDecimal balance;

        if(from != null && to != null) {
            balance = service.queryAmountOf(accountNumber, from, to);
        }
        else {
            balance = service.queryAmountOf(accountNumber);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(balance);
    }
}
