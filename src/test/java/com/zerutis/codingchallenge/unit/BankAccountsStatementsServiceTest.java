package com.zerutis.codingchallenge.unit;

import com.zerutis.codingchallenge.helper.CSVHelper;
import com.zerutis.codingchallenge.repository.BankAccountsStatementsRepository;
import com.zerutis.codingchallenge.service.BankAccountsStatementsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
        void callSaveAll() throws IOException {
            service.saveBankAccountsStatements(mockFile);

            verify(repository, times(1)).saveAll(any());
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
}
