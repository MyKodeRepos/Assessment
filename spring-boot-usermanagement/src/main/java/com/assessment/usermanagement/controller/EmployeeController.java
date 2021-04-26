package com.assessment.usermanagement.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.assessment.usermanagement.dao.UserRepository;
import com.assessment.usermanagement.entity.User;
import com.assessment.usermanagement.exceptions.UserNotFoundException;
import com.assessment.usermanagement.service.FileUploadService;

@RestController
@CrossOrigin(origins="http://localhost:4200")  
@RequestMapping(value="/cud") 
public class EmployeeController {
	
	@Autowired
	public FileUploadService fileUploadService;
	@Autowired
	public UserRepository employeeRepo;
	
	Logger logger = Logger.getLogger(EmployeeController.class);
	
	@GetMapping("/getUsers")
	public List<User> getUsers() {
		return employeeRepo.findAll();
	}
	
	@PostMapping("/users/upload")
	public ResponseEntity<Object> createUser(@RequestParam("file") MultipartFile file) throws IOException {
				InputStream in=file.getInputStream();
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(file).toUri();
		/*
		 * if (! "text/csv".equals(file.getContentType())) { return
		 * ResponseEntity.badRequest().build(); }
		 */
		List<User> savedEmployees =  fileUploadService.readAndSaveEmpDetails(in);
		
		if(savedEmployees!=null && savedEmployees.size()>0) {
			
			return ResponseEntity.created(location).build();
		}
		else {
			return ResponseEntity.status(500).build();
		}
	}

	
	@GetMapping("/users")
	public List<User> getUsersWithCriteria(@RequestParam("minSalary") double minSalary,
			@RequestParam("maxSalary") double maxSalary, @RequestParam("offset") int offset,
			@RequestParam("limit") int limit, @RequestParam("sort") String sorting) {
		System.out.println("minSalary="+minSalary);
		System.out.println("sorting="+sorting);
		Sort  sort ;
		String sortingCol = sorting.charAt(0)=='-'?"desc":"asc";
		String sortingProp = sorting.substring(1, sorting.length());
		if(sortingCol=="asc") {
			sort = Sort.by(sortingProp).ascending();
		}else {
			sort = Sort.by(sortingProp).descending();
		}
		Pageable pageable = PageRequest.of(offset, limit,sort);
		List<User> finalList = new ArrayList<>();
		List<User> tempList = new ArrayList<>();
	
		tempList = employeeRepo.findBySalaryGreaterThanEqual(minSalary, pageable).getContent();
		if(tempList!=null &&tempList.size() >0) {
			for (User user : tempList) {
				finalList.add(user);
			}
		}
		tempList = employeeRepo.findBySalaryLessThanEqual(maxSalary, pageable).getContent();
		if(tempList!=null &&tempList.size() >0) {
			for (User user : tempList) {
				finalList.add(user);
			}
		}
		return finalList;
	}
	
	@PatchMapping("/users/update/{id}")
	public ResponseEntity<Object> updateEmployee(@PathVariable("id") String employeeId ,@RequestBody User employee) {
		Optional<User> emp = employeeRepo.findById(employeeId);
		if(emp.isPresent()) {
			emp.get().setLogin(employee.getLogin());
			emp.get().setName(employee.getName());
			emp.get().setSalary(employee.getSalary());
			employeeRepo.save(emp.get());
			return ResponseEntity.ok().build();
		}else {
				throw new UserNotFoundException("employeeId -"+employeeId+" not found");
		}
	}
	
	@PostMapping("/users/create")
	public ResponseEntity<Object> createEmployee(@RequestBody User employee) {
			employeeRepo.save(employee);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(employee.getEmployeeId()).toUri();
			return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("users/delete/{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable("id") String employeeId) {
		Optional<User> emp = employeeRepo.findById(employeeId);
		if(emp.isPresent()) {
			employeeRepo.delete(emp.get());
			return ResponseEntity.ok().build();
		}else {
			throw new UserNotFoundException("employeeId -"+employeeId+" not found");
		}
	}
	
	@GetMapping("/users/{id}")
	public User getEmployee(@PathVariable("id") String employeeId) {
		Optional<User> emp = employeeRepo.findById(employeeId);
		if(emp.isPresent()) {
			return emp.get();
		}else {
			return null;
		}
	}
}
