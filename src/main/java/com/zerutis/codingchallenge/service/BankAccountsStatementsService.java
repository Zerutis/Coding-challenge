package com.zerutis.codingchallenge.service;

import com.zerutis.codingchallenge.repository.BankAccountsStatementsRepository;
import org.springframework.stereotype.Service;

@Service
public class BankAccountsStatementsService {

    final BankAccountsStatementsRepository repository;

    public BankAccountsStatementsService(BankAccountsStatementsRepository repository) {
        this.repository = repository;
    }
}
