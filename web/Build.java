import java.io.File;

import org.exigencecorp.bd.Zip;
import org.exigencecorp.bd.java.Jar;
import org.exigencecorp.bd.java.Lib;
import org.exigencecorp.bd.java.Source;

public class Build {

    public Lib libMain = new Lib("lib/main");
    public Lib libBd = new Lib("lib/bd");
    public File binMain = new File("bin/main");
    public Source srcMain = new Source("src", this.binMain).lib(this.libMain);
    public Jar binaryJar = new Jar("bin/joist.web.jar").includes(this.binMain);
    public Zip sourceZip = new Zip("bin/joist.web.zip").includes(this.srcMain.getSourceFiles().get(0));

    public Build() {
    }

    public void updateLibs() {
        this.libMain.updateFromHomeCache();
        this.libBd.updateFromHomeCache();
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
