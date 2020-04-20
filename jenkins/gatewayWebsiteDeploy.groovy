def nodeName = currentBuild.projectName.find(/\(([^\)]+)\)/, {group -> group[1]})

dev env = [
        buildProjectName: 'xkw-gateway-build(159test)',
        appDir: '/data/apps/xkw_gateway_client',
        artifactName: 'gateway-admin-client/gateway_client.zip',
]

def envProp = allEnvProps[projectName]

node(nodeName) {
    stage('deploy') {
        copyArtifacts(projectName: "${env.buildProjectName}")

        sh "rm -rf ${env.appDir}/*"
        sh "cp ${env.artifactName} ${env.appDir}"
        sh "unzip ${env.appDir}/${env.artifactName} -d ${env.appDir}"
        sh "rm -f ${env.appDir}/${env.artifactName}"
    }
}