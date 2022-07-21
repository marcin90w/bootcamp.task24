package pl.javny.expensesrepository.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ReckonSave {

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        final DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        boolean exit = false;
        String type = "";
        type = getType(scanner, exit, type);
        String description = scanner.nextLine();
        System.out.println("podaj kwotę");
        Double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Podaj datę YYYY-MM-DD");
        String dateToParse = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateToParse, datePattern);
        Reckon reckon = new Reckon(type, description, amount, date);

        ReckonDao reckonDao = new ReckonDao();
        reckonDao.addToBudget(reckon);

    }

    private static String getType(Scanner scanner, boolean exit, String type) {
        while (!exit) {
            System.out.println("Wpisz income lub outcome");
            type = scanner.nextLine();
            if (type.equals("income") || type.equals("outcome")) {
                exit = true;
            } else {
                System.out.println("Wprowadzono niepoprawny typ! podaj jeszcze raz");
            }
        }
        System.out.println("Podaj opis");
        return type;
    }
}
