package com.in28minutes.springboot.rest.example.student;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class StudentClientFallback implements StudentFeignClient {

	@Override
	public List<Student> retrieveAllStudents() {
		System.out.println("Student Client fallback");
		return null;
	}

}
