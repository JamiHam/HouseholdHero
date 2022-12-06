package group4.householdhero.model;

import java.time.LocalDate;

public class BudgetFactory {

	/**
	 * Creates a new Budget-object
	 * @param id
	 * @param plannedBudget
	 * @param spentBudget
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Budget createBudget(int id, double plannedBudget, double spentBudget, LocalDate startDate, LocalDate endDate) {
		return new Budget(id, plannedBudget, spentBudget, startDate, endDate);
	}
}
