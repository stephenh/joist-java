package org.exigencecorp.domainobjects.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.exigencecorp.domainobjects.Code;
import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.util.Interpolate;
import org.exigencecorp.util.Join;

/**
 * Describes domain objects in a (basically) human readable fashion.
 *
 * The convention for showing things on the screen is:
 *
 *    <class name> <instance description>
 *
 * E.g.:
 *
 *    Employee john smith
 *
 * The class name is pretty easy to follow, but each domain object needs
 * to implement toFriendlyString() to decide what users should see. (Note,
 * this is different from toString() itself which should be more compact
 * and programmer friendly as it will show up in the logs).
 *
 * The instance description should ideally be a combination of the instance's
 * attributes, as long as they aren't null, and space separated. (Employee
 * above with the comma is an exception--we might change that.)
 *
 * The idea with this helper method is that the caller should not care how
 * the instance's attributes are turned into strings, and indeed shouldn't be
 * decided willy nilly, but instead deferring to this standard class that will
 * just do the right thing (e.g. if an attribute is an ado, call that ado's
 * friendly string, if a code, use the name, etc.).
 *
 * This method also handles null checks, so NPEs, etc., should be avoided.
 *
 * @param parameters objects that, if not null, have toFriendlyString()/getName()/toString() called on them and joined together
 */
public class FriendlyString {

    private FriendlyString() {
    }

    public static String join(Object... parameters) {
        List<String> friendly = new ArrayList<String>();
        for (Object object : parameters) {
            if (object instanceof DomainObject) {
                friendly.add(((DomainObject) object).toFriendlyString());
            } else if (object instanceof Code) {
                friendly.add(((Code) object).getName());
            } else if (object != null) {
                friendly.add(ObjectUtils.toString(object));
            }
        }
        return Join.space(friendly);
    }

    /** The same as above but will prefix domain objects with their type name. */
    public static String joinWithTypeNamePrefixes(Object... parameters) {
        List<String> friendly = new ArrayList<String>();
        for (Object object : parameters) {
            if (object instanceof DomainObject) {
                friendly.add(((DomainObject) object).toFriendlyTypeName() + " " + ((DomainObject) object).toFriendlyString());
            } else if (object instanceof Code) {
                friendly.add(((Code) object).getName());
            } else if (object != null) {
                friendly.add(ObjectUtils.toString(object));
            }
        }
        return Join.space(friendly);
    }

    public static String interpolate(String message, Object... args) {
        for (int i = 0; i < args.length; i++) {
            args[i] = FriendlyString.join(args[i]);
        }
        return Interpolate.string(message, args);
    }

}
