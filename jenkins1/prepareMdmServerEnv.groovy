def basePath = '/data/test'
// nginx config
def nginxBasePath = "$basePath/nginx"
def nginxLogPath = "$basePath/log"
def nginxDockerImageName = "xuekewang/jetty-9:v1"
def nginxContainerName = "docker-nginx"

node('28test') {
    git 'https://github.com/lndlwangwei/xkw-api'
    def scriptHome = "$WORKSPACE/jenkins1"

    stage('prepare nginx') {


        if (!fileExists(basePath)) {
            sh "mkdir $basePath"
        }
        if (!fileExists(nginxBasePath)) {
            sh "mkdir $nginxBasePath"
        }

        // 复制nginx配置文件
        sh "cp -r $scriptHome/mdmServerEnv/nginx/conf $nginxBasePath"

        // 创建nginx日志目录
        if (!fileExists(nginxLogPath)) {
            sh "mkdir $nginxLogPath"
        }

//        sh "docker pull $nginxDockerImageName"

        sh "docker stop $nginxContainerName"
        def status = sh(script: "docker rm $nginxContainerName", returnStatus: true)
        echo "status: $status"

        sh "docker run -d -p 9080:9080 -v $nginxLogPath:/var/log/nginx -v $nginxBasePath/conf:/etc/nginx/ --name=$nginxContainerName xuekewang/jetty-9:v1"
    }

//    stage('prepare jetty') {
//
//    }
}