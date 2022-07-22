package pl.javny.expensesrepository.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ReckonUpdate {

    public static void main(String[] args) {
        DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ReckonDao reckonDao = new ReckonDao();
        Scanner scanner = new Scanner(System.in);
        int index = 0;

        boolean stop = false;
        while (!stop) {
            System.out.println("Podaj nr indeksu do aktualizacji:");
            try {
                index = scanner.nextInt();
                scanner.nextLine();
                stop = true;
            } catch (InputMismatchException e) {
                System.out.println("Podaj liczbę! spróbuj jeszcze raz");
            }
        }
        System.out.println("Podaj dane do aktualizacji: ");
        System.out.println("Podaj typ: (income lub outcome");
        String type = scanner.nextLine();
        System.out.println("Podaj opis:");
        String description = scanner.nextLine();
        System.out.println("Podaj kwotę");
        int amount = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Podaj datę: pattern yyyy-mm-dd");
        String dateToParse = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateToParse, datePattern);
        Reckon reckon = new Reckon(index, type, description, amount, date);
        reckonDao.updateRow(reckon);
        System.out.println("Zaktualizowano indeks nr " + index);
    }
}
