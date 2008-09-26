import org.exigencecorp.util.Log;
import org.exigencecorp.util.Reflection;

public class Cli {

    private Cli() {
    }

    public static final void main(String[] args) {
        int exitCode = 0; // default, "success" exit code
        long start = System.currentTimeMillis();
        Log.debug("Starting {}", (Object[]) args);

        try {
            String packageName = args[0];
            for (String toolClassName : args[1].split(", ?")) {
                ((Runnable) Reflection.newInstance(packageName + "." + toolClassName)).run();
                Log.info("{} Done", toolClassName);
            }
        } catch (Exception e) {
            Log.error(e.getMessage(), e);
            exitCode = 1; // we failed, so let our caller know.
        }

        long end = System.currentTimeMillis();
        Log.debug("{} ms", (end - start));

        System.exit(exitCode);
    }

}
