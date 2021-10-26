package seedu.duke.ui;

import seedu.duke.data.AllRecordList;
import seedu.duke.data.records.Category;
import seedu.duke.data.records.Expenditure;
import seedu.duke.data.records.Loan;

import java.util.ArrayList;
import java.util.Scanner;

import static seedu.duke.common.Messages.MESSAGE_EXIT;

public class TextUi {

    /**
     * A platform independent line separator.
     */
    public static final String LS = System.lineSeparator();

    private static final String DIVIDER = "========================================================";
    private static final String LOGO = "  ____            _            _ _______             _\n"
            + " |  _ \\          | |          | |__   __|           | |\n"
            + " | |_) |_   _  __| | __ _  ___| |_ | |_ __ __ _  ___| | _____ _ __\n"
            + " |  _ <| | | |/ _` |/ _` |/ _ \\ __|| | '__/ _` |/ __| |/ / _ \\ '__|\n"
            + " | |_) | |_| | (_| | (_| |  __/ |_ | | | | (_| | (__|   <  __/ |\n"
            + " |____/ \\__,_|\\__,_|\\__, |\\___|\\__||_|_|  \\__,_|\\___|_|\\_\\___|_|\n"
            + "                     __/ |\n"
            + "                    |___/";

    private static final String buffer = "     ";

    private final Scanner in;

    public TextUi() {
        in = new Scanner(System.in);
    }

    //    public static void showRecordsListView(RecordList budgetList) {
    //        int budgetListLength = RecordList.numberOfRecords;
    //        for (int i = 0; i < budgetListLength; i += 1) {
    //            System.out.println(budgetList.getRecord(i));
    //        }
    //    }

    public static void showWelcomeMessage() {
        System.out.println(DIVIDER + LS
                + LOGO + LS
                + "    Hello! I'm Budget Tracker\n"
                + "    What entries do you wish to enter today?\n"
                + DIVIDER);
    }

    public static void showExpenditureAddedMessage(Expenditure addedExpenditure, boolean isLoadingStorage,
                                                   AllRecordList recordList) {
        if (!isLoadingStorage) {
            System.out.println("Expenditure successfully added!"
                    + LS
                    + "Description: " + addedExpenditure.getDescription()
                    + "\nAmount: $" + addedExpenditure.getAmount()
                    + "\nDate: " + addedExpenditure.getDate()
                    + "\nCategory: " + addedExpenditure.getCategory());

            int month = addedExpenditure.getMonth();
            double amount = recordList.getBudget(month).getAmount();
            double totalSpending = 0.0;

            for (int i = 0; i < recordList.getExpenditureListSize(month); i += 1) {
                totalSpending += recordList.getExpenditure(i, month).getAmount();
            }

            System.out.println("Total Spent: $" + totalSpending);

            double amountLeft = amount - totalSpending;

            double percentageLeft;
            if (amountLeft > 0) {
                percentageLeft = (amountLeft / amount) * 100;
                System.out.print("Percentage of Budget Left: ");
            } else {
                percentageLeft = (totalSpending / amount) * 100;
                System.out.print("You overspend your Budget by: ");
            }

            System.out.printf("%.2f", percentageLeft);
            System.out.println("%");
            System.out.println(DIVIDER);
        }
    }

    public static void showBudgetAddedMessage(double amount, boolean isLoadingStorage) {
        if (!isLoadingStorage) {
            System.out.println("Your budget of "
                    + amount
                    + " for this month is successfully added!"
                    + LS
                    + DIVIDER);
        }
    }

    public static void showLoanAddedMessage(Loan newLoan, boolean isLoadingStorage) {
        if (!isLoadingStorage) {
            System.out.println("Loan successfully added!"
                    + LS
                    + newLoan.getName() + " owes you: $" + newLoan.getAmount()
                    + LS
                    + "Date of loan: " + newLoan.getDate()
                    + LS
                    + DIVIDER);
        }
    }

    public static String getMonth(int month) {
        String monthString = null;
        switch (month) {
        case 1:
            monthString = "January";
            break;
        case 2:
            monthString = "February";
            break;
        case 3:
            monthString = "March";
            break;
        case 4:
            monthString = "April";
            break;
        case 5:
            monthString = "May";
            break;
        case 6:
            monthString = "June";
            break;
        case 7:
            monthString = "July";
            break;
        case 8:
            monthString = "August";
            break;
        case 9:
            monthString = "September";
            break;
        case 10:
            monthString = "October";
            break;
        case 11:
            monthString = "November";
            break;
        case 12:
            monthString = "December";
            break;
        default:
            break;
        }
        return monthString;
    }

    public static void showRecordsListView(AllRecordList records, int month, boolean isListAll) {
        if (isListAll) {
            for (int i = 1; i <= 12; i++) {
                printRecordList(records, i);
            }
        } else {
            printRecordList(records, month);
        }
    }

    private static void printRecordList(AllRecordList records, int i) {
        String monthString = getMonth(i);
        double totalSpending = 0.0;
        double currentMonthBudget = records.getBudget(i).getRawValue();
        ArrayList<Expenditure> currentMonthRecordList = records.getExpenditureRecords(i);

        for (int j = 0; j < currentMonthRecordList.size(); j++) {
            totalSpending += currentMonthRecordList.get(j).getAmount();
        }
        String budget = "";
        boolean printInfo = true;

        if (currentMonthBudget == 0) {
            budget = " Budget has not been allocated!";
            printInfo = false;
        } else {
            budget = records.getBudget(i).toString();
        }

        if (totalSpending > currentMonthBudget && currentMonthBudget > 0 && printInfo) {
            System.out.println("You are spending too much for " + monthString + "!");
            double percentage = (totalSpending / currentMonthBudget) * 100;
            System.out.printf("%.2f", percentage);
            System.out.println("% of your overall budget has been spent");
        } else if (printInfo) {
            System.out.println("You did not overspend for " + monthString + ", Good JOB!");
            double percentage = (totalSpending / currentMonthBudget) * 100;
            System.out.println(percentage + "% of your overall budget has been spent");
        }
        getMonthListView(records, i, monthString, budget);
    }

    /**
     * Possible error: Names/Descriptions longer than 20characters get truncated.
     */
    private static void getMonthListView(AllRecordList list, int month, String monthString, String budget) {
        System.out.println("Your budget for " + monthString + ":" + budget + LS
                + "Your expenditures:");
        if (list.getMonthListSize(month) > 0) {
            System.out.printf("%-20.20s  %-20.20s %-20.20s %-20.20s%n", "  Information", "   | Amount",
                    "   | Date ", "   | Category");
            printEnumeratedExpenditureList(list.getExpenditureRecords(month));
        } else {
            System.out.println("No Expenditure records yet.");
        }
        System.out.println("Your loans: ");
        if (list.getLoanListSize(month) > 0) {
            System.out.printf("%-20.20s  %-20.20s %-20.20s%n", "  Debtor name", "   | Amount", "   | Date ");
            printEnumeratedLoanList(list.getLoanRecords(month));
        } else {
            System.out.println("No Loan records yet.");
        }
        System.out.println(DIVIDER);
    }

    private static void printEnumeratedExpenditureList(ArrayList<Expenditure> monthExpenditureList) {
        for (int i = 0; i < monthExpenditureList.size(); i++) {
            Expenditure currentExpenditure = monthExpenditureList.get(i);
            System.out.println(i + 1 + "." + currentExpenditure);
        }
    }

    private static void printEnumeratedLoanList(ArrayList<Loan> monthLoanList) {
        for (int i = 0; i < monthLoanList.size(); i++) {
            Loan currentLoan = monthLoanList.get(i);
            System.out.println(i + 1 + "." + currentLoan);
        }
    }

    public static void showSingleExpenditureDeletedMessage(int indexOfExpenditure, Expenditure deletedExpenditure) {
        System.out.println("Successfully deleted Expenditure " + indexOfExpenditure + "." + deletedExpenditure);
        System.out.println(DIVIDER);
    }

    public static void showSingleLoanDeletedMessage(int indexOfLoan, Loan deletedLoan) {
        System.out.println("Successfully deleted Loan " + indexOfLoan + "." + deletedLoan);
        System.out.println(DIVIDER);
    }

    public static void showAllExpenditureDeletedMessage() {
        System.out.println("Successfully deleted all Expenditures in this month!");
        System.out.println(DIVIDER);
    }

    public static void showAllLoanDeletedMessage() {
        System.out.println("Successfully deleted all Loans in this month!");
        System.out.println(DIVIDER);
    }

    public static void showMultipleExpenditureDeletedMessage(int index, int endIndex, Expenditure deletedExpenditure) {
        System.out.println("Successfully deleted Expenditure " + index + "." + deletedExpenditure);
        if (index == endIndex) {
            System.out.println(DIVIDER);
        }
    }

    public static void showMultipleLoanDeletedMessage(int index, int endIndex, Loan deletedLoan) {
        System.out.println("Successfully deleted Loan " + index + "." + deletedLoan);
        if (index == endIndex) {
            System.out.println(DIVIDER);
        }
    }

    public static void showBudgetDeletedMessage() {
        System.out.println("Successfully deleted the Budget for this month!");
        System.out.println("Now the budget amount is 0.00!");
        System.out.println(DIVIDER);
    }

    public static void drawVerticalPercentage(double[] barPercentage, String typeOfStat) {
        System.out.println("Percentage of Money Spent");
        if (typeOfStat.equals("year")) {
            System.out.println(buffer + "JAN FEB MAR APR MAY JUN JUL AUG SEP OCT NOV DEC ");
        } else if (typeOfStat.equals("category")) {
            printCategoryString();
        }
        for (int i = 10; i > 0; i--) {
            if (i == 10) {
                System.out.print("100% ");
            } else {
                System.out.print((i * 10) + "%  ");
            }
            printRow(barPercentage, i);
            System.out.println();
        }
    }

    private static void printRow(double[] barPercentage, int i) {
        for (double v : barPercentage) {
            if ((i * 10) <= v) {
                System.out.print(" #  ");
            } else {
                System.out.print("    ");
            }
        }
    }

    private static void printCategoryString() {
        System.out.print(buffer);
        for (Category category : Category.values()) {
            System.out.printf("%s", " " + category.toString() + " ");
        }
        System.out.println();
    }

    public static void showExitMessage() {
        System.out.println(MESSAGE_EXIT + LS + DIVIDER);
    }

    /*
     * ===========================================
     *           ERROR MESSAGES
     * ===========================================
     */

    public static void showInvalidCommandMessage(String feedbackToUser) {
        System.out.println(feedbackToUser);
        System.out.println(DIVIDER);
    }

    /**
     * Prints out the divider line.
     */
    public static void printDivider() {
        System.out.println(DIVIDER);
    }


    /**
     * Reads the text entered by the user.
     *
     * @return command (full input) entered by the user.
     */
    public String getUserInput() {
        String input = in.nextLine();
        System.out.println(DIVIDER);
        return input;
    }
}
