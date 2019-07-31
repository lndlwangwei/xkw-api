def nodes = ['dev':['gateway1'], '28test':['gateway1']]
def serviceBasePath = '/data/service/gateways'
def buildProjectName = "xkw-api-gateway-build"

nodes.entrySet().each {entry ->
    def env = entry.key
    def services = entry.value

    node(env) {
        stage('deploy') {
//            deleteDir('target')
            copyArtifacts(projectName: "${buildProjectName}")

            services.each { service ->
                def servicePath = "$serviceBasePath/$service"
                if (!fileExists(servicePath)) {
                    sh "mkdir ${servicePath}"
                    writeFile encoding: 'utf-8', file: "$servicePath/test.txt", text: "this is $service"
                }

                sh "cp target/*.jar ${servicePath}"

                    def serviceIndex = service.substring(service.length() - 1)
                withEnv(['JENKINS_NODE_COOKIE=dontkillme']) {
                    sh "java -jar ${servicePath}/gateway-0.0.1-SNAPSHOT.jar --spring.profiles.active=node$serviceIndex &"
                }
            }
        }
    }
}
