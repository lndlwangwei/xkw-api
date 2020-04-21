def nodeName = currentBuild.projectName.find(/\(([^\)]+)\)/, {group -> group[1]})
def projectName = currentBuild.projectName.split('\\(')[0];
def allEnvProps = [
    'xkw-gateway': [
        buildProjectName: 'xkw-gateway-build(159test)',
        appDir: '/data/apps/xkw-gateway',
        artifact: 'gateway/target/gateway-0.0.1-SNAPSHOT.jar',
        artifactName: 'gateway-0.0.1-SNAPSHOT.jar',
        scriptPath: 'jenkins/scripts/*',
        scriptLocalDir: '/data/jenkins/xkw-gateway/scripts',
        env: 'product'
    ],
    'xkw-gateway-admin': [
         buildProjectName: 'xkw-gateway-build(159test)',
         appDir: '/data/apps/xkw-gateway-admin',
         scriptPath: 'jenkins/scripts/*',
         scriptLocalDir: '/data/jenkins/xkw-gateway/scripts',
         artifact: 'gateway-admin/target/gateway-admin-0.0.1-SNAPSHOT.jar',
         artifactName: 'gateway-admin-0.0.1-SNAPSHOT.jar',
         env: 'product'
    ]
]

def env = allEnvProps[projectName]

node(nodeName) {
    copyArtifacts(projectName: "${env.buildProjectName}")
//    copyArtifacts(projectName: "${env.buildScriptsProjectName}")

    stage('prepare appDir') {
        if (!fileExists("${env.appDir}")) {
            sh "sudo mkdir -p ${env.appDir}"
            sh "sudo chown -R xkwx.xkwx ${env.appDir}"
        }
    }

    stage('prepare scripts') {
        if (!fileExists(env.scriptLocalDir)) {
            sh "mkdir -p ${env.scriptLocalDir}"
        }
        sh "cp -r ${env.scriptPath} ${env.scriptLocalDir}"
        sh "chmod +x ${env.scriptLocalDir}/*.sh"
    }

    if (env.libDirInServer != null) {
        stage('prepare aspectjweaver jar') {
            if (!fileExists(env.libDirInServer)) {
                sh "sudo mkdir -p ${env.libDirInServer}"
                sh "sudo chown -R xkwx.xkwx ${env.libDirInServer}"
            }
            if (!fileExists("${env.libDirInServer}/${env.aspectjweaverJarName}")) {
                sh "${env.scriptLocalDir}/downloadAgentJar.sh ${env.libDirInServer}"
            }
        }
    }

    stage('backup old artifact') {
        if (fileExists("${env.appDir}/${env.artifactName}")) {
            sh "rm -rf ${env.appDir}.bak/*"

            if (!fileExists("${env.appDir}.bak")) {
                sh "mkdir -p ${env.appDir}.bak"
            }
            sh "cp ${env.appDir}/${env.artifactName} ${env.appDir}.bak"
        }
    }

    stage('stop server') {
        sh "${env.scriptLocalDir}/stopJarServer.sh ${env.appDir}"
    }

    stage('prepare artifacts') {
        sh "rm -rf ${env.appDir}/*"
        sh "cp ${env.artifact} ${env.appDir}/"
    }

    stage('deploy') {
        def agentJar = ''
        if (env.libDirInServer != null) {
            agentJar = "${env.libDirInServer}/${env.aspectjweaverJarName}"
        }
        withEnv(['JENKINS_NODE_COOKIE=dontkillme']) {
            sh "${env.scriptLocalDir}/startJarServer.sh ${env.appDir} ${env.artifactName} ${env.env} ${agentJar} &"
        }
    }
}