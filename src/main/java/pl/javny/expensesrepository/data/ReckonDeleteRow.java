package pl.javny.expensesrepository.data;

import java.util.Scanner;

public class ReckonDeleteRow {

    public static void main(String[] args) {

        ReckonDao reckonDao = new ReckonDao();
        Scanner scanner = new Scanner(System.in);
        String id = scanner.nextLine();
        boolean rowDeleted = reckonDao.delete(id);
        if (rowDeleted) {
            System.out.println("Wpis o indeksie - " + id + " - został usunięty");
        }
    }
}
