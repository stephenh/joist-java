
# just for buildr to get trax, then everything else is ivy
repositories.remote << 'http://repo1.maven.org/maven2/'
repositories.release_to = 'sftp://joist.ws/var/joist.repo'
repositories.release_to[:permissions] = 0644

require 'buildr/ivy_extension'

THIS_VERSION = ENV['version'] || 'SNAPSHOT'

# to resolve the ${joist.version} in the ivy.xml
Java.java.lang.System.setProperty("joist.version", THIS_VERSION)

def package_with_ivy(project)
  project.group = 'joist'
  project.version = THIS_VERSION
  package_with_sources

  package(:jar).pom.tap do |pom|
    pom.enhance [task('ivy:makepom')]
    pom.from 'target/pom.xml'
  end
end

