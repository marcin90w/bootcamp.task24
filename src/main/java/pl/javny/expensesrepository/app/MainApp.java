package pl.javny.expensesrepository.app;

import pl.javny.expensesrepository.data.ReckonDao;
import pl.javny.expensesrepository.data.ReckonLogic;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
    // First - Create table from file createtable.txt from /resources

        Scanner scanner = new Scanner(System.in);
        ReckonLogic app = new ReckonLogic();
        app.runAppLogic(scanner);


        /* if you want to input some samples - use this code
        Sample sample = new Sample();
        String sql = sample.readFile();
        if (sql.equals("")) {
            System.out.println("brak danych w pliku");
        } else {
            dao.insertSampleData(sql);
        }
         */
    }
}
