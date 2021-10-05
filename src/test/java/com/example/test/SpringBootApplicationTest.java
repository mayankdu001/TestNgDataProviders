package com.example.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.example.controller.TestController;
import com.example.model.Employee;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class SpringBootApplicationTest {

	@Autowired
	private TestController testController;

	@BeforeMethod
	public void init() {
		testController = new TestController();
	}

	@DataProvider(name = "testDp1")
	public static Object[][] testDpMethod1() {
		return new Object[][] { { "1", "emp1" }, { "2", "emp2" }, { "4", "emp4" }, { "34test", "emp1" },
				{ null, "emp1" }, };
	}

	@DataProvider(name = "testDp2")
	public Object[] testDpMethod2() throws JsonParseException, JsonMappingException, IOException {
		Map<String, String> map = new ObjectMapper().readValue(new File(getClass().getResource("test.json").getFile()),
				new TypeReference<HashMap<String, String>>() {
				});
		return new Object[] { map };

	}

	@Test(dataProvider = "testDp1")
	public void testFindEmployee(String id, String expected) {
		Employee e = testController.findEmployee(id);
		assertEquals(e.getName(), expected);
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