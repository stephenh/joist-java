package joist;

import java.io.File;

import joist.util.Inflector;
import joist.util.Read;
import joist.util.Write;

public class Bootstrap {

    private final String projectName; // Stored with underscores, e.g. your_project_name

    public Bootstrap(String projectName) {
        this.projectName = Inflector.underscore(projectName); // assume they typed camel case, if not, this is a no-op
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java -jar joist.bootstrap.jar project_name");
            return;
        }
        new Bootstrap(args[0]).bootstrap();
    }

    public void bootstrap() {
        for (String path : Files.files) {
            String outputContent = Read.fromClasspath("/templates/" + path) //
                .replaceAll("@project_name@", this.projectName)
                .replaceAll("@projectName@", this.getProjectNameCamelCasedUncapitalized())
                .replaceAll("@ProjectName@", this.getProjectNameCamelCased());
            String outputPath = "./" + path //
                .replaceAll("project_name", this.projectName)
                .replaceAll("projectName", this.getProjectNameCamelCasedUncapitalized())
                .replaceAll("ProjectName", this.getProjectNameCamelCased());
            System.out.println("Generating " + outputPath + "...");
            Write.toFile(outputPath, outputContent);
        }
        new File("./src/codegen").mkdirs(); // we don't output any files here, so manually kick it
        System.out.println("...done.");
    }

    private String getProjectNameCamelCased() {
        return Inflector.camelize(this.projectName);
    }

    private String getProjectNameCamelCasedUncapitalized() {
        return Inflector.uncapitalize(this.getProjectNameCamelCased());
    }

}
