def nodes = ['dev':['temp', 'gateway1'], '28test':['temp', 'gateway1']]
def serviceBasePath = '/data/service/gateways'
def buildProjectName = "xkw-api-gateway-build"

nodes.entrySet().each {entry ->
    def env = entry.key
    def services = entry.value

    node(env) {
        stage('deploy') {
            dir('target') {
                deleteDir()
            }

            copyArtifacts(projectName: "${buildProjectName}")

            services.each { service ->
                def serviceIndex = service.equals("temp") ? 9 : Integer.parseInt(service.substring(service.length() - 1))
                def profile = service.equals("temp") ? 'temp' : "node$serviceIndex"

//                try {
//                    // 将服务从eureka server中主动下线
//                    sh "curl localhost:807$serviceIndex/offline"
//                    sh "sleep 2m"
//                } catch (Exception e) {
//
//                }

                def servicePath = "$serviceBasePath/$service"
                if (!fileExists(servicePath)) {
                    sh "mkdir ${servicePath}"
                }
                sh "cp target/*.jar ${servicePath}"

                withEnv(['JENKINS_NODE_COOKIE=dontkillme']) {
                    sh "java -jar ${servicePath}/gateway-0.0.1-SNAPSHOT.jar --spring.profiles.active=$profile &"
                }
            }
        }

//        stage('stop temp server') {
//            echo 'stopping temp server'
//            // 如果服务中有临时服务，需要停掉临时服务
//            if (services.contains('temp')) {
//                sh "curl localhost:8079/offline"
//                sh "sleep 2m"
//
//                // 确保临时服务没有被访问后，在停掉临时服务
//                sh "http://localhost:8079/actuator/shutdown"
//            }
//        }
    }
}
