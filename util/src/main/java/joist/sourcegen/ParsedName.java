package joist.sourcegen;

class ParsedName {

  /** @return a tuple of package name, simple name, and simple name with generics */
  static ParsedName parse(String fullNameWithPossibleGenerics) {
    String s = fullNameWithPossibleGenerics.replaceAll("<.+>", ""); // prune generics
    int lastDot = s.lastIndexOf('.');
    if (lastDot == -1) {
      return new ParsedName(null, s, fullNameWithPossibleGenerics);
    } else {
      return new ParsedName(s.substring(0, lastDot), s.substring(lastDot + 1), fullNameWithPossibleGenerics.substring(lastDot + 1));
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

}
