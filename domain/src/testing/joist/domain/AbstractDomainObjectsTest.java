package joist.domain;

import java.util.HashMap;
import java.util.Map;

import joist.domain.uow.UoW;
import joist.registry.Ref;
import joist.util.TestCounters;
import junit.framework.TestCase;

public abstract class AbstractDomainObjectsTest extends TestCase {

    private Map<Ref<? extends Object>, Object> stubs = new HashMap<Ref<? extends Object>, Object>();

    public void setUp() throws Exception {
        super.setUp();
        TestCounters.resetAll();
        // Protect against previous tests that didn't clean up
        if (UoW.isOpen()) {
            UoW.close();
        }
        UoW.open();
    }

    public void tearDown() throws Exception {
        UoW.close();
        for (Map.Entry<Ref<? extends Object>, Object> entry : this.stubs.entrySet()) {
            ((Ref<Object>) entry.getKey()).set(entry.getValue());
        }
        super.tearDown();
    }

    protected void commitAndReOpen() {
        UoW.commitAndReOpen();
    }

    protected void rollback() {
        UoW.rollback();
    }

    protected void flush() {
        UoW.flush();
    }

    protected <T extends DomainObject> T reload(T instance) {
        return (T) UoW.load(instance.getClass(), instance.getId());
    }

    protected <T> void stub(Ref<T> ref, T tempValue) {
        this.stubs.put(ref, ref.get());
        ref.set(tempValue);
    }

}
