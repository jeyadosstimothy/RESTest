package com.xflowpay.restest;

import static es.us.isa.restest.util.FileManager.createDir;

import es.us.isa.restest.generators.ConstraintBasedTestCaseGenerator;
import es.us.isa.restest.runners.RESTestLoader;
import es.us.isa.restest.testcases.TestCase;
import es.us.isa.restest.util.RESTestException;
import es.us.isa.restest.writers.restassured.RESTAssuredWriter;
import java.util.Collection;

public class TestGenerator {

  public static String propertyFilePath = "src/main/resources/Xflow/restest.properties";

  public static void main(String[] args) throws RESTestException {
    // Load properties
    RESTestLoader loader = new RESTestLoader(propertyFilePath);

    // Create test case generator
    ConstraintBasedTestCaseGenerator generator =
        (ConstraintBasedTestCaseGenerator) loader.createGenerator();
    Collection<TestCase> testCases = generator.generate();

    // Create target directory for test cases if it does not exist
    createDir(loader.getTargetDirJava());

    // Write (RestAssured) test cases
    RESTAssuredWriter writer = (RESTAssuredWriter) loader.createWriter();
    writer.write(testCases);

    System.out.println(
        testCases.size() + " test cases generated and written to " + loader.getTargetDirJava());
  }
}
