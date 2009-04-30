package joist.util;

import java.util.List;

import joist.util.TopologicalSort.CycleException;
import junit.framework.Assert;
import junit.framework.TestCase;

public class TopologicalSortTest extends TestCase {

    private TopologicalSort<String> ts;

    public void setUp() {
        this.ts = new TopologicalSort<String>();
    }

    public void testNone() {
        this.assertSorted("");
    }

    public void testFailsIfParentDoesNotExist() {
        this.addNodes("A");
        try {
            this.addDependencies("AB");
            Assert.fail();
        } catch (RuntimeException re) {
            Assert.assertEquals("The parent is not yet a node: B", re.getMessage());
        }
    }

    public void testFailsIfDependentDoesNotExist() {
        this.addNodes("A");
        try {
            this.addDependencies("BA");
            Assert.fail();
        } catch (RuntimeException re) {
            Assert.assertEquals("The dependent is not yet a node: B", re.getMessage());
        }
    }

    public void testOneWithNoDependencies() {
        this.addNodes("A");
        this.assertSorted("A");
    }

    public void testOneThatDependsOnItself() {
        this.addNodes("A");
        try {
            this.addDependencies("AA"); // um?
            Assert.fail();
        } catch (CycleException re) {
            Assert.assertEquals("cycle occurred adding A -> A", re.getMessage());
        }
    }

    public void testTwoAlreadyInOrder() {
        this.addNodes("AB");
        this.addDependencies("BA");
        this.assertSorted("AB");
    }

    public void testTwoOutOfOrder() {
        this.addNodes("AB");
        this.addDependencies("AB");
        this.assertSorted("BA");
    }

    public void testFourWithTwoDeps() {
        this.addNodes("ABCD");
        this.addDependencies("AB", "CD");
        this.assertSorted("BADC");
    }

    public void testFourWithThreeDeps() {
        this.addNodes("ABCD");
        this.addDependencies("AB", "CD", "BD");
        this.assertSorted("DBAC");
    }

    public void testTwoWithWeakCycle() {
        this.addNodes("CAB");
        this.addDependencies("AB");
        this.assertSorted("CBA");
        this.addWeakDependencies("CB", "BA");
        this.assertSorted("BCA"); // CB took effect, BA was ignored
    }

    private void addNodes(String nodes) {
        for (int i = 0; i < nodes.length(); i++) {
            this.ts.addNode(String.valueOf(nodes.charAt(i)));
        }
    }

    private void addDependencies(String... dependencies) {
        for (String d : dependencies) {
            this.ts.addDependency(String.valueOf(d.charAt(0)), String.valueOf(d.charAt(1)));
        }
    }

    private void addWeakDependencies(String... dependencies) {
        for (String d : dependencies) {
            this.ts.addWeakDependency(String.valueOf(d.charAt(0)), String.valueOf(d.charAt(1)));
        }
    }

    private void assertSorted(String expected) {
        List<String> sorted = this.ts.sort();
        Assert.assertEquals(expected, Join.join(sorted, ""));
    }
}
