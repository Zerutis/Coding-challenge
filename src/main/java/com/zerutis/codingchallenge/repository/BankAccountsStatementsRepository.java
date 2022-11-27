package com.zerutis.codingchallenge.repository;

import com.zerutis.codingchallenge.model.BankAccountStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountsStatementsRepository extends JpaRepository<BankAccountStatement, String> {

    @Query(
            value = "SELECT * FROM bank_account_statement WHERE operation_datetime > ?1 AND operation_datetime < ?2",
            nativeQuery = true
    )
    List<BankAccountStatement> findByOperationDate(String from, String to);
}
