package es.us.isa.restest.inputs.semantic;

import es.us.isa.restest.configuration.pojos.*;
import es.us.isa.restest.specification.OpenAPISpecification;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.junit.Test;

import java.util.*;

import static es.us.isa.restest.configuration.TestConfigurationIO.loadConfiguration;
import static es.us.isa.restest.inputs.semantic.NLPUtils.extractPredicateCandidatesFromDescription;
import static es.us.isa.restest.inputs.semantic.NLPUtils.posTagging;
import static es.us.isa.restest.inputs.semantic.Predicates.generatePredicateQuery;
import static es.us.isa.restest.inputs.semantic.SPARQLUtils.generateQuery;
import static es.us.isa.restest.inputs.semantic.SemanticInputGenerator.getSemanticOperations;
import static org.junit.Assert.*;

public class SPARQLQueryGenerationTest {

	@Test
	public void testSemanticOperationsRetrieval(){
		OpenAPISpecification specification = new OpenAPISpecification("src/test/resources/ClimaCell/ClimaCell.yaml");
		TestConfigurationObject conf = loadConfiguration("src/test/resources/ClimaCell/testConf.yaml", specification);

		Set<SemanticOperation> semanticOperations = getSemanticOperations(conf);
		assertTrue("Incorrect number of semantic operations", semanticOperations.size()==3);

		for(SemanticOperation semanticOperation: semanticOperations){
			assertTrue("Incorrect number of semantic parameters", semanticOperation.getSemanticParameters().size()==2);
		}

	}

	@Test
	public void testObtainParameterNameFromDescription(){
		String parameterName = "t";
		String parameterDescription = "The title of a movie";

		List<String> possibleNames = posTagging(parameterDescription, parameterName);

		assertTrue("Error obtaining complete parameter name", possibleNames.get(0).equals("title"));

	}

	@Test
	public void testExtractPredicateCandidatesFromDescription(){
		Map<Double, Set<String>> descriptionCandidates = new HashMap<>();

		String parameterName1 = "currency";
		String parameterDescription1 = "A valid currency code";

		descriptionCandidates = extractPredicateCandidatesFromDescription(parameterName1, parameterDescription1);

		assertTrue("Error extracting predicate candidates from description", descriptionCandidates.keySet().size()==1);
		assertTrue("Error extracting predicate candidates from description", descriptionCandidates.get(3.0).contains("currencycode"));

		String parameterName2 = "lang";
		String parameterDescription2 = "A valid language code";

		descriptionCandidates = extractPredicateCandidatesFromDescription(parameterName2, parameterDescription2);

		assertTrue("Error extracting predicate candidates from description", descriptionCandidates.keySet().size()==1);
		assertTrue("Error extracting predicate candidates from description", descriptionCandidates.get(2.0).contains("langcode"));
		assertTrue("Error extracting predicate candidates from description", descriptionCandidates.get(2.0).contains("languagecode"));


		String parameterName3 = "foo";
		String parameterDescription3 = "A city code or a imdb id";

		descriptionCandidates = extractPredicateCandidatesFromDescription(parameterName3, parameterDescription3);

		assertTrue("Error extracting predicate candidates from description", descriptionCandidates.keySet().size()==1);
		assertTrue("Error extracting predicate candidates from description", descriptionCandidates.get(1.0).contains("citycode"));
		assertTrue("Error extracting predicate candidates from description", descriptionCandidates.get(1.0).contains("imdbid"));

	}

	@Test
	public void testGeneratePredicateQuery(){
		String predicateQuery = generatePredicateQuery("keyword");

		String targetPredicateQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
				"\n" +
				"SELECT distinct ?predicate where {\n" +
				"    ?predicate a rdf:Property\n" +
				"    OPTIONAL { ?predicate rdfs:label ?label }\n" +
				"\n" +
				"FILTER regex(str(?predicate), \"keyword\" , 'i')\n" +
				"}\n" +
				"order by strlen(str(?predicate)) \n";

		assertTrue("Error generating predicate query", predicateQuery.equals(targetPredicateQuery));

		Query query = QueryFactory.create(predicateQuery);

		assertNotNull(query);
	}

	@Test
	public void testGetParameterValues(){

		OpenAPISpecification specification = new OpenAPISpecification("src/test/resources/ClimaCell/ClimaCell.yaml");
		TestConfigurationObject conf = loadConfiguration("src/test/resources/ClimaCell/testConf.yaml", specification);

		Set<SemanticOperation> semanticOperations = getSemanticOperations(conf);
		SemanticOperation semanticOperation = semanticOperations.stream().findFirst().orElse(null);
		assertNotNull(semanticOperation);

		Set<SemanticParameter> semanticParameters = semanticOperation.getSemanticParameters();

		for(SemanticParameter semanticParameter: semanticParameters){
			semanticParameter.setPredicates(Collections.singleton("http://dbpedia.org/ontology/" + semanticParameter.getTestParameter().getName()));
		}

		String queryString = generateQuery(semanticParameters, false);

		Query query = QueryFactory.create(queryString);
		assertNotNull(query);
	}

}
