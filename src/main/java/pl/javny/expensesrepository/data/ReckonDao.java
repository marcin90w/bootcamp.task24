package pl.javny.expensesrepository.data;

import java.sql.Connection;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReckonDao {

    private final DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public void insertSampleData(String sql) {
        Connection connection = connect();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void addToBudget(Reckon reckon) {

        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        try {
            String sql = String.format("INSERT INTO budget.expenses (type, description, amount, date) VALUES('%s', '%s', ?, '%tF')",
                    reckon.getType(), reckon.getDescription(), reckon.getDate());
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, reckon.getAmount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Nie udało się zapisać rozliczenia w bazie danych: " + e.getMessage());
        }
        closeConnection(connection);
    }

    protected boolean updateRow(Reckon reckon) {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = reckon.getDate().format(datePattern);

        try {
            String sql = "UPDATE expenses SET type = ?, description = ?, amount = ?, date = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, reckon.getType());
            preparedStatement.setString(2, reckon.getDescription());
            preparedStatement.setInt(3, reckon.getAmount());
            preparedStatement.setDate(4, Date.valueOf(date));
            preparedStatement.setInt(5, reckon.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Nie udało się zapisać rozliczenia w bazie danych: " + e.getMessage());
        }
        closeConnection(connection);
        return false;
    }

    protected List<Reckon> getOutcomes() {
        String sql = "SELECT * FROM budget.expenses WHERE type = 'outcome'";
        List<Reckon> outcomesList = getReconsList(sql);
        closeConnection(connect());
        return outcomesList;
    }

    protected List<Reckon> getIncomes() {
        String sql = "SELECT * FROM budget.expenses WHERE type = 'income'";
        List<Reckon> incomesList = getReconsList(sql);
        closeConnection(connect());
        return incomesList;
    }

    private List<Reckon> getReconsList(String sql) {
        List<Reckon> arrayList = new ArrayList<>();
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String type = resultSet.getString("type");
                String description = resultSet.getString("description");
                int amount = resultSet.getInt("amount");
                String date = resultSet.getString("date");
                LocalDate createdDate = LocalDate.parse(date, datePattern);
                arrayList.add(new Reckon(id, type, description, amount, createdDate));
            }
            return arrayList;
        } catch (SQLException e) {
            System.out.println("nie udało się wczytać rozliczenia: " + e.getMessage());
        }
        closeConnection(connection);
        return null;
    }

    protected boolean delete(String index) {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "DELETE FROM budget.expenses WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, index);
            preparedStatement.executeUpdate(sql);
            closeConnection(connection);
            return true;
        } catch (SQLException e) {
            System.out.println("Nie udało się usunąć podanego wpisu: " + e.getMessage());
        }
        closeConnection(connection);
        return false;
    }

    private Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String url = "jdbc:mysql://localhost:3306/budget?serverTimezone=UTC";
        try {
            return DriverManager.getConnection(url, "root", "admin");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
