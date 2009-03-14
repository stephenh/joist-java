import java.io.File;

import org.exigencecorp.bd.resources.Dir;
import org.exigencecorp.bd.resources.Zip;
import org.exigencecorp.bd.resources.java.Jar;
import org.exigencecorp.bd.resources.java.Source;

public class Build {

    static {
        Source.defaultIncludeHomeCache = true;
    }

    public Dir bin = new Dir("bin");
    public Dir binMain = this.bin.dir("main");

    public Dir libMain = new Dir("lib/main");
    public Dir libBd = new Dir("lib/bd");

    public Source srcMain = new Source("src", this.binMain).lib(this.libMain);
    public Jar binaryJar = new Jar("bin/joist.web.jar").includes(this.binMain);
    public Zip sourceZip = new Zip("bin/joist.web.zip").includes(this.srcMain.getSources());

    public Build() {
    }

    public void updateLibs() {
        // this.libMain.updateFromHomeCache();
        // this.libBd.updateFromHomeCache();
    }

    public void compile() {
        this.srcMain.compile();
    }

    public void jar() {
        this.compile();
        this.binaryJar.create();
        this.binaryJar.publishToHomeCache();
        this.sourceZip.create();
        this.sourceZip.publishToHomeCache();
    }

}
