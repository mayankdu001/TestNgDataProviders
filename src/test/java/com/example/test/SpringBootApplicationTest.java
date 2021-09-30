package com.example.test;

import static org.junit.Assert.assertEquals;
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
	public static Object[][] testDp() {
	    return new Object[][]{
	    	{"1", "emp1"}, 
	    	{"2", "emp2"}, 
	    	{"4", "emp4"},
	    	{"34test", "emp1"},
	    	{null, "emp1"},
	    	};
	}
	
	@Test(dataProvider = "testDp1")
	public void testFindEmployee(String id, String expected) {
		Employee e = testController.findEmployee(id);
		assertEquals(e.getName() , expected);
	}

}