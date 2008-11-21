import org.exigencecorp.bd.Zip;
import org.exigencecorp.bd.java.Jar;
import org.exigencecorp.bd.java.Lib;
import org.exigencecorp.bd.java.Source;

public class Build {

    public Lib libMain = new Lib("lib/main");
    public Source srcMain = new Source("src/main", "bin/main").lib(this.libMain);
    public Jar binaryJar = new Jar("bin/exigence-utilites.jar").includes(this.srcMain.getDestination());
    public Zip sourceZip = new Zip("bin/exigence-utilites.zip").includes(this.srcMain.getSourceFiles());

    public Build() {
        this.libMain.updateFromHomeCache();
    }

    public void compile() {
        this.srcMain.compile();
    }

    public void jar() {
        this.compile();
        this.binaryJar.create();
        this.sourceZip.create();
        this.binaryJar.publishToHomeCache();
        this.sourceZip.publishToHomeCache();
    }

}
