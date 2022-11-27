package com.zerutis.codingchallenge.helper;

import com.zerutis.codingchallenge.model.BankAccountStatement;
import org.apache.commons.csv.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class CSVHelper {
    public static String TYPE = "text/csv";
    private String CHARSET_NAME = "UTF-8";

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public List<BankAccountStatement> csvToBankAccountStatement(InputStream inputStream) {
        try (
             BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, CHARSET_NAME));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
        ) {
            List<BankAccountStatement> bankAccountStatementsList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            csvRecords.forEach((record) -> {
                BankAccountStatement statement = new BankAccountStatement(
                        record.get("AccountNumber"),
                        LocalDateTime.parse(record.get("OperationDateTime")),
                        record.get("Beneficiary"),
                        record.get("Comment"),
                        new BigDecimal(record.get("Amount")),
                        record.get("Currency")
                );

                bankAccountStatementsList.add(statement);
            });

            return bankAccountStatementsList;
        } catch (IOException e) {
            throw new RuntimeException("failed to parse CSV file: " + e.getMessage());
        }
    }

    public ByteArrayInputStream bankAccountStatementToCSV(List<BankAccountStatement> bankAccountStatementList) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (
             ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)
        ) {
            for (BankAccountStatement bankAccountStatement : bankAccountStatementList) {
                List<String> data = Arrays.asList(
                        bankAccountStatement.getAccountNumber(),
                        bankAccountStatement.getOperationDateTime().toString(),
                        bankAccountStatement.getBeneficiary(),
                        bankAccountStatement.getComment(),
                        bankAccountStatement.getAmount().toString(),
                        bankAccountStatement.getCurrency()
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("failed to import data to CSV file: " + e.getMessage());
        }
    }
}
