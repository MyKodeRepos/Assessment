package com.assessment.usermanagement.entity;

public class SearchEmployees {
	
	private double minSalary;
	
	private double maxSalary;
	
	private String sortingElement;
	
	private String sortOrder;
	
	private int page;
	
	private int offset;
	
	public SearchEmployees() {
		
	}
	
	public SearchEmployees(double minSalary, double maxSalary, String sortOrder) {
		this.minSalary = minSalary;
		this.maxSalary = maxSalary;
		this.sortOrder = sortOrder;
	}

	public double getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(double minSalary) {
		this.minSalary = minSalary;
	}

	public double getMaxSalary() {
		return maxSalary;
	}

	public void setMaxSalary(double maxSalary) {
		this.maxSalary = maxSalary;
	}
	
	public String getSortingElement() {
		return sortingElement;
	}

	public void setSortingElement(String sortingElement) {
		this.sortingElement = sortingElement;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	
}
