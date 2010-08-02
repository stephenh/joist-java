package joist.sourcegen;

import java.util.ArrayList;
import java.util.List;

public class Argument {

  public final String type;
  public final String name;

  /** @param typesAndNames can be one "Foo f, Bar b" or multiple "Foo f", "Bar b" */
  public static List<Argument> split(String... typesAndNames) {
    List<Argument> arguments = new ArrayList<Argument>();
    if (typesAndNames.length == 1) {
      String line = typesAndNames[0].trim();
      if (line.length() == 0) {
        return arguments;
      }
      int last = 0;
      int parenCount = 0;
      int current = 0;
      for (; current < line.length(); current++) {
        if (line.charAt(current) == '<') {
          parenCount++;
        } else if (line.charAt(current) == '>') {
          parenCount--;
        } else if (line.charAt(current) == ',' && parenCount == 0) {
          arguments.add(new Argument(line.substring(last, current)));
          last = current + 1;
        }
      }
      arguments.add(new Argument(line.substring(last, current)));
    } else {
      for (String typeAndName : typesAndNames) {
        arguments.add(new Argument(typeAndName));
      }
    }
    return arguments;
  }

  public Argument(String typeAndName) {
    int lastSpace = typeAndName.lastIndexOf(' ');
    if (lastSpace == -1) {
      throw new RuntimeException("No space in typeAndName" + typeAndName);
    }
    this.type = typeAndName.substring(0, lastSpace).trim();
    this.name = typeAndName.substring(lastSpace + 1).trim();
  }

  public Argument(String type, String name) {
    this.type = type;
    this.name = name;
  }

  public String toString() {
    return this.type + " " + this.name;
  }
}
