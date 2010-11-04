
require 'buildr/ivy_extension'

THIS_VERSION = ENV['version'] || '0.6-SNAPSHOT'

# to resolve the ${joist.version} in the ivy.xml
Java.java.lang.System.setProperty("joist.version", THIS_VERSION)

# just for buildr to get trax, then everything else is ivy
repositories.remote << 'http://www.ibiblio.org/maven2'
repositories.release_to = 'sftp://joist.ws/var/joist.repo'
repositories.release_to[:permissions] = 0644

i = Buildr.settings.build['ivy'] = {}
i['home.dir'] = "#{ENV['HOME']}/.ivy2"
i['settings.file'] = './ivysettings.xml'

def package_with_ivy(project)
  project.group = 'joist'
  project.version = THIS_VERSION
  package_with_sources

  package(:jar).pom.tap do |pom|
    pom.enhance [task('ivy:makepom')]
    pom.from 'target/pom.xml'
  end
end

