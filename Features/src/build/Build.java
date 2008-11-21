import org.exigencecorp.domainobjects.codegen.tasks.DomainObjectBuilder;

public class Build {

    public DomainObjectBuilder dobj = new DomainObjectBuilder("features");

    public Build() {
        this.dobj.codegenConfig.setJavaType("date", "com.domainlanguage.time.CalendarDate", "features.domain.orm.CalendarDateAliasColumn");
        this.dobj.databaseSaPassword = "postgresql_chimera";
    }

}
