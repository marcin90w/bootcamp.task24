package pl.javny.expensesrepository.app;

import pl.javny.expensesrepository.data.ExpenseDao;
import pl.javny.expensesrepository.data.Sample;

public class MainApp {
    public static void main(String[] args) {
    // First - Create table from file createtable.txt from /resources

        ExpenseDao dao = new ExpenseDao();

        /* if you want to input some samples - use this code
        Sample sample = new Sample();
        String sql = sample.readFile();
        if (sql.equals("")) {
            System.out.println("brak danych w pliku");
        } else {
            dao.insertSampleData(sql);
        }
         */

        dao.run();

    }
}
