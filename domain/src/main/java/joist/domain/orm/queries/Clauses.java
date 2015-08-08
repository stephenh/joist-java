package joist.domain.orm.queries;

import java.util.ArrayList;
import java.util.List;

public class Clauses {

  private final List<Clause> clauses = new ArrayList<Clause>();

  public void append(StringBuilder sb) {
    for (Clause c : this.clauses) {
      c.append(sb);
    }
  }

  public void add(String c) {
    this.clauses.add(new Clause(c, 0));
  }

  public void add(Clauses other) {
    for (Clause c : other.clauses) {
      this.clauses.add(c.indent());
    }
  }

  public void addNoIndent(Clauses other) {
    for (Clause c : other.clauses) {
      this.clauses.add(c);
    }
  }

  private static class Clause {
    private final String sql;
    private final int indent;

    private Clause(String sql, int indent) {
      this.sql = sql;
      this.indent = indent;
    }

    private void append(StringBuilder sb) {
      for (int i = 0; i < this.indent; i++) {
        sb.append(" ");
      }
      sb.append(this.sql);
      sb.append("\n");
    }

    private Clause indent() {
      return new Clause(this.sql, this.indent + 1);
    }
  }
}
