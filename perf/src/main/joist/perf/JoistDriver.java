package joist.perf;

import javax.sql.DataSource;

import joist.domain.orm.Repository;
import joist.domain.uow.UoW;
import joist.domain.util.ConnectionSettings;
import joist.domain.util.PgUtilFactory;
import joist.registry.ResourceRef;

import com.sun.japex.TestCase;

import features.domain.Parent;

public class JoistDriver extends com.sun.japex.JapexDriverBase {

    @Override
    public void initializeDriver() {
        if (Repository.datasource == null) {
            final DataSource ds = new PgUtilFactory(ConnectionSettings.forApp("features")).create();
            Repository.datasource = new ResourceRef<DataSource>() {
                public DataSource get() {
                    return ds;
                }

                public boolean isStarted() {
                    return true;
                }
            };
        }
    }

    @Override
    public void run(TestCase testCase) {
        int number = new Integer(testCase.getParam("number"));
        boolean commitOnEach = testCase.getBooleanParam("commitOnEach");
        boolean insert = testCase.getParam("type").equals("insert");
        if (!commitOnEach) {
            UoW.open();
        }

        for (int i = 0; i < number; i++) {
            if (commitOnEach) {
                UoW.open();
            }

            if (insert) {
                new Parent(String.valueOf(i));
            } else {
                Parent.queries.find(i + 1).setName(System.currentTimeMillis() + "");
            }

            if (commitOnEach) {
                UoW.commit();
                UoW.close();
            }
        }

        if (!commitOnEach) {
            UoW.commit();
            UoW.close();
        }
    }

}
