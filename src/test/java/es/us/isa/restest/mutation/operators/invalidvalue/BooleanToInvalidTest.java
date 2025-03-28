package es.us.isa.restest.mutation.operators.invalidvalue;

import static org.junit.Assert.*;

import es.us.isa.restest.specification.OpenAPIParameter;
import es.us.isa.restest.specification.OpenAPISpecification;
import es.us.isa.restest.testcases.TestCase;
import io.swagger.v3.oas.models.PathItem;
import java.util.Arrays;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

public class BooleanToInvalidTest {

  @Test
  public void mutationAppliedTest() {
    OpenAPISpecification spec = new OpenAPISpecification("src/test/resources/Travel/swagger.yaml");
    TestCase tc =
        new TestCase("dfgsdfg", true, "getTripsFromUser", "/trips/user", PathItem.HttpMethod.GET);

    TestCase oldTc = SerializationUtils.clone(tc);
    OpenAPIParameter paramToMutate =
        new OpenAPIParameter(
            spec.getSpecification().getPaths().get("/trips/user").getGet().getParameters().stream()
                .filter(p -> p.getName().equals("isAdmin"))
                .findFirst()
                .get());

    assertTrue(
        "The test case should be mutated",
        BooleanToInvalid.mutate(tc, paramToMutate)
            .matches("Changed value of boolean parameter isAdmin from '.*' to .* '.*'"));
    assertFalse(
        "The value should not be boolean",
        Arrays.asList("true", "false").contains(tc.getParameterValue(paramToMutate)));
    assertNotEquals("The two test cases should be different", tc, oldTc);
  }
}
