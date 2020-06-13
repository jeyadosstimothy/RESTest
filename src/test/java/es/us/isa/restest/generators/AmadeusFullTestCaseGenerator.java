package es.us.isa.restest.generators;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import es.us.isa.restest.configuration.TestConfigurationIO;
import es.us.isa.restest.configuration.pojos.TestConfigurationObject;
import es.us.isa.restest.generators.AbstractTestCaseGenerator;
import es.us.isa.restest.generators.RandomTestCaseGenerator;
import es.us.isa.restest.specification.OpenAPISpecification;
import es.us.isa.restest.testcases.TestCase;
import es.us.isa.restest.testcases.writers.RESTAssuredWriter;

public class AmadeusFullTestCaseGenerator {

    @Test
    public void amadeusFullTestCaseGenerator() {
        // Load specification
        String OAISpecPath = "src/test/resources/Amadeus/spec.yaml";
        OpenAPISpecification spec = new OpenAPISpecification(OAISpecPath);

        // Load configuration
        TestConfigurationObject conf = TestConfigurationIO.loadConfiguration("src/test/resources/Amadeus/fullConf.yaml", spec);

        // Set number of test cases to be generated on each path, on each operation (HTTP method)
        int numTestCases = 4;

        // Create generator and filter
        AbstractTestCaseGenerator generator = new RandomTestCaseGenerator(spec, conf, numTestCases);


        Collection<TestCase> testCases = generator.generate();

        assertEquals("Incorrect number of test cases", 92, testCases.size());

        // Write RESTAssured test cases
        String basePath = spec.getSpecification().getServers().get(0).getUrl();
        RESTAssuredWriter writer = new RESTAssuredWriter(OAISpecPath, "src/generation/java/restassured", "AmadeusTest", "restassured", basePath.toLowerCase());
        writer.setOAIValidation(true);
        writer.setLogging(true);
        writer.write(testCases);

    }
}
