package joist.sourcegen;

class ParsedName {

  /** @return a tuple of package name, simple name, and simple name with generics */
  static ParsedName parse(String fullNameWithPossibleGenerics) {
    String fullNameWithoutGenerics = fullNameWithPossibleGenerics.replaceAll("<.+>", ""); // prune generics
    int lastDot = fullNameWithoutGenerics.lastIndexOf('.');
    if (lastDot == -1) {
      return new ParsedName( //
        null,
        fullNameWithoutGenerics,
        fullNameWithPossibleGenerics);
    } else {
      return new ParsedName(
        fullNameWithoutGenerics.substring(0, lastDot),
        fullNameWithoutGenerics.substring(lastDot + 1),
        fullNameWithPossibleGenerics.substring(lastDot + 1));
    }
  }

  final String packageName;
  final String simpleName;
  final String simpleNameWithGenerics;

  private ParsedName(String packageName, String simpleName, String simpleNameWithGenerics) {
    this.packageName = packageName;
    this.simpleName = simpleName;
    this.simpleNameWithGenerics = simpleNameWithGenerics;
  }

  String getFullName() {
    if (this.packageName == null) {
      return this.simpleName;
    } else {
      return this.packageName + "." + this.simpleName;
    }
  }

  boolean hasGenerics() {
    return !(this.simpleName.equals(this.simpleNameWithGenerics));
  }

}
