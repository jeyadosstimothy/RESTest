package spotify_Playlists;

import org.junit.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.FixMethodOrder;
import static org.junit.Assert.fail;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.Assert.assertTrue;
import org.junit.runners.MethodSorters;
import io.qameta.allure.restassured.AllureRestAssured;
import es.us.isa.restest.writers.restassured.filters.StatusCode5XXFilter;
import es.us.isa.restest.writers.restassured.filters.NominalOrFaultyTestCaseFilter;
import es.us.isa.restest.writers.restassured.filters.StatefulFilter;
import java.io.File;
import es.us.isa.restest.writers.restassured.filters.ResponseValidationFilter;
import es.us.isa.restest.writers.restassured.filters.CSVFilter;
import java.io.PrintStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.io.IoBuilder;
import org.apache.logging.log4j.core.LoggerContext;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import es.us.isa.restest.util.LoggerStream;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Spotify_Playlists {

	private static final String OAI_JSON_URL = "src/main/resources/Examples/Ex7_CBTStatefulGeneration/openapi.yaml";
	private static final StatusCode5XXFilter statusCode5XXFilter = new StatusCode5XXFilter();
	private static final NominalOrFaultyTestCaseFilter nominalOrFaultyTestCaseFilter = new NominalOrFaultyTestCaseFilter();
	private static final ResponseValidationFilter validationFilter = new ResponseValidationFilter(OAI_JSON_URL);
	private static final StatefulFilter statefulFilter = new StatefulFilter("src/main/resources/Examples/Ex7_CBTStatefulGeneration");
	private static RequestLoggingFilter requestLoggingFilter;
	private static ResponseLoggingFilter responseLoggingFilter;
	private static Logger logger = LogManager.getLogger(Spotify_Playlists.class.getName());
	private static final AllureRestAssured allureFilter = new AllureRestAssured();
	private static final String APIName = "spotify_Playlists";
	private static final String testId = "spotify_Playlists";
	private static final CSVFilter csvFilter = new CSVFilter(APIName, testId);

	@BeforeClass
	public static void setUp() {
		RestAssured.baseURI = "https://api.spotify.com/v1";

		// Configure logging
		System.setProperty("logFilename", "target/log/spotify_Playlists/log");
		logger = LogManager.getLogger(Spotify_Playlists.class.getName());
		PrintStream logStream = IoBuilder.forLogger(logger).buildPrintStream();
		requestLoggingFilter = RequestLoggingFilter.logRequestTo(logStream);
		responseLoggingFilter = new ResponseLoggingFilter(logStream);
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		File file = new File("src/main/resources/log4j2-logToFile.properties");
		ctx.setConfigLocation(file.toURI());
		ctx.reconfigure();

		statusCode5XXFilter.setAPIName(APIName);
		statusCode5XXFilter.setTestId(testId);
		nominalOrFaultyTestCaseFilter.setAPIName(APIName);
		nominalOrFaultyTestCaseFilter.setTestId(testId);
		validationFilter.setAPIName(APIName);
		validationFilter.setTestId(testId);
	}

	@Test
	public void test_1k22u5kiajrci_endpointcreateplaylist() {
		String testResultId = "test_1k22u5kiajrci_endpointcreateplaylist";

		nominalOrFaultyTestCaseFilter.updateFaultyData(false, true, "none");
		statusCode5XXFilter.updateFaultyData(false, true, "none");
		csvFilter.setTestResultId(testResultId);
		statusCode5XXFilter.setTestResultId(testResultId);
		nominalOrFaultyTestCaseFilter.setTestResultId(testResultId);
		validationFilter.setTestResultId(testResultId);

		try {
			Response response = RestAssured
			.given()
				.header("Spotify/headers.json", "null")
				.pathParam("user_id", "null")
				.contentType("application/json")
				.body("{\"collaborative\":\"\\\\0\",\"description\":\"one space\",\"name\":\"\\\\0\",\"public\":2147483648}")
				.filter(requestLoggingFilter)
				.filter(responseLoggingFilter)
				.filter(allureFilter)
				.filter(statusCode5XXFilter)
				.filter(nominalOrFaultyTestCaseFilter)
				.filter(validationFilter)
				.filter(csvFilter)
			.when()
				.post("/users/{user_id}/playlists");

			response.then();
			System.out.println("Test passed.");
		} catch (RuntimeException ex) {
			System.err.println(ex.getMessage());
			fail(ex.getMessage());
		}
	}

	@Test
	public void test_rkwrykuopid0_endpointcreateplaylist() {
		String testResultId = "test_rkwrykuopid0_endpointcreateplaylist";

		nominalOrFaultyTestCaseFilter.updateFaultyData(false, true, "none");
		statusCode5XXFilter.updateFaultyData(false, true, "none");
		csvFilter.setTestResultId(testResultId);
		statusCode5XXFilter.setTestResultId(testResultId);
		nominalOrFaultyTestCaseFilter.setTestResultId(testResultId);
		validationFilter.setTestResultId(testResultId);

		try {
			Response response = RestAssured
			.given()
				.header("Spotify/headers.json", "null")
				.pathParam("user_id", "one space")
				.contentType("application/json")
				.body("{\"name\":null,\"public\":\"\\\\0\"}")
				.filter(requestLoggingFilter)
				.filter(responseLoggingFilter)
				.filter(allureFilter)
				.filter(statusCode5XXFilter)
				.filter(nominalOrFaultyTestCaseFilter)
				.filter(validationFilter)
				.filter(csvFilter)
			.when()
				.post("/users/{user_id}/playlists");

			response.then();
			System.out.println("Test passed.");
		} catch (RuntimeException ex) {
			System.err.println(ex.getMessage());
			fail(ex.getMessage());
		}
	}

	@Test
	public void test_urhbnwukhso3_endpointcreateplaylist() {
		String testResultId = "test_urhbnwukhso3_endpointcreateplaylist";

		nominalOrFaultyTestCaseFilter.updateFaultyData(false, true, "none");
		statusCode5XXFilter.updateFaultyData(false, true, "none");
		csvFilter.setTestResultId(testResultId);
		statusCode5XXFilter.setTestResultId(testResultId);
		nominalOrFaultyTestCaseFilter.setTestResultId(testResultId);
		validationFilter.setTestResultId(testResultId);

		try {
			Response response = RestAssured
			.given()
				.header("Spotify/headers.json", "null")
				.pathParam("user_id", "null")
				.contentType("application/json")
				.body("{\"collaborative\":\"\",\"name\":\"\"}")
				.filter(requestLoggingFilter)
				.filter(responseLoggingFilter)
				.filter(allureFilter)
				.filter(statusCode5XXFilter)
				.filter(nominalOrFaultyTestCaseFilter)
				.filter(validationFilter)
				.filter(csvFilter)
			.when()
				.post("/users/{user_id}/playlists");

			response.then();
			System.out.println("Test passed.");
		} catch (RuntimeException ex) {
			System.err.println(ex.getMessage());
			fail(ex.getMessage());
		}
	}

	@Test
	public void test_rhitvwqlsx15_endpointcreateplaylist() {
		String testResultId = "test_rhitvwqlsx15_endpointcreateplaylist";

		nominalOrFaultyTestCaseFilter.updateFaultyData(false, true, "none");
		statusCode5XXFilter.updateFaultyData(false, true, "none");
		csvFilter.setTestResultId(testResultId);
		statusCode5XXFilter.setTestResultId(testResultId);
		nominalOrFaultyTestCaseFilter.setTestResultId(testResultId);
		validationFilter.setTestResultId(testResultId);

		try {
			Response response = RestAssured
			.given()
				.header("Spotify/headers.json", "null")
				.pathParam("user_id", "\\0")
				.contentType("application/json")
				.body("{\"name\":\"one space\",\"public\":true}")
				.filter(requestLoggingFilter)
				.filter(responseLoggingFilter)
				.filter(allureFilter)
				.filter(statusCode5XXFilter)
				.filter(nominalOrFaultyTestCaseFilter)
				.filter(validationFilter)
				.filter(csvFilter)
			.when()
				.post("/users/{user_id}/playlists");

			response.then();
			System.out.println("Test passed.");
		} catch (RuntimeException ex) {
			System.err.println(ex.getMessage());
			fail(ex.getMessage());
		}
	}

	@Test
	public void test_t8cxh32xm2qq_endpointcreateplaylist() {
		String testResultId = "test_t8cxh32xm2qq_endpointcreateplaylist";

		nominalOrFaultyTestCaseFilter.updateFaultyData(false, true, "none");
		statusCode5XXFilter.updateFaultyData(false, true, "none");
		csvFilter.setTestResultId(testResultId);
		statusCode5XXFilter.setTestResultId(testResultId);
		nominalOrFaultyTestCaseFilter.setTestResultId(testResultId);
		validationFilter.setTestResultId(testResultId);

		try {
			Response response = RestAssured
			.given()
				.header("Spotify/headers.json", "null")
				.pathParam("user_id", "null")
				.contentType("application/json")
				.body("{\"collaborative\":\"\",\"name\":\"one space\",\"public\":\"\\\\0\"}")
				.filter(requestLoggingFilter)
				.filter(responseLoggingFilter)
				.filter(allureFilter)
				.filter(statusCode5XXFilter)
				.filter(nominalOrFaultyTestCaseFilter)
				.filter(validationFilter)
				.filter(csvFilter)
			.when()
				.post("/users/{user_id}/playlists");

			response.then();
			System.out.println("Test passed.");
		} catch (RuntimeException ex) {
			System.err.println(ex.getMessage());
			fail(ex.getMessage());
		}
	}

}
