import org.exigencecorp.build.BuildRunner;
import org.exigencecorp.domainobjects.DomainObjectBuilder;

import com.domainlanguage.time.CalendarDate;

import features.domain.orm.CalendarDateAliasColumn;

public class Build {

    public static void main(String[] args) {
        BuildRunner.run(new Build(), args);
    }

    public DomainObjectBuilder dobj = new DomainObjectBuilder("features");

    public Build() {
        this.dobj.codegenConfig.setJavaType("date", CalendarDate.class, CalendarDateAliasColumn.class);
        this.dobj.databaseSaPassword = "postgresql_chimera";
    }

}
