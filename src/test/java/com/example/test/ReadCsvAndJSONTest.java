package com.example.test;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

@SpringBootTest
public class ReadCsvAndJSONTest {
	@Autowired
	private TestController testController;

	@BeforeMethod
	public void init() {
		testController = new TestController();
	}
	
	public Iterator<Object []> readfromCSV() throws InterruptedException {
		String[] data = null;
		List<Object []> testCases = new ArrayList<>();
		BufferedReader br = null;
		String line = "";
		final String DELIMITER = ";";
		try {
			br = new BufferedReader(new FileReader("D:/eclipe-workspace/TestNgDataProviders/src/main/resources/username.csv"));
			while ((line = br.readLine()) != null) {
				data = line.split(DELIMITER);
				testCases.add(data);
			}
			System.out.println(testCases);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return testCases.iterator();
	}

	@DataProvider(name = "dpWithCsv")
	public Iterator<Object[]> csvDataProvider() throws InterruptedException {
        return readfromCSV();
	}

	@Test(dataProvider = "dpWithCsv")
	public void testWithCsv(String username, String identifier, String firstName, String lastName) {
		System.out.println(username+","+identifier+","+firstName+","+lastName);
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
		return new Object[][] { { map } };
	}

	@Test(dataProvider = "testDp2")
	public void testFindEmployeeBySalary(Map<String, String> map) {
		for (Map.Entry<String, String> m : map.entrySet()) {
			List<Employee> list = testController.findEmployeebySalary(m.getValue());
			if (m.getValue().equals("manager"))
				assertTrue(list.stream().allMatch(e -> e.getSalary() > Double.valueOf(m.getKey())));
			else
				assertTrue(list.stream().allMatch(e -> e.getSalary() <= Double.valueOf(m.getKey())));
		}
	}

}
