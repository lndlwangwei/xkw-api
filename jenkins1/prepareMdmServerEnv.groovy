def basePath = '/data/test'
// nginx path
def nginxBasePath = "$basePath/nginx"

node('28test') {
    def scriptHome = "$WORKSPACE/jenkins1"

    stage('prepare nginx') {
        git 'https://github.com/lndlwangwei/xkw-api'

        if (!fileExists(basePath)) {
            sh "mkdir $basePath"
        }
        if (!fileExists(nginxBasePath)) {
            sh "mkdir $nginxBasePath"
        }

        sh "cp -r $scriptHome/mdmServerEnv/nginx/conf $nginxBasePath"
    }

}