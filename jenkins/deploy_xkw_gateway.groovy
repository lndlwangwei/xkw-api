def nodes = ['dev':['gateway1', 'gateway2', 'gateway3'], '28test':['gateway1', 'gateway2']]
def serviceBasePath = '/data/service/gateways'
def buildProjectName = "xkw-api-gateway-build"

nodes.entrySet().each {entry ->
    def nodeName = entry.key
    def services = entry.value

    node(nodeName) {
        stage('deploy') {
            copyArtifacts(projectName: "${buildProjectName}")

            services.each {
                def servicePath = "$serviceBasePath/$it"
                if (!fileExists(servicePath)) {
                    sh "mkdir ${servicePath}"
                    writeFile encoding: 'utf-8', file: "$servicePath/test.txt", text: "this is $it"
                }

                sh "cp target/*.jar ${servicePath}"

//                withEnv(['JENKINS_NODE_COOKIE=dontkillme']) {
                    sh "java -jar ${servicePath}/gateway-0.0.1-SNAPSHOT.jar &"
//                }
            }
        }
    }
}
