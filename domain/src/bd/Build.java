import org.exigencecorp.bd.resources.Dir;
import org.exigencecorp.bd.resources.Zip;
import org.exigencecorp.bd.resources.java.Jar;
import org.exigencecorp.bd.resources.java.Lib;
import org.exigencecorp.bd.resources.java.Source;

public class Build {

    public Dir bin = new Dir("bin");
    public Dir binMain = this.bin.dir("main");
    public Lib libMain = new Lib("lib/main");
    public Lib libBd = new Lib("lib/bd");
    public Lib homeCache = Lib.homeCache();
    public Source srcMain = new Source("src/main", this.binMain).lib(this.libMain).lib(this.homeCache);
    public Jar binaryJar = new Jar("bin/joist.domain.jar").includes(this.binMain);
    public Zip sourceZip = new Zip("bin/joist.domain.zip").includes(this.srcMain.getSources());

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
