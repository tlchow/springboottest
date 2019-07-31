package com.in28minutes.springboot.rest.example.student;

import java.util.List;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.in28minutes.springboot.rest.example.RibbonConfiguration;

//@FeignClient(name = "studentClient", fallback=StudentClientFallback.class, url="${feign.client.studentClient.url}")
//@FeignClient(name = "studentClient", fallback=StudentClientFallback.class)
@FeignClient(name = "studentClient")
@RibbonClient(name = "studentClient", configuration= RibbonConfiguration.class)
public interface StudentClient {
	@RequestMapping(method = RequestMethod.GET, value = "/students")
	public List<Student> retrieveAllStudents();
}
