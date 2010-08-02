
require 'buildr/ivy_extension'

THIS_VERSION = '0.5-SNAPSHOT'

# just for buildr to get trax, then everything else is ivy
repositories.remote << 'http://www.ibiblio.org/maven2'
repositories.release_to = 'sftp://joist.ws/var/joist.repo'
repositories.release_to[:permissions] = 0644

i = Buildr.settings.build['ivy'] = {}
i['home.dir'] = "#{ENV['HOME']}/.ivy2"
i['settings.file'] = './ivysettings.xml'

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

def package_with_ivy_pom(project)
  project.package :jar
  project.package :sources
  project.package(:jar).pom.from _("target/#{project.name}-#{project.version}.pom")
end

