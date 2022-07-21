package pl.javny.expensesrepository.data;

import java.util.List;

class ReckonOutcome {

    public static void main(String[] args) {
        ReckonDao reckonDao = new ReckonDao();
        List<Reckon> outcomes = reckonDao.getOutcomes();
        for (Reckon outcome : outcomes) {
            System.out.println(outcome);
        }

    }

}
