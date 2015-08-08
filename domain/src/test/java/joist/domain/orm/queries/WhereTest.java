package joist.domain.orm.queries;

import joist.util.Join;

import org.junit.Assert;
import org.junit.Test;

public class WhereTest {

  @Test
  public void testAnd() {
    Where w1 = new Where("a = ?", 1);
    Where w2 = new Where("b = ?", 2);
    Assert.assertEquals(Join.lines(//
      "a = ?",
      "AND b = ?"), w1.and(w2).getSql());

    Where w3 = new Where("c = ?", 3);
    Assert.assertEquals(Join.lines(//
      "a = ?",
      "AND b = ?",
      "AND c = ?"), w1.and(w2).and(w3).getSql());
  }

  @Test
  public void testOr() {
    Where w1 = new Where("a = ?", 1);
    Where w2 = new Where("b = ?", 2);
    Assert.assertEquals(Join.lines(//
      "a = ?",
      "OR b = ?"), w1.or(w2).getSql());

    Where w3 = new Where("c = ?", 3);
    Assert.assertEquals(Join.lines(//
      "a = ?",
      "OR b = ?",
      "OR c = ?"), w1.or(w2).or(w3).getSql());
  }

  @Test
  public void testAndThenOr() {
    Where w1 = new Where("a = ?", 1);
    Where w2 = new Where("b = ?", 2);
    Assert.assertEquals(Join.lines(//
      "a = ?",
      "AND b = ?"), w1.and(w2).getSql());

    Where w3 = new Where("c = ?", 3);
    Assert.assertEquals(Join.lines(//
      "(",
      " a = ?",
      " AND b = ?",
      ") OR (",
      " c = ?",
      ")"), w1.and(w2).or(w3).getSql());

    Where w4 = new Where("d = ?", 4);
    Assert.assertEquals(Join.lines(//
      "(",
      " a = ?",
      " AND b = ?",
      ") OR (",
      " c = ?",
      ")",
      "OR d = ?"), w1.and(w2).or(w3).or(w4).getSql());
  }

  @Test
  public void testOrTwoAnds() {
    Where w1 = new Where("a = ?", 1).and(new Where("b = ?", 2));
    Where w2 = new Where("c = ?", 3).and(new Where("d = ?", 4));
    Assert.assertEquals(Join.lines(//
      "(",
      " a = ?",
      " AND b = ?",
      ") OR (",
      " c = ?",
      " AND d = ?",
      ")"), w1.or(w2).getSql());
  }

  @Test
  public void testOrTwoComplexAnds() {
    Where w1 = new Where("a = ?", 1).and(new Where("b = ?", 2).or(new Where("b = ?", 6)));
    Where w2 = new Where("c = ?", 3).and(new Where("d = ?", 4).or(new Where("d = ?", 5)));
    Assert.assertEquals(Join.lines(//
      "(",
      " a = ?",
      " AND (",
      "  b = ?",
      "  OR b = ?",
      " )",
      ") OR (",
      " c = ?",
      " AND (",
      "  d = ?",
      "  OR d = ?",
      " )",
      ")"), w1.or(w2).getSql());
  }

  @Test
  public void testAnds() {
    Where w1 = new Where("a = ?", 1);
    Where w2 = new Where("b = ?", 2);
    Where w3 = new Where("c = ?", 3);
    Assert.assertEquals(Join.lines(//
      "a = ?",
      "AND b = ?",
      "AND c = ?"), Where.and(w1, w2, w3).getSql());
  }

  @Test
  public void testOrs() {
    Where w1 = new Where("a = ?", 1);
    Where w2 = new Where("b = ?", 2);
    Where w3 = new Where("c = ?", 3);
    Assert.assertEquals(Join.lines(//
      "a = ?",
      "OR b = ?",
      "OR c = ?"), Where.or(w1, w2, w3).getSql());
  }

  @Test
  public void testComplexAnds() {
    Where w1 = new Where("a = ?", 1).and(new Where("b = ?", 2));
    Where w2 = new Where("b = ?", 2);
    Where w3 = new Where("c = ?", 3);
    Assert.assertEquals(Join.lines(//
      "(",
      " a = ?",
      " AND b = ?",
      ") AND (",
      " b = ?",
      ") AND (",
      " c = ?",
      ")"), Where.and(w1, w2, w3).getSql());
  }
}
