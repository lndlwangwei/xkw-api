def nodeName = currentBuild.projectName.find(/\(([^\)]+)\)/, {group -> group[1]})

def env = [
        buildProjectName: 'xkw-gateway-build(159test)',
        appDir: '/data/apps/xkw-gateway-client',
        artifactName: 'gateway-admin-client/gateway_client.zip',
]

node(nodeName) {
    stage('deploy') {
        copyArtifacts(projectName: "${env.buildProjectName}")

        sh "rm -rf ${env.appDir}/*"
        sh "cp ${env.artifactName} ${env.appDir}"
        sh "unzip ${env.appDir}/${env.artifactName} -d ${env.appDir}"
        sh "rm -f ${env.appDir}/${env.artifactName}"
    }
}