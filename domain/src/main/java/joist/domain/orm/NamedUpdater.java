package joist.domain.orm;

public class NamedUpdater implements Updater {
  private final String name;

  public NamedUpdater(final String name) {
    this.name = name;
  }

  public String getUpdaterId() {
    return this.name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}
