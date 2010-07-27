
require 'buildr/ivy_extension'

# buildr needs this to bootstrap with ant-trax, then ivy4r will use our ivysettings
repositories.remote << 'http://www.ibiblio.org/maven2'
# Use ~/.ivy2
Buildr.settings.build['ivy']['home.dir'] = "#{ENV['HOME']}/.ivy2"

define 'joist' do

  project.version = 'SNAPSHOT'

  define 'joist-domain' do
    package :jar, :id => 'joist-domain'
    package :sources, :id => 'joist-domain'
    ivy.compile_conf('default').test_conf('unit')
    task "ivy:publish" => "ivy:makepom"
    compile.with project('joist:joist-util')
  end

  define 'joist-util' do
    package :jar, :id => 'joist-util'
    package :sources, :id => 'joist-util'
    ivy.compile_conf('default').test_conf('unit')
    task "ivy:publish" => "ivy:makepom"
  end

end
