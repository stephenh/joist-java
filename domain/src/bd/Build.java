import java.io.File;

import org.exigencecorp.bd.resources.Files;
import org.exigencecorp.bd.resources.Zip;
import org.exigencecorp.bd.resources.java.Jar;
import org.exigencecorp.bd.resources.java.Lib;
import org.exigencecorp.bd.resources.java.Source;

public class Build {

    public Files bin = new Files("bin");
    public File binMain = new File("bin/main");
    public Lib libMain = new Lib("lib/main");
    public Lib libBd = new Lib("lib/bd");
    public Source srcMain = new Source("src/main", this.binMain).lib(this.libMain);
    public Jar binaryJar = new Jar("bin/joist.domain.jar").includes(this.binMain);
    public Zip sourceZip = new Zip("bin/joist.domain.zip").includes(this.srcMain.getSourceFiles().get(0));

    public Build() {
    }

    public void updateLibs() {
        this.libMain.updateFromHomeCache();
        this.libBd.updateFromHomeCache();
    }

    public void clean() {
        this.bin.delete();
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
