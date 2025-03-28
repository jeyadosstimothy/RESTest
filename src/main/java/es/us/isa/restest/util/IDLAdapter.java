package es.us.isa.restest.util;

import static es.us.isa.restest.configuration.TestConfigurationVisitor.searchTestParameter;

import es.us.isa.restest.configuration.pojos.Operation;
import es.us.isa.restest.configuration.pojos.TestParameter;
import es.us.isa.restest.testcases.TestCase;
import io.swagger.v3.oas.models.parameters.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements a set of methods for adapting RESTest objects to IDL or IDLReasoner
 * objects, e.g., test cases.
 *
 * @author Alberto Martin-Lopez
 */
public class IDLAdapter {

  public static void idl2restestTestCase(
      TestCase tc, Map<Parameter, String> request, Operation testOperation) {
    for (Map.Entry<Parameter, String> parameter : request.entrySet()) {
      if (testOperation.getTestParameters().isEmpty()) {
        tc.addParameter(
            parameter.getKey().getIn(), parameter.getKey().getName(), parameter.getValue());
      } else {
        TestParameter testParameter =
            searchTestParameter(parameter.getKey().getName(), testOperation.getTestParameters());
        tc.addParameter(testParameter, parameter.getValue());
      }
    }
  }

  public static Map<String, String> restest2idlTestCase(TestCase tc) {
    Map<String, String> request = new HashMap<>();

    tc.getPathParameters().forEach(request::put);
    tc.getHeaderParameters().forEach(request::put);
    tc.getQueryParameters().forEach(request::put);
    tc.getFormParameters().forEach(request::put);

    return request;
  }
}
