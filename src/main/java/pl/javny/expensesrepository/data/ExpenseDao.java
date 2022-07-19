package pl.javny.expensesrepository.data;

import java.sql.Connection;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ExpenseDao {

    private final Connection connection;
    private Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public ExpenseDao() {
        try {
             this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/budget?serverTimezone=UTC",
                    "root", "admin");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertSampleData(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        String option = "";
        while (!option.equals("0")) {
            printOptions();
            option = scanner.nextLine();
            switch (option) {
                case "1" -> addToBudget();
                case "2" -> update();
                case "3" -> delete();
                case "4" -> printIncomes();
                case "5" -> printOutcomes();
                default -> {
                    option = "0";
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private void addToBudget() {
        Expense expense = loadInfo();
        //tutaj cos mam nie tak z formatowaniem daty....
        String sql = String.format("INSET INTO budget.expenses (type, description, amount, date) VALUES('%d', '%d', %.2f, %1$tY-%1$tb-%1$tm)",
                expense.getType(), expense.getDescription(), expense.getAmount(), expense.getDate());
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                expense.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        private Expense loadInfo() {
        String type = "";
        String description = "";
        Double amount = 0.00;
        LocalDate date = null;
        boolean exit = false;
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
        description = scanner.nextLine();
        System.out.println("podaj kwotę");
        amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Podaj datę YYYY-MM-DD");
        String dateToParse = scanner.nextLine();
        date = LocalDate.parse(dateToParse, datePattern);
        return new Expense(type, description, amount, date);
    }

    private void printOutcomes() {
        String sql = "SELECT * FROM budget.expenses WHERE type = 'outcome'";
        printInfoFromQuery(sql);
    }

    private void printIncomes() {
        String sql = "SELECT * FROM budget.expenses WHERE type = 'income'";
        printInfoFromQuery(sql);
    }

    private void printInfoFromQuery(String sql) {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");
                double amount = resultSet.getDouble("amount");
                String date = resultSet.getString("date");
                LocalDate createdDate = LocalDate.parse(date, datePattern);
                Expense expense = new Expense(id, type, description, amount, createdDate);
                System.out.println(expense);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void delete() {
        System.out.println("Podaj index pozycji do usuniecia:");
        int id = scanner.nextInt();
        scanner.nextLine();
        final String sql = "DELETE FROM budget.expenses WHERE id = " + id;
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
            System.out.println("Usunięto podany rekord");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void update() {

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
