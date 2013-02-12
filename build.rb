
# just for buildr to get trax, then everything else is ivy
repositories.remote << 'http://repo1.maven.org/maven2/'
repositories.release_to = 'sftp://joist.ws/var/www/joist.repo'
repositories.release_to[:permissions] = 0644

require 'buildr/ivy_extension'

THIS_VERSION = ENV['version'] || 'SNAPSHOT'

# to resolve the ${joist.version} in the ivy.xml
Java.java.lang.System.setProperty("joist.version", THIS_VERSION)
Java.java.lang.System.setProperty("ivy.pom.version", THIS_VERSION)

def package_with_ivy(project)
  project.group = 'joist'
  project.version = THIS_VERSION
  package_with_sources

  # use ivy4r's pom instead of the default buildr one
  file 'target/pom.xml' => task('ivy:makepom')
  package(:jar).pom.tap do |pom|
    pom.from 'target/pom.xml'
  end
end

