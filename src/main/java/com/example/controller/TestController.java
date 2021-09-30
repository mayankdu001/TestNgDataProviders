package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Employee;

@RestController
public class TestController {

	@RequestMapping(value = {"/employee/", "/employee/{id}"}, method = RequestMethod.GET)
	public Employee testEmployee(@PathVariable(required = false) String id) {
		return findEmployee(id);
	}
	
	public Employee findEmployee(String id){
		if(StringUtils.hasText(id)) {
			return getEmployee(id).orElse(getAllEmployees().get(0));
		}
		return getAllEmployees().get(0);
	}
	
	public static Optional<Employee> getEmployee(String id){
		return getAllEmployees().stream().filter(e->e.getEmpId().equals(id)).findFirst();
	}
	
	public static List<Employee> getAllEmployees(){
		List<Employee> employees = new ArrayList<>();
		employees.add(Employee.builder().name("emp1")
				.designation("developer").empId("1").salary(15000.00).build());
		employees.add(Employee.builder().name("emp2")
				.designation("manager").empId("2").salary(25000.00).build());
		employees.add(Employee.builder().name("emp3")
				.designation("developer").empId("3").salary(10000.00).build());
		employees.add(Employee.builder().name("emp4")
				.designation("developer").empId("4").salary(18000.00).build());
		employees.add(Employee.builder().name("emp5")
				.designation("manager").empId("5").salary(21000.00).build());
		employees.add(Employee.builder().name("emp6")
				.designation("manager").empId("6").salary(42000.00).build());
		employees.add(Employee.builder().name("emp7")
				.designation("developer").empId("7").salary(8000.00).build());
		return employees;
	}

}