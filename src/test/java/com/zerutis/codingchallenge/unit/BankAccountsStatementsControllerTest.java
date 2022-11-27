package com.zerutis.codingchallenge.unit;

import com.zerutis.codingchallenge.controller.BankAccountsStatementsController;
import com.zerutis.codingchallenge.helper.CSVHelper;
import com.zerutis.codingchallenge.service.BankAccountsStatementsService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("BankAccountStatementsController")
public class BankAccountsStatementsControllerTest {

    @Autowired
    BankAccountsStatementsController controller;

    @MockBean
    BankAccountsStatementsService service;


    @Nested
    @DisplayName("uploadBankAccountStatements should")
    class Upload {

        final MultipartFile mockFile = mock(MultipartFile.class);

        @Test
        @DisplayName("return OK with empty body when given CSV file")
        void shouldReturnOkWhenGivenCsvFile() throws IOException {
            when(mockFile.getContentType()).thenReturn(CSVHelper.TYPE);
            doNothing().when(service).saveBankAccountsStatements(mockFile);

            ResponseEntity expected = ResponseEntity.status(HttpStatus.OK).body("");
            ResponseEntity actual = controller.uploadBankAccountsStatements(mockFile);

            Assertions.assertEquals(expected, actual);
        }

        @Test
        @DisplayName("return BAD_REQUEST with error message when saving fails")
        void shouldReturnBadRequestWhenFailedToParseCsv() throws IOException {
            String errorMessage = "Failed to parse CSV";
            when(mockFile.getContentType()).thenReturn(CSVHelper.TYPE);
            doThrow(new IOException(errorMessage)).when(service).saveBankAccountsStatements(mockFile);
            ResponseEntity expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
            ResponseEntity actual = controller.uploadBankAccountsStatements(mockFile);

            Assertions.assertEquals(expected, actual);
        }

        @Test
        @DisplayName("return BAD_REQUEST with error message when content-type is not text/csv")
        void shouldReturnBadRequestWhenGivenNotCsvFile() {
            String message = "Please upload CSV file";
            when(mockFile.getContentType()).thenReturn("");
            ResponseEntity expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
            ResponseEntity actual = controller.uploadBankAccountsStatements(null);

            Assertions.assertEquals(expected, actual);
        }

        @Test
        @DisplayName("return BAD_REQUEST with error message when given null")
        void shouldReturnBadRequestWhenGivenNull() {
            String message = "Please upload CSV file";
            ResponseEntity expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
            ResponseEntity actual = controller.uploadBankAccountsStatements(null);

            Assertions.assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("downloadBankAccountStatements should")
    class Download {

        final String filename = "bank-accounts-statements.csv";
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(filename.getBytes());
        final InputStreamResource inputStreamResource = new InputStreamResource(byteArrayInputStream);
        final String dateFrom = "2020-02-02";
        final String dateTo = "2023-02-02";

        @Test
        @DisplayName("return httpStatus.OK")
        void returnHttpStatusOK() {
            when(service.loadBankAccountsStatements()).thenReturn(byteArrayInputStream);

            ResponseEntity expected = ResponseEntity
                    .status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/csv"))
                    .body(inputStreamResource);
            ResponseEntity actual = controller.downloadBankAccountsStatements(null, null);

            Assertions.assertEquals(expected, actual);
        }

        @Test
        @DisplayName("return httpStatus.OK when given dates")
        void returnHttpStatusOKWhenGivenDates() {
            when(service.loadBankAccountsStatements(dateFrom, dateTo)).thenReturn(byteArrayInputStream);

            ResponseEntity expected = ResponseEntity
                    .status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/csv"))
                    .body(inputStreamResource);
            ResponseEntity actual = controller.downloadBankAccountsStatements(dateFrom, dateTo);

            Assertions.assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("queryBalance should")
    class AccountBalance {

        final String accountNumber = "10000";
        final String dateFrom = "2020-02-02";
        final String dateTo = "2023-02-02";
        final BigDecimal balance = new BigDecimal(10);

        @Test
        @DisplayName("return HttpStatus.OK with balance as 10")
        void returnHttpStatusOkWithBalance() {
            when(service.calculateBalanceOf(accountNumber)).thenReturn(balance);

            ResponseEntity expected = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(balance);
            ResponseEntity actual = controller.calculateBalance(accountNumber, null, null);

            Assertions.assertEquals(expected, actual);
        }

        @Test
        @DisplayName("return HttpStatus.OK with balance as 10 when given dates")
        void returnHttpStatusOkWithBalanceWhenGivenDates() {
            when(service.calculateBalanceOf(accountNumber, dateFrom, dateTo)).thenReturn(balance);

            ResponseEntity expected = ResponseEntity
                    .status(HttpStatus.OK)
                    .body(balance);
            ResponseEntity actual = controller.calculateBalance(accountNumber, dateFrom, dateTo);

            Assertions.assertEquals(expected, actual);
        }
    }
}
