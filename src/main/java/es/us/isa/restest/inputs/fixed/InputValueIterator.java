package es.us.isa.restest.inputs.fixed;

import es.us.isa.restest.inputs.ITestDataGenerator;
import java.util.Iterator;
import java.util.List;

/**
 * Sequential iterator on a list of input values of type T
 *
 * @author Sergio Segura
 */
public class InputValueIterator<T> implements ITestDataGenerator {

  private List<T> values;
  private Iterator<T> iterator;

  public InputValueIterator() {}

  /**
   * @param values List of the values to be returned (data dictionary).
   */
  public InputValueIterator(List<T> values) {
    this.values = values;
    this.iterator = values.iterator();
  }

  public List<T> getValues() {
    return values;
  }

  /**
   * @param values List of the values to be returned (data dictionary).
   */
  public void setValues(List<T> values) {
    this.values = values;
  }

  public Object nextValue() {
    Object value = null;

    if (iterator.hasNext()) value = iterator.next();
    else {
      this.resetIterator();
      value = iterator.next();
    }

    return value;
  }

  public String nextValueAsString() {
    return nextValue().toString();
  }

  public void resetIterator() {
    iterator = values.iterator();
  }
}
