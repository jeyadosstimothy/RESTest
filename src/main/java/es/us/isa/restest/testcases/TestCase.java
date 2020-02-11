package es.us.isa.restest.testcases;

import java.util.HashMap;
import java.util.Map;
import io.swagger.models.HttpMethod;
import io.swagger.models.Response;

import static es.us.isa.restest.util.CSVManager.*;
import static es.us.isa.restest.util.FileManager.*;

/** Domain-independent test case
 * 
 * @author Sergio Segura
 *
 */
public class TestCase {
	
	private String id;										// Test unique identifier
	private String operationId;								// Id of the operation (ex. getAlbums)
	private HttpMethod method;								// HTTP method
	private String path;									// Request path
	private String inputFormat;								// Input format
	private String outputFormat;							// Output format
	private Map<String, String> headerParameters;			// Header parameters
	private Map<String, String> pathParameters;				// Path parameters
	private Map<String, String> queryParameters;			// Input parameters and values
	private Map<String, String> formParameters;
	private String bodyParameter;							// Body parameter
	private String authentication;							// Name of the authentication scheme used in the request (e.g. 'BasicAuth'), null if none
	private Map<String, Response> expectedOutputs;			// Possible outputs
	private Response expectedSuccessfulOutput; 				// Expected output in case the request is successful (helpful for stats computation)
	
	public TestCase(String id, String operationId, String path, HttpMethod method) {
		this.id = id;
		this.operationId = operationId;
		this.path = path;
		this.method = method;
		this.inputFormat = "application/json";
		this.outputFormat = "application/json";
		this.headerParameters = new HashMap<String,String>();
		this.queryParameters = new HashMap<String,String>();
		this.pathParameters = new HashMap<String,String>();
		this.formParameters = new HashMap<String,String>();
		this.authentication = null;
	}

	public Response getExpectedSuccessfulOutput() {
		return expectedSuccessfulOutput;
	}

	public void setExpectedSuccessfulOutput(Response expectedSuccessfulOutput) {
		this.expectedSuccessfulOutput = expectedSuccessfulOutput;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, String> getQueryParameters() {
		return queryParameters;
	}

	public void setQueryParameters(Map<String, String> inputParameters) {
		this.queryParameters = inputParameters;
	}

	public Map<String, String> getFormParameters() { return formParameters; }

	public void setFormParameters(Map<String, String> formParameters) { this.formParameters = formParameters; }

	public Map<String, Response> getExpectedOutputs() {
		return expectedOutputs;
	}

	public void setExpectedOutputs(Map<String, Response> expectedOutputs) {
		this.expectedOutputs = expectedOutputs;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getInputFormat() {
		return inputFormat;
	}

	public void setInputFormat(String inputFormat) {
		this.inputFormat = inputFormat;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}

	public String getAuthentication() {
		return authentication;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

	public Map<String, String> getHeaderParameters() {
		return headerParameters;
	}

	public void setHeaderParameters(Map<String, String> headerParameters) {
		this.headerParameters = headerParameters;
	}
	
	public void addQueryParameter(String name, String value) {
		queryParameters.put(name, value);
	}
	
	public void addQueryParameters(Map<String,String> params) {
		queryParameters.putAll(params);
	}
	
	public void addPathParameter(String name, String value) {
		pathParameters.put(name, value);
	}
	
	public void addPathParameters(Map<String,String> params) {
		pathParameters.putAll(params);
	}
	
	public void addHeaderParameter(String name, String value) {
		headerParameters.put(name, value);
	}
	
	public void addHeaderParameters(Map<String,String> params) {
		headerParameters.putAll(params);
	}

	public void addFormParameter(String name, String value) { formParameters.put(name, value); }

	public void addFormParameters(Map<String,String> params) { formParameters.putAll(params); }

	public Map<String, String> getPathParameters() {
		return pathParameters;
	}

	public void setPathParameters(Map<String, String> pathParameters) {
		this.pathParameters = pathParameters;
	}

	public String getBodyParameter() {
		return bodyParameter;
	}

	public void setBodyParameter(String bodyParameter) {
		this.bodyParameter = bodyParameter;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void exportToCSV(String filePath) {
		if (!checkIfExists(filePath)) // If the file doesn't exist, create it (only once)
			createFileWithHeader(filePath, "testCaseId,operationId,path,httpMethod,inputContentType,outputContentType," +
					"headerParameters,pathParameters,queryParameters,formParameters,bodyParameter,authentication,expectedOutputs," +
					"expectedSuccessfulOutput");

		// Generate row
		String row = id + "," + operationId + "," + path + "," + method.toString() + "," + inputFormat + "," + outputFormat + ",";
		for (Map.Entry<String, String> h: headerParameters.entrySet()) {
			row += h.getKey() + ":" + h.getValue() + ";";
		}
		for (Map.Entry<String, String> p: pathParameters.entrySet()) {
			row += p.getKey() + ":" + p.getValue() + ";";
		}
		for (Map.Entry<String, String> q: queryParameters.entrySet()) {
			row += q.getKey() + ":" + q.getValue() + ";";
		}
		for (Map.Entry<String, String> q: formParameters.entrySet()) {
			row += q.getKey() + ":" + q.getValue() + ";";
		}
		row += "," + bodyParameter + ",,,";

		writeRow(filePath, row);
	}
}
