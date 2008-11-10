package org.exigencecorp.domainobjects.codegen.dtos;

import java.util.List;

public interface Property {

    String getJavaType();

    String getCapitalVariableName();

    String getVariableName();

    List<String> getCustomRules();

}
