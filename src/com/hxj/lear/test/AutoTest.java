package com.hxj.lear.test;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.hxj.lear.parser.AutoTestCaseFromFile;
public class AutoTest {
	
	AutoTestCaseFromFile acf = new AutoTestCaseFromFile();
	
	@Test
	@Parameters({"moduleId"})
	public void runTestCase(String moduleId) throws IOException {
		acf.testByModuleId(Driver.SDriver, moduleId);
	}
	@AfterMethod
	public void afterMethod() {

	}
}
