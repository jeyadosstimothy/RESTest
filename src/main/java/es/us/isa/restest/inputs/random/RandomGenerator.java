package es.us.isa.restest.inputs.random;

import es.us.isa.restest.inputs.ITestDataGenerator;
import org.apache.commons.math3.random.RandomDataGenerator;

/**
 * Superclass for random generators. Seed management
 *
 * @author Sergio Segura
 */
public abstract class RandomGenerator implements ITestDataGenerator {

  long seed = -1;
  RandomDataGenerator rand;

  public RandomGenerator() {
    this.rand = new RandomDataGenerator();
    this.seed = rand.getRandomGenerator().nextLong();
    rand.reSeed(seed);
  }

  public void setSeed(long seed) {
    this.seed = seed;
    rand.reSeed(seed);
  }

  public long getSeed() {
    return this.seed;
  }

  public abstract Object nextValue();
}
