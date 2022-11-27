package com.zerutis.codingchallenge.service;

import com.zerutis.codingchallenge.helper.CSVHelper;
import com.zerutis.codingchallenge.model.BankAccountStatement;
import com.zerutis.codingchallenge.repository.BankAccountsStatementsRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class BankAccountsStatementsService {

    final BankAccountsStatementsRepository repository;
    final CSVHelper csvHelper;

    public BankAccountsStatementsService(BankAccountsStatementsRepository repository, CSVHelper csvHelper) {
        this.repository = repository;
        this.csvHelper = csvHelper;
    }

    public void saveBankAccountsStatements(MultipartFile file) throws IOException {
        List statements = csvHelper.csvToBankAccountStatement(file.getInputStream());

        repository.saveAll(statements);
    }

    public ByteArrayInputStream loadBankAccountsStatements() {
        List<BankAccountStatement> bankAccountStatementList = repository.findAll();

        ByteArrayInputStream byteArrayInputStream = csvHelper.bankAccountStatementToCSV(bankAccountStatementList);
        return byteArrayInputStream;
    }

    public ByteArrayInputStream loadBankAccountsStatements(String from, String to) {
        List<BankAccountStatement> bankAccountStatementList = repository.findByOperationDate(from, to);

        ByteArrayInputStream byteArrayInputStream = csvHelper.bankAccountStatementToCSV(bankAccountStatementList);
        return byteArrayInputStream;
    }

    public BigDecimal queryAmountOf(String accountNumber) {
        List<BigDecimal> amountList = repository.findAllByAccountNumber(accountNumber);

        return calculateBalance(amountList);
    }

    public BigDecimal queryAmountOf(String accountNumber, String from, String to) {
        List<BigDecimal> amountList = repository.findByAccountNumberAndDate(accountNumber, from, to);

        return calculateBalance(amountList);
    }

     private BigDecimal calculateBalance(List<BigDecimal> amountList) {
        return amountList
                .stream()
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
     }
}
