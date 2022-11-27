package com.zerutis.codingchallenge.service;

import com.zerutis.codingchallenge.helper.CSVHelper;
import com.zerutis.codingchallenge.repository.BankAccountsStatementsRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

        statements.forEach(System.out::println);

        repository.saveAll(statements);
    }
}
