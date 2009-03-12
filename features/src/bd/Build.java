import joist.domain.codegen.JoistTask;

import org.exigencecorp.bd.HomeCache;
import org.exigencecorp.bd.resources.Dir;

public class Build {

    public JoistTask joist = new JoistTask("features");
    public Dir lib = new Dir("lib");

    public Build() {
        HomeCache.updateIfNeeded(this.lib.files());
        this.joist.databaseSaPassword = "postgresql_chimera";
    }

}
