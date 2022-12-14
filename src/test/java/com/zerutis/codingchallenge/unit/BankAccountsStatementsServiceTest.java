package com.zerutis.codingchallenge.unit;

import com.zerutis.codingchallenge.helper.CSVHelper;
import com.zerutis.codingchallenge.model.BankAccountStatement;
import com.zerutis.codingchallenge.repository.BankAccountsStatementsRepository;
import com.zerutis.codingchallenge.service.BankAccountsStatementsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("BankAccountStatementsService")
public class BankAccountsStatementsServiceTest {

    @Autowired
    BankAccountsStatementsService service;

    @MockBean
    BankAccountsStatementsRepository repository;

    @MockBean
    CSVHelper csvHelper;

    final String accountNumber = "12345";
    final BigDecimal amount = new BigDecimal(10);
    final BankAccountStatement statement = new BankAccountStatement(
            accountNumber,
            LocalDateTime.now(),
            "Inventi",
            "",
            amount,
            "EUR"
    );
    final List<BankAccountStatement> statements = new ArrayList(){{add(statement);}};

    Optional<String> givenDate() { return Optional.of(LocalDate.now().toString()); }

    final Optional<String> dateFrom = givenDate();
    final Optional<String> dateTo = givenDate();

    @Nested
    @DisplayName("saveBankAccountsStatements should")
    class SaveBankAccountsStatements {

        final MultipartFile mockFile = mock(MultipartFile.class);


        @Test
        @DisplayName("call csvHelper.csvToBankAccountStatement")
        void callCsvToBankAccountStatements() throws IOException {
            service.saveBankAccountsStatements(mockFile);

            verify(csvHelper, times(1)).csvToBankAccountStatement(any());
        }

        @Test
        @DisplayName("call repository.saveAll")
        void callSaveAllWithStatements() throws IOException {
            when(csvHelper.csvToBankAccountStatement(any())).thenReturn(statements);

            service.saveBankAccountsStatements(mockFile);

            verify(repository, times(1)).saveAll(statements);
        }

        @Test
        @DisplayName("do not call repository.saveAll when could not parse csv")
        void doNotCallSaveAll() {
            doThrow(new RuntimeException()).when(csvHelper).csvToBankAccountStatement(any());

            try {
                service.saveBankAccountsStatements(mockFile);
            } catch (Exception ignored) {}

            verify(repository, never()).saveAll(any());
        }
    }

    @Nested
    @DisplayName("loadBankAccountsStatements should")
    class loadBankAccountsStatements {

        @Test
        @DisplayName("call repository.findAll")
        void callFindAll() {
            service.loadBankAccountsStatements(Optional.empty(), Optional.empty());

            verify(repository, times(1)).findAll();
        }

        @Test
        @DisplayName("call csvHelper.bankAccountStatementToCSV")
        void callBankAccountStatementToCSV() {
            when(repository.findAll()).thenReturn(statements);

            service.loadBankAccountsStatements(Optional.empty(), Optional.empty());

            verify(csvHelper, times(1)).bankAccountStatementToCSV(statements);
        }

        @Test
        @DisplayName("call repository.findByOperationDate")
        void callFindByOperationDate() {
            service.loadBankAccountsStatements(dateFrom, dateTo);

            verify(repository, times(1)).findByOperationDate(any(), any());
        }

        @Test
        @DisplayName("call csvHelper.bankAccountStatementToCSV when given dates")
        void callBankAccountStatementToCsvWhenGivenDates() {
            when(repository.findByOperationDate(any(), any())).thenReturn(statements);

            service.loadBankAccountsStatements(dateFrom, dateTo);

            verify(csvHelper, times(1)).bankAccountStatementToCSV(statements);
        }
    }

    @Nested
    @DisplayName("calculateBalanceOf should")
    class calculateBalanceOf {
        @Test
        @DisplayName("call repository.findAllByAccountNumber")
        void callFindAllByAccountNumber() {
            service.calculateBalanceOf(accountNumber, Optional.empty(), Optional.empty());

            verify(repository, times(1)).findAllByAccountNumber(accountNumber);
        }

        @Test
        @DisplayName("return sum of statements amount")
        void returnSumOfStatementsAmount() {
            final List<BigDecimal> amountList = new ArrayList<>(){{
                add(amount);
                add(amount);
                add(amount);
            }};
            when(repository.findAllByAccountNumber(accountNumber)).thenReturn(amountList);

            final BigDecimal actual = service.calculateBalanceOf(accountNumber,Optional.empty(), Optional.empty());

            Assertions.assertEquals(amount.multiply(new BigDecimal(3)), actual);
        }

        @Test
        @DisplayName("call repository.findAllByAccountNumberAndDate with given dates")
        void callFindAllByAccountNumberAndDate() {
            service.calculateBalanceOf(accountNumber, dateFrom, dateTo);

            verify(repository, times(1)).findByAccountNumberAndDate(accountNumber, dateFrom.get(), dateTo.get());
        }

        @Test
        @DisplayName("return sum of statements amount with given dates")
        void returnSumOfStatementsAmountWithGivenDates() {
            final List<BigDecimal> amountList = new ArrayList<>(){{
                add(amount);
                add(amount);
                add(amount);
            }};
            when(repository.findByAccountNumberAndDate(any(), any(), any())).thenReturn(amountList);

            final BigDecimal actual = service.calculateBalanceOf(accountNumber, dateFrom, dateTo);

            Assertions.assertEquals(amount.multiply(new BigDecimal(3)), actual);
        }

    }
}
