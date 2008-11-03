package org.exigencecorp.domainobjects.codegen.dtos;

import java.util.List;

public interface Property {

    String getJavaType();

    String getDefaultJavaString();

    String getCapitalVariableName();

    String getVariableName();

    boolean isNotNull();

    List<String> getCustomRules();

}
