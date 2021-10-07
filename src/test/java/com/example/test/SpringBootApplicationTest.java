package com.example.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.example.controller.TestController;
import com.example.model.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.testng.AbstractTestNGCucumberTests;

@SpringBootTest
public class SpringBootApplicationTest extends AbstractTestNGCucumberTests{

	@Autowired
	private TestController testController;

	@BeforeMethod
	public void init() {
		testController = new TestController();
	}

	@DataProvider(name = "testDp1", parallel = true)
	public static Object[][] testDpMethod1() {
		return new Object[][] { { "1", "emp1" }, { "2", "emp2" }, { "4", "emp4" }, { "34test", "emp1" },
				{ null, "emp1" }, };
	}

	

	@Test(dataProvider = "testDp1")
	public void testFindEmployee(String id, String expected) {
		Employee e = testController.findEmployee(id);
		assertEquals(e.getName(), expected);
	}
	
	@DataProvider(name = "testDp2")
	public Object[][] testDpMethod2() {
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = new ObjectMapper().readValue(new File(getClass().getResource("test.json").getFile()),
					new TypeReference<HashMap<String, String>>() {
					});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Object[][] { {map} };
	}

	@Test(dataProvider = "testDp2")
	public void testFindEmployeeBySalary(Map<String, String> map) {
		for (Map.Entry<String, String> m : map.entrySet()) {
		List<Employee> list = testController.findEmployeebySalary(m.getValue());
		if (m.getValue().equals("manager"))
			assertTrue(list.stream().allMatch(e -> e.getSalary() > Double.valueOf( m.getKey())));
		else
			assertTrue(list.stream().allMatch(e -> e.getSalary() <= Double.valueOf( m.getKey())));
		}
	}

}