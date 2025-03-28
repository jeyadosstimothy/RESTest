package es.us.isa.restest.specification;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Encoding;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Sergio Segura
 */
public class OpenAPISpecificationVisitor {

  private OpenAPISpecificationVisitor() {
    // Utility class
  }

  public static final String MEDIA_TYPE_APPLICATION_JSON_REGEX =
      "^((application/.*(\\\\+)?json.*)|(\\*/\\*))$";
  public static final String MEDIA_TYPE_TEXT_PLAIN_REGEX = "^text/plain.*$";
  public static final String MEDIA_TYPE_APPLICATION_X_WWW_FORM_URLENCODED =
      "application/x-www-form-urlencoded";
  public static final String MEDIA_TYPE_MULTIPART_FORM_DATA = "multipart/form-data";
  private static final String BOOLEAN_TYPE = "boolean";

  /**
   * Returns the operation's parameter with name "paramName"
   *
   * @param operation Operation
   * @param paramName Parameter's name
   * @param in Parameter's type (header, path, query, body or formData)
   * @return the operation's parameter
   */
  public static OpenAPIParameter findParameterFeatures(
      Operation operation, String paramName, String in) {
    OpenAPIParameter param;

    switch (in) {
      case "header":
      case "path":
      case "query":
        param = findQueryHeaderPathParameterFeatures(operation, paramName);
        break;
      case "body":
        param = findBodyParameterFeatures(operation);
        break;
      case "formData":
        param = findFormDataParameterFeatures(operation, paramName);
        break;
      default:
        throw new IllegalArgumentException("Parameter type not supported: " + in);
    }

    return param;
  }

  private static OpenAPIParameter findQueryHeaderPathParameterFeatures(
      Operation operation, String paramName) {
    OpenAPIParameter param = null;
    boolean found = false;
    Iterator<Parameter> it = operation.getParameters().iterator();
    while (it.hasNext() && !found) {
      Parameter p = it.next();
      if (p.getName().equalsIgnoreCase(paramName)) {
        param = new OpenAPIParameter(p);
        found = true;
      }
    }
    return param;
  }

  private static OpenAPIParameter findBodyParameterFeatures(Operation operation) {
    OpenAPIParameter param = null;
    if (operation.getRequestBody().getContent().keySet().stream()
            .anyMatch(x -> x.matches(MEDIA_TYPE_APPLICATION_JSON_REGEX))
        || operation.getRequestBody().getContent().keySet().stream()
            .anyMatch(x -> x.matches(MEDIA_TYPE_TEXT_PLAIN_REGEX))) {
      param = new OpenAPIParameter("body", "body", operation.getRequestBody().getRequired());
    }
    return param;
  }

  private static OpenAPIParameter findFormDataParameterFeatures(
      Operation operation, String paramName) {
    OpenAPIParameter param = null;
    MediaType mediaType =
        operation
                .getRequestBody()
                .getContent()
                .containsKey(MEDIA_TYPE_APPLICATION_X_WWW_FORM_URLENCODED)
            ? operation
                .getRequestBody()
                .getContent()
                .get(MEDIA_TYPE_APPLICATION_X_WWW_FORM_URLENCODED)
            : operation.getRequestBody().getContent().get(MEDIA_TYPE_MULTIPART_FORM_DATA);
    Iterator<Map.Entry> formDataIterator =
        mediaType.getSchema().getProperties().entrySet().iterator();

    while (formDataIterator.hasNext()) {
      Map.Entry<String, Schema> formDataEntry = formDataIterator.next();
      if (formDataEntry.getKey().equalsIgnoreCase(paramName)) {
        param =
            new OpenAPIParameter(
                formDataEntry.getKey(),
                formDataEntry.getValue(),
                mediaType.getSchema().getRequired() != null
                    && mediaType.getSchema().getRequired().contains(formDataEntry.getKey()));
        break;

      } else if (paramName.startsWith(formDataEntry.getKey())
          && paramName.contains("[")
          && mediaType.getEncoding() != null
          && mediaType.getEncoding().get(formDataEntry.getKey()) != null
          && mediaType
              .getEncoding()
              .get(formDataEntry.getKey())
              .getStyle()
              .equals(Encoding.StyleEnum.DEEP_OBJECT)) {

        String propertyName = paramName.split("\\[")[1];
        if (propertyName.equals("]") && formDataEntry.getValue().getType().equals("array")) {
          param =
              new OpenAPIParameter(
                  paramName,
                  formDataEntry.getValue(),
                  mediaType.getSchema().getRequired() != null
                      && mediaType.getSchema().getRequired().contains(formDataEntry.getKey()));
          break;
        } else if (formDataEntry.getValue().getType().equals("object")
            && formDataEntry
                .getValue()
                .getProperties()
                .containsKey(propertyName.substring(0, propertyName.length() - 1))) {
          Schema schema =
              (Schema)
                  formDataEntry
                      .getValue()
                      .getProperties()
                      .get(propertyName.substring(0, propertyName.length() - 1));
          param =
              new OpenAPIParameter(
                  paramName,
                  schema,
                  mediaType.getSchema().getRequired() != null
                      && mediaType.getSchema().getRequired().contains(formDataEntry.getKey())
                      && schema
                          .getRequired()
                          .contains(propertyName.substring(0, propertyName.length() - 1)));
          break;
        }
      }
    }
    return param;
  }

  /**
   * Returns the parameters that are required for the operation.
   *
   * @param operation Operation in the specification
   * @return the required parameters
   */
  public static List<OpenAPIParameter> getRequiredParametersFeatures(Operation operation) {

    List<OpenAPIParameter> requiredParameters = new ArrayList<>();
    if (operation.getParameters() != null) {
      operation.getParameters().stream()
          .filter(x -> x.getRequired() != null && x.getRequired())
          .map(OpenAPIParameter::new)
          .forEach(requiredParameters::add);
    }

    if (operation.getRequestBody() != null
        && operation.getRequestBody().getContent() != null
        && operation.getRequestBody().getContent().keySet().stream()
            .anyMatch(x -> x.matches(MEDIA_TYPE_APPLICATION_JSON_REGEX))
        && operation.getRequestBody().getRequired() != null
        && operation.getRequestBody().getRequired()) {
      requiredParameters.add(new OpenAPIParameter("body", "body", Boolean.TRUE));

    } else if (operation.getRequestBody() != null
        && operation.getRequestBody().getContent() != null
        && (operation
                .getRequestBody()
                .getContent()
                .containsKey(MEDIA_TYPE_APPLICATION_X_WWW_FORM_URLENCODED)
            || operation
                .getRequestBody()
                .getContent()
                .containsKey(MEDIA_TYPE_MULTIPART_FORM_DATA))) {

      MediaType mediaType =
          operation
                  .getRequestBody()
                  .getContent()
                  .containsKey(MEDIA_TYPE_APPLICATION_X_WWW_FORM_URLENCODED)
              ? operation
                  .getRequestBody()
                  .getContent()
                  .get(MEDIA_TYPE_APPLICATION_X_WWW_FORM_URLENCODED)
              : operation.getRequestBody().getContent().get(MEDIA_TYPE_MULTIPART_FORM_DATA);

      if (mediaType.getSchema().getRequired() != null
          && !mediaType.getSchema().getRequired().isEmpty()) {

        for (Object schemaProperty : mediaType.getSchema().getProperties().entrySet()) {
          Schema s = ((Map.Entry<String, Schema>) schemaProperty).getValue();
          if (mediaType.getSchema().getRequired().contains(s.getName())) {
            requiredParameters.add(
                new OpenAPIParameter(
                    ((Map.Entry<String, Schema>) schemaProperty).getKey(), s, true));
          }
        }
      }
    }

    return requiredParameters;
  }

  /**
   * Returns the parameters that are required for the operation excepting the path ones.
   *
   * @param operation Operation in the specification
   * @return the required parameters
   */
  public static List<OpenAPIParameter> getRequiredNotPathParametersFeatures(Operation operation) {
    return getRequiredParametersFeatures(operation).stream()
        .filter(p -> !p.getIn().equals("path"))
        .collect(Collectors.toList());
  }

  /**
   * Returns the parameters of an operation whose values can be changed for invalid values. These
   * include the following:
   *
   * <ol>
   *   <li>Integer. Can be changed to number, boolean or string.
   *   <li>Integer with min/max constraints. Can violate constraints.
   *   <li>Number. Can be changed to boolean or string.
   *   <li>Number with min/max constraints. Can violate constraints.
   *   <li>Boolean. Can be changed to number, integer or string.
   *   <li>String with format. Can be changed to random string.
   *   <li>String with minLength/maxLength. Can violate constraints.
   *   <li>Enum. Can be changed to value out of enum range.
   * </ol>
   *
   * <b>NOTE: Body parameters are not considered.</b>
   *
   * @param operation Operation in the specification
   * @return the parameters whose values can be changed for invalid ones
   */
  public static List<OpenAPIParameter> getParametersFeaturesSubjectToInvalidValueChange(
      Operation operation) {
    List<OpenAPIParameter> result = new ArrayList<>();

    if (operation.getParameters() != null) {
      operation.getParameters().stream()
          .map(OpenAPIParameter::new)
          .filter(
              p ->
                  (p.getType().equals("integer")
                      || p.getType().equals("number")
                      || p.getType().equals(BOOLEAN_TYPE)
                      || (p.getType().equals("string")
                          && (p.getMinLength() != null
                              || p.getMaxLength() != null
                              || p.getFormat() != null))
                      || p.getEnumValues() != null))
          .forEach(result::add);
    }

    if (operation.getRequestBody() != null
        && operation.getRequestBody().getContent() != null
        && (operation
                .getRequestBody()
                .getContent()
                .containsKey(MEDIA_TYPE_APPLICATION_X_WWW_FORM_URLENCODED)
            || operation
                .getRequestBody()
                .getContent()
                .containsKey(MEDIA_TYPE_MULTIPART_FORM_DATA))) {

      MediaType mediaType =
          operation
                  .getRequestBody()
                  .getContent()
                  .containsKey(MEDIA_TYPE_APPLICATION_X_WWW_FORM_URLENCODED)
              ? operation
                  .getRequestBody()
                  .getContent()
                  .get(MEDIA_TYPE_APPLICATION_X_WWW_FORM_URLENCODED)
              : operation.getRequestBody().getContent().get(MEDIA_TYPE_MULTIPART_FORM_DATA);

      for (Object o : mediaType.getSchema().getProperties().entrySet()) {

        Schema s = ((Map.Entry<String, Schema>) o).getValue();
        OpenAPIParameter p =
            new OpenAPIParameter(
                ((Map.Entry<String, Schema>) o).getKey(),
                s,
                mediaType.getSchema().getRequired() != null
                    && mediaType.getSchema().getRequired().contains(s.getName()));

        if ((p.getType().equals("integer")
            || p.getType().equals("number")
            || p.getType().equals(BOOLEAN_TYPE)
            || (p.getType().equals("string")
                && (p.getMinLength() != null || p.getMaxLength() != null || p.getFormat() != null))
            || p.getEnumValues() != null)) {
          result.add(p);
        }
      }
    }

    return result;
  }

  public static Boolean hasDependencies(Operation operation) {
    try {
      List<String> dependencies = (List<String>) operation.getExtensions().get("x-dependencies");
      return dependencies != null && !dependencies.isEmpty();
    } catch (Exception e) { // If the "x-dependencies" extension is not correctly used
      return false;
    }
  }

  public static List<Parameter> getNonEnumParameters(Operation operation) {
    return operation.getParameters().stream()
        .filter(
            p -> {
              OpenAPIParameter pFeatures = new OpenAPIParameter(p);
              return (pFeatures.getEnumValues() == null
                  && !pFeatures.getType().equals(BOOLEAN_TYPE));
            })
        .collect(Collectors.toList());
  }
}
