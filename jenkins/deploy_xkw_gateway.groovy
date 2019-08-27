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

                // result = sh(script: "<shell command>", returnStdout: true).trim()
                // result = sh(script: "<shell command>", returnStatus: true).trim()
                // 先检查服务是否是启动的，如果是，就下线并关闭服务，如果不是，就什么也不做
                def portStatus = sh(script: "echo -n '\n'|telnet localhost 807$serviceIndex|grep Connected|wc -l", returnStdout: true).trim()
                echo "port status: $portStatus"
                if (portStatus == "1") {
                    // 将服务从eureka server中主动下线
                    sh "curl localhost:807$serviceIndex/offline"
// todo                    sh "sleep 1m"
                    // 确保下线后，再停掉服务
                    sh "curl -X POST localhost:807$serviceIndex/actuator/shutdown"
                }

                def servicePath = "$serviceBasePath/$service"
                if (!fileExists(servicePath)) {
                    sh "mkdir ${servicePath}"
                }
                sh "cp gateway/target/*.jar ${servicePath}"

                withEnv(['JENKINS_NODE_COOKIE=dontkillme']) {
                    sh "java -jar ${servicePath}/gateway-0.0.1-SNAPSHOT.jar --spring.profiles.active=$profile &"
                }
            }
        }

//        stage('stop temp server') {
//            echo 'stopping temp server'
//
//// todo            sh "sleep 1m"
//            // 如果服务中有临时服务，需要停掉临时服务
//            if (services.contains('temp')) {
//
//                sh "curl localhost:8079/offline"
//// todo                sh "sleep 1m"
//                // 确保临时服务没有被访问后，在停掉临时服务
//                sh "curl -X POST localhost:8079/actuator/shutdown"
//            }
//        }
    }
}
