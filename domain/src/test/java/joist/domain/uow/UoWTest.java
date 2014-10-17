package joist.domain.uow;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import joist.domain.orm.Db;
import joist.domain.orm.NamedUpdater;
import joist.domain.orm.Repository;

import org.junit.Test;
import org.mockito.Mockito;

public class UoWTest {

  @Test
  public void testUoWCloseHandlesDbFailures() throws Exception {
    // given an initially-fine connection
    DataSource ds = Mockito.mock(DataSource.class);
    Connection conn = Mockito.mock(Connection.class);
    Mockito.doReturn(conn).when(ds).getConnection();
    // we can UoW.open
    UoW.open(new Repository(Db.MYSQL, ds), null);
    assertThat(UoW.isOpen(), is(true));
    // when connection.close becomes faulty
    Mockito.doThrow(new RuntimeException("foo")).when(conn).close();
    // and we try to close, it will fail
    try {
      UoW.close();
      fail();
    } catch (RuntimeException re) {
      assertThat(re.getMessage(), is("foo"));
    }
    // and we actually let go of the UoW thread local
    assertThat(UoW.isOpen(), is(false));
    Mockito.verify(conn).close();
  }

  @Test
  public void testUoWCloseHandlesDbFailuresInUpdaterSetNull() throws Exception {
    // given an initially-fine connection
    DataSource ds = Mockito.mock(DataSource.class);
    Connection conn = Mockito.mock(Connection.class);
    PreparedStatement ps = Mockito.mock(PreparedStatement.class);
    Mockito.doReturn(conn).when(ds).getConnection();
    Mockito.doReturn(ps).when(conn).prepareStatement(Mockito.anyString());
    // we can UoW.open
    UoW.open(new Repository(Db.MYSQL, ds), new NamedUpdater("foo"));
    assertThat(UoW.isOpen(), is(true));
    // when connection.close becomes faulty
    Mockito.doThrow(new RuntimeException("foo")).when(conn).prepareStatement(Mockito.anyString());
    // and we try to close, it will fail
    try {
      UoW.close();
      fail();
    } catch (RuntimeException re) {
      assertThat(re.getMessage(), is("foo"));
    }
    // and we actually let go of the UoW thread local
    assertThat(UoW.isOpen(), is(false));
    Mockito.verify(conn).close();
    Mockito.verify(conn, Mockito.atLeast(2)).prepareStatement(Mockito.anyString());
  }

  @Test
  public void testUoWOpenHandlesDbFailures() throws Exception {
    // given an initially-faulty connection
    DataSource ds = Mockito.mock(DataSource.class);
    Connection conn = Mockito.mock(Connection.class);
    Mockito.doReturn(conn).when(ds).getConnection();
    Mockito.doThrow(new RuntimeException("foo")).when(conn).setAutoCommit(false);
    // we can't UoW.open
    try {
      UoW.open(new Repository(Db.MYSQL, ds), null);
      fail();
    } catch (RuntimeException re) {
      assertThat(re.getMessage(), is("foo"));
    }
    // which is fine as long as we let go of the UoW
    assertThat(UoW.isOpen(), is(false));
    Mockito.verify(conn).setAutoCommit(false);
    Mockito.verify(conn).close();
  }

  @Test
  public void testUoWGoHandlesDbFailures() throws Exception {
    // given an initially-fine connection
    DataSource ds = Mockito.mock(DataSource.class);
    final Connection conn = Mockito.mock(Connection.class);
    Mockito.doReturn(conn).when(ds).getConnection();
    // we can do UoW.go
    try {
      UoW.go(new Repository(Db.MYSQL, ds), null, new Block() {
        public void go() {
          // and during our block, the connection becomes faulty
          try {
            RuntimeException re = new RuntimeException("foo");
            Mockito.doThrow(re).when(conn).commit();
            Mockito.doThrow(re).when(conn).rollback();
            Mockito.doThrow(re).when(conn).close();
          } catch (SQLException se) {
            throw new RuntimeException(se);
          }
        }
      });
      fail();
    } catch (RuntimeException re) {
      assertThat(re.getMessage(), is("foo"));
    }
    // which is fine as long as we let go of the UoW
    assertThat(UoW.isOpen(), is(false));
    Mockito.verify(conn).commit();
    Mockito.verify(conn).rollback();
    Mockito.verify(conn).close();
  }
}
