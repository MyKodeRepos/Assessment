package com.assessment.usermanagement.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	
	@Id
	@Column(name="employeeid")
	private String employeeId;
	
	@Column(name="login")
	private String login;
	
	@Column(name="name")
	private String name;
	
	@Column(name="salary")
	private Double salary;


	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}
	
	public User() {
		
	}
	
	public User(String employeeId, String login, String name, Double salary) {
		this.employeeId = employeeId;
		this.login = login;
		this.name = name;
		this.salary = salary;
	}
	
	
	

}
