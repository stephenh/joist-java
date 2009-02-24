package click;

import org.apache.velocity.app.VelocityEngine;

public class ClickConfig {

    private final PageResolver pageResolver;
    private final VelocityEngine velocityEngine;
    private final String basePackageName;

    public ClickConfig(String basePackageName) {
        this.basePackageName = basePackageName;
        this.pageResolver = this.createPageResolver();
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

    protected PageResolver createPageResolver() {
        return new PageResolver(this.basePackageName);
    }

    public String getBasePackageName() {
        return this.basePackageName;
    }

    public PageResolver getPageResolver() {
        return this.pageResolver;
    }

    public VelocityEngine getVelocityEngine() {
        return this.velocityEngine;
    }
}
