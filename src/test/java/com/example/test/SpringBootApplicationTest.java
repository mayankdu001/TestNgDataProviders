package com.example.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.example.controller.TestController;
import com.example.model.Employee;

@SpringBootTest
public class SpringBootApplicationTest{
	
	@Autowired
	private TestController testController;
	
	@BeforeMethod
	public void init() {
		testController = new TestController();
	}
	
	@DataProvider(name = "testDp1")
	public static Object[][] testDpMethod1() {
	    return new Object[][]{
	    	{"1", "emp1"}, 
	    	{"2", "emp2"}, 
	    	{"4", "emp4"},
	    	{"34test", "emp1"},
	    	{null, "emp1"},
	    	};
	}
	
	@DataProvider(name = "testDp2")
	public static Object[][] testDpMethod2() {
	    return new Object[][]{
	    	{20000.00, "manager"}, 
	    	{20000.00, "developer"}
	    	};
	}
	
	@Test(dataProvider = "testDp1")
	public void testFindEmployee(String id, String expected) {
		Employee e = testController.findEmployee(id);
		assertEquals(e.getName() , expected);
	}
	
	@Test(dataProvider = "testDp2")
	public void testFindEmployeeBySalary(double salary, String designation) {
		List<Employee> list = testController.findEmployeebySalary(designation);
		if( designation.equals("manager"))
			assertTrue(list.stream().allMatch(e -> e.getSalary() > salary));
		else 
			assertTrue(list.stream().allMatch(e -> e.getSalary() <= salary));
	}

}