def basePath = '/data/test'
// nginx path
def nginxBasePath = "$basePath/nginx"

node('28test') {
    stage('prepare nginx') {
        if (!fileExists(basePath)) {
            sh "mkdir $basePath"
        }
        if (!fileExists(nginxBasePath)) {
            sh "mkdir $nginxBasePath"
        }

        sh "cp -r $WORKSPACE/mdmServerEnv/nginx/conf $nginxBasePath"
    }

}