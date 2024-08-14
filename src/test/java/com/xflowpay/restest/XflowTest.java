package com.xflowpay.restest;

import static org.junit.Assert.fail;

import es.us.isa.restest.writers.restassured.filters.CSVFilter;
import es.us.isa.restest.writers.restassured.filters.NominalOrFaultyTestCaseFilter;
import es.us.isa.restest.writers.restassured.filters.ResponseValidationFilter;
import es.us.isa.restest.writers.restassured.filters.StatusCode5XXFilter;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class XflowTest {

  private static final String OAI_JSON_URL = "src/main/resources/Xflow/openapi.yaml";
  private static final StatusCode5XXFilter statusCode5XXFilter = new StatusCode5XXFilter();
  private static final NominalOrFaultyTestCaseFilter nominalOrFaultyTestCaseFilter =
      new NominalOrFaultyTestCaseFilter();
  private static final ResponseValidationFilter validationFilter =
      new ResponseValidationFilter(OAI_JSON_URL);
  private static final AllureRestAssured allureFilter = new AllureRestAssured();
  private static final String APIName = "xflow";
  private static final String testId = "xflow";
  private static final CSVFilter csvFilter = new CSVFilter(APIName, testId);

  @BeforeClass
  public static void setUp() {
    RestAssured.baseURI = "https://api-stage.xflowpay.com/";

    statusCode5XXFilter.setAPIName(APIName);
    statusCode5XXFilter.setTestId(testId);
    nominalOrFaultyTestCaseFilter.setAPIName(APIName);
    nominalOrFaultyTestCaseFilter.setTestId(testId);
    validationFilter.setAPIName(APIName);
    validationFilter.setTestId(testId);
  }

  @Test
  public void test_1k1rpkvpxx9h1_CreateAccount() {
    String testResultId = "test_1k1rpkvpxx9h1_CreateAccount";

    nominalOrFaultyTestCaseFilter.updateFaultyData(true, false, "inter_parameter_dependency");
    statusCode5XXFilter.updateFaultyData(true, false, "inter_parameter_dependency");
    csvFilter.setTestResultId(testResultId);
    statusCode5XXFilter.setTestResultId(testResultId);
    nominalOrFaultyTestCaseFilter.setTestResultId(testResultId);
    validationFilter.setTestResultId(testResultId);

    try {
      Response response =
          RestAssured.given()
              .header("Xflow-Account", "toString922")
              .header("Authorization", "Bearer sk_test_1718977534_cYbJbD9zX1ZZo5EnmMeU5U21rMyG33dM")
              .header("XFlow-Account", "account_F0A_1718958932447_rED85_000")
              .contentType("application/json")
              .body("toString228")
              .filter(allureFilter)
              .filter(statusCode5XXFilter)
              .filter(nominalOrFaultyTestCaseFilter)
              .filter(validationFilter)
              .filter(csvFilter)
              .when()
              .post("/v1/accounts");

      response.then();
      System.out.println("Test passed.");
    } catch (RuntimeException ex) {
      System.err.println(ex.getMessage());
      fail(ex.getMessage());
    }
  }

  @Test
  public void test_rhtw6zlls5et_CreateAccount() {
    String testResultId = "test_rhtw6zlls5et_CreateAccount";

    nominalOrFaultyTestCaseFilter.updateFaultyData(true, false, "inter_parameter_dependency");
    statusCode5XXFilter.updateFaultyData(true, false, "inter_parameter_dependency");
    csvFilter.setTestResultId(testResultId);
    statusCode5XXFilter.setTestResultId(testResultId);
    nominalOrFaultyTestCaseFilter.setTestResultId(testResultId);
    validationFilter.setTestResultId(testResultId);

    try {
      Response response =
          RestAssured.given()
              .header("Xflow-Account", "KY")
              .header("Authorization", "Bearer sk_test_1718977534_cYbJbD9zX1ZZo5EnmMeU5U21rMyG33dM")
              .header("XFlow-Account", "account_F0A_1718958932447_rED85_000")
              .contentType("application/json")
              .body("toString906")
              .filter(allureFilter)
              .filter(statusCode5XXFilter)
              .filter(nominalOrFaultyTestCaseFilter)
              .filter(validationFilter)
              .filter(csvFilter)
              .when()
              .post("/v1/accounts");

      response.then();
      System.out.println("Test passed.");
    } catch (RuntimeException ex) {
      System.err.println(ex.getMessage());
      fail(ex.getMessage());
    }
  }

  @Test
  public void test_s15k2rxrb3g5_CreateAccount() {
    String testResultId = "test_s15k2rxrb3g5_CreateAccount";

    nominalOrFaultyTestCaseFilter.updateFaultyData(true, false, "inter_parameter_dependency");
    statusCode5XXFilter.updateFaultyData(true, false, "inter_parameter_dependency");
    csvFilter.setTestResultId(testResultId);
    statusCode5XXFilter.setTestResultId(testResultId);
    nominalOrFaultyTestCaseFilter.setTestResultId(testResultId);
    validationFilter.setTestResultId(testResultId);

    try {
      Response response =
          RestAssured.given()
              .header("Authorization", "Bearer sk_test_1718977534_cYbJbD9zX1ZZo5EnmMeU5U21rMyG33dM")
              .header("XFlow-Account", "account_F0A_1718958932447_rED85_000")
              .contentType("application/json")
              .body("toString803")
              .filter(allureFilter)
              .filter(statusCode5XXFilter)
              .filter(nominalOrFaultyTestCaseFilter)
              .filter(validationFilter)
              .filter(csvFilter)
              .when()
              .post("/v1/accounts");

      response.then();
      System.out.println("Test passed.");
    } catch (RuntimeException ex) {
      System.err.println(ex.getMessage());
      fail(ex.getMessage());
    }
  }

  @Test
  public void test_1hrbfqmiw9dgk_CreateAccount() {
    String testResultId = "test_1hrbfqmiw9dgk_CreateAccount";

    nominalOrFaultyTestCaseFilter.updateFaultyData(true, false, "inter_parameter_dependency");
    statusCode5XXFilter.updateFaultyData(true, false, "inter_parameter_dependency");
    csvFilter.setTestResultId(testResultId);
    statusCode5XXFilter.setTestResultId(testResultId);
    nominalOrFaultyTestCaseFilter.setTestResultId(testResultId);
    validationFilter.setTestResultId(testResultId);

    try {
      Response response =
          RestAssured.given()
              .header("Xflow-Account", "toString985")
              .header("Authorization", "Bearer sk_test_1718977534_cYbJbD9zX1ZZo5EnmMeU5U21rMyG33dM")
              .header("XFlow-Account", "account_F0A_1718958932447_rED85_000")
              .contentType("application/json")
              .body("toString786")
              .filter(allureFilter)
              .filter(statusCode5XXFilter)
              .filter(nominalOrFaultyTestCaseFilter)
              .filter(validationFilter)
              .filter(csvFilter)
              .when()
              .post("/v1/accounts");

      response.then();
      System.out.println("Test passed.");
    } catch (RuntimeException ex) {
      System.err.println(ex.getMessage());
      fail(ex.getMessage());
    }
  }

  @Test
  public void test_1k1tz155hhwz7_CreateAccount() {
    String testResultId = "test_1k1tz155hhwz7_CreateAccount";

    nominalOrFaultyTestCaseFilter.updateFaultyData(true, false, "inter_parameter_dependency");
    statusCode5XXFilter.updateFaultyData(true, false, "inter_parameter_dependency");
    csvFilter.setTestResultId(testResultId);
    statusCode5XXFilter.setTestResultId(testResultId);
    nominalOrFaultyTestCaseFilter.setTestResultId(testResultId);
    validationFilter.setTestResultId(testResultId);

    try {
      Response response =
          RestAssured.given()
              .header("Authorization", "Bearer sk_test_1718977534_cYbJbD9zX1ZZo5EnmMeU5U21rMyG33dM")
              .header("XFlow-Account", "account_F0A_1718958932447_rED85_000")
              .contentType("application/json")
              .body("toString594")
              .filter(allureFilter)
              .filter(statusCode5XXFilter)
              .filter(nominalOrFaultyTestCaseFilter)
              .filter(validationFilter)
              .filter(csvFilter)
              .when()
              .post("/v1/accounts");

      response.then();
      System.out.println("Test passed.");
    } catch (RuntimeException ex) {
      System.err.println(ex.getMessage());
      fail(ex.getMessage());
    }
  }
}
