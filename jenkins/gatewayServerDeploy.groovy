def nodeName = currentBuild.projectName.find(/\(([^\)]+)\)/, {group -> group[1]})
def projectName = currentBuild.projectName.split('\\(')[0];
def allEnvProps = [
    'rbm-pilot-console': [
        buildProjectName: 'rbm-project-build(159test,pilotrun)',
        buildScriptsProjectName: 'rbm-build-scripts(159test)',
        appDir: '/data/apps/rbm_server',
        artifact: 'console-webapp/target/xkw-rbm-console-webapp-1.0-SNAPSHOT.jar',
        artifactName: 'xkw-rbm-console-webapp-1.0-SNAPSHOT.jar',
        scriptPath: 'jenkins/scripts/*',
        scriptLocalDir: '/data/jenkins/rbm/scripts',
        libDirInJenkins: 'jenkins/lib',
        libDirInServer: '/data/lib',
        aspectjweaverJarName: 'aspectjweaver-1.9.5.jar',
        env: 'pilotrun',
    ],
    'rbm-pilot-api': [
         buildProjectName: 'rbm-project-build(159test,pilotrun)',
         buildScriptsProjectName: 'rbm-build-scripts(159test)',
         scriptPath: 'jenkins/scripts/*',
         scriptLocalDir: '/data/jenkins/rbm/scripts',
         env: 'pilotrun',
         appDir: '/data/apps/rbs_server',
         artifact: 'api-webapp/target/xkw-rbm-api-webapp-1.0-SNAPSHOT.jar',
         artifactName: 'xkw-rbm-api-webapp-1.0-SNAPSHOT.jar',
         libDirInJenkins: 'jenkins/lib',
         libDirInServer: '/data/lib',
         aspectjweaverJarName: 'aspectjweaver-1.9.5.jar',
    ],
    'rbm-pilot-eureka-server': [
            buildProjectName: 'rbm-project-build(159test,pilotrun)',
            buildScriptsProjectName: 'rbm-build-scripts(159test)',
            appDir: '/data/apps/eureka_server',
            artifact: 'eureka-server/target/xkw-rbm-eureka-server-1.0-SNAPSHOT.jar',
            artifactName: 'xkw-rbm-eureka-server-1.0-SNAPSHOT.jar',
            scriptPath: 'jenkins/scripts/*',
            scriptLocalDir: '/data/jenkins/rbm/scripts',
            env: 'pilotrun',
    ],
    'rbm-pilot-filehandler': [
        buildProjectName: 'rbm-project-build(159test,pilotrun)',
        buildScriptsProjectName: 'rbm-build-scripts(159test)',
        appDir: '/data/apps/fh_pilot_server',
        artifact: 'file-handler-webapp/target/xkw-rbm-file-handler-webapp-1.0-SNAPSHOT.jar',
        artifactName: 'xkw-rbm-file-handler-webapp-1.0-SNAPSHOT.jar',
        scriptPath: 'jenkins/scripts/*',
        scriptLocalDir: '/data/jenkins/rbm/scripts',
        libDirInJenkins: 'jenkins/lib',
        libDirInServer: '/data/lib',
        aspectjweaverJarName: 'aspectjweaver-1.9.5.jar',
        env: 'pilotrun',
    ],
    'rbm-pilot-gateway': [
        buildProjectName: 'rbm-project-build(159test,pilotrun)',
        buildScriptsProjectName: 'rbm-build-scripts(159test)',
        appDir: '/data/apps/rbm-gateway',
        artifact: 'rbm-gateway/target/rbm-gateway-1.0-SNAPSHOT.jar',
        artifactName: 'rbm-gateway-1.0-SNAPSHOT.jar',
        scriptPath: 'jenkins/scripts/*',
        scriptLocalDir: '/data/jenkins/rbm/scripts',
        env: 'pilotrun',
    ],
    'rbm-prod-console': [
        buildProjectName: 'rbm-project-build(159test,product)',
        buildScriptsProjectName: 'rbm-build-scripts(159test)',
        appDir: '/data/apps/rbm_server',
        artifact: 'console-webapp/target/xkw-rbm-console-webapp-1.0-SNAPSHOT.jar',
        artifactName: 'xkw-rbm-console-webapp-1.0-SNAPSHOT.jar',
        scriptPath: 'jenkins/scripts/*',
        scriptLocalDir: '/data/jenkins/rbm/scripts',
        libDirInJenkins: 'jenkins/lib',
        libDirInServer: '/data/lib',
        aspectjweaverJarName: 'aspectjweaver-1.9.5.jar',
        env: 'product'    
    ],
    'rbm-prod-api': [
        buildProjectName: 'rbm-project-build(159test,product)',
        buildScriptsProjectName: 'rbm-build-scripts(159test)',
        scriptPath: 'jenkins/scripts/*',
        scriptLocalDir: '/data/jenkins/rbm/scripts',
        
        env: 'product',
        appDir: '/data/apps/rbs_server',
        artifact: 'api-webapp/target/xkw-rbm-api-webapp-1.0-SNAPSHOT.jar',
        artifactName: 'xkw-rbm-api-webapp-1.0-SNAPSHOT.jar',
        libDirInJenkins: 'jenkins/lib',
        libDirInServer: '/data/lib',
        aspectjweaverJarName: 'aspectjweaver-1.9.5.jar',
    ],
    'rbm-prod-eureka-server': [
        buildProjectName: 'rbm-project-build(159test,product)',
        buildScriptsProjectName: 'rbm-build-scripts(159test)',
        appDir: '/data/apps/eureka_server',
        artifact: 'eureka-server/target/xkw-rbm-eureka-server-1.0-SNAPSHOT.jar',
        artifactName: 'xkw-rbm-eureka-server-1.0-SNAPSHOT.jar',
        scriptPath: 'jenkins/scripts/*',
        scriptLocalDir: '/data/jenkins/rbm/scripts',
        env: 'product',
    ],
    'rbm-prod-filehandler': [
        buildProjectName: 'rbm-project-build(159test,product)',
        buildScriptsProjectName: 'rbm-build-scripts(159test)',
        appDir: '/data/apps/fh_server',
        artifact: 'file-handler-webapp/target/xkw-rbm-file-handler-webapp-1.0-SNAPSHOT.jar',
        artifactName: 'xkw-rbm-file-handler-webapp-1.0-SNAPSHOT.jar',
        scriptPath: 'jenkins/scripts/*',
        scriptLocalDir: '/data/jenkins/rbm/scripts',
        libDirInJenkins: 'jenkins/lib',
        libDirInServer: '/data/lib',
        aspectjweaverJarName: 'aspectjweaver-1.9.5.jar',
        env: 'product',
    ],
    'rbm-prod-gateway': [
        buildProjectName: 'rbm-project-build(159test,product)',
        buildScriptsProjectName: 'rbm-build-scripts(159test)',
        appDir: '/data/apps/rbm-gateway',
        artifact: 'rbm-gateway/target/rbm-gateway-1.0-SNAPSHOT.jar',
        artifactName: 'rbm-gateway-1.0-SNAPSHOT.jar',
        scriptPath: 'jenkins/scripts/*',
        scriptLocalDir: '/data/jenkins/rbm/scripts',
        env: 'product',
    ],
    'rbm-test-console': [
        buildProjectName: 'rbm-project-build(159test,test)',
        buildScriptsProjectName: 'rbm-build-scripts(159test)',
        appDir: '/data/apps/rbm_server',
        artifact: 'console-webapp/target/xkw-rbm-console-webapp-1.0-SNAPSHOT.jar',
        artifactName: 'xkw-rbm-console-webapp-1.0-SNAPSHOT.jar',
        scriptPath: 'jenkins/scripts/*',
        scriptLocalDir: '/data/jenkins/rbm/scripts',
        libDirInJenkins: 'jenkins/lib',
        libDirInServer: '/data/lib',
        aspectjweaverJarName: 'aspectjweaver-1.9.5.jar',
        env: 'test',
    ],
    'rbm-test-api': [
        buildProjectName: 'rbm-project-build(159test,test)',
        buildScriptsProjectName: 'rbm-build-scripts(159test)',
        scriptPath: 'jenkins/scripts/*',
        scriptLocalDir: '/data/jenkins/rbm/scripts',
        env: 'test',
        appDir: '/data/apps/rbs_server',
        artifact: 'api-webapp/target/xkw-rbm-api-webapp-1.0-SNAPSHOT.jar',
        artifactName: 'xkw-rbm-api-webapp-1.0-SNAPSHOT.jar',
        libDirInJenkins: 'jenkins/lib',
        libDirInServer: '/data/lib',
        aspectjweaverJarName: 'aspectjweaver-1.9.5.jar',
    ],
    'rbm-test-eureka-server': [
        buildProjectName: 'rbm-project-build(159test,test)',
        buildScriptsProjectName: 'rbm-build-scripts(159test)',
        appDir: '/data/apps/eureka_server',
        artifact: 'eureka-server/target/xkw-rbm-eureka-server-1.0-SNAPSHOT.jar',
        artifactName: 'xkw-rbm-eureka-server-1.0-SNAPSHOT.jar',
        scriptPath: 'jenkins/scripts/*',
        scriptLocalDir: '/data/jenkins/rbm/scripts',
        env: 'test',
    ],
    'rbm-test-filehandler': [
        buildProjectName: 'rbm-project-build(159test,test)',
        buildScriptsProjectName: 'rbm-build-scripts(159test)',
        appDir: '/data/apps/rbm-file-handler_server',
        artifact: 'file-handler-webapp/target/xkw-rbm-file-handler-webapp-1.0-SNAPSHOT.jar',
        artifactName: 'xkw-rbm-file-handler-webapp-1.0-SNAPSHOT.jar',
        scriptPath: 'jenkins/scripts/*',
        scriptLocalDir: '/data/jenkins/rbm/scripts',
        env: 'test',
        libDirInJenkins: 'jenkins/lib',
        libDirInServer: '/data/lib',
        aspectjweaverJarName: 'aspectjweaver-1.9.5.jar',
    ],
    'rbm-test-gateway': [
        buildProjectName: 'rbm-project-build(159test,test)',
        buildScriptsProjectName: 'rbm-build-scripts(159test)',
        appDir: '/data/apps/rbm-gateway',
        artifact: 'rbm-gateway/target/rbm-gateway-1.0-SNAPSHOT.jar',
        artifactName: 'rbm-gateway-1.0-SNAPSHOT.jar',
        scriptPath: 'jenkins/scripts/*',
        scriptLocalDir: '/data/jenkins/rbm/scripts',
        env: 'test',
    ]
]

def envProp = allEnvProps[projectName]

node(nodeName) {
    checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[cancelProcessOnExternalsFail: true, credentialsId: 'jcyy_svn_credential', depthOption: 'infinity', ignoreExternalsOption: true, local: '.', remote: 'http://114.55.64.147/svn/rbm/trunk/jenkins/deploy-scripts']], quietOperation: true, workspaceUpdater: [$class: 'UpdateUpdater']])
    def deploy = load 'springbootServerSimpleDeploy.groovy'
    deploy.deploy(envProp)
}