package click;

import org.apache.velocity.app.VelocityEngine;

public class ClickConfig {

    private final PageUrlResolver pageUrlResolver;
    private final VelocityEngine velocityEngine;
    private final String basePackageName;

    public ClickConfig(String basePackageName) {
        this.basePackageName = basePackageName;
        this.pageUrlResolver = this.createPageUrlResolver();
        this.velocityEngine = this.createVelocityEngine();
    }

    protected VelocityEngine createVelocityEngine() {
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty("resource.loader", "class");
        engine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        try {
            engine.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return engine;
    }

    protected PageUrlResolver createPageUrlResolver() {
        return new PageUrlResolver(this.basePackageName);
    }

    public String getBasePackageName() {
        return this.basePackageName;
    }

    public PageUrlResolver getPageUrlResolver() {
        return this.pageUrlResolver;
    }

    public VelocityEngine getVelocityEngine() {
        return this.velocityEngine;
    }
}
