package features.domain;

import joist.domain.uow.UoW;

public class BatchingTest extends AbstractFeaturesTest {

  public void testNoop() {
  }

  public void testBatch() {
    int number = 0; // change when testing batching

    Parent[] ps = new Parent[number];
    for (int i = 0; i < number; i++) {
      ps[i] = new Parent("parent" + i);
    }
    UoW.commit();

    for (int i = 0; i < number; i++) {
      ps[i].setName("Parent" + i);
    }
    UoW.commit();
  }

}
