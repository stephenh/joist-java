package joist.domain.util.converters;

import joist.converter.Converter;
import joist.converter.ConverterRegistry;
import joist.util.Interpolate;

import org.apache.commons.lang.StringUtils;

/**
 * Describes domain objects for on-screen display to users.
 *
 * The convention is:
 *
 *    <humanized simple class name> <instance attribute 1> <instance attribute 2 ...>
 *
 * E.g.:
 *
 *    Employee John Smith 123-456-7890
 *
 * Each domain object needs to implement toTextString() to decide what
 * users should see.
 *
 * However, instead of custom-formatting each attribute in <code>toTextString</code>,
 * domain objects should just defer to this class, e.g.:
 * 
 *     public String toTextString() {
 *         return TextString.join(this.getAttribute1(), this.getAttribute2());
 *     }
 *
 * This <code>TextString</code> class will then consistently format the in
 * a screen-appropriate fashion. This can be customized with the
 * <code>ConverterRegistry</code>.
 */
public class TextString {

  private static final ConverterRegistry textConverterRegistry = ConverterRegistry.newRegistryWithDefaultConverters();

  static {
    TextString.textConverterRegistry.addConverter(new DomainObjectToTextStringConverter());
    TextString.textConverterRegistry.addConverter(new CodeToTextStringConverter());
  }

  public static void addConverter(Converter<?, ?> c) {
    TextString.textConverterRegistry.addConverter(c);
  }

  public static String interpolate(String message, Object... args) {
    return Interpolate.string(message, TextString.textConverterRegistry, args);
  }

  public static String join(Object... args) {
    String message = StringUtils.removeEnd(StringUtils.repeat("{} ", args.length), " ");
    return TextString.interpolate(message, args);
  }

  private TextString() {
  }

}
