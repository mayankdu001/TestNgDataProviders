package com.example.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.example.controller.TestController;
import com.example.model.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.testng.AbstractTestNGCucumberTests;

@SpringBootTest
public class SpringBootApplicationTest extends AbstractTestNGCucumberTests {

	@Autowired
	private TestController testController;

	@BeforeMethod
	public void init() {
		testController = new TestController();
	}
	
	@Test
    @Parameters ("Message in TestNG Parameters")
    public void OP( @Optional("Optional Parameter Selected") String message) {
        System.out.println(message);
    }
	
	@Factory(dataProvider = "dpWithFactory")
	public Object[] getTestClasses(String name) {
		Object[] tests = new Object[] { new com.example.test.Test(name.split(" ")[0],name.split(" ")[1]), new com.example.test.Test() };
		return tests;
	}

	@DataProvider
	public Object[] dpWithFactory() {
		return new Object[] {"first1 last1", "first2 last2"};
	}
	
	@Test(dataProvider = "dpWithFactory")
	public void dpFactoryTestser(String test) {
		System.out.println(test);
	}

	@DataProvider(name = "testDp1", parallel = true)
	public static Object[][] testDpMethod1() {
		return new Object[][] { { "1", "emp1" }, { "2", "emp2" }, { "4", "emp4" }, { "34test", "emp1" },
				{ null, "emp1" }, };
	}

	@DataProvider(name = "testDp3", parallel = true)
	public static Object[][] testDpMethod3() {
		return new Object[][] { { "first" }, { "second" } };
	}

	@DataProvider(name = "data-provider2")
	public Object[][] dpMethod() {
		return new Object[][] { { 4, 5, 9 }, { 7, 8, 15 } };
	}

	@DataProvider(name = "TestDPwithList")
	public Object[][] readListDetails() throws Exception {
		List<String> data = new ArrayList<>();
		data.add("test");
		data.add("1234");
		Object[][] objData = new Object[1][1];
		objData[0][0] = data;
		return objData;
	}

	@DataProvider(name = "TestDPwithMap")
	public Object[][] readDetails() throws Exception {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("Name", "test");
		data.put("ID", "1234");
		Object[][] objData = new Object[1][1];
		objData[0][0] = data;
		return objData;
	}

	@Test(dataProvider = "TestDPwithMap")
	public void dpMapTestser(HashMap<String, String> data) {
		System.out.println(data);
		System.out.println(data.get("ID"));
		System.out.println(data.get("Name"));
	}

	@Test(dataProvider = "TestDPwithList")
	public void dpListTestser(List<String> data) {
		System.out.println(data);
		for (String s : data) {
			System.out.println(s);
		}
	}

	@Test(dataProvider = "data-provider2")
	public void myTest2(int a, int b, int expectedResult) {
		int sum = a + b;
		System.out.println("Sum of 1st and 2nd parameters is=" + sum);
		Assert.assertEquals(expectedResult, sum);
	}

	@Test(dataProvider = "testDp3")
	public void testDP(String value) {
		System.out.println(value);
	}

	@Test(dataProvider = "data-provider", dataProviderClass = com.example.test.DataProvider.class)
	public void myTest(String val) {
		System.out.println("Current Status- " + val);
	}

	@Test(dataProvider = "testDp1")
	public void testFindEmployee(String id, String expected) {
		Employee e = testController.findEmployee(id);
		assertEquals(e.getName(), expected);
	}

}