package com.in28minutes.springboot.rest.example.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeacherController {
	
	@Autowired
	private StudentFeignClient studentClient;

	@GetMapping("/teacher/students")
	public List<Student> retrieveAllStudentsForThisTecher() {
		List<Student> students = studentClient.retrieveAllStudents();
		return students;
	}
}
