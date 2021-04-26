package com.assessment.usermanagement.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import com.assessment.usermanagement.entity.User;

@CrossOrigin("http://localhost:4200")
public interface UserRepository extends JpaRepository<User, String> {
	
	Page<User> findBySalaryGreaterThanEqual(@RequestParam(value="salary")  Double salary , Pageable pageable);
	
	Page<User> findBySalaryLessThanEqual(@RequestParam(value="salary")  Double salary , Pageable pageable);
	
	Page<User> findBySalaryBetween(@RequestParam(value="salary_From")  Double salary_From , @RequestParam(value="salary_To")  Double salary_To, Pageable pageable);
	
	List<User> findByLogin(String login);
	
	
}
