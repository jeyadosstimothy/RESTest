package es.us.isa.restest.configuration.pojos;

import es.us.isa.restest.util.PropertyManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static es.us.isa.restest.util.CSVManager.readValues;
import static es.us.isa.restest.util.FileManager.createDir;
import static es.us.isa.restest.util.FileManager.createFileIfNotExists;

public class SemanticParameter {

    private TestParameter testParameter;
    private Set<String> predicates;
    private Set<String> values;
    private Set<String> validValues;
    private Set<String> invalidValues;

    // Initial generation:
    public SemanticParameter(TestParameter testParameter){

        this.testParameter = testParameter;
        this.predicates = new HashSet<>();
        this.values = new HashSet<>();
        this.validValues = new HashSet<>();
        this.invalidValues = new HashSet<>();

    }

    public TestParameter getTestParameter() {
        return testParameter;
    }

    public void setTestParameter(TestParameter testParameter) {
        this.testParameter = testParameter;
    }

    public Set<String> getPredicates() {
        return predicates;
    }

    public void setPredicates(Set<String> predicates) {
        this.predicates = predicates;
    }

    public Set<String> getValues() {
        return values;
    }

    public void setValues(Set<String> values) {
        this.values = values;
    }

    public void addValues(Set<String> values) {
        this.values.addAll(values);
    }

    public Set<String> getValidValues() { return validValues; }
    public void setValidValues(Set<String> validValues) { this.validValues = validValues; }

    public Set<String> getInvalidValues() { return invalidValues; }
    public void setInvalidValues(Set<String> invalidValues) { this.invalidValues = invalidValues; }

    public static Set<SemanticParameter> generateSemanticParameters(Set<TestParameter> testParameters){
        Set<SemanticParameter> res = new HashSet<>();

        for(TestParameter testParameter: testParameters){
            SemanticParameter generatedSemanticParameter = new SemanticParameter(testParameter);
            res.add(generatedSemanticParameter);
        }

        return res;
    }

    // Regex and Second predicate search:
    // Create Semantic parameter with a list of predicates and a set of valid and invalid values
    public SemanticParameter(TestParameter testParameter, List<String> currentPredicates, String experimentName, String operationId){

        this.testParameter = testParameter;
        this.predicates = new HashSet<>(currentPredicates);
        this.values = new HashSet<>();

        // Get valid and invalid values from the respective csv paths

        // Read from csv (if exists)
        String csvPath = this.getCSVPath(experimentName, operationId);
        createDir(csvPath); // This dir is created if it does not exist

        // Get valid and invalid paths
        String validPath = this.getValidCSVPath(experimentName, operationId);
        String invalidPath = this.getInvalidCSVPath(experimentName, operationId);
        createFileIfNotExists(validPath);
        createFileIfNotExists(invalidPath);

        this.validValues = new HashSet<>(readValues(validPath));
        this.invalidValues = new HashSet<>(readValues(invalidPath));

    }

    public String getCSVPath(String experimentName, String operationId){
        return PropertyManager.readProperty("data.tests.dir") + "/" + experimentName + "/validAndInvalidValues/" + operationId + "/" + this.testParameter.getName() + "/";
    }

    public String getValidCSVPath(String experimentName, String operationId){
        String csvPath = this.getCSVPath(experimentName, operationId);
        return  csvPath + "valid.csv";
    }

    public String getInvalidCSVPath(String experimentName, String operationId){
        String csvPath = this.getCSVPath(experimentName, operationId);
        return  csvPath + "invalid.csv";
    }

    public void addValidValue(String newValidValue){
        this.validValues.add(newValidValue);
        // Each time a valid value is added, it must be removed from the list of invalid values
        this.invalidValues.remove(newValidValue);
    }

    public void addInvalidValue(String newInvalidValue) {
        // If a value has been part of a valid API request before, it cannot be considered invalid
        if(!this.validValues.contains(newInvalidValue)){
            this.invalidValues.add(newInvalidValue);
        }
    }

    // Write new valid and invalid values as CSV files
    

}
