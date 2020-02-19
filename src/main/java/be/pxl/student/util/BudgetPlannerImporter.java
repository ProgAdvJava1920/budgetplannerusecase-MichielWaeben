package be.pxl.student.util;
import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {
    private static final Logger LOGGER = LogManager.getLogger(BudgetPlannerImporter.class);
    private PathMatcher csvMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.csv");

    public void importCV(Path pad) {
        if (!csvMatcher.matches(pad)) {
            LOGGER.error("Invalid file: .csv expected. Provided {}",pad);
        }

        if (!Files.exists(pad)) {
            LOGGER.error("File {} does not exist",pad);
        }

        try (BufferedReader reader = Files.newBufferedReader(pad)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] lines = line.split(",");
                Account account = new Account();
                account.setIBAN(lines[1]);
                account.setName(lines[0]);
                LocalDateTime datum = LocalDateTime.parse(lines[3], DateTimeFormatter.ofPattern("dd-M-yyyy hh:mm:ss"));
                Payment payment = new Payment(LocalDateTime.parse(lines[3]),Float.parseFloat(lines[4]),lines[5],lines[6]);
                List<Payment> payments = new ArrayList<>();
                payments.add(payment);
                account.setPayments(payments);
                System.out.println(account.toString());
            }
        } catch (IOException e) {
            LOGGER.fatal("An error occured while reading : {}", pad);
        }
    }
}