package org.exigencecorp.conversion;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.exigencecorp.util.TestCounters;

public class ConverterInheritanceTest extends TestCase {
    private ConverterRegistry r = new ConverterRegistry();

    public void setUp() throws Exception {
        super.setUp();
        TestCounters.resetAll();
        this.r.addConverter(new AbstractConverter<DomainObject, String>() {
            public String convertOneToTwo(DomainObject value, Class<? extends String> toType) {
                return value.getId().toString();
            }

            public DomainObject convertTwoToOne(String value, Class<? extends DomainObject> toType) {
                try {
                    // We get the toType, so even though we return the generic DomainObject, we have
                    // the concrete type to do database lookups, etc.
                    DomainObject d = toType.newInstance();
                    d.setId(new Integer(value));
                    return d;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void testInterfaceConverterWorksForClass() {
        // Employee --> String picks up the DomainObjectToString converter
        Assert.assertEquals("1", this.r.convert(new Employee(1), String.class));
        // String --> Employee picks up the DomainObjectToString converter
        Assert.assertEquals(1, this.r.convert("1", Employee.class).getId().intValue());
    }

    public void testProbingIsCached() {
        // Start at 0
        Assert.assertEquals(0, ConverterRegistry.probes.get());

        // We have to probe the first time
        Assert.assertEquals("1", this.r.convert(new Employee(1), String.class));
        Assert.assertEquals(1, ConverterRegistry.probes.get());

        // The second time we won't have to probe
        Assert.assertEquals("1", this.r.convert(new Employee(1), String.class));
        Assert.assertEquals(1, ConverterRegistry.probes.get());
    }

    public interface DomainObject {
        Integer getId();

        void setId(Integer id);
    }

    public static class Employee implements DomainObject {
        public Integer id;

        public Employee() {
        }

        public Employee(Integer id) {
            this.id = id;
        }

        public Integer getId() {
            return this.id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}
