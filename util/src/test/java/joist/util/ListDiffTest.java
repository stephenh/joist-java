package joist.util;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

public class ListDiffTest {

  @Test
  public void removed() {
    List<Integer> original = asList(1, 2, 3);
    List<Integer> updated = asList(1, 2);
    assertThat(ListDiff.of(original, updated).removed, is(asList(3)));
  }

  @Test
  public void added() {
    List<Integer> original = asList(1, 2, 3);
    List<Integer> updated = asList(1, 2, 3, 4);
    assertThat(ListDiff.of(original, updated).added, is(asList(4)));
  }

  @Test
  public void same() {
    List<Integer> original = asList(1, 2, 3);
    List<Integer> updated = asList(1, 2, 3);
    assertThat(ListDiff.of(original, updated).added.size(), is(0));
    assertThat(ListDiff.of(original, updated).removed.size(), is(0));
  }

}
