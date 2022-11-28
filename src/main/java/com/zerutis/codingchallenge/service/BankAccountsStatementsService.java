package com.zerutis.codingchallenge.service;

import com.zerutis.codingchallenge.helper.CSVHelper;
import com.zerutis.codingchallenge.helper.DateHelper;
import com.zerutis.codingchallenge.model.BankAccountStatement;
import com.zerutis.codingchallenge.repository.BankAccountsStatementsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BankAccountsStatementsService {

    final BankAccountsStatementsRepository repository;
    final CSVHelper csvHelper;

    public void saveBankAccountsStatements(MultipartFile file) throws IOException {
        List<BankAccountStatement> statements = csvHelper.csvToBankAccountStatement(file.getInputStream());

        repository.saveAll(statements);
    }

    public ByteArrayInputStream loadBankAccountsStatements(Optional<String> from, Optional<String> to) {
        List<BankAccountStatement> bankAccountStatementList;

        if(from.isPresent() && to.isPresent()) {
            String dateFrom = from.filter(DateHelper::isValidDate).orElseThrow();
            String dateTo = to.filter(DateHelper::isValidDate).orElseThrow();

            bankAccountStatementList = repository.findByOperationDate(dateFrom, dateTo);
        } else {
            bankAccountStatementList = repository.findAll();
        }

        ByteArrayInputStream byteArrayInputStream = csvHelper.bankAccountStatementToCSV(bankAccountStatementList);
        return byteArrayInputStream;
    }

    public BigDecimal calculateBalanceOf(String accountNumber, Optional<String> from, Optional<String> to) {
        List<BigDecimal> amountList;

        if(from.isPresent() && to.isPresent()) {
            String dateFrom = from.filter(DateHelper::isValidDate).orElseThrow();
            String dateTo = to.filter(DateHelper::isValidDate).orElseThrow();

            amountList = repository.findByAccountNumberAndDate(accountNumber, dateFrom, dateTo);
        } else {
           amountList = repository.findAllByAccountNumber(accountNumber);
        }

        return calculateBalance(amountList);
    }

     private BigDecimal calculateBalance(List<BigDecimal> amountList) {
        return amountList
                .stream()
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
     }
}
