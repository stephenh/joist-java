package joist.perf;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.click.util.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.bindgen.Bindable;
import org.bindgen.Binding;
import org.bindgen.BindingRoot;

import features.domain.Child;
import features.domain.ChildBinding;
import features.domain.Parent;

@Bindable
public class BindgenPerfTest extends TestCase {

  private static final int loops = 1000000;
  public Child child;

  public void testOgnlWithCachingByClickPropertyUtils() throws Exception {
    Parent p = new Parent("p");
    Child c = new Child(p, "c");

    Map<String, String> context = new HashMap<String, String>();
    PropertyUtils.getValueOgnl(c, "parent.name", context); // Let it cache  
    long start = System.nanoTime();
    for (int i = 0; i < BindgenPerfTest.loops; i++) {
      Assert.assertEquals("p", PropertyUtils.getValueOgnl(c, "parent.name", context));
    }
    this.stop("ognl cached", start);
  }

  public void testClickPropertyUtils() {
    Parent p = new Parent("p");
    Child c = new Child(p, "c");

    Map<?, ?> cache = new HashMap<String, String>();
    PropertyUtils.getValue(c, "parent.name", cache); // Let it cache  
    long start = System.nanoTime();
    for (int i = 0; i < BindgenPerfTest.loops; i++) {
      Assert.assertEquals("p", PropertyUtils.getValue(c, "parent.name", cache));
    }
    this.stop("click proputil", start);
  }

  public void testBindgenNewBindingEachTime() {
    Parent p = new Parent("p");
    Child c = new Child(p, "c");

    long start = System.nanoTime();
    for (int i = 0; i < BindgenPerfTest.loops; i++) {
      Assert.assertEquals("p", new ChildBinding(c).parent().name().get());
    }
    this.stop("new binding", start);
  }

  public void testBindgenReuseExistingBinding() {
    Parent p = new Parent("p");
    Child c = new Child(p, "c");

    ChildBinding cb = new ChildBinding();
    Binding<String> b = cb.parent().name();
    long start = System.nanoTime();
    for (int i = 0; i < BindgenPerfTest.loops; i++) {
      cb.set(c);
      Assert.assertEquals("p", b.get());
    }
    this.stop("existing binding", start);
  }

  public void testBindgenReuseStaticBinding() {
    Parent p = new Parent("p");
    Child c = new Child(p, "c");

    ChildBinding cb = new ChildBinding();
    BindingRoot<Child, String> b = cb.parent().name();
    long start = System.nanoTime();
    for (int i = 0; i < BindgenPerfTest.loops; i++) {
      Assert.assertEquals("p", b.getWithRoot(c));
    }
    this.stop("static binding", start);
  }

  private void stop(String description, long start) {
    System.out.println(StringUtils.leftPad(description, 18) + ": " + StringUtils.leftPad((System.nanoTime() - start) + "ns", 20));
  }

}
