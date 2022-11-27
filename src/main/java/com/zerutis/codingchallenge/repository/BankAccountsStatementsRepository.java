package com.zerutis.codingchallenge.repository;

import com.zerutis.codingchallenge.model.BankAccountStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountsStatementsRepository extends JpaRepository<BankAccountStatement, Long> { }
