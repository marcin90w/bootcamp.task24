package pl.javny.expensesrepository.data;

import java.util.Scanner;

public class ReckonLogic {

    private final String save = "1";
    private final String update = "2";
    private final String delete = "3";
    private final String incomes = "4";
    private final String outcomes = "5";

    public void runAppLogic(Scanner scanner) {
        while (true) {

            printOptions();
            String userInput = scanner.nextLine();

            switch (userInput) {
                case save -> ReckonSave.main(new String[0]);
                case update -> ReckonUpdate.main(new String[0]);
                case delete -> ReckonDeleteRow.main(new String[0]);
                case incomes -> ReckonIncome.main(new String[0]);
                case outcomes -> ReckonOutcome.main(new String[0]);
                default -> System.out.println("Błędny wybór!");
            }
        }
    }

    private void printOptions() {
        System.out.println("Wybierz opcję:");
        System.out.println("0 - Wyjście z programu");
        System.out.println("1 - Dodaj transakcję");
        System.out.println("2 - modyfikuj transakcję");
        System.out.println("3 - usuń transakcję");
        System.out.println("4 - wyświetl wszystkie przychody");
        System.out.println("5 - wyświetl wszystkie wydatki");
    }
}
