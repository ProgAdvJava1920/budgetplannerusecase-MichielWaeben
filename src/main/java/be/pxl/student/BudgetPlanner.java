package be.pxl.student;

import be.pxl.student.util.BudgetPlannerImporter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BudgetPlanner {
    public static void main(String[] args) {
        BudgetPlannerImporter budgetPlannerImporter = new BudgetPlannerImporter();
        Path path = Paths.get(System.getProperty("user.dir")).resolve("src/main/resources/account_payments.csv");
        budgetPlannerImporter.importCV(path);
    }
}
