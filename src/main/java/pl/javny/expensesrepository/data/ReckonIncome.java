package pl.javny.expensesrepository.data;

import java.util.List;

class ReckonIncome {

    public static void main(String[] args) {
        ReckonDao reckonDao = new ReckonDao();
        List<Reckon> incomes = reckonDao.getIncomes();
        for (Reckon income : incomes) {
            System.out.println(income);
        }

    }

}
