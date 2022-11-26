package com.zerutis.codingchallenge.unit;

import com.zerutis.codingchallenge.controller.BankAccountsStatementsController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
@DisplayName("BankAccountStatementsController")
public class BankAccountsStatementsControllerTest {

    @Autowired
    BankAccountsStatementsController bankAccountsStatementsController;

    @Nested
    @DisplayName("uploadBankAccountStatements should")
    class Upload {

        @Test
        @DisplayName("be not implemented")
        void shouldBeNotImplemented() {
            ResponseEntity expected = new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
            ResponseEntity actual = bankAccountsStatementsController.uploadBankAccountsStatements();

            Assertions.assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("downloadBankAccountStatements should")
    class Download {

        @Test
        @DisplayName("be not implemented")
        void shouldBeNotImplemented() {
            ResponseEntity expected = new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
            ResponseEntity actual = bankAccountsStatementsController.downloadBankAccountsStatements("", "");

            Assertions.assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("queryBalance should")
    class AccountBalance {

        @Test
        @DisplayName("be not implemented")
        void shouldBeNotImplemented() {
            ResponseEntity expected = new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
            ResponseEntity actual = bankAccountsStatementsController.queryBalance("", "", "");

            Assertions.assertEquals(expected, actual);
        }
    }
}
