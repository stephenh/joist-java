package joist.web;

import joist.converter.ConverterRegistry;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.Log4JLogChute;

/** User configuration for {@link WebServlet}.
 *
 * If you want to customize more than just the base package name,
 * you should override methods like <code>createVelocityEngine</code>
 * and <code>createPageResolver</code>.
 */
public class WebConfig {

  private final PageResolver pageResolver;
  private final VelocityEngine velocityEngine;
  private final String basePackageName;
  private final ConverterRegistry urlConverterRegistry;
  private final ConverterRegistry textConverterRegistry;

  public WebConfig(String basePackageName) {
    this.basePackageName = basePackageName;
    this.pageResolver = this.createPageResolver();
    this.velocityEngine = this.createVelocityEngine();
    this.urlConverterRegistry = this.createUrlConverterRegistry();
    this.textConverterRegistry = this.createTextConverterRegistry();
  }

  /** Override if you want to customize the velocity engine. */
  protected VelocityEngine createVelocityEngine() {
    VelocityEngine engine = new VelocityEngine();
    engine.setProperty("resource.loader", "class");
    engine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, Log4JLogChute.class.getName());
    engine.setProperty(Log4JLogChute.RUNTIME_LOG_LOG4J_LOGGER, "joist.web.util.velocity");
    engine.setProperty(Log4JLogChute.RUNTIME_LOG_LOG4J_LOGGER_LEVEL, "INFO");
    try {
      engine.init();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return engine;
  }

  /** Override if you want to customize the page resolver. */
  protected PageResolver createPageResolver() {
    return new PageResolver(this.basePackageName);
  }

  /** Override if you want to customize the converter registry. */
  protected ConverterRegistry createUrlConverterRegistry() {
    return ConverterRegistry.newRegistryWithDefaultConverters();
  }

  /** Override if you want to customize the converter registry. */
  protected ConverterRegistry createTextConverterRegistry() {
    return ConverterRegistry.newRegistryWithDefaultConverters();
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

  public ConverterRegistry getUrlConverterRegistry() {
    return this.urlConverterRegistry;
  }

  public ConverterRegistry getTextConverterRegistry() {
    return this.textConverterRegistry;
  }
}
