package joist.web.fakedomain;

import org.exigencecorp.conversion.AbstractOneWayConverter;

public class EmployeeToFriendlyStringConverter extends AbstractOneWayConverter<Employee, String> {

    public String convertOneToTwo(Employee value, Class<? extends String> toType) {
        return value.toFriendlyString();
    }

}
