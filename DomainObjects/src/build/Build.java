import org.exigencecorp.build.BuildRunner;
import org.exigencecorp.build.SourceDirectory;

public class Build {

    public SourceDirectory srcMain = new SourceDirectory("src/main").output("build/main");

    public static void main(String[] args) {
        BuildRunner.run(new Build(), args);
    }

    public void compile() {
        this.srcMain.compile();
    }

}
