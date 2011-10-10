package joist.perf.joist;

import javax.sql.DataSource;

import joist.domain.orm.Db;
import joist.domain.orm.Repository;
import joist.domain.uow.Block;
import joist.domain.uow.UoW;
import joist.domain.util.ConnectionSettings;
import joist.domain.util.pools.Pgc3p0Factory;
import joist.jdbc.Jdbc;
import features.domain.Child;
import features.domain.GrandChild;
import features.domain.Parent;

public class JoistPrefetch {

  public static void main(String[] args) {
    final DataSource ds = new Pgc3p0Factory(ConnectionSettings.forApp(Db.PG, "features")).create();
    Jdbc.queryForInt(ds, "select * from flush_test_database()");
    System.out.println("flushed");
    Repository.db = Db.PG;
    Repository.datasource = ds;

    UoW.go(null, new Block() {
      public void go() {
        Parent parent = new Parent();
        parent.setName("p");
        for (int i = 0; i < 2; i++) {
          Child child = new Child();
          child.setName("child." + i);
          child.setParent(parent);
          for (int j = 0; j < 3; j++) {
            GrandChild grandchild = new GrandChild();
            grandchild.setName("grandchild." + i + "." + j);
            grandchild.setChild(child);
          }
        }
      }
    });

    UoW.go(null, new Block() {
      public void go() {
        Parent p = Parent.queries.find(1);
        for (Child c : p.getChilds()) {
          for (GrandChild gc : c.getGrandChilds()) {
            System.out.println(gc.getName());
          }
        }
      }
    });

    System.out.println("saved");
  }
}
