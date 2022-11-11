package group4.householdhero.model;

import java.time.LocalDate;

public class Budget {
	private int id;
	private double plannedBudget;
	private double spentBudget;
	private LocalDate startDate;
	private LocalDate endDate;
	
	public Budget(int id, double plannedBudget, double spentBudget, LocalDate startDate, LocalDate endDate) {
		this.id = id;
		this.plannedBudget = plannedBudget;
		this.spentBudget = spentBudget;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPlannedBudget() {
		return plannedBudget;
	}

	public void setPlannedBudget(double plannedBudget) {
		this.plannedBudget = plannedBudget;
	}

	public double getSpentBudget() {
		return spentBudget;
	}

	public void setSpentBudget(double spentBudget) {
		this.spentBudget = spentBudget;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public String toString() {
		return startDate + " - " + endDate;
	}
}
