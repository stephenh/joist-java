package click.util;

import org.exigencecorp.bindgen.Binding;

import click.CurrentContext;

/** A wrapper for content to show the user--could be just a string, a binding, etc. */
public class TextContent {

    private Object content;

    public void set(Object content) {
        this.content = content;
    }

    public String get() {
        Object value = this.content;
        if (this.content instanceof Binding) {
            value = ((Binding<?>) this.content).get();
        }
        String valueAsString = CurrentContext.get().getClickConfig().getTextConverterRegistry().convert(value, String.class);
        return valueAsString;
    }

}
