
repositories.release_to = "file://#{ENV['HOME']}/repo"
repositories.remote << 'http://www.ibiblio.org/maven2'
repositories.remote << 'http://repo.joist.ws'
# http://download.java.net/maven/1/

THIS_VERSION = "0.5-SNAPSHOT"

C3P0 = 'c3p0:c3p0:jar:0.9.1.2'
COMMONS_LANG = 'commons-lang:commons-lang:jar:2.4'
JUNIT = 'junit:junit:jar:3.8.2'
SLF4J_API = 'org.slf4j:slf4j-api:jar:1.5.8'
SLF4J_JDK = 'org.slf4j:slf4j-jdk14:jar:1.5.8'
TIMEANDMONEY = 'timeandmoney:timeandmoney:jar:0.5.1'
MYSQL = 'mysql:mysql-connector-java:jar:5.1.13'
VELOCITY = transitive('org.apache.velocity:velocity:jar:1.6.2').reject { |a| a.group == 'ant' or a.group == 'javax.servlet' }
BINDGEN = 'org.bindgen:bindgen:jar:2.3'
SERVLET_API = 'javax.servlet:servlet-api:jar:2.5'

define 'joist' do
  project.version = THIS_VERSION
  project.compile.options.target = '1.6'
  project.compile.options.source = '1.6'
  project.compile.options.XprintRounds

  define 'domain' do
    package :jar
    package :sources
    compile.with projects('util'), COMMONS_LANG, TIMEANDMONEY, C3P0
    test.with SLF4J_JDK, JUNIT
  end

  define 'domain-testing' do
    package :jar
    package :sources
    compile.with projects('domain', 'util'), JUNIT
  end

  define 'util' do
    package :jar
    package :sources
    compile.with SLF4J_API
    test.with SLF4J_JDK, JUNIT
  end

  define 'migrations' do
    package :jar
    package :sources
    compile.with projects('domain', 'util'), COMMONS_LANG, C3P0
    test.with JUNIT
  end

  define 'features' do
    package :jar
    package :sources
    compile.from _('src/codegen')
    compile.with projects('domain', 'migrations', 'util'), COMMONS_LANG, TIMEANDMONEY
    test.with projects('domain-testing'), JUNIT, MYSQL, C3P0, SLF4J_API, SLF4J_JDK
  end

  define 'web' do
    package :jar
    package :sources
    compile.from _('target/apt')
    resources.from(_('src/test/java')).include("**/*.htm")
    compile.with projects('util'), VELOCITY, BINDGEN, COMMONS_LANG, SERVLET_API
    test.with BINDGEN, SLF4J_API, SLF4J_JDK
    # when 'buildr clean test', this mkdir's target/apt
    file(_('target/apt') => FileList[_('src/main/java/**.*java')]) do |dir|
      mkdir_p dir.to_s
    end
  end
end
