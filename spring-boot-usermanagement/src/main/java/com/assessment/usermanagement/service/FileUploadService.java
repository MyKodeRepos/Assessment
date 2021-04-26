package com.assessment.usermanagement.service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.assessment.usermanagement.entity.User;

public interface FileUploadService {

	public List<User> readAndSaveEmpDetails(InputStream in);
	
	public List<User> getAllUsers();
	
	public List<User> findByCriteria(double minSalary, double maxSalary, Pageable pageable);
	
	public Optional<User> findById(String employeeId);
	
	public User saveEmployee(User employee);
	
	public void deleteById(String employeeId);
	
}
