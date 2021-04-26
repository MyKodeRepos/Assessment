package com.assessment.usermanagement.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.assessment.usermanagement.dao.UserRepository;
import com.assessment.usermanagement.entity.User;

@Component
public class FileUploadImpl implements FileUploadService {
	Logger logger = Logger.getLogger(FileUploadImpl.class);
	@Autowired
	public UserRepository employeeRepo;

	public List<User> readAndSaveEmpDetails(InputStream in) {

		 try {
			List<String> list = new ArrayList<>();
			 BufferedReader br
		      = new BufferedReader(new InputStreamReader(in , "UTF-8"));
			 	String line;
			 	line = br.readLine();
		        while ((line = br.readLine()) != null) {
		        	if(!line.startsWith("#"))
		        	list.add(line);
		        }
		            List<User> employeeList = new ArrayList<>()  ;
		            
		            for(String empRecord : list) {
		            	if(empRecord!=null ) {
		            	String[] contents = empRecord.split(",");
			            	try {
				            	if((contents.length == User.class.getDeclaredFields().length) &&
				            			(Double.parseDouble(contents[3]) >= 0.00))
				            	employeeList.add(CreateEmployeeObject(contents));
			            	}catch(Exception e) {
			            		logger.error("Error while reading the line"+empRecord,e);
			            	}
		            	}
		            }
		            
		            List<User> savedEmployees = saveAllEmployess(employeeList);
		            logger.info(" Number of employees saved are "+savedEmployees.size());
		            return savedEmployees;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return null;
	}
	
	
	private List<User> saveAllEmployess(List<User> employeeList){
		List<User> finalList = new ArrayList<User>();
		
		for(User user : employeeList) {
			Optional<User> tempUser = employeeRepo.findById(user.getEmployeeId());
			if(tempUser.isPresent()) {
				List<User> recordWithSmeLogin =	employeeRepo.findByLogin(user.getLogin()).stream().filter((u)->!u.getEmployeeId().equals(tempUser.get().getEmployeeId())).collect(Collectors.toList());			
				if(recordWithSmeLogin !=null && !(recordWithSmeLogin.size()>1)) {
					User tempObject = recordWithSmeLogin.get(0);
					User object1 = new User();
					object1.setEmployeeId(tempObject.getEmployeeId());
					object1.setLogin(tempUser.get().getLogin());
					object1.setName(tempObject.getName());
					object1.setSalary(tempObject.getSalary());
					finalList.add(object1);		
					finalList.add(user);
					}
					
			}else {
				finalList.add(tempUser.get());
			}
		}
		
		return employeeRepo.saveAll(finalList);
	}
	public User  CreateEmployeeObject(String[] contents) {
		return  new User(contents[0],contents[1],contents[2],Double.parseDouble(contents[3]));
	}

	@Override
	public List<User> getAllUsers() {
		return employeeRepo.findAll();
	}

	@Override
	public List<User> findByCriteria(double minSalary, double maxSalary, Pageable pageable) {
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

	@Override
	public Optional<User> findById(String employeeId) {
		return employeeRepo.findById(employeeId);
	}

	@Override
	public User saveEmployee(User employee) {
		User emp= employeeRepo.save(employee);
		 return emp;
	}

	@Override
	public void deleteById(String employeeId) {
		 employeeRepo.deleteById(employeeId);
	}
}
