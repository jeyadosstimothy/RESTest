package es.us.isa.restest.mutation.rules;

import static es.us.isa.restest.util.SchemaManager.generateFullyResolvedSchema;
import static org.junit.Assert.assertEquals;

import es.us.isa.restest.specification.OpenAPISpecification;
import es.us.isa.restest.util.SchemaManager;
import io.swagger.v3.oas.models.media.Schema;
import java.lang.reflect.Field;
import org.junit.Before;
import org.junit.Test;

public class SelectRuleTest {

  @Before
  public void resetSchemaManager() throws NoSuchFieldException, IllegalAccessException {
    Field currentRefPath = SchemaManager.class.getDeclaredField("currentRefPath");
    currentRefPath.setAccessible(true);
    currentRefPath.set(null, "");
  }

  @Test
  public void applySelectRuleCommentsPostCommentTest() {
    OpenAPISpecification spec =
        new OpenAPISpecification("src/test/resources/Comments/swagger.yaml");
    Schema postCommentSchema =
        spec.getSpecification()
            .getPaths()
            .get("/comments")
            .getPost()
            .getRequestBody()
            .getContent()
            .get("application/json")
            .getSchema();
    postCommentSchema = generateFullyResolvedSchema(postCommentSchema, spec.getSpecification());

    SelectRule.getInstance().apply(postCommentSchema, spec.getSpecification());

    assertEquals(1, postCommentSchema.getProperties().size());
  }

  @Test
  public void applySelectRulePetstorePostPetTest() {
    OpenAPISpecification spec =
        new OpenAPISpecification("src/test/resources/specifications/petstore.yaml");
    Schema postPetSchema =
        spec.getSpecification()
            .getPaths()
            .get("/pet")
            .getPost()
            .getRequestBody()
            .getContent()
            .get("application/json")
            .getSchema();
    postPetSchema = generateFullyResolvedSchema(postPetSchema, spec.getSpecification());

    SelectRule.getInstance().apply(postPetSchema, spec.getSpecification());

    assertEquals(2, postPetSchema.getProperties().size());
    assertEquals(
        1, ((Schema) postPetSchema.getProperties().get("category")).getProperties().size());
  }
}
