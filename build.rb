
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

# A hack to share build.yaml settings across projects
i['publish.options'] = {}
i['publish.options']['resolver'] = 'maven-share'
i['publish.options']['artifactspattern'] = 'target/[artifact]-[revision](-[classifier]).[ext]'
i['publish.options']['publishivy'] = 'false'
i['publish.options']['overwrite'] = 'true'

i['makepom.options'] = {}
i['makepom.options']['nested'] = {}
i['makepom.options']['nested']['mapping_1'] = {}
i['makepom.options']['nested']['mapping_1']['conf'] = 'default'
i['makepom.options']['nested']['mapping_1']['scope'] = 'compile'
i['makepom.options']['nested']['mapping_2'] = {}
i['makepom.options']['nested']['mapping_2']['conf'] = 'test'
i['makepom.options']['nested']['mapping_2']['scope'] = 'test'
i['makepom.options']['nested']['mapping_3'] = {}
i['makepom.options']['nested']['mapping_3']['conf'] = 'provided'
i['makepom.options']['nested']['mapping_3']['scope'] = 'provided'

def package_with_ivy(project)
  project.group = 'joist'
  project.version = THIS_VERSION
  package(:jar)
  package(:jar, :classifier => 'sources').clean.include :from => compile.sources

  file _("target/#{project.name}-#{project.version}.pom") => task('ivy:makepom')

  # monkey patch the pom to always go out, courtesy of Alex
  package(:jar).pom.tap do |pom|
    class << pom
      def needed?
        true
      end
    end
    _("target/#{project.name}-#{project.version}.pom").tap do |xml|
      pom.enhance [xml]
      pom.enhance { cp xml, pom.name }
    end
  end
end

